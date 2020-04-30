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

    public static int fossilChance;
    public static int icebergChance;
    public static int dungeonChance;

    public static Boolean oceanRuin;
    public static Boolean oceanMonument;
    public static Boolean shipWreck;
    public static Boolean mineShaft;
    public static Boolean stronghold;
    public static Boolean treasure;
    public static Boolean swampHut;
    public static Boolean woodlandMansion;
    public static Boolean pyramid;
    public static Boolean pillagerOutpost;

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
        oceanRuin = CLIENT.oceanRuin.get();
        oceanMonument = CLIENT.oceanMonument.get();
        shipWreck = CLIENT.shipWreck.get();
        mineShaft = CLIENT.mineShaft.get();
        stronghold = CLIENT.stronghold.get();
        treasure = CLIENT.treasure.get();
        swampHut = CLIENT.swampHut.get();
        woodlandMansion = CLIENT.woodlandMansion.get();
        pyramid = CLIENT.pyramid.get();
        pillagerOutpost = CLIENT.pillagerOutpost.get();
        fossilChance = CLIENT.fossilChance.get();
        icebergChance = CLIENT.icebergChance.get();
        dungeonChance = CLIENT.dungeonChance.get();
    }



}
