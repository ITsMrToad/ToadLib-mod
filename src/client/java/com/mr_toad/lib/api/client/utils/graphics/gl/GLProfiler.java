package com.mr_toad.lib.api.client.utils.graphics.gl;

import com.mr_toad.lib.core.ToadLib;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL33;
import org.lwjgl.opengl.GL45;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class GLProfiler implements AutoCloseable {

    private static final int BUFFER_SIZE = 16;

    private final long[] cpuTimes = new long[BUFFER_SIZE];
    private final int[] queries = new int[BUFFER_SIZE];

    private long start;
    private int frame;
    private boolean running;

    private final String name;
    private final int target;
    private final Marker marker;

    /**
     * Parameter {@code target} accepts one of:
     * <br/>
     * {@link org.lwjgl.opengl.GL15#GL_SAMPLES_PASSED}
     * <br/>
     * {@link org.lwjgl.opengl.GL33#GL_TIME_ELAPSED}
     * <br/>
     * {@link org.lwjgl.opengl.GL33#GL_ANY_SAMPLES_PASSED}
     * <br/>
     * {@link org.lwjgl.opengl.GL30#GL_PRIMITIVES_GENERATED}
     * */
    public GLProfiler(String name, int target) {
        this.name = name;
        this.target = target;
        this.marker = MarkerFactory.getMarker("RenderProfiler[" + name + "]");
        GL45.glCreateQueries(target, this.queries);
    }

    public GLProfiler start() {
        if (this.running) {
            ToadLib.LOGGER.warn(this.marker, "Profiler '{}' is already started.", this.name);
            return this;
        }
        this.running = true;
        GL15.glBeginQuery(this.target, this.queries[this.frame]);
        this.start = System.nanoTime();
        return this;
    }

    @Override
    public void close() {
        if (!this.running) {
            ToadLib.LOGGER.warn(this.marker, "Profiler '{}' not started.", this.name);
            return;
        }
        GL15.glEndQuery(this.target);
        this.running = false;
        this.cpuTimes[this.frame] = System.nanoTime() - this.start;
        int read = (this.frame + 1) % BUFFER_SIZE;
        if (GL15.glGetQueryObjecti(this.queries[read], GL15.GL_QUERY_RESULT_AVAILABLE) != 0) {
            long value = GLU.getQueryResult(this.queries[read]);
            long cpu = this.cpuTimes[read];
            ToadLib.LOGGER.info(this.marker, "{} + {} | CPU Time: {}, GPU Statistics: {}({} in ms)", this.name, this.targetName(), cpu * 1.0E-6, value, value * 1.0E-6);
        }
        this.frame = (this.frame + 1) % BUFFER_SIZE;
    }

    public void destroy() {
        GL15.glDeleteQueries(this.queries);
    }

    public int[] getQueries() {
        return this.queries;
    }

    private String targetName() {
        return switch (this.target) {
            case GL33.GL_TIME_ELAPSED -> "TIME";
            case GL33.GL_ANY_SAMPLES_PASSED -> "ANY_SAMPLES_PASSED";
            case GL15.GL_SAMPLES_PASSED -> "SAMPLES_PASSED";
            case GL30.GL_PRIMITIVES_GENERATED -> "PRIMITIVES_GENERATED";
            default -> "0x" + Integer.toHexString(this.target);
        };
    }
}
