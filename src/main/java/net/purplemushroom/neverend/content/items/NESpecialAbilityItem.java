package net.purplemushroom.neverend.content.items;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class NESpecialAbilityItem extends Item implements INESpecialAbilityItem {
    private final NEItemAbility ability;

    public NESpecialAbilityItem(Properties pProperties, NEItemAbility ability) {
        super(pProperties);
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
