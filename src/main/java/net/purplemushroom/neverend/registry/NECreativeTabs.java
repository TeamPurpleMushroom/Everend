package net.purplemushroom.neverend.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.purplemushroom.neverend.Neverend;
import ru.timeconqueror.timecore.api.registry.SimpleVanillaRegister;
import ru.timeconqueror.timecore.api.registry.util.AutoRegistrable;

public class NECreativeTabs {
    public static final ResourceKey<CreativeModeTab> BLOCKS = ResourceKey.create(Registries.CREATIVE_MODE_TAB, Neverend.rl("blocks"));

    @AutoRegistrable
    private static final SimpleVanillaRegister<CreativeModeTab> TABS = new SimpleVanillaRegister<>(Registries.CREATIVE_MODE_TAB, Neverend.MODID);

    @AutoRegistrable.Init
    private static void setup() {
        TABS.register(BLOCKS.location().getPath(), () -> CreativeModeTab.builder()
                .title(Component.translatable("itemGroup." + Neverend.MODID))
                .icon(() -> new ItemStack(NEBlocks.PALE_END_STONE))
                .build());
    }
}