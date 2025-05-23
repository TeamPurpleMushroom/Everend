package net.purplemushroom.everend.client;

import net.minecraft.sounds.Music;
import net.purplemushroom.everend.util.EverendBossInfo;

import java.util.HashMap;
import java.util.UUID;

public class BossInfoHandler {
    public static final HashMap<UUID, EverendBossInfo<?>> info = new HashMap<>();

    public static Music getBossMusic() {
        for (EverendBossInfo<?> bossInfo : info.values()) {
            if (bossInfo.getMusicTrack() != null) return bossInfo.getMusicTrack();
        }
        return null;
    }
}
