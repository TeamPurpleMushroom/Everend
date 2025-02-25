package net.purplemushroom.neverend.client.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.purplemushroom.neverend.Neverend;
import net.purplemushroom.neverend.client.render.world.EndSpecialEffects;

@Mod.EventBusSubscriber(modid = Neverend.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class DimensionSpecialEffectsHandler {

    @SubscribeEvent
    public static void onEffectRegister(RegisterDimensionSpecialEffectsEvent event) {
        event.register(BuiltinDimensionTypes.END_EFFECTS, new EndSpecialEffects());
    }
}
