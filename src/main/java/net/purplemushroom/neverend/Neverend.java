package net.purplemushroom.neverend;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.purplemushroom.neverend.client.event.KeyInputEventHandler;
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
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(KeyInputEventHandler.class);
    }

    private void enqueue(InterModEnqueueEvent event) { }

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
