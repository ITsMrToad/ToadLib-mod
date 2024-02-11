package com.mr_toad.lib.api.util.time;

import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanList;
import it.unimi.dsi.fastutil.booleans.BooleanLists;
import it.unimi.dsi.fastutil.booleans.BooleanSet;
import it.unimi.dsi.fastutil.booleans.BooleanSets;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class Disposable {

    private boolean canUse = true;
    private boolean canOff;

    @Nullable
    public final BooleanCollection exceptions;
    public final String serialName;

    public static final Logger LOGGER = LogUtils.getLogger();
    public static final Marker MARKER = MarkerFactory.getMarker("Disposables");

    private Disposable(@Nullable BooleanCollection exceptions, String serialName) {
        this.exceptions = exceptions;
        this.serialName = serialName;
    }

    public static Disposable createWithSet(String name, boolean[] booleans) {
        return new Disposable(BooleanSet.of(booleans), name);
    }

    public static Disposable createWithList(String name, boolean[] booleans) {
        return new Disposable(BooleanList.of(booleans), name);
    }

    public static Disposable createEmptyList(String name) {
        return new Disposable(BooleanLists.emptyList(), name);
    }

    public static Disposable createEmptySet(String name) {
        return new Disposable(BooleanSets.emptySet(), name);
    }

    public static Disposable createNull(String name) {
        return new Disposable(null, name);
    }

    public boolean canUse() {
        return this.canUse;
    }

    public void setUsed() {
        this.check();
        LOGGER.info(MARKER, "{} Set off", this.serialName);
        if (this.canOff()) {
            this.canUse = false;
        }
    }

    public boolean canOff() {
        return this.canOff;
    }

    public void check() {
        if (this.exceptions != null && !this.exceptions.isEmpty()) {
            for (boolean b : this.exceptions) {
                if (!b) {
                    this.canOff = true;
                    break;
                } else {
                    LOGGER.info(MARKER, "Exception used! {} Cannot set off", this.serialName);
                    this.canOff = false;
                }
            }
        }
    }
}
