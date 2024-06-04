package com.mr_toad.lib.api.entity.entitydata;

import com.mr_toad.lib.api.entity.HybridAttackType;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;
import java.util.OptionalDouble;

@MethodsReturnNonnullByDefault
public class ToadlyEntityDataSerializers {

    public static final EntityDataSerializer<Double> DOUBLE = EntityDataSerializer.simple(FriendlyByteBuf::writeDouble, FriendlyByteBuf::readDouble);
    public static final EntityDataSerializer<HybridAttackType> HYBRID_ATTACK_TYPE = EntityDataSerializer.simpleEnum(HybridAttackType.class);
    public static final EntityDataSerializer<ResourceLocation> RESOURCE_LOCATION = EntityDataSerializer.simple(FriendlyByteBuf::writeResourceLocation, FriendlyByteBuf::readResourceLocation);
    public static final EntityDataSerializer<Optional<ResourceLocation>> OPTIONAL_RESOURCE_LOCATION = EntityDataSerializer.optional(FriendlyByteBuf::writeResourceLocation, FriendlyByteBuf::readResourceLocation);
    public static final EntityDataSerializer<OptionalDouble> OPTIONAL_UNSIGNED_DOUBLE = new EntityDataSerializer.ForValueType<>() {
        @Override
        public void write(FriendlyByteBuf buf, OptionalDouble optionalDouble) {
            buf.writeDouble(optionalDouble.orElse(-1.0D) + 1.0D);
        }

        @Override
        public OptionalDouble read(FriendlyByteBuf buf) {
            double i = buf.readDouble();
            return i == 0.0D ? OptionalDouble.empty() : OptionalDouble.of(i - 1.0D);
        }
    };

    static {
        EntityDataSerializers.registerSerializer(HYBRID_ATTACK_TYPE);
        EntityDataSerializers.registerSerializer(DOUBLE);
        EntityDataSerializers.registerSerializer(RESOURCE_LOCATION);
        EntityDataSerializers.registerSerializer(OPTIONAL_RESOURCE_LOCATION);
        EntityDataSerializers.registerSerializer(OPTIONAL_UNSIGNED_DOUBLE);
    }

}
