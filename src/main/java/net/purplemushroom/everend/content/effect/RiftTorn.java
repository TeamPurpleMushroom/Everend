package net.purplemushroom.everend.content.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.purplemushroom.everend.util.BitUtil;

import java.util.UUID;

public class RiftTorn extends MobEffect {
    public RiftTorn() {
        super(MobEffectCategory.HARMFUL, BitUtil.rgbToInt(7, 105, 92));
        addAttributeModifier(Attributes.MAX_HEALTH, "0BFDF211-4CFF-400D-AEE3-C94E4C4ECF80", -2.0f, AttributeModifier.Operation.ADDITION);
    }
}
