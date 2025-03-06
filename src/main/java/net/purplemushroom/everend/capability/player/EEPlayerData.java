package net.purplemushroom.everend.capability.player;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.util.LazyOptional;

public interface EEPlayerData {
    static LazyOptional<EEPlayerData> of(ServerPlayer player) {
        return EEPlayer.of(player)
                .lazyMap(playerData -> playerData);
    }
}
