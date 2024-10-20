package net.purplemushroom.neverend.registry;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.purplemushroom.neverend.Neverend;
import net.purplemushroom.neverend.content.items.ShifterineItem;
import ru.timeconqueror.timecore.api.client.resource.StandardItemModelParents;
import ru.timeconqueror.timecore.api.registry.ItemRegister;
import ru.timeconqueror.timecore.api.registry.util.AutoRegistrable;

import java.util.function.Function;
import java.util.function.Supplier;

@AutoRegistrable.Entries("item")
public class NEItems {
    public static Item SHIFTERINE_CRYSTAL;

    @AutoRegistrable
    private static final ItemRegister ITEMS = new ItemRegister(Neverend.MODID);

    @AutoRegistrable.Init
    private static void register() {
        ITEMS.register("shifterine_crystal", ShifterineItem::new)
                .name("Shifterine Crystal")
                .defaultModel(Neverend.tl("item/shifterine_crystal"))
                .onCreativeTabBuilding(ItemRegister.CreativeTabAdder.tabBased(NECreativeTabs.ITEMS));
        registerItem("test", "Test", item -> new Item(baseProps().stacksTo(3).rarity(Rarity.EPIC)), StandardItemModelParents.HANDHELD);
    }

    /**
     * Registers a tool. The stats of which vary on the supplier's class and the args within its constructor.
     *
     * @param name             Registry name
     * @param enName           English name
     * @param toolItemSupplier Supplier for the ToolItem class
     */
    private static void registerHandheldItem(String name, String enName, Supplier<Item> toolItemSupplier) {
        ITEMS.register(name, toolItemSupplier)
                .model(StandardItemModelParents.HANDHELD)
                .name(enName)
                .onCreativeTabBuilding(ItemRegister.CreativeTabAdder.tabBased(NECreativeTabs.ITEMS));
    }

    private static void registerHandheldRodItem(String name, String enName, Supplier<Item> toolItemSupplier) {
        ITEMS.register(name, toolItemSupplier)
                .model(StandardItemModelParents.HANDHELD_ROD)
                .name(enName)
                .onCreativeTabBuilding(ItemRegister.CreativeTabAdder.tabBased(NECreativeTabs.ITEMS));
    }

    private static void registerItem(String name, String enName, Supplier<Item> itemSupplier) {
        ITEMS.register(name, itemSupplier)
                .model(StandardItemModelParents.DEFAULT)
                .name(enName)
                .onCreativeTabBuilding(ItemRegister.CreativeTabAdder.tabBased(NECreativeTabs.ITEMS));
    }

    private static void registerItem(String name, String enName, Supplier<Item> itemSupplier, String itemLoc) {
        ITEMS.register(name, itemSupplier)
                .model(Neverend.iml(itemLoc))
                .name(enName)
                .onCreativeTabBuilding(ItemRegister.CreativeTabAdder.tabBased(NECreativeTabs.ITEMS));
    }

    private static void registerItem(String name, String enName) {
        Function<Item.Properties, ? extends Item> itemFactory = Item::new;

        ITEMS.register(name, () -> itemFactory.apply(baseProps()))
                .model(StandardItemModelParents.DEFAULT)
                .name(enName)
                .onCreativeTabBuilding(ItemRegister.CreativeTabAdder.tabBased(NECreativeTabs.ITEMS));
    }

    private static void registerItem(String name, String enName, Function<Item.Properties, ? extends Item> itemFactory) {
        ITEMS.register(name, () -> itemFactory.apply(baseProps()))
                .model(StandardItemModelParents.DEFAULT)
                .name(enName)
                .onCreativeTabBuilding(ItemRegister.CreativeTabAdder.tabBased(NECreativeTabs.ITEMS));
    }

    private static void registerItem(String name, String enName, Function<Item.Properties, ? extends Item> itemFactory, StandardItemModelParents modelType) {
        ITEMS.register(name, () -> itemFactory.apply(baseProps()))
                .model(modelType)
                .name(enName)
                .onCreativeTabBuilding(ItemRegister.CreativeTabAdder.tabBased(NECreativeTabs.ITEMS));
    }

    //Could probably remove this, as tabs are now set by registry chain and not item properties
    private static Item.Properties baseProps() {
        return new Item.Properties();
    }
}
