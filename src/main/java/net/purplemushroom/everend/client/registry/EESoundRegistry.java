package net.purplemushroom.everend.client.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.purplemushroom.everend.Everend;
import net.purplemushroom.everend.content.entities.*;
import ru.timeconqueror.timecore.api.registry.EntityRegister;
import ru.timeconqueror.timecore.api.registry.SoundRegister;
import ru.timeconqueror.timecore.api.registry.VanillaRegister;
import ru.timeconqueror.timecore.api.registry.util.AutoRegistrable;
import ru.timeconqueror.timecore.api.registry.util.Promised;

@AutoRegistrable.Entries("sound_event")
public class EESoundRegistry {
    //public static Holder<SoundEvent> MENU_MUSIC;

    public static SoundEvent MUSIC_MENU;
    public static SoundEvent MUSIC_ENDER_LORD;

    @AutoRegistrable
    private static final SoundRegister SOUNDS = new SoundRegister(Everend.MODID);

    @AutoRegistrable.Init
    public static void init() {
        SOUNDS.register("music_menu");
        SOUNDS.register("music_ender_lord");

        //MENU_MUSIC = BuiltInRegistries.SOUND_EVENT.wrapAsHolder(MENU_MUSIC_EVENT);
    }
}
