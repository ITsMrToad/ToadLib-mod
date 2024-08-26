package com.mr_toad.lib.api.db.storage;

import com.google.common.base.Objects;
import com.mr_toad.lib.core.ToadLib;
import com.mr_toad.lib.mtjava.collections.UniqueList;
import com.mr_toad.lib.mtjava.strings.func.ToStringFunction;
import net.minecraft.nbt.NbtIo;
import net.minecraft.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerLevel;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

public abstract class AbstractGameDataNbtStorage<L> {

    public static final String ERROR_CODE = "errorcode1";
    protected static final ExecutorService POOL = Util.ioPool();

    protected final UniqueList<L> values = new UniqueList<>();

    private final File dir;
    private final ToStringFunction<L> flow;

    public AbstractGameDataNbtStorage(String child, ToStringFunction<L> flow) {
        this.dir = new File(Minecraft.getInstance().gameDirectory, child);
        this.flow = flow;
    }

    protected abstract Optional<L> load(ServerLevel serverLevel, CompoundTag nbt);

    protected abstract void save(CompoundTag nbt, L value);

    public void load(ServerLevel serverLevel) {
        File[] files = this.getDir().listFiles();
        if (files == null || files.length == 0) {
            ToadLib.LOGGER.warn("Nothing to read from '{}'", this.getDir());
        } else {
            POOL.execute(() -> Arrays.stream(files).map(File::getPath).forEach(s -> {
                if (!s.endsWith(".nbt")) {
                    ToadLib.LOGGER.error("Unknown object: '{}' in '{}'", s, this.getDir());
                } else if(s.contains(ERROR_CODE)) {
                    ToadLib.LOGGER.error("Illegal name of object in {}", this.getDir());
                } else {
                    try {
                        CompoundTag nbt = NbtIo.readCompressed(new File(this.getDir(), "/" + s + ".mmnbt"));
                        this.load(serverLevel, nbt).ifPresentOrElse(this.values::add, () -> ToadLib.LOGGER.error("Loaded value is null!"));
                    } catch (IOException e) {
                        ToadLib.LOGGER.error("Cannot load '{}' from '{}'", s, this.getDir());
                    }
                }
            }));
        }
    }

    public final void save() {
        POOL.execute(() -> this.values.forEach(v -> {
            CompoundTag nbt = NbtUtils.addCurrentDataVersion(new CompoundTag());
            this.save(nbt, v);
            File file = this.getAbsolutePath(v);
            try {
                NbtIo.writeCompressed(nbt, file);
            } catch (IOException e) {
                ToadLib.LOGGER.error("Cannot parse '{}' to '{}'", file, this.getDir());
            }
        }));
    }

    public File getDir() {
        return this.dir;
    }

    public File getAbsolutePath(L v) {
        return new File(this.getDir(), "/" + this.getFlow(v) + ".nbt");
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
        return this.getDir().hashCode() - 3;
    }

    @Override
    public String toString() {
        return "MMNGD(" + this.getDir() + ")";
    }
