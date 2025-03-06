package net.purplemushroom.everend.capability.player.data.serializers;

import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;
import ru.timeconqueror.timecore.common.capability.property.serializer.IPropertySerializer;

public class BooleanSerializer implements IPropertySerializer<Boolean> {
    public static final IPropertySerializer<Boolean> INSTANCE = new BooleanSerializer();

    @Override
    public void serialize(@NotNull String key, Boolean value, @NotNull CompoundTag compoundTag) {
        compoundTag.putBoolean(key, value);
    }

    @Override
    public Boolean deserialize(@NotNull String key, @NotNull CompoundTag compoundTag) {
        return compoundTag.getBoolean(key);
    }
}
