package net.purplemushroom.neverend.content.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Map;

public class EnchantmentShrineBlock extends Block {
    public EnchantmentShrineBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack item = player.getMainHandItem();
        if (item.isEnchanted() && !(item.hasTag() && item.getTag().getBoolean("ShrineEnchanted"))) {
            // TODO: consume crystal
            if (!level.isClientSide()) {
                boolean enchanted = false;
                for (Map.Entry<Enchantment, Integer> enchantmentEntry : item.getAllEnchantments().entrySet()) {
                    Enchantment enchant = enchantmentEntry.getKey();
                    if (enchant.getMaxLevel() > 1) {
                        item.enchant(enchant, enchantmentEntry.getValue() + 1); // FIXME: duplicate enchant
                        enchanted = true;
                    }
                }
                if (enchanted) {
                    item.getOrCreateTag().putBoolean("ShrineEnchanted", true);
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.use(blockState, level, blockPos, player, hand, hitResult);
    }
}
