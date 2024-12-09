package net.purplemushroom.neverend.registry;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.purplemushroom.neverend.Neverend;
import net.purplemushroom.neverend.client.render.tile.DeathObeliskBER;
import net.purplemushroom.neverend.client.render.tile.EndAnchorBER;
import net.purplemushroom.neverend.content.blocks.tile.DeathObeliskBlockEntity;
import net.purplemushroom.neverend.content.blocks.tile.EndAnchorBlockEntity;
import net.purplemushroom.neverend.content.blocks.tile.GazeCrystalBlockEntity;
import ru.timeconqueror.timecore.api.registry.BlockEntityRegister;
import ru.timeconqueror.timecore.api.registry.util.AutoRegistrable;

@AutoRegistrable.Entries("block_entity_type")
public class NEBlockEntities {
    public static BlockEntityType<DeathObeliskBlockEntity> DEATH_OBELISK;
    public static BlockEntityType<EndAnchorBlockEntity> END_ANCHOR;
    public static BlockEntityType<GazeCrystalBlockEntity> GAZE_CRYSTAL;

    @AutoRegistrable
    private static final BlockEntityRegister REGISTER = new BlockEntityRegister(Neverend.MODID);

    @AutoRegistrable.Init
    private static void register() {
        REGISTER.registerSingleBound("death_obelisk", DeathObeliskBlockEntity::new, () -> NEBlocks.DEATH_OBELISK).regCustomRenderer(() -> DeathObeliskBER::new);
        REGISTER.registerSingleBound("end_anchor", EndAnchorBlockEntity::new, () -> NEBlocks.END_ANCHOR).regCustomRenderer(() -> EndAnchorBER::new);
        REGISTER.registerSingleBound("gaze_crystal", GazeCrystalBlockEntity::new, () -> NEBlocks.GAZE_CRYSTAL);
    }
}
