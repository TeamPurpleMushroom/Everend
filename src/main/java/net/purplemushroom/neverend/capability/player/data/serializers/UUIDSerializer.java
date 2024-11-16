package net.purplemushroom.neverend.capability.player.data.serializers;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import ru.timeconqueror.timecore.common.capability.property.serializer.IPropertySerializer;
import ru.timeconqueror.timecore.common.capability.property.serializer.NullPropertySerializer;

import java.util.UUID;

public class UUIDSerializer implements IPropertySerializer<UUID> {
    public static final IPropertySerializer<UUID> INSTANCE = new UUIDSerializer();
    public static final IPropertySerializer<UUID> NULLABLE_INSTANCE = new NullPropertySerializer<>(INSTANCE);

    @Override
    public void serialize(@NotNull String key, UUID value, @NotNull CompoundTag compoundTag) {
        compoundTag.putUUID(key, value);
    }

    @Override
    public UUID deserialize(@NotNull String key, @NotNull CompoundTag compoundTag) {
        return compoundTag.getUUID(key);
    }
}
