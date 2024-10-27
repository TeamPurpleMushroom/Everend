package net.purplemushroom.neverend.content.capability.player;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.util.LazyOptional;

public interface NEPlayerData {
    static LazyOptional<NEPlayerData> of(ServerPlayer player) {
        return NEPlayer.of(player)
                .lazyMap(playerData -> playerData);
    }
}
