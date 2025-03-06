package net.purplemushroom.everend.content.blocks.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.purplemushroom.everend.content.items.DragonboneItemAbility;
import net.purplemushroom.everend.content.items.INESpecialAbilityItem;
import net.purplemushroom.everend.content.items.LuduniteItemAbility;
import net.purplemushroom.everend.registry.EEBlockEntities;
import net.purplemushroom.everend.registry.EEItems;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class EndAltarBlockEntity extends BlockEntity {
    //TODO: make dust requirement for ludunium charge depend on the amount of durability used
    //TODO: remove debug ticking code when done
    private ItemStack placedItem = ItemStack.EMPTY;
    private int dustCount = 0;

    private static final HashMap<Item, AltarRecipe> recipes = new HashMap<>();

    public static void registerRecipes() {
        if (!recipes.isEmpty()) throw new IllegalStateException("Altar recipes have already be initialized!");
        recipes.put(EEItems.DULL_SHIFTERINE, new AltarRecipe(EEItems.SHIFTERINE_CRYSTAL, 2));
        recipes.put(EEItems.DULL_ALDORES, new AltarRecipe(EEItems.ALDORES_THING, 4));
    }

    public EndAltarBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(EEBlockEntities.END_ALTAR, pPos, pBlockState);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, EndAltarBlockEntity blockEntity) {
        blockEntity.tick();
    }

    public void tick() {
        System.out.println("Dust: " + dustCount);
    }

    public void addItem(ItemStack stack) {
        if (stack.getItem() != EEItems.ENDERIUM_DUST) {
            if (!placedItem.isEmpty()) {
                if (!ItemStack.isSameItemSameTags(stack, placedItem)) {
                    dropItem();
                }
            } else {
                placedItem = stack.copyWithCount(1);
                stack.shrink(1);
            }
        }

        if (!placedItem.isEmpty()) {
            int requiredDust = -1;
            if (recipes.containsKey(placedItem.getItem())) {
                requiredDust = recipes.get(placedItem.getItem()).dust;
            } else if (placedItem.getItem() instanceof INESpecialAbilityItem abilityItem &&
                    (abilityItem.getAbility() == LuduniteItemAbility.INSTANCE || abilityItem.getAbility() == DragonboneItemAbility.INSTANCE) &&
                    placedItem.getTag() != null &&
                    placedItem.getTag().getInt("Charge") < placedItem.getMaxDamage()) {
                requiredDust = 10;
            }
            if (requiredDust > 0) {
                if (dustCount < requiredDust && stack.getItem() == EEItems.ENDERIUM_DUST) {
                    stack.shrink(1);
                    dustCount++;
                }

                if (dustCount >= requiredDust) {
                    if (placedItem.getItem() instanceof INESpecialAbilityItem abilityItem &&
                            (abilityItem.getAbility() == LuduniteItemAbility.INSTANCE || abilityItem.getAbility() == DragonboneItemAbility.INSTANCE) &&
                            placedItem.getTag() != null &&
                            placedItem.getTag().getInt("Charge") < placedItem.getMaxDamage()) {
                        placedItem.getOrCreateTag().putInt("Charge", placedItem.getMaxDamage());
                    } else if (recipes.containsKey(placedItem.getItem())) {
                        placedItem = new ItemStack(recipes.get(placedItem.getItem()).result, placedItem.getCount());
                    } else {
                        throw new RuntimeException("Itemstack should not have recipe on altar, yet somehow it's using dust?");
                    }

                    dustCount -= requiredDust;
                }
            }
        }

        level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
    }

    public void dropItem() {
        if (!placedItem.isEmpty()) {
            BlockPos pos = this.worldPosition.above();
            ItemEntity entity = new ItemEntity(this.level, pos.getX(), pos.getY(), pos.getZ(), placedItem);
            entity.setDefaultPickUpDelay();
            this.level.addFreshEntity(entity);
            placedItem = ItemStack.EMPTY;
            level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
        }
    }

    public ItemStack getItem() {
        return placedItem;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = super.getUpdateTag();
        nbt.put("Item", placedItem.serializeNBT());
        nbt.putInt("Dust", dustCount);
        return nbt;
    }

    /*@Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        //placedItem = ItemStack.of(tag);
    }*/

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("Item", placedItem.serializeNBT());
        pTag.putInt("Dust", dustCount);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        placedItem = ItemStack.of((CompoundTag) pTag.get("Item"));
        dustCount = pTag.getInt("Dust");
    }

    private static class AltarRecipe {
        private Item result;
        private int dust;

        private AltarRecipe(Item result, int count) {
            this.result = result;
            this.dust = count;
        }
    }
}
