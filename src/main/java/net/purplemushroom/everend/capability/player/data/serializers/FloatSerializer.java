package net.purplemushroom.everend.capability.player.data.serializers;

import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;
import ru.timeconqueror.timecore.common.capability.property.serializer.IPropertySerializer;

public class FloatSerializer implements IPropertySerializer<Float> {
    public static final IPropertySerializer<Float> INSTANCE = new FloatSerializer();

    @Override
    public void serialize(@NotNull String key, Float value, @NotNull CompoundTag compoundTag) {
        compoundTag.putFloat(key, value);
    }

    @Override
    public Float deserialize(@NotNull String key, @NotNull CompoundTag compoundTag) {
        return compoundTag.getFloat(key);
    }
}
