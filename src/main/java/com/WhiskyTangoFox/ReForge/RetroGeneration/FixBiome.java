package com.WhiskyTangoFox.ReForge.RetroGeneration;

import net.minecraft.world.biome.Biomes;
import net.minecraft.world.chunk.IChunk;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FixBiome {

    public static final Logger LOGGER = LogManager.getLogger();

   static void fixBiome(IChunk chunk){
        for (int n = 0; n< chunk.getBiomes().biomes.length; n++){
            chunk.getBiomes().biomes[n] = Biomes.THE_END;
        }

    }


    /*
     Biomes: May not exist. 1024 entries of biome data. Each number in the array is the biome for a
     4x4x4 volume in the chunk. These 4×4×4 volumes are arranged by Z, then X, then Y. That is, the
     first 4×4 values in the array are for the 16×16 chunk, at Y levels 0–3, the next 4×4 is for Y
     levels 4–7, etc. See Java Edition data values § Biomes for biome IDs. If this tag does not exist,
     it gets created and filled by Minecraft when the chunk is loaded an
     */

}
