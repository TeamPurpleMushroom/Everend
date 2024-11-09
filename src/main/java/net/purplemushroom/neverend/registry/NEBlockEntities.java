package net.purplemushroom.neverend.registry;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.purplemushroom.neverend.Neverend;
import net.purplemushroom.neverend.client.render.tile.DeathObeliskBER;
import net.purplemushroom.neverend.content.blocks.tile.DeathObeliskBlockEntity;
import ru.timeconqueror.timecore.api.registry.BlockEntityRegister;
import ru.timeconqueror.timecore.api.registry.util.AutoRegistrable;

@AutoRegistrable.Entries("block_entity_type")
public class NEBlockEntities {
    public static BlockEntityType<DeathObeliskBlockEntity> DEATH_OBELISK;

    @AutoRegistrable
    private static final BlockEntityRegister REGISTER = new BlockEntityRegister(Neverend.MODID);

    @AutoRegistrable.Init
    private static void register() {
        REGISTER.registerSingleBound("death_obelisk", DeathObeliskBlockEntity::new, () -> NEBlocks.DEATH_OBELISK).regCustomRenderer(() -> DeathObeliskBER::new);
    }
}
