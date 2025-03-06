package net.purplemushroom.everend.config;

import net.purplemushroom.everend.Everend;
import ru.timeconqueror.timecore.api.registry.ConfigRegister;
import ru.timeconqueror.timecore.api.registry.util.AutoRegistrable;

public class EEConfigs {
    public static final EECommonConfig COMMON = new EECommonConfig();
    public static final EEClientConfig CLIENT = new EEClientConfig();

    @AutoRegistrable
    private static final ConfigRegister CONFIG_REGISTER = new ConfigRegister(Everend.MODID);

    @AutoRegistrable.Init
    private static void register() {
        CONFIG_REGISTER.register(COMMON);
        CONFIG_REGISTER.register(CLIENT);
    }

    public static String resolveConfigPath(String fileName) {
        return Everend.MODID + "/" + fileName + ".toml";
    }
}
