package net.purplemushroom.neverend.capability.player.data.serializers;

import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;
import ru.timeconqueror.timecore.common.capability.property.serializer.IPropertySerializer;

public class DoubleSerializer implements IPropertySerializer<Double> {
    public static final IPropertySerializer<Double> INSTANCE = new DoubleSerializer();

    @Override
    public void serialize(@NotNull String key, Double value, @NotNull CompoundTag compoundTag) {
        compoundTag.putDouble(key, value);
    }

    @Override
    public Double deserialize(@NotNull String key, @NotNull CompoundTag compoundTag) {
        return compoundTag.getDouble(key);
    }
}
