package net.purplemushroom.everend.client.event;

import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.purplemushroom.everend.Everend;
import net.purplemushroom.everend.client.render.world.EndSpecialEffects;

@Mod.EventBusSubscriber(modid = Everend.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class DimensionSpecialEffectsHandler {

    @SubscribeEvent
    public static void onEffectRegister(RegisterDimensionSpecialEffectsEvent event) {
        event.register(BuiltinDimensionTypes.END_EFFECTS, new EndSpecialEffects());
    }
}
