package net.purplemushroom.everend.content.blocks.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.purplemushroom.everend.Everend;
import net.purplemushroom.everend.registry.EEBlockEntities;
import net.purplemushroom.everend.util.EntityUtil;

import java.util.List;

public class GazeCrystalBlockEntity extends BlockEntity {
    public GazeCrystalBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(EEBlockEntities.GAZE_CRYSTAL, pPos, pBlockState);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, GazeCrystalBlockEntity blockEntity) {
        List<ServerPlayer> entityList = level.getEntitiesOfClass(ServerPlayer.class, new AABB(pos).inflate(128));
        for (ServerPlayer player : entityList) {
            if (EntityUtil.isLookingAt(player, pos)) {
                Everend.LOGGER.info("HE'S LOOKING RIGHT AT ME HELP!!!!!!!!!!!!!!!!!!!!");
            }
        }
    }
}
