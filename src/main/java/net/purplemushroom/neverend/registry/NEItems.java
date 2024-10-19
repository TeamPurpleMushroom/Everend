package net.purplemushroom.neverend.registry;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.purplemushroom.neverend.Neverend;
import net.purplemushroom.neverend.content.items.ShifterineItem;
import ru.timeconqueror.timecore.api.registry.BlockRegister;
import ru.timeconqueror.timecore.api.registry.ItemRegister;
import ru.timeconqueror.timecore.api.registry.util.AutoRegistrable;
import ru.timeconqueror.timecore.api.registry.util.BlockPropsFactory;
import ru.timeconqueror.timecore.api.registry.util.ItemPropsFactory;

@AutoRegistrable.Entries("item")
public class NEItems {
    public static Item SHIFTERINE_TEST;

    @AutoRegistrable
    private static final ItemRegister ITEMS = new ItemRegister(Neverend.MODID);

    @AutoRegistrable.Init
    private static void register() {
        ITEMS.register("shifterine_test", ShifterineItem::new)
                .name("Shifterine Test")
                .onCreativeTabBuilding(ItemRegister.CreativeTabAdder.tabBased(NECreativeTabs.BLOCKS));
    }
}
