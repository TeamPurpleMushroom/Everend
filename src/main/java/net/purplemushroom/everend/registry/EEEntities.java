package net.purplemushroom.everend.registry;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.purplemushroom.everend.Everend;
import net.purplemushroom.everend.content.entities.*;
import ru.timeconqueror.timecore.api.registry.EntityRegister;
import ru.timeconqueror.timecore.api.registry.util.AutoRegistrable;
import ru.timeconqueror.timecore.api.registry.util.Promised;

@AutoRegistrable.Entries("entity")
public class EEEntities {

    @AutoRegistrable
    private static final EntityRegister ENTITIES = new EntityRegister(Everend.MODID);

    public static final Promised<EntityType<FishingRift>> FISHING_RIFT_TYPE = ENTITIES.register(
            "fishing_rift",
            EntityType.Builder.of(FishingRift::new, MobCategory.MISC)
                    .sized(5.0f, 5.0f)).asPromised();

    public static final Promised<EntityType<VoidRift>> VOID_RIFT_TYPE = ENTITIES.register(
            "void_rift", EntityType.Builder.of(VoidRift::new, MobCategory.MISC)
                    .sized(5.0f, 5.0f)).asPromised();

    public static final Promised<EntityType<EnderLord>> ENDER_LORD_TYPE = ENTITIES.register(
            "ender_lord", EntityType.Builder.of(EnderLord::new, MobCategory.MONSTER)
                    .sized(0.7f, 2.9f)
                    .clientTrackingRange(8)).asPromised();

    public static final Promised<EntityType<RadiantEnergy>> RADIANT_ENERGY_TYPE = ENTITIES.register(
            "radiant_energy", EntityType.Builder.<RadiantEnergy>of(RadiantEnergy::new, MobCategory.MISC)
                    .sized(0.0f, 0.0f)).asPromised();

    public static final Promised<EntityType<Portal>> PORTAL_TYPE = ENTITIES.register(
            "portal", EntityType.Builder.<Portal>of(Portal::new, MobCategory.MISC)
                    .sized(0.8f, 2.0f)).asPromised();
}



