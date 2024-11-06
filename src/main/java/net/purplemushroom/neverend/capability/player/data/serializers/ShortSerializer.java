package net.purplemushroom.neverend.capability.player.data.serializers;

import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;
import ru.timeconqueror.timecore.common.capability.property.serializer.IPropertySerializer;

public class ShortSerializer implements IPropertySerializer<Short> {
    public static final IPropertySerializer<Short> INSTANCE = new ShortSerializer();

    @Override
    public void serialize(@NotNull String key, Short value, @NotNull CompoundTag compoundTag) {
        compoundTag.putInt(key, value);
    }

    @Override
    public Short deserialize(@NotNull String key, @NotNull CompoundTag compoundTag) {
        return compoundTag.getShort(key);
    }
}