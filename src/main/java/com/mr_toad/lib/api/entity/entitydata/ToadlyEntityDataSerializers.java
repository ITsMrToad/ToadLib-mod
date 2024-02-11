package com.mr_toad.lib.api.entity.entitydata;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;

public class ToadlyEntityDataSerializers {

    public static final EntityDataSerializer<Double> DOUBLE = EntityDataSerializer.simple(FriendlyByteBuf::writeDouble, FriendlyByteBuf::readDouble);
    
}
