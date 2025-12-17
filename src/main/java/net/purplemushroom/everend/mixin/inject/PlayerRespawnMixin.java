package net.purplemushroom.everend.mixin.inject;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.purplemushroom.everend.content.blocks.EndAnchorBlock;
import net.purplemushroom.everend.registry.EEItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Player.class)
public class PlayerRespawnMixin {
    @Inject(at = @At("HEAD"), method = "findRespawnPositionAndUseSpawnBlock", cancellable = true)
    private static void respawnAtEndAnchor(ServerLevel serverLevel, BlockPos blockPos, float pPlayerOrientation, boolean isRespawnForced, boolean respawnAfterWinning, CallbackInfoReturnable<Optional<Vec3>> cir) {
        BlockState blockstate = serverLevel.getBlockState(blockPos);
        Block block = blockstate.getBlock();
        if (block instanceof EndAnchorBlock && (isRespawnForced || blockstate.getValue(EndAnchorBlock.CHARGE) > 0) && EndAnchorBlock.canSetSpawn(serverLevel)) {
            Optional<Vec3> playerPosOptional = EndAnchorBlock.findStandUpPosition(EntityType.PLAYER, serverLevel, blockPos);
            if (!isRespawnForced && !respawnAfterWinning && playerPosOptional.isPresent()) {
                serverLevel.setBlock(blockPos, blockstate.setValue(EndAnchorBlock.CHARGE, blockstate.getValue(EndAnchorBlock.CHARGE) - 1), 3);
            }
            cir.setReturnValue(playerPosOptional);
        }
    }
}