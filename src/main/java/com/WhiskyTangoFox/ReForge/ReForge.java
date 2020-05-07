package com.WhiskyTangoFox.ReForge;

import com.WhiskyTangoFox.ReForge.Config.ReforgeConfig;
import com.WhiskyTangoFox.ReForge.RetroGeneration.ChunkLoadEventHandler;
import com.WhiskyTangoFox.ReForge.RetroGeneration.QuarksGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vazkii.quark.base.world.WorldGenHandler;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("reforge")
public class ReForge
{
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public ReForge() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ReforgeConfig.CLIENT_SPEC);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(new ChunkLoadEventHandler());
        QuarksGenerator.generators = ObfuscationReflectionHelper.getPrivateValue(WorldGenHandler.class, new WorldGenHandler(), "generators");
    }

    private void setup(final FMLCommonSetupEvent event)
    {

    }

    private void doClientStuff(final FMLClientSetupEvent event) {

    }



}
