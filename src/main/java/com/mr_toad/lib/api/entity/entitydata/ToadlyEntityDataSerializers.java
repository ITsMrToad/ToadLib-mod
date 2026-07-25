package com.mr_toad.lib.api.entity.entitydata;

import com.mr_toad.lib.api.entity.HybridAttackType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.resources.Identifier;

import org.jetbrains.annotations.NotNull;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class ToadlyEntityDataSerializers {

    public static final EntityDataSerializer<@NotNull Double> DOUBLE = of(RegistryFriendlyByteBuf::writeDouble, RegistryFriendlyByteBuf::readDouble);
    public static final EntityDataSerializer<@NotNull HybridAttackType> HYBRID_ATTACK_TYPE = EntityDataSerializer.forValueType(HybridAttackType.PACKET_CODEC);
    public static final EntityDataSerializer<@NotNull Identifier> RESOURCE_LOCATION = of(RegistryFriendlyByteBuf::writeIdentifier, RegistryFriendlyByteBuf::readIdentifier);
    public static final EntityDataSerializer<@NotNull Optional<Identifier>> OPTIONAL_RESOURCE_LOCATION = ofOptional(RegistryFriendlyByteBuf::writeIdentifier, RegistryFriendlyByteBuf::readIdentifier);
    public static final EntityDataSerializer<@NotNull OptionalDouble> OPTIONAL_UNSIGNED_DOUBLE = EntityDataSerializer.forValueType(new StreamCodec<>() {
        @Override
        public OptionalDouble decode(RegistryFriendlyByteBuf buf) {
            double i = buf.readDouble();
            return i == 0.0D ? OptionalDouble.empty() : OptionalDouble.of(i - 1.0D);
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buf, OptionalDouble value) {
            buf.writeDouble(value.orElse(-1.0D) + 1.0D);
        }
    });

    public static<T> EntityDataSerializer<@NotNull Optional<T>> ofOptional(BiConsumer<RegistryFriendlyByteBuf, T> writer, Function<RegistryFriendlyByteBuf, T> reader) {
        return EntityDataSerializer.forValueType(new StreamCodec<>() {
            @Override
            public Optional<T> decode(RegistryFriendlyByteBuf buf) {
                return buf.readBoolean() ? Optional.of(reader.apply(buf)) : Optional.empty();
            }

            @Override
            public void encode(RegistryFriendlyByteBuf buf, Optional<T> value) {
                if (value.isPresent()) {
                    buf.writeBoolean(true);
                    writer.accept(buf, value.get());
                } else {
                    buf.writeBoolean(false);
                }
            }
        });
    }

    public static<T> EntityDataSerializer<@NotNull T> of(BiConsumer<RegistryFriendlyByteBuf, T> writer, Function<RegistryFriendlyByteBuf, T> reader) {
        return EntityDataSerializer.forValueType(new StreamCodec<>() {
            @Override
            public T decode(RegistryFriendlyByteBuf buf) {
                return reader.apply(buf);
            }

            @Override
            public void encode(RegistryFriendlyByteBuf buf, T value) {
                writer.accept(buf, value);
            }
        });
    }

    static {
        EntityDataSerializers.registerSerializer(HYBRID_ATTACK_TYPE);
        EntityDataSerializers.registerSerializer(DOUBLE);
        EntityDataSerializers.registerSerializer(RESOURCE_LOCATION);
        EntityDataSerializers.registerSerializer(OPTIONAL_RESOURCE_LOCATION);
        EntityDataSerializers.registerSerializer(OPTIONAL_UNSIGNED_DOUBLE);
    }
}
