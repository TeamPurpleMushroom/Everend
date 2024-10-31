package net.purplemushroom.neverend.registry;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.purplemushroom.neverend.Neverend;
import net.purplemushroom.neverend.content.entities.Rift;
import ru.timeconqueror.timecore.api.registry.EntityRegister;
import ru.timeconqueror.timecore.api.registry.util.AutoRegistrable;
import ru.timeconqueror.timecore.api.registry.util.Promised;

@AutoRegistrable.Entries("entity")
public class NEEntities {

    @AutoRegistrable
    private static final EntityRegister ENTITIES = new EntityRegister(Neverend.MODID);

    public static final Promised<EntityType<Rift>> RIFT_TYPE = ENTITIES.register("rift", EntityType.Builder.of(Rift::new, MobCategory.UNDERGROUND_WATER_CREATURE)).asPromised();
}



