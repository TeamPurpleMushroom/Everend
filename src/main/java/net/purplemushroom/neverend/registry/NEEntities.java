package net.purplemushroom.neverend.registry;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.purplemushroom.neverend.Neverend;
import net.purplemushroom.neverend.content.entities.Gordon;
import ru.timeconqueror.timecore.api.registry.EntityRegister;
import ru.timeconqueror.timecore.api.registry.util.AutoRegistrable;
import ru.timeconqueror.timecore.api.registry.util.Promised;

@AutoRegistrable.Entries("entity")
public class NEEntities {

    @AutoRegistrable
    private static final EntityRegister ENTITIES = new EntityRegister(Neverend.MODID);

    public static final Promised<EntityType<Gordon>> GORDON_TYPE = ENTITIES.register("gordon", EntityType.Builder.of(Gordon::new, MobCategory.UNDERGROUND_WATER_CREATURE)).asPromised();
}



