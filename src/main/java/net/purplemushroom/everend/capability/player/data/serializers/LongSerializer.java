package net.purplemushroom.everend.capability.player.data.serializers;

import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;
import ru.timeconqueror.timecore.common.capability.property.serializer.IPropertySerializer;

public class LongSerializer implements IPropertySerializer<Long> {
    public static final IPropertySerializer<Long> INSTANCE = new LongSerializer();

    @Override
    public void serialize(@NotNull String key, Long value, @NotNull CompoundTag compoundTag) {
        compoundTag.putLong(key, value);
    }

    @Override
    public Long deserialize(@NotNull String key, @NotNull CompoundTag compoundTag) {
        return compoundTag.getLong(key);
    }
}