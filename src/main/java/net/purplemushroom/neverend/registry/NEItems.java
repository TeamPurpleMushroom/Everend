package net.purplemushroom.neverend.registry;

import net.minecraft.world.item.Item;
import net.purplemushroom.neverend.Neverend;
import net.purplemushroom.neverend.content.items.ShifterineItem;
import ru.timeconqueror.timecore.api.registry.ItemRegister;
import ru.timeconqueror.timecore.api.registry.util.AutoRegistrable;

@AutoRegistrable.Entries("item")
public class NEItems {
    public static Item SHIFTERINE;

    @AutoRegistrable
    private static final ItemRegister ITEMS = new ItemRegister(Neverend.MODID);

    @AutoRegistrable.Init
    private static void register() {
        ITEMS.register("shifterine_crystal", ShifterineItem::new)
                .name("Shifterine Crystal")
                .onCreativeTabBuilding(ItemRegister.CreativeTabAdder.tabBased(NECreativeTabs.ITEMS));
    }
}
