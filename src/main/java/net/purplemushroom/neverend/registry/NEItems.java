package net.purplemushroom.neverend.registry;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.purplemushroom.neverend.Neverend;
import net.purplemushroom.neverend.content.items.*;
import net.purplemushroom.neverend.content.items.gear.*;
import ru.timeconqueror.timecore.api.client.resource.StandardItemModelParents;
import ru.timeconqueror.timecore.api.registry.ItemRegister;
import ru.timeconqueror.timecore.api.registry.util.AutoRegistrable;

import java.util.function.Function;
import java.util.function.Supplier;

@AutoRegistrable.Entries("item")
public class NEItems {
    public static Item DULL_SHIFTERINE;
    public static Item SHIFTERINE_CRYSTAL;
    public static Item LUDUNITE_THING;
    public static Item DULL_ALDORES;
    public static Item ALDORES_THING;
    public static Item DRAGONBONE;
    public static Item ENCHANTMENT_CRYSTAL;
    public static Item ENDERIUM_DUST;

    public static Item SHIFTERINE_ROD;

    @AutoRegistrable
    private static final ItemRegister ITEMS = new ItemRegister(Neverend.MODID);

    @AutoRegistrable.Init
    private static void register() {
        register("dull_shifterine", "Dull Shifterine");
        register("shifterine_crystal", "Shifterine Crystal", () -> new NESpecialAbilityResource(new Item.Properties(), ShifterineItemAbility.INSTANCE));

        // TODO: stats
        register("shifterine_sword", "Shifterine Sword", () -> new NESword(Tiers.DIAMOND, 3, 1.0f, new Item.Properties(), ShifterineItemAbility.INSTANCE));
        register("shifterine_pickaxe", "Shifterine Pickaxe", () -> new NEPickaxe(Tiers.DIAMOND, 3, 1.0f, new Item.Properties(), ShifterineItemAbility.INSTANCE));
        register("shifterine_axe", "Shifterine Axe", () -> new NEAxe(Tiers.DIAMOND, 3, 1.0f, new Item.Properties(), ShifterineItemAbility.INSTANCE));
        register("shifterine_shovel", "Shifterine Shovel", () -> new NEShovel(Tiers.DIAMOND, 3, 1.0f, new Item.Properties(), ShifterineItemAbility.INSTANCE));
        register("shifterine_hoe", "Shifterine Hoe", () -> new NEHoe(Tiers.DIAMOND, 3, 1.0f, new Item.Properties(), ShifterineItemAbility.INSTANCE));
        register("shifterine_helmet", "Shifterine Helmet", () -> new NEArmor(ArmorMaterials.DIAMOND, ArmorItem.Type.HELMET, new Item.Properties(), ShifterineItemAbility.INSTANCE));
        register("shifterine_chestplate", "Shifterine Chestplate", () -> new NEArmor(ArmorMaterials.DIAMOND, ArmorItem.Type.CHESTPLATE, new Item.Properties(), ShifterineItemAbility.INSTANCE));
        register("shifterine_leggings", "Shifterine Leggings", () -> new NEArmor(ArmorMaterials.DIAMOND, ArmorItem.Type.LEGGINGS, new Item.Properties(), ShifterineItemAbility.INSTANCE));
        register("shifterine_boots", "Shifterine Boots", () -> new NEArmor(ArmorMaterials.DIAMOND, ArmorItem.Type.BOOTS, new Item.Properties(), ShifterineItemAbility.INSTANCE));


        register("ludunite_thing", "Ludunite Thing");

        register("ludunite_sword", "Ludunite Sword", () -> new NESword(Tiers.GOLD, 3, 1.0f, new Item.Properties(), LuduniteItemAbility.INSTANCE));
        register("ludunite_pickaxe", "Ludunite Pickaxe", () -> new NEPickaxe(Tiers.GOLD, 3, 1.0f, new Item.Properties(), LuduniteItemAbility.INSTANCE));
        register("ludunite_axe", "Ludunite Axe", () -> new NEAxe(Tiers.GOLD, 3, 1.0f, new Item.Properties(), LuduniteItemAbility.INSTANCE));
        register("ludunite_shovel", "Ludunite Shovel", () -> new NEShovel(Tiers.GOLD, 3, 1.0f, new Item.Properties(), LuduniteItemAbility.INSTANCE));
        register("ludunite_hoe", "Ludunite Hoe", () -> new NEHoe(Tiers.GOLD, 3, 1.0f, new Item.Properties(), LuduniteItemAbility.INSTANCE));
        register("ludunite_helmet", "Ludunite Helmet", () -> new NEArmor(ArmorMaterials.GOLD, ArmorItem.Type.HELMET, new Item.Properties(), LuduniteItemAbility.INSTANCE));
        register("ludunite_chestplate", "Ludunite Chestplate", () -> new NEArmor(ArmorMaterials.GOLD, ArmorItem.Type.CHESTPLATE, new Item.Properties(), LuduniteItemAbility.INSTANCE));
        register("ludunite_leggings", "Ludunite Leggings", () -> new NEArmor(ArmorMaterials.GOLD, ArmorItem.Type.LEGGINGS, new Item.Properties(), LuduniteItemAbility.INSTANCE));
        register("ludunite_boots", "Ludunite Boots", () -> new NEArmor(ArmorMaterials.GOLD, ArmorItem.Type.BOOTS, new Item.Properties(), LuduniteItemAbility.INSTANCE));

        register("dull_aldores", "Dull Aldores");
        register("aldores_thing", "Aldores Thing");

        //TODO: stats
        register("aldores_sword", "Aldores Sword", () -> new NESword(Tiers.DIAMOND, 3, 1.0f, new Item.Properties(), AldoresItemAbility.INSTANCE));
        register("aldores_pickaxe", "Aldores Pickaxe", () -> new NEPickaxe(Tiers.DIAMOND, 3, 1.0f, new Item.Properties(), AldoresItemAbility.INSTANCE));
        register("aldores_axe", "Aldores Axe", () -> new NEAxe(Tiers.DIAMOND, 3, 1.0f, new Item.Properties(), AldoresItemAbility.INSTANCE));
        register("aldores_shovel", "Aldores Shovel", () -> new NEShovel(Tiers.DIAMOND, 3, 1.0f, new Item.Properties(), AldoresItemAbility.INSTANCE));
        register("aldores_hoe", "Aldores Hoe", () -> new NEHoe(Tiers.DIAMOND, 3, 1.0f, new Item.Properties(), AldoresItemAbility.INSTANCE));
        register("aldores_helmet", "Aldores Helmet", () -> new NEArmor(ArmorMaterials.DIAMOND, ArmorItem.Type.HELMET, new Item.Properties(), AldoresItemAbility.INSTANCE));
        register("aldores_chestplate", "Aldores Chestplate", () -> new NEArmor(ArmorMaterials.DIAMOND, ArmorItem.Type.CHESTPLATE, new Item.Properties(), AldoresItemAbility.INSTANCE));
        register("aldores_leggings", "Aldores Leggings", () -> new NEArmor(ArmorMaterials.DIAMOND, ArmorItem.Type.LEGGINGS, new Item.Properties(), AldoresItemAbility.INSTANCE));
        register("aldores_boots", "Aldores Boots", () -> new NEArmor(ArmorMaterials.DIAMOND, ArmorItem.Type.BOOTS, new Item.Properties(), AldoresItemAbility.INSTANCE));

        register("dragonbone", "Dragonbone");
        register("enchantment_crystal", "Enchantment Crystal");
        register("enderium_dust", "Enderium Dust");
        register("nullberry", "Nullberry", () -> new NullberryItem(baseProps().food(new FoodProperties.Builder().alwaysEat().nutrition(2).fast().saturationMod(1.0F).build())));
        registerHandheldRod("shifterine_rod", "Shifterine Rod", ShifterineRodItem::new);

        registerWithPresetModel("end_porter", "End Porter", () -> new EndPorterItem(baseProps().stacksTo(1).rarity(Rarity.EPIC)), StandardItemModelParents.HANDHELD);
    }

    /**
     * Registers a tool. The stats of which vary on the supplier's class and the args within its constructor.
     *
     * @param name             Registry name
     * @param enName           English name
     * @param toolItemSupplier Supplier for the ToolItem class
     */
    //TODO: consolidate registry with modified ItemRegisterChain and/or custom ItemRegister
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