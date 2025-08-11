package com.mr_toad.lib.api.config.entry;

import com.google.common.annotations.Beta;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mr_toad.lib.api.config.entry.type.ConfigEntryTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@Beta
@OnlyIn(Dist.CLIENT)
public class BoolEntry extends ConfigEntry<Boolean, BoolEntry> {

    private boolean setAfterFirstLoad = false;
    private boolean setTo = false;
    
    public BoolEntry(String name, Boolean defaultValue) {
        super(name, defaultValue, Codec.BOOL, ConfigEntryTypes.BOOL);
    }

    @Override
    public void load(JsonElement element) {
        super.load(element);
        if (element.isJsonObject() && element.getAsJsonObject().has("flag")) {
            this.setAfterFirstLoad = true;
            this.setTo = element.getAsJsonObject().get("set_to").getAsBoolean();
            if (this.value != this.setTo) {
                this.value = this.setTo;
            }
        }
    }

    @Override
    public void save(JsonObject obj) {
        super.save(obj);
        if (this.setAfterFirstLoad) {
            obj.addProperty("flag", (byte) 0);
            obj.addProperty("set_to", this.setTo);
        }
    }

    public void resetAfterFirstLoad() {
        this.setAfterFirstLoad = true;
        this.setTo = this.defaultValue;
    }

    public void setAfterFirstLoad(boolean value) {
        this.setAfterFirstLoad = true;
        this.setTo = value;
    }
}


