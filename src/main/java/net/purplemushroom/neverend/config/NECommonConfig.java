package net.purplemushroom.neverend.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.timeconqueror.timecore.api.common.config.Config;
import ru.timeconqueror.timecore.api.common.config.ConfigSection;
import ru.timeconqueror.timecore.api.common.config.ImprovedConfigBuilder;

public class NECommonConfig extends Config {
    public ItemsCategory itemsCategory;

    public NECommonConfig() {
        super(ModConfig.Type.COMMON, "common", "Common Configuration");
    }

    @Override
    public @NotNull String getRelativePath() {
        return NEConfigs.resolveConfigPath(getKey());
    }

    /**
     * Sets up config entries and categories that operate on both client and server
     */
    @Override
    public void setup(ImprovedConfigBuilder builder) {
        itemsCategory = new ItemsCategory("items", "Items Category");
        builder.addAndSetupSection(itemsCategory);
    }

    public static class ItemsCategory extends ConfigSection {
        private ForgeConfigSpec.BooleanValue enableNullberry;

        public ItemsCategory(@NotNull String key, @Nullable String comment) {
            super(key, comment);
        }

        @Override
        public void setup(ImprovedConfigBuilder improvedConfigBuilder) {
            //this doesn't actually do anything, it's just an example
            enableNullberry = improvedConfigBuilder
                    .comment("If set to 'true', the Nullberry item will teleport player to their last stable block position.")
                    .define("Enable Nullberry: ", true);
        }
    }
}