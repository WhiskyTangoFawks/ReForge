package com.WhiskyTangoFox.ReForge.RetroGeneration;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.biome.provider.OverworldBiomeProvider;
import net.minecraft.world.biome.provider.OverworldBiomeProviderSettings;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.OverworldChunkGenerator;
import net.minecraft.world.gen.OverworldGenSettings;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.ModList;

public class TickThreadGenerator implements Runnable {

    private final IChunk chunk;

    public TickThreadGenerator(IChunk chunk){
        this.chunk = chunk;
    }

    @Override
    public void run() {
        //FixBiome.fixBiome(chunk);
        ChunkScanner scanner = new ChunkScanner(chunk);
        //scanner.scanChunk();
        scanner.backupTileEntities();
        regenChunk(chunk, scanner.hasTileEntities());
        scanner.checkTileentities();
        markChunkDone(chunk);
        chunk.setModified(true);

        //LOGGER.info("Generated");
    }

    void regenChunk(IChunk chunk, Boolean hasTileEntities) {
        ChunkGenerator regenerator = getChunkGenerator(chunk.getWorldForge());
        VanillaFeatureGenerator vanillaGen = new VanillaFeatureGenerator(chunk.getWorldForge(), chunk, regenerator, chunk.getPos().asBlockPos(), hasTileEntities);
        vanillaGen.generate();
       if (ModList.get().isLoaded("quark")) {
           QuarksGenerator qGen = new QuarksGenerator(chunk.getWorldForge(), regenerator, chunk.getPos().asBlockPos(), hasTileEntities);
           qGen.doGen();
       }
    }

    void markChunkDone(IChunk chunk){
        chunk.setBlockState(new BlockPos(1, 1, 1), Blocks.BARRIER.getDefaultState(), false);
    }

    ChunkGenerator getChunkGenerator(IWorld world){
        WorldInfo info = world.getWorldInfo();
        OverworldBiomeProviderSettings biomesettings = new OverworldBiomeProviderSettings(info);
        BiomeProvider biomeProvider = new OverworldBiomeProvider(biomesettings);
        OverworldGenSettings gensettings = new OverworldGenSettings();
        return new OverworldChunkGenerator(world, biomeProvider, gensettings);
    }
}
