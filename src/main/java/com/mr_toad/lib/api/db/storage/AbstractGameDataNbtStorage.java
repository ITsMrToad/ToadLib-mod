package com.mr_toad.lib.api.db.storage;

import com.google.common.base.Objects;
import com.mr_toad.lib.core.ToadLib;
import com.mr_toad.lib.mtjava.strings.func.ToStringFunction;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.TracingExecutor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("ResultOfMethodCallIgnored")
public abstract class AbstractGameDataNbtStorage<L> {

    public static final String ERROR_CODE = "errorcode1";
    protected static final TracingExecutor POOL = Util.ioPool();

    protected final List<L> values = Collections.synchronizedList(new ArrayList<>());

    private final File dir;
    private final ToStringFunction<L> flow;

    public AbstractGameDataNbtStorage(String child, ToStringFunction<L> flow) {
        this.dir = FabricLoader.getInstance().getGameDir().resolve("toadlib/database").resolve(child).toFile();
        this.flow = flow;
        if (!this.dir.exists()) {
            this.dir.mkdirs();
        }
    }

    protected abstract Optional<L> load(ServerLevel serverWorld, CompoundTag nbt);

    protected abstract void save(CompoundTag nbt, L value);

    public void load(ServerLevel serverWorld) {
        File[] files = this.dir.listFiles((d, name) -> name.endsWith(".nbt") && !name.contains(ERROR_CODE));
        if (files == null || files.length == 0) {
            ToadLib.LOGGER.warn("Nothing to read from '{}'", dir);
            return;
        }
        POOL.execute(() -> {
            for (File file : files) {
                try {
                    CompoundTag nbt = NbtIo.readCompressed(file.toPath(), NbtAccounter.unlimitedHeap());
                    Optional<L> value = load(serverWorld, nbt);
                    value.ifPresentOrElse(this.values::add, () -> ToadLib.LOGGER.error("Loaded value is null! File: {}", file));
                } catch (IOException e) {
                    ToadLib.LOGGER.error("Cannot load '{}' from '{}'", file, this.dir, e);
                }
            }
        });
    }

    public final void save() {
        POOL.execute(() -> {
            synchronized (this.values) {
                for (L v : this.values) {
                    CompoundTag nbt = new CompoundTag();
                    this.save(nbt, v);
                    File file = this.getAbsolutePath(v);
                    try {
                        if (!this.dir.exists()) {
                            this.dir.mkdirs();
                        }
                        NbtIo.writeCompressed(nbt, file.toPath());
                    } catch (IOException e) {
                        ToadLib.LOGGER.error("Cannot save '{}' to '{}'", file, dir, e);
                    }
                }
            }
        });
    }

    public File getDir() {
        return this.dir;
    }

    public File getAbsolutePath(L v) {
        return new File(this.dir, this.getFlow(v) + ".nbt");
    }

    protected String getFlow(L value) {
        return this.flow.applyAsString(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else {
            if (obj instanceof AbstractGameDataNbtStorage<?> storage) {
                return Objects.equal(this.getDir(), storage.getDir());
            }
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getDir());
    }

    @Override
    public String toString() {
        return "MMNGD(" + this.getDir() + ")";
    }

}
