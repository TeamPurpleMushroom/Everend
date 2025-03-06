package net.purplemushroom.everend.registry;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.purplemushroom.everend.Everend;
import net.purplemushroom.everend.client.render.tile.DeathObeliskBER;
import net.purplemushroom.everend.client.render.tile.EndAltarBER;
import net.purplemushroom.everend.client.render.tile.EndAnchorBER;
import net.purplemushroom.everend.content.blocks.tile.DeathObeliskBlockEntity;
import net.purplemushroom.everend.content.blocks.tile.EndAltarBlockEntity;
import net.purplemushroom.everend.content.blocks.tile.EndAnchorBlockEntity;
import net.purplemushroom.everend.content.blocks.tile.GazeCrystalBlockEntity;
import ru.timeconqueror.timecore.api.registry.BlockEntityRegister;
import ru.timeconqueror.timecore.api.registry.util.AutoRegistrable;

@AutoRegistrable.Entries("block_entity_type")
public class EEBlockEntities {
    public static BlockEntityType<DeathObeliskBlockEntity> DEATH_OBELISK;
    public static BlockEntityType<EndAnchorBlockEntity> END_ANCHOR;
    public static BlockEntityType<GazeCrystalBlockEntity> GAZE_CRYSTAL;
    public static BlockEntityType<EndAltarBlockEntity> END_ALTAR;

    @AutoRegistrable
    private static final BlockEntityRegister REGISTER = new BlockEntityRegister(Everend.MODID);

    @AutoRegistrable.Init
    private static void register() {
        REGISTER.registerSingleBound("death_obelisk", DeathObeliskBlockEntity::new, () -> EEBlocks.DEATH_OBELISK).regCustomRenderer(() -> DeathObeliskBER::new);
        REGISTER.registerSingleBound("end_anchor", EndAnchorBlockEntity::new, () -> EEBlocks.END_ANCHOR).regCustomRenderer(() -> EndAnchorBER::new);
        REGISTER.registerSingleBound("gaze_crystal", GazeCrystalBlockEntity::new, () -> EEBlocks.GAZE_CRYSTAL);
        REGISTER.registerSingleBound("end_altar", EndAltarBlockEntity::new, () -> EEBlocks.END_ALTAR).regCustomRenderer(() -> EndAltarBER::new);
    }
}
