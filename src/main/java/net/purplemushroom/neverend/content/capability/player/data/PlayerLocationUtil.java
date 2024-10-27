package net.purplemushroom.neverend.content.capability.player.data;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.Heightmap;
import org.jetbrains.annotations.NotNull;
import ru.timeconqueror.timecore.common.capability.property.serializer.IPropertySerializer;

/**
 * @author Dizzlepop12 (Cornman)
 */
public class PlayerLocationUtil {
    public BlockPos getSolidGlobalPosition(ServerLevel serverLevel) {
        return serverLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, BlockPos.containing(serverLevel.getWorldBorder().getCenterX(), 0.0, serverLevel.getWorldBorder().getCenterZ()));
    }

    public static class BlockPositionSerializer implements IPropertySerializer<BlockPos> {
        public static final IPropertySerializer<BlockPos> INSTANCE = new BlockPositionSerializer();

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

    public static class ResourceLocationSerializer implements IPropertySerializer<ResourceLocation> {
        public static final IPropertySerializer<ResourceLocation> INSTANCE = new ResourceLocationSerializer();

        @Override
        public ResourceLocation deserialize(@NotNull String string, @NotNull CompoundTag compoundTag) {
            String key = compoundTag.getString(string);
            try {
                return ResourceLocation.read(new StringReader(key));
            } catch (CommandSyntaxException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void serialize(@NotNull String string, ResourceLocation levelResourceKey, @NotNull CompoundTag compoundTag) {
            compoundTag.putString(string, levelResourceKey.toString());
        }
    }
}
