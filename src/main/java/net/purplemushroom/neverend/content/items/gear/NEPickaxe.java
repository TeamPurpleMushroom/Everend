package net.purplemushroom.neverend.content.items.gear;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.*;
import net.purplemushroom.neverend.content.items.INESpecialAbilityItem;
import net.purplemushroom.neverend.content.items.NEItemAbility;

public class NEPickaxe extends PickaxeItem implements INESpecialAbilityItem {
    private final NEItemAbility ability;

    public NEPickaxe(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties, NEItemAbility ability) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
        this.ability = ability;
    }


    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        ability.onDroppedTick(stack, entity);
        return super.onEntityItemUpdate(stack, entity);
    }

    @Override
    public NEItemAbility getAbility() {
        return ability;
    }
}
