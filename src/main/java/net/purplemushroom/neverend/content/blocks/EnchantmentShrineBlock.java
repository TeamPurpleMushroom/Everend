package net.purplemushroom.neverend.content.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.purplemushroom.neverend.content.items.INESpecialAbilityItem;
import net.purplemushroom.neverend.content.items.LuduniteItemAbility;
import net.purplemushroom.neverend.registry.NEItems;
import net.purplemushroom.neverend.util.InventoryUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class EnchantmentShrineBlock extends Block {
    public EnchantmentShrineBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        ItemStack item = player.getMainHandItem();
        CompoundTag tag = item.getTag();
        if (item.getItem() instanceof INESpecialAbilityItem abilityItem) {
            if (abilityItem.getAbility() == LuduniteItemAbility.INSTANCE) {
                tag.putInt("Charge", item.getMaxDamage());
            }
        }
        if (item.isEnchanted() && !(item.hasTag() && tag != null && tag.getBoolean("ShrineEnchanted"))) {
            if (InventoryUtil.consumeItem(player, NEItems.ENCHANTMENT_CRYSTAL)) {
                if (!level.isClientSide()) {
                    boolean enchanted = false;
                    Map<Enchantment, Integer> enchantments = item.getAllEnchantments();
                    for (Map.Entry<Enchantment, Integer> enchantmentEntry : enchantments.entrySet()) {
                        Enchantment enchant = enchantmentEntry.getKey();
                        if (enchant.getMaxLevel() > 1) {
                            enchantments.put(enchant, enchantmentEntry.getValue() + 1);
                            enchanted = true;
                        }
                    }
                    if (enchanted) {
                        EnchantmentHelper.setEnchantments(enchantments, item);
                        item.getOrCreateTag().putBoolean("ShrineEnchanted", true);
                        level.playSound(null, blockPos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 1.0F, level.random.nextFloat() * 0.1F + 0.9F);
                        return InteractionResult.sidedSuccess(level.isClientSide);
                    }
                }
            }
        }
        return super.use(blockState, level, blockPos, player, hand, hitResult);
    }
}
