package net.purplemushroom.neverend.content.blocks.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.purplemushroom.neverend.Neverend;
import net.purplemushroom.neverend.registry.NEBlockEntities;
import net.purplemushroom.neverend.util.EntityUtil;

import javax.annotation.Nullable;
import java.util.List;

public class GazeCrystalBlockEntity extends BlockEntity {
    public GazeCrystalBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(NEBlockEntities.GAZE_CRYSTAL, pPos, pBlockState);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, GazeCrystalBlockEntity blockEntity) {
        List<ServerPlayer> entityList = level.getEntitiesOfClass(ServerPlayer.class, new AABB(pos).inflate(128));
        for (ServerPlayer player : entityList) {
            if (EntityUtil.isLookingAt(player, pos)) {
                Neverend.LOGGER.info("HE'S LOOKING RIGHT AT ME HELP!!!!!!!!!!!!!!!!!!!!");
            }
        }
    }
}
