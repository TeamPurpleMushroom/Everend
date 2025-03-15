package net.purplemushroom.everend.content.effect;

import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.purplemushroom.everend.util.BitUtil;

import java.util.Collections;
import java.util.List;

public class RiftTornEffect extends MobEffect {
    public RiftTornEffect() {
        super(MobEffectCategory.HARMFUL, BitUtil.rgbToInt(7, 105, 92));
        addAttributeModifier(Attributes.MAX_HEALTH, "0BFDF211-4CFF-400D-AEE3-C94E4C4ECF80", -2.0f, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return Collections.emptyList();
    }

    @Override
    public void addAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
        if (pLivingEntity.getHealth() > pLivingEntity.getMaxHealth()) {
            pLivingEntity.setHealth(pLivingEntity.getMaxHealth());
        }
    }
}
