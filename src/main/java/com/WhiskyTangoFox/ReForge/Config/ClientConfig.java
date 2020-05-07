package com.WhiskyTangoFox.ReForge.Config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {

    public final ForgeConfigSpec.IntValue minX;
    public final ForgeConfigSpec.IntValue maxX;
    public final ForgeConfigSpec.IntValue minZ;
    public final ForgeConfigSpec.IntValue maxZ;
    public final ForgeConfigSpec.IntValue maxYToSkipGen;

    public final ForgeConfigSpec.BooleanValue oceanRuin;
    public final ForgeConfigSpec.BooleanValue oceanMonument;
    public final ForgeConfigSpec.BooleanValue shipWreck;
    public final ForgeConfigSpec.BooleanValue mineShaft;
    public final ForgeConfigSpec.BooleanValue stronghold;
    public final ForgeConfigSpec.BooleanValue treasure;
    public final ForgeConfigSpec.BooleanValue swampHut;
    public final ForgeConfigSpec.BooleanValue woodlandMansion;
    public final ForgeConfigSpec.BooleanValue pyramid;
    public final ForgeConfigSpec.BooleanValue pillagerOutpost;
    public final ForgeConfigSpec.IntValue fossilChance;
    //public final ForgeConfigSpec.IntValue icebergChance;
    //public final ForgeConfigSpec.IntValue dungeonChance;

    public ClientConfig(ForgeConfigSpec.Builder builder) {
        builder.push("ReGeneration Range");
        minX = builder
                .comment("Min X value on map to run gen on")
                .translation("reforge" + ".config." + "minX")
                .defineInRange("minX", -5640, -10000, 10000);
        maxX = builder
                .comment("Max X value on map to run gen on")
                .translation("reforge" + ".config." + "maxX")
                .defineInRange("maxX", 7175, -10000, 10000);
        minZ = builder
                .comment("Min Z value on map to run gen on")
                .translation("reforge" + ".config." + "minZ")
                .defineInRange("minZ", -5210, -10000, 10000);
        maxZ = builder
                .comment("Max Z value on map to run gen on")
                .translation("reforge" + ".config." + "maxZ")
                .defineInRange("maxZ", 7680, -10000, 10000);
        maxYToSkipGen = builder
                .comment("If a chunk contains a tile enitty below this Y value, underground structures wont generate")
                .translation("reforge" + ".config." + "maxYToSkipGen")
                .defineInRange("maxYToSkipGen", 60, 0, 255);
        builder.pop();
        builder.push("Generate Stuctures");
        oceanRuin = builder
                .comment("Generate Ocean Ruins")
                .translation("reforge" + ".config." + "oceanRuin")
                .define("oceanRuin", true);
        oceanMonument = builder
                .comment("Generate Ocean Monuments")
                .translation("reforge" + ".config." + "oceanMonument")
                .define("oceanMonument", true);
        shipWreck = builder
                .comment("Generate Shipwrecks")
                .translation("reforge" + ".config." + "shipWreck")
                .define("shipWreck", true);
        mineShaft = builder
                .comment("Generate Mineshafts")
                .translation("reforge" + ".config." + "mineShaft")
                .define("mineShaft", true);
        stronghold = builder
                .comment("Generate Strongholds")
                .translation("reforge" + ".config." + "stronghold")
                .define("stronghold", false);
        treasure = builder
                .comment("Generate Buried Treasire")
                .translation("reforge" + ".config." + "treasure")
                .define("treasure", true);
        swampHut = builder
                .comment("Generate Swamp Huts")
                .translation("reforge" + ".config." + "swampHut")
                .define("swampHut", false);
        woodlandMansion = builder
                .comment("Generate Woodland Mansions")
                .translation("reforge" + ".config." + "woodlandMansion")
                .define("woodlandMansion", false);
        pyramid = builder
                .comment("Generate Desert Pyramids")
                .translation("reforge" + ".config." + "pyramid")
                .define("pyramid", false);
        pillagerOutpost = builder
                .comment("Generate Pillager Outposts")
                .translation("reforge" + ".config." + "pillagerOutpost")
                .define("pillagerOutpost", false);
        fossilChance = builder
                .comment("1 chance in X per chunk for a fossil, 0 to disable, the larger the value the more rare")
                .translation("reforge" + ".config." + "fossilChance")
                .defineInRange("fossilChance", 100, 0, 10000);
        /*icebergChance = builder
                .comment("1 Chance X per chunk for a iceberg, 0 to disable, the larger the value the more rare")
                .translation("reforge" + ".config." + "icebergChance")
                .defineInRange("icebergChance", 100, 0, 10000);
        dungeonChance = builder
                .comment("1 Chance X per chunk for a dungeon, 0 to disable, the larger the value the more rare")
                .translation("reforge" + ".config." + "dungeonChance")
                .defineInRange("dungeonChance", 100, 0, 10000);*/
        builder.pop();
    }


}
