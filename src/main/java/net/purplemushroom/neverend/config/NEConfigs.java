package net.purplemushroom.neverend.config;

import net.purplemushroom.neverend.Neverend;
import ru.timeconqueror.timecore.api.registry.ConfigRegister;
import ru.timeconqueror.timecore.api.registry.util.AutoRegistrable;

public class NEConfigs {
    public static final NECommonConfig COMMON = new NECommonConfig();
    public static final NEClientConfig CLIENT = new NEClientConfig();

    @AutoRegistrable
    private static final ConfigRegister CONFIG_REGISTER = new ConfigRegister(Neverend.MODID);

    @AutoRegistrable.Init
    private static void register() {
        CONFIG_REGISTER.register(COMMON);
        CONFIG_REGISTER.register(CLIENT);
    }

    public static String resolveConfigPath(String fileName) {
        return Neverend.MODID + "/" + fileName + ".toml";
    }
}
