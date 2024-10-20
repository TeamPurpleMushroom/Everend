package net.purplemushroom.neverend;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.timeconqueror.timecore.api.TimeCoreAPI;
import ru.timeconqueror.timecore.api.client.resource.location.BlockModelLocation;
import ru.timeconqueror.timecore.api.client.resource.location.ItemModelLocation;
import ru.timeconqueror.timecore.api.client.resource.location.TextureLocation;

@Mod(Neverend.MODID)
public class Neverend {
    public static final String MODID = "neverend";

    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public final Neverend INSTANCE;

    public Neverend() {
        INSTANCE = this;
        TimeCoreAPI.setup(this);
    }

    /** TODO: Register config using TimeCore registry 
     * See {@link ru.timeconqueror.timecore.api.registry.ConfigRegister}
     */

    public static ResourceLocation rl(String path) {
        return new ResourceLocation(MODID, path);
    }

    public static TextureLocation tl(String path) {
        return new TextureLocation(MODID, path);
    }

    public static BlockModelLocation bml(String path) {
        return new BlockModelLocation(MODID, path);
    }

    public static ItemModelLocation iml(String path) {
        return new ItemModelLocation(MODID, path);
    }
}
