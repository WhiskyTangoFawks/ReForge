package com.WhiskyTangoFox.ReForge.RetroGeneration;

import com.WhiskyTangoFox.ReForge.Config.ReforgeConfig;
import com.WhiskyTangoFox.ReForge.ReForge;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.IChunk;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ChunkLoadEventHandler {


    static ThreadPoolExecutor threadpool = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
    static HashSet<Long> adjacentCalls = new HashSet<Long>();


    @SubscribeEvent
    public void chunkLoad(ChunkEvent.Load event) {
        //ReForge.LOGGER.info("Chunk Load Event Caught");
        //Doing server only results in invisible blocks in the world

        if (event.getWorld() == null || event.getWorld().isRemote()) {
            return;
        }

        if (chunkAlreadyDone(event.getChunk())) {
            //ReForge.LOGGER.info("Skipping, already done");

            return;
        }

        if (!insideBounds(event.getChunk().getPos().asBlockPos())) {
            //LOGGER.info("Skipping, Outside world bounds");
            return;
        }
        //TODO Run PrepStructureStarts here?

        if (adjacentCalls.contains(event.getChunk().getPos().asLong())) {
            //TODO Run PrepStructureStarts  Maybe here instead?
            //ReForge.LOGGER.info("A chunk registered as adjacent has been caught by the chunk load event");
            adjacentCalls.remove(event.getChunk().getPos().asLong());
        } else {
            threadpool.execute(new TickThreadGenerator(event.getChunk()));
        }

    }

    boolean insideBounds(BlockPos pos) {
        return pos.getX() > ReforgeConfig.minX && pos.getX() < ReforgeConfig.maxX && pos.getZ() > ReforgeConfig.minZ && pos.getZ() < ReforgeConfig.maxZ;
    }

    boolean chunkAlreadyDone(IChunk chunk) {
        return chunk.getBlockState(new BlockPos(chunk.getPos().asBlockPos().getX() + 1, 1, chunk.getPos().asBlockPos().getZ())) == Blocks.BARRIER.getDefaultState();
    }

    public static TickThreadGenerator thread = null;
    public static ChunkPos pos = null;
    int tickCounter = 0;

    @SubscribeEvent
    public void tickEvent(TickEvent event) {
        tickCounter++;
        if (tickCounter > 1000) {
            tickCounter = 0;
            if (threadpool.getQueue().size() > 100) {
                ReForge.LOGGER.warn("ReForging queue is backed up: " + threadpool.getQueue().size());
                if (thread != null && thread.getChunkPos() != pos) {
                    pos = thread.getChunkPos();
                } else {
                    ReForge.LOGGER.warn("still trying to generate the same chunk: " + pos.toString());
                }

            }
        }
    }


}