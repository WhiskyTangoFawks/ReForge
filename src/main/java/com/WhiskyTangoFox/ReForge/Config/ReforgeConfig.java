package com.WhiskyTangoFox.ReForge.Config;

import com.WhiskyTangoFox.ReForge.RetroGeneration.VanillaFeatureGenerator;
import net.minecraft.world.gen.feature.Feature;
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
    public static int maxYToSkipGen;

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
        //if (configEvent.getConfig().getSpec() == ReforgeConfig.CLIENT_SPEC) {
            bakeConfig();
        //}
    }

    public static void bakeConfig() {
        minX = CLIENT.minX.get();
        maxX = CLIENT.maxX.get();
        minZ = CLIENT.minZ.get();
        maxZ = CLIENT.maxZ.get();
        maxYToSkipGen = CLIENT.maxYToSkipGen.get();
        oceanRuin = CLIENT.oceanRuin.get();
        if (oceanRuin) {
            VanillaFeatureGenerator.structure.add(Feature.OCEAN_RUIN);
        }
        oceanMonument = CLIENT.oceanMonument.get();
        if (oceanMonument) {
            VanillaFeatureGenerator.structure.add(Feature.OCEAN_MONUMENT);
        }
        shipWreck = CLIENT.shipWreck.get();
        if (shipWreck) {
            VanillaFeatureGenerator.structure.add(Feature.SHIPWRECK);
        }
        mineShaft = CLIENT.mineShaft.get();
        if (mineShaft) {
            VanillaFeatureGenerator.structure.add(Feature.MINESHAFT);
        }
        stronghold = CLIENT.stronghold.get();
        if (stronghold) {
            VanillaFeatureGenerator.structure.add(Feature.STRONGHOLD);
        }
        treasure = CLIENT.treasure.get();
        if (treasure) {
            VanillaFeatureGenerator.structure.add(Feature.BURIED_TREASURE);
        }
        swampHut = CLIENT.swampHut.get();
        if (swampHut) {
            VanillaFeatureGenerator.structure.add(Feature.SWAMP_HUT);
        }
        woodlandMansion = CLIENT.woodlandMansion.get();
        if (woodlandMansion) {
            VanillaFeatureGenerator.structure.add(Feature.WOODLAND_MANSION);
        }
        pyramid = CLIENT.pyramid.get();
        if (pyramid) {
            VanillaFeatureGenerator.structure.add(Feature.DESERT_PYRAMID);
        }
        pillagerOutpost = CLIENT.pillagerOutpost.get();
        if (pillagerOutpost) {
            VanillaFeatureGenerator.structure.add(Feature.PILLAGER_OUTPOST);
        }
        fossilChance = CLIENT.fossilChance.get();
        //icebergChance = CLIENT.icebergChance.get();
        //dungeonChance = CLIENT.dungeonChance.get();
    }



}
