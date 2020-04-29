package com.WhiskyTangoFox.ReForge.RetroGeneration;

import com.WhiskyTangoFox.ReForge.Config.ReforgeConfig;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.IChunk;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChunkLoadEventHandler {

    //static Queue<IChunk> chunkQ = new LinkedList<>();
    static ExecutorService threadpool = Executors.newFixedThreadPool(1);

    @SubscribeEvent
    public void chunkLoad(ChunkEvent.Load event) {
        //ReForge.LOGGER.info("Chunk Load Event Caught");
        //Doing server only results in invisible blocks in the world
        if (event.getWorld() == null) {//|| event.getWorld().isRemote()){
            return;
        }

        //TODO Marked out for testing
        if (chunkAlreadyDone(event.getChunk())) {
           //LOGGER.info("Skipping, already done");
           return;
        }

        if (!insideBounds(event.getChunk().getPos().asBlockPos())) {
            //LOGGER.info("Skipping, Outside world bounds");
            return;
        }

        threadpool.execute(new TickThreadGenerator(event.getChunk()));

    }

    boolean insideBounds(BlockPos pos) {
        return pos.getX() > ReforgeConfig.minX && pos.getX() < ReforgeConfig.maxX && pos.getZ() > ReforgeConfig.minZ && pos.getZ() < ReforgeConfig.maxZ;
    }

    boolean chunkAlreadyDone(IChunk chunk) {
        return chunk.getBlockState(new BlockPos(1, 1, 1)) == Blocks.BARRIER.getDefaultState();
    }

}