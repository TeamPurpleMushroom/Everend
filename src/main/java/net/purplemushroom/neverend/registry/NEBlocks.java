package net.purplemushroom.neverend.registry;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.purplemushroom.neverend.Neverend;
import net.purplemushroom.neverend.client.render.NEBlockStateResources;
import net.purplemushroom.neverend.content.blocks.DeathObeliskBlock;
import net.purplemushroom.neverend.content.blocks.EnchantmentShrineBlock;
import net.purplemushroom.neverend.content.blocks.tile.DeathObeliskBlockEntity;
import ru.timeconqueror.timecore.api.client.resource.BlockModels;
import ru.timeconqueror.timecore.api.registry.BlockRegister;
import ru.timeconqueror.timecore.api.registry.util.AutoRegistrable;
import ru.timeconqueror.timecore.api.registry.util.BlockPropsFactory;

@AutoRegistrable.Entries("block")
public class NEBlocks {
    public static Block PALE_END_STONE;
    public static Block SHIFTERINE_ORE;
    public static Block LUDUNITE_ORE;
    public static Block ALDORES_ORE;
    public static Block DRAGONBONE_ORE;
    public static Block ENCHANTMENT_CRYSTAL_ORE;
    public static Block ENCHANTMENT_SHRINE;
    public static Block DEATH_OBELISK;

    @AutoRegistrable
    private static final BlockRegister BLOCKS = new BlockRegister(Neverend.MODID);

    @AutoRegistrable.Init
    private static void register() {
        BLOCKS.register("pale_end_stone", () -> new Block(new BlockPropsFactory(() -> BlockBehaviour.Properties.copy(Blocks.END_STONE)
                        .strength(1.0F, 3.0F)
                        .lightLevel(value -> 5)
                        .requiresCorrectToolForDrops()).create()))
                .oneVarStateAndCubeAllModel()
                .defaultBlockItem(NECreativeTabs.BLOCKS)
                .name("Pale End Stone");
        BLOCKS.register("shifterine_ore", () -> new Block(new BlockPropsFactory(() -> BlockBehaviour.Properties.copy(Blocks.END_STONE)
                        .strength(2.0F, 6.0F)
                        .requiresCorrectToolForDrops()).create()))
                .oneVarStateAndCubeAllModel()
                .defaultBlockItem(NECreativeTabs.BLOCKS)
                .name("Shifterine Ore");
        BLOCKS.register("ludunite_ore", () -> new Block(new BlockPropsFactory(() -> BlockBehaviour.Properties.copy(Blocks.END_STONE)
                        .strength(2.0F, 6.0F)
                        .requiresCorrectToolForDrops()).create()))
                .oneVarStateAndCubeAllModel()
                .defaultBlockItem(NECreativeTabs.BLOCKS)
                .name("Ludunite Ore");
        BLOCKS.register("aldores_ore", () -> new Block(new BlockPropsFactory(() -> BlockBehaviour.Properties.copy(Blocks.END_STONE)
                        .strength(2.0F, 6.0F)
                        .requiresCorrectToolForDrops()).create()))
                .oneVarStateAndCubeAllModel()
                .defaultBlockItem(NECreativeTabs.BLOCKS)
                .name("Aldores Ore");
        BLOCKS.register("dragonbone_ore", () -> new Block(new BlockPropsFactory(() -> BlockBehaviour.Properties.copy(Blocks.END_STONE)
                        .strength(2.0F, 6.0F)
                        .requiresCorrectToolForDrops()).create()))
                .oneVarStateAndCubeAllModel()
                .defaultBlockItem(NECreativeTabs.BLOCKS)
                .name("Dragonbone Ore");
        BLOCKS.register("enchantment_crystal_ore", () -> new Block(new BlockPropsFactory(() -> BlockBehaviour.Properties.copy(Blocks.END_STONE)
                        .strength(2.0F, 6.0F)
                        .requiresCorrectToolForDrops()).create()))
                .oneVarStateAndCubeAllModel()
                .defaultBlockItem(NECreativeTabs.BLOCKS)
                .name("Enchantment Crystal Ore");
        BLOCKS.register("enchantment_shrine", () -> new EnchantmentShrineBlock(new BlockPropsFactory(() -> BlockBehaviour.Properties.copy(Blocks.END_STONE)
                        .strength(2.0F, 6.0F)
                        .requiresCorrectToolForDrops()).create()))
                .oneVarStateAndCubeAllModel()
                .defaultBlockItem(NECreativeTabs.BLOCKS)
                .name("Enchantment Shrine");
        BLOCKS.register("death_obelisk", () -> new DeathObeliskBlock(new BlockPropsFactory(() -> BlockBehaviour.Properties.copy(Blocks.END_STONE)
                        .strength(2.0F, 6.0F)
                        .lightLevel((level) -> 4)
                        .requiresCorrectToolForDrops()).create(), DeathObeliskBlockEntity::new))

                .renderLayer(() -> RenderTypeWrappers.TRANSLUCENT)
                .state(NEBlockStateResources.halfState(Neverend.bml("block/death_obelisk_bottom"), Neverend.bml("block/death_obelisk_top")))

                .model(Neverend.bml("block/death_obelisk_top"), () -> BlockModels.cubeColumnModel(
                        Neverend.tl("block/death_obelisk_bottom"),
                        Neverend.tl("block/death_obelisk_top_front")))

                .model(Neverend.bml("block/death_obelisk_bottom"), BlockModels.cubeColumnModel(
                        Neverend.tl("block/death_obelisk_bottom"),
                        Neverend.tl("block/death_obelisk_bottom_side")))
                .defaultBlockItem(NECreativeTabs.BLOCKS)
                .name("Death Obelisk");
    }

    public static class RenderTypeWrappers {
        public static final BlockRegister.RenderTypeWrapper CUTOUT = new BlockRegister.RenderTypeWrapper(RenderType.cutout());
        public static final BlockRegister.RenderTypeWrapper CUTOUT_MIPPED = new BlockRegister.RenderTypeWrapper(RenderType.cutoutMipped());
        public static final BlockRegister.RenderTypeWrapper TRANSLUCENT = new BlockRegister.RenderTypeWrapper(RenderType.translucent());
        public static final BlockRegister.RenderTypeWrapper TRANSLUCENT_NO_CRUMBLING = new BlockRegister.RenderTypeWrapper(RenderType.translucentNoCrumbling());
    }
}
