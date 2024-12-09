package net.purplemushroom.neverend.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import net.purplemushroom.neverend.config.enums.IsometricAngleSnap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.timeconqueror.timecore.api.common.config.Config;
import ru.timeconqueror.timecore.api.common.config.ConfigSection;
import ru.timeconqueror.timecore.api.common.config.ImprovedConfigBuilder;

public class NEClientConfig extends Config {
    public GuiCategory guiCategory;

    public NEClientConfig() {
        super(ModConfig.Type.CLIENT, "client", "Client Configuration");
    }

    @Override
    public @NotNull String getRelativePath() {
        return NEConfigs.resolveConfigPath(getKey());
    }

    /**
     * Sets up config entries and categories that operate on client
     */
    @Override
    public void setup(ImprovedConfigBuilder improvedConfigBuilder) {
        guiCategory = new GuiCategory("gui", "Gui Category");
        improvedConfigBuilder.addAndSetupSection(guiCategory);
    }

    public static class GuiCategory extends ConfigSection {
        private ForgeConfigSpec.BooleanValue enableIsometricCamera;
        private ForgeConfigSpec.BooleanValue lockIsometricPerspective;
        private ForgeConfigSpec.EnumValue<IsometricAngleSnap> isometricAngleSnap;

        public GuiCategory(@NotNull String key, @Nullable String comment) {
            super(key, comment);
        }

        /**
         * Register gui-based config options here
         */
        @Override
        public void setup(ImprovedConfigBuilder builder) {
            enableIsometricCamera = builder
                    .comment("If set to 'true', the camera will be locked to the isometric view.")
                    .define("Enable Isometric Camera: ", false);

            lockIsometricPerspective = builder
                    .comment("If set to 'true', the perspective will be locked for the isometric camera.")
                    .define("Lock Isometric Perspective: ", false);

            isometricAngleSnap = builder
                    .comment("Determines snap angle of the isometric camera view.")
                    .defineEnum("Isometric Camera Angle: ", IsometricAngleSnap.NORTH_WEST);
        }

        public boolean isIsometricFOVEnabled() {
            return enableIsometricCamera.get();
        }

        public void setIsometricFov(boolean enabled) {
            enableIsometricCamera.set(enabled);
        }

        public boolean isIsometricPerspectiveLocked() {
            return lockIsometricPerspective.get();
        }

        public void lockIsometricPerspective(boolean enabled) {
            lockIsometricPerspective.set(enabled);
        }

        public IsometricAngleSnap getIsometricAngleSnap() {
            return isometricAngleSnap.get();
        }

        public void setIsometricAngleSnap(IsometricAngleSnap isometricAngleSnap) {
            this.isometricAngleSnap.set(isometricAngleSnap);
        }
    }
}
