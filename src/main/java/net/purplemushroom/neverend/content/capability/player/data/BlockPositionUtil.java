package net.purplemushroom.neverend.content.capability.player.data;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;
import ru.timeconqueror.timecore.common.capability.property.serializer.IPropertySerializer;

public class BlockPositionUtil {
    public static class Serializer implements IPropertySerializer<BlockPos> {
        public static final IPropertySerializer<BlockPos> INSTANCE = new Serializer();

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
}
