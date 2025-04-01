package net.purplemushroom.everend.client.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.Music;

public class EEMusic {
    public static final Music MENU = new Music(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(EESoundRegistry.MUSIC_MENU), 20, 600, true);
    public static final Music ENDER_LORD = new Music(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(EESoundRegistry.MUSIC_ENDER_LORD), 0, 0, true);
}
