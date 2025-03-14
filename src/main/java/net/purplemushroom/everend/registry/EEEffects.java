package net.purplemushroom.everend.registry;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.purplemushroom.everend.Everend;
import net.purplemushroom.everend.content.effect.RiftTorn;

public class EEEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Everend.MODID);

    public static final RegistryObject<MobEffect> RIFT_TORN = EFFECTS.register("rift_torn", RiftTorn::new);

    public static void register(IEventBus bus) {
        EFFECTS.register(bus);
    }
}
