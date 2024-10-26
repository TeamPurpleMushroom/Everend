package net.purplemushroom.neverend.content.capability.player.data;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;
import ru.timeconqueror.timecore.common.capability.property.serializer.IPropertySerializer;
import ru.timeconqueror.timecore.common.capability.property.serializer.NullPropertySerializer;

public class BlockPosHandler {
    public static class Serializer implements IPropertySerializer<BlockPos> {
        public static final IPropertySerializer<BlockPos> INSTANCE = new Serializer();

        @Override
        public BlockPos deserialize(@NotNull String string, @NotNull CompoundTag compoundTag) {
            int[] array = compoundTag.getIntArray(string);
            return new BlockPos(array[0], array[1], array[3]);
        }

        @Override
        public void serialize(@NotNull String string, BlockPos blockPos, @NotNull CompoundTag compoundTag) {
            compoundTag.putIntArray(string, new int[] {
                    blockPos.getX(),
                    blockPos.getY(),
                    blockPos.getZ()
            });
        }
    }
}
