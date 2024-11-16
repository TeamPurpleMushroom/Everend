package net.purplemushroom.neverend.config;

import net.minecraftforge.fml.config.ModConfig;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.timeconqueror.timecore.api.common.config.Config;
import ru.timeconqueror.timecore.api.common.config.ImprovedConfigBuilder;

public class NEClientConfig extends Config {
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

    }
}
