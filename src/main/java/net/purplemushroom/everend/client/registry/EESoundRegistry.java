package net.purplemushroom.everend.client.registry;

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
    public static SoundEvent MENU_MUSIC_EVENT;
    public static SoundEvent ENDER_LORD_MUSIC_EVENT;

    @AutoRegistrable
    private static final SoundRegister SOUNDS = new SoundRegister(Everend.MODID);

    /*public static final Promised<SoundEvent> MENU_MUSIC = SOUNDS.register("music/menu.ogg");
    public static final Promised<SoundEvent> ENDER_LORD_MUSIC = SOUNDS.register("music/ender_lord.ogg");*/

    @AutoRegistrable.Init
    public static void init() {
        SOUNDS.register("music/menu.ogg");
        SOUNDS.register("music/ender_lord.ogg");
    }
}
