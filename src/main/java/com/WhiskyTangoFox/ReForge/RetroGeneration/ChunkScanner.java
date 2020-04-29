package com.WhiskyTangoFox.ReForge.RetroGeneration;

import com.WhiskyTangoFox.ReForge.ReForge;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.IChunk;

import java.util.HashMap;
import java.util.Set;

public class ChunkScanner {

    public ChunkScanner(IChunk chunk) {
        this.chunk = chunk;
    }

    public final IChunk chunk;
    public HashMap<BlockState, Integer> counter = new HashMap<BlockState, Integer>();

    public void processPos(BlockPos pos) {
        BlockState state = chunk.getBlockState(pos);
        counter.put(state, counter.get(state) + 1);
    }

    public void scanChunk() {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 256; j++) {
                for (int k = 0; k < 16; k++) {
                    processPos(new BlockPos(i, j, k));
                }
            }
        }
    }

    public HashMap<BlockPos, BlockState> tileEntitiesBackup = new HashMap();
    public HashMap<BlockPos, CompoundNBT> nbtBackup = new HashMap();
    Set<BlockPos> entitiesList;

    public void backupTileEntities() {
        entitiesList = chunk.getTileEntitiesPos();
        for (BlockPos pos : entitiesList) {
            tileEntitiesBackup.put(pos, chunk.getBlockState(pos));
            nbtBackup.put(pos, chunk.getTileEntityNBT(pos).copy());
        }
    }

    public void checkTileentities() {
        Set<BlockPos> newSet = chunk.getTileEntitiesPos();
        for (BlockPos pos : entitiesList) {
            if (!newSet.contains(pos)) {
                ReForge.LOGGER.warn("ReForging has resulted in a tile entity being removed");
                chunk.getWorldForge().setBlockState(pos, tileEntitiesBackup.get(pos), 0);
                chunk.getWorldForge().getTileEntity(pos).read(nbtBackup.get(pos));
            }
        }
    }

    public boolean hasTileEntities() {
        return tileEntitiesBackup.size() > 0;
    }

}