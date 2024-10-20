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
    public static Item LUDUNITE_THING;
    public static Item ALDORES_THING;
    public static Item DRAGONBONE;
    public static Item ENCHANTMENT_CRYSTAL;

    @AutoRegistrable
    private static final ItemRegister ITEMS = new ItemRegister(Neverend.MODID);

    @AutoRegistrable.Init
    private static void register() {
        register("shifterine_crystal", "Shifterine Crystal", ShifterineItem::new);
        register("ludunite_thing", "Ludunite Thing");
        register("aldores_thing", "Aldores Thing");
        register("dragonbone", "Dragonbone");
        register("enchantment_crystal", "Enchantment Crystal");
        registerWithPresetModel("test", "Test", () -> new Item(baseProps().stacksTo(3).rarity(Rarity.EPIC)), StandardItemModelParents.HANDHELD);
    }

    /**
     * Registers a tool. The stats of which vary on the supplier's class and the args within its constructor.
     *
     * @param name             Registry name
     * @param enName           English name
     * @param toolItemSupplier Supplier for the ToolItem class
     */
    private static void registerHandheld(String name, String enName, Supplier<Item> toolItemSupplier) {
        ITEMS.register(name, toolItemSupplier)
                .model(StandardItemModelParents.HANDHELD, Neverend.tl("item/" + name))
                .name(enName)
                .onCreativeTabBuilding(ItemRegister.CreativeTabAdder.tabBased(NECreativeTabs.ITEMS));
    }

    private static void registerHandheldRod(String name, String enName, Supplier<Item> toolItemSupplier) {
        ITEMS.register(name, toolItemSupplier)
                .model(StandardItemModelParents.HANDHELD_ROD, Neverend.tl("item/" + name))
                .name(enName)
                .onCreativeTabBuilding(ItemRegister.CreativeTabAdder.tabBased(NECreativeTabs.ITEMS));
    }

    private static void register(String name, String enName, Function<Item.Properties, ? extends Item> itemFactory) {
        ITEMS.register(name, () -> itemFactory.apply(baseProps()))
                .defaultModel(Neverend.tl("item/" + name))
                .name(enName)
                .onCreativeTabBuilding(ItemRegister.CreativeTabAdder.tabBased(NECreativeTabs.ITEMS));
    }

    private static void register(String name, String enName, Supplier<Item> itemSupplier) {
        ITEMS.register(name, itemSupplier)
                .defaultModel(Neverend.tl("item/" + name))
                .name(enName)
                .onCreativeTabBuilding(ItemRegister.CreativeTabAdder.tabBased(NECreativeTabs.ITEMS));
    }

    private static void register(String name, String enName, Supplier<Item> itemSupplier, String itemLoc) {
        ITEMS.register(name, itemSupplier)
                .defaultModel(Neverend.tl("item/" + itemLoc))
                .name(enName)
                .onCreativeTabBuilding(ItemRegister.CreativeTabAdder.tabBased(NECreativeTabs.ITEMS));
    }

    private static void register(String name, String enName) {
        Function<Item.Properties, ? extends Item> itemFactory = Item::new;

        ITEMS.register(name, () -> itemFactory.apply(baseProps()))
                .defaultModel(Neverend.tl("item/" + name))
                .name(enName)
                .onCreativeTabBuilding(ItemRegister.CreativeTabAdder.tabBased(NECreativeTabs.ITEMS));
    }

    private static void registerWithCustomModel(String name, String enName, Function<Item.Properties, ? extends Item> itemFactory, String modelName) {
        ITEMS.register(name, () -> itemFactory.apply(baseProps()))
                .model(Neverend.iml(modelName))
                .name(enName)
                .onCreativeTabBuilding(ItemRegister.CreativeTabAdder.tabBased(NECreativeTabs.ITEMS));
    }

    private static void registerWithCustomModel(String name, String enName, Supplier<Item> itemSupplier, String modelName) {
        ITEMS.register(name, itemSupplier)
                .model(Neverend.iml(modelName))
                .name(enName)
                .onCreativeTabBuilding(ItemRegister.CreativeTabAdder.tabBased(NECreativeTabs.ITEMS));
    }

    private static void registerWithPresetModel(String name, String enName, Function<Item.Properties, ? extends Item> itemFactory, StandardItemModelParents modelType) {
        ITEMS.register(name, () -> itemFactory.apply(baseProps()))
                .model(modelType, Neverend.tl("item/" + name))
                .name(enName)
                .onCreativeTabBuilding(ItemRegister.CreativeTabAdder.tabBased(NECreativeTabs.ITEMS));
    }

    private static void registerWithPresetModel(String name, String enName, Supplier<Item> itemSupplier, StandardItemModelParents modelType) {
        ITEMS.register(name, itemSupplier)
                .model(modelType, Neverend.tl("item/" + name))
                .name(enName)
                .onCreativeTabBuilding(ItemRegister.CreativeTabAdder.tabBased(NECreativeTabs.ITEMS));
    }

    //Could probably remove this, as tabs are now set by registry chain and not item properties
    private static Item.Properties baseProps() {
        return new Item.Properties();
    }
}