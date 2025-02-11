package net.purplemushroom.neverend.content.items.gear;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.*;
import net.purplemushroom.neverend.content.items.INESpecialAbilityItem;
import net.purplemushroom.neverend.content.items.NEItemAbility;

public class NEArmor extends ArmorItem implements INESpecialAbilityItem {
    private final NEItemAbility ability;

    public NEArmor(ArmorMaterial pMaterial, Type pType, Properties pProperties, NEItemAbility ability) {
        super(pMaterial, pType, pProperties);
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
