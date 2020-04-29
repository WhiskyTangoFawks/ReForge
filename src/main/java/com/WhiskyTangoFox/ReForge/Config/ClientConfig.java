package com.WhiskyTangoFox.ReForge.Config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {


    public final ForgeConfigSpec.IntValue minX;
    public final ForgeConfigSpec.IntValue maxX;
    public final ForgeConfigSpec.IntValue minZ;
    public final ForgeConfigSpec.IntValue maxZ;

    public ClientConfig(ForgeConfigSpec.Builder builder) {
        builder.push("Range");
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
        builder.pop();
    }


}
