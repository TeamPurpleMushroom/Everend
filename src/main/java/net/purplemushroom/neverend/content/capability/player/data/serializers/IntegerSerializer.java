package net.purplemushroom.neverend.content.capability.player.data.serializers;

import net.minecraft.nbt.CompoundTag;
import net.purplemushroom.neverend.content.capability.player.data.RiftFishingData;
import org.jetbrains.annotations.NotNull;
import ru.timeconqueror.timecore.common.capability.property.serializer.IPropertySerializer;

public class IntegerSerializer implements IPropertySerializer<Integer> {
    public static final IPropertySerializer<Integer> INSTANCE = new IntegerSerializer();

    @Override
    public void serialize(@NotNull String key, Integer value, @NotNull CompoundTag compoundTag) {
        compoundTag.putInt(key, value);
    }

    @Override
    public Integer deserialize(@NotNull String key, @NotNull CompoundTag compoundTag) {
        return compoundTag.getInt(key);
    }
}
