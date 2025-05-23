package net.purplemushroom.everend;

import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.purplemushroom.everend.client.event.EEOverlayHandler;
import net.purplemushroom.everend.client.registry.EESoundRegistry;
import net.purplemushroom.everend.content.blocks.tile.EndAltarBlockEntity;
import net.purplemushroom.everend.registry.EEEffects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.timeconqueror.timecore.api.TimeCoreAPI;
import ru.timeconqueror.timecore.api.client.resource.location.BlockModelLocation;
import ru.timeconqueror.timecore.api.client.resource.location.ItemModelLocation;
import ru.timeconqueror.timecore.api.client.resource.location.TextureLocation;

@Mod(Everend.MODID)
public class Everend {
    public static final String MODID = "everend";

    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public final Everend INSTANCE;

    public Everend() {
        INSTANCE = this;
        TimeCoreAPI.setup(this);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        EEEffects.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        EndAltarBlockEntity.registerRecipes();
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        //MinecraftForge.EVENT_BUS.register(KeyInputEventHandler.class);
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
