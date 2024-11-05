package net.purplemushroom.neverend.content.capability.player.data.serializers;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;
import ru.timeconqueror.timecore.common.capability.property.serializer.IPropertySerializer;
import ru.timeconqueror.timecore.common.capability.property.serializer.NullPropertySerializer;

public class BlockPositionSerializer implements IPropertySerializer<BlockPos> {
    public static final IPropertySerializer<BlockPos> INSTANCE = new BlockPositionSerializer();
    public static final IPropertySerializer<BlockPos> NULLABLE_INSTANCE = new NullPropertySerializer<>(INSTANCE);

    @Override
    public BlockPos deserialize(@NotNull String string, @NotNull CompoundTag compoundTag) {
        long packedPos = compoundTag.getLong(string);
        return BlockPos.of(packedPos);
    }

    @Override
    public void serialize(@NotNull String string, BlockPos blockPos, @NotNull CompoundTag compoundTag) {
        compoundTag.putLong(string, blockPos.asLong());
    }
}
