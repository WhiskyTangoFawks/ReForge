package com.WhiskyTangoFox.ReForge.RetroGeneration;

import net.minecraft.block.Blocks;
import net.minecraft.command.Commands;
import net.minecraft.command.FunctionObject;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.CommandBlockTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.biome.provider.OverworldBiomeProvider;
import net.minecraft.world.biome.provider.OverworldBiomeProviderSettings;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.OverworldChunkGenerator;
import net.minecraft.world.gen.OverworldGenSettings;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.ModList;

public class TickThreadGenerator implements Runnable {

    private final IChunk chunk;

    public TickThreadGenerator(IChunk chunk) {
        this.chunk = chunk;
    }

    @Override
    public void run() {
        ChunkLoadEventHandler.thread = this;
        //FixBiome.fixBiome(chunk);

        fixHeightMaps();
        ChunkScanner scanner = new ChunkScanner(chunk);
        //scanner.scanChunk();
        scanner.backupTileEntities();
        regenChunk(chunk, scanner.hasTileEntities());
        scanner.checkTileentities();
        markChunkDone(chunk);

        //Experiment to see if I can build a hook for loading a datapack
        //oreReGen();
        chunk.setModified(true);
        if (ChunkLoadEventHandler.adjacentCalls.size() > 0) {
            ChunkLoadEventHandler.adjacentCalls.clear();
        }
    }

    private void fixHeightMaps() {
        Chunk chunk = (Chunk) this.chunk;
        chunk.heightMap.put(Heightmap.Type.OCEAN_FLOOR_WG, chunk.heightMap.get(Heightmap.Type.OCEAN_FLOOR));
        chunk.heightMap.put(Heightmap.Type.WORLD_SURFACE_WG, chunk.heightMap.get(Heightmap.Type.WORLD_SURFACE));
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

    void oreReGen() {
        MinecraftServer server = chunk.getWorldForge().getWorld().getServer();
        if (server == null) {
            return;
        }
        Commands manager = server.getCommandManager();
        FunctionObject test = server.getFunctionManager().getFunctions().get(new ResourceLocation("oregen:test"));

        CommandBlockTileEntity entity;
        BlockPos pos = chunk.getPos().asBlockPos();
        server.getFunctionManager().execute(test, server.getCommandSource().withPos(new Vec3d(pos)));
    }

    void markChunkDone(IChunk chunk) {
        chunk.setBlockState(new BlockPos(chunk.getPos().asBlockPos().getX() + 1, 1, chunk.getPos().asBlockPos().getZ()), Blocks.BARRIER.getDefaultState(), false);
    }

    ChunkGenerator getChunkGenerator(IWorld world) {
        WorldInfo info = world.getWorldInfo();
        OverworldBiomeProviderSettings biomesettings = new OverworldBiomeProviderSettings(info);
        BiomeProvider biomeProvider = new OverworldBiomeProvider(biomesettings);
        OverworldGenSettings gensettings = new OverworldGenSettings();
        return new OverworldChunkGenerator(world, biomeProvider, gensettings);
    }

    public ChunkPos getChunkPos() {
        return chunk.getPos();
    }
}
