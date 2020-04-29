package com.WhiskyTangoFox.ReForge.Config;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;


@Mod.EventBusSubscriber(modid = "reforge", bus = Mod.EventBusSubscriber.Bus.MOD)
public class ReforgeConfig {

    public static final ClientConfig CLIENT;
    public static final ForgeConfigSpec CLIENT_SPEC;
    static {
        final Pair<ClientConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
        CLIENT_SPEC = specPair.getRight();
        CLIENT = specPair.getLeft();
    }

    public static int minX;
    public static int maxX;
    public static int minZ;
    public static int maxZ;

    @SubscribeEvent
    public static void onModConfigEvent(final ModConfig.ModConfigEvent configEvent) {
        if (configEvent.getConfig().getSpec() == ReforgeConfig.CLIENT_SPEC) {
            bakeConfig();
        }
    }

    public static void bakeConfig() {
        minX = CLIENT.minX.get();
        maxX = CLIENT.maxX.get();
        minZ = CLIENT.minZ.get();
        maxZ = CLIENT.maxZ.get();
    }



}