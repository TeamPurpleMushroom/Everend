package net.purplemushroom.neverend.content.capability.player.data.serializers;

import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;
import ru.timeconqueror.timecore.common.capability.property.serializer.IPropertySerializer;

public class ByteSerializer implements IPropertySerializer<Byte> {
    public static final IPropertySerializer<Byte> INSTANCE = new ByteSerializer();

    @Override
    public void serialize(@NotNull String key, Byte value, @NotNull CompoundTag compoundTag) {
        compoundTag.putByte(key, value);
    }

    @Override
    public Byte deserialize(@NotNull String key, @NotNull CompoundTag compoundTag) {
        return compoundTag.getByte(key);
    }
}