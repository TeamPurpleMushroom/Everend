package net.purplemushroom.neverend.registry;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.purplemushroom.neverend.Neverend;
import net.purplemushroom.neverend.content.entities.FishingRift;
import net.purplemushroom.neverend.content.entities.VoidRift;
import ru.timeconqueror.timecore.api.registry.EntityRegister;
import ru.timeconqueror.timecore.api.registry.util.AutoRegistrable;
import ru.timeconqueror.timecore.api.registry.util.Promised;

@AutoRegistrable.Entries("entity")
public class NEEntities {

    @AutoRegistrable
    private static final EntityRegister ENTITIES = new EntityRegister(Neverend.MODID);

    public static final Promised<EntityType<FishingRift>> FISHING_RIFT_TYPE = ENTITIES.register(
            "fishing_rift",
            EntityType.Builder.of(FishingRift::new, MobCategory.MISC)
                    .sized(5.0f, 5.0f)).asPromised();

    public static final Promised<EntityType<VoidRift>> VOID_RIFT_TYPE = ENTITIES.register(
            "void_rift", EntityType.Builder.of(VoidRift::new, MobCategory.MISC)
                    .sized(5.0f, 5.0f)).asPromised();
}



