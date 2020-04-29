package com.WhiskyTangoFox.ReForge.RetroGeneration;

import com.WhiskyTangoFox.ReForge.ReForge;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import vazkii.quark.base.world.WeightedGenerator;
import vazkii.quark.base.world.generator.IGenerator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;


public class QuarksGenerator {

    public static Map<GenerationStage.Decoration, SortedSet<WeightedGenerator>> generators = new HashMap();

    final IWorld worldIn;
    final ChunkGenerator generator;
    final BlockPos pos;
    final Boolean hasTileEntities;

    public QuarksGenerator(IWorld world, ChunkGenerator gen, BlockPos pos, boolean hasTiles) {
        this.worldIn = world;
        this.generator = gen;
        this.pos = pos;
        this.hasTileEntities = hasTiles;
    }


    public void generate(GenerationStage.Decoration stage) {
        SharedSeedRandom random = new SharedSeedRandom();
        long seed = random.setDecorationSeed(worldIn.getSeed(), pos.getX(), pos.getZ());
        int stageNum = stage.ordinal() * 10000;
        if (generators.containsKey(stage)) {
            SortedSet<WeightedGenerator> set = (SortedSet) generators.get(stage);
            Iterator var10 = set.iterator();

            while (var10.hasNext()) {
                WeightedGenerator wgen = (WeightedGenerator) var10.next();
                IGenerator gen = wgen.generator;
                if (wgen.module.enabled && gen.canGenerate(worldIn)) {
                    stageNum = gen.generate(stageNum, seed, stage, worldIn, generator, random, pos);
                }
            }
        }
    }


    public void doGen() {
        //ReForge.LOGGER.info("Doing Quarks Generation");
        try {
            generate(GenerationStage.Decoration.RAW_GENERATION);
        } catch (Exception e) {
            ReForge.LOGGER.warn("Error during ReForgine: " + e.toString());
        }
        try {
            generate(GenerationStage.Decoration.LOCAL_MODIFICATIONS);
        } catch (Exception e) {
            ReForge.LOGGER.warn("Error during ReForgine: " + e.toString());
        }
        generate(GenerationStage.Decoration.UNDERGROUND_ORES);
        try {
            generate(GenerationStage.Decoration.TOP_LAYER_MODIFICATION);
        } catch (Exception e) {
            ReForge.LOGGER.warn("Error during ReForgine: " + e.toString());
        }
        try {
            generate(GenerationStage.Decoration.UNDERGROUND_DECORATION);
        } catch (Exception e) {
            ReForge.LOGGER.warn("Error during ReForgine: " + e.toString());
        }
        try {
            generate(GenerationStage.Decoration.VEGETAL_DECORATION);
        } catch (Exception e) {
            ReForge.LOGGER.warn("Error during ReForgine: " + e.toString());
        }
        if (!hasTileEntities) { //If the chunk has any tile entities already, skip structure generation
            try {
                generate(GenerationStage.Decoration.UNDERGROUND_STRUCTURES);
            } catch (Exception e) {
                ReForge.LOGGER.warn("Error during ReForgine: " + e.toString());
            }
            try {
                generate(GenerationStage.Decoration.SURFACE_STRUCTURES);
            } catch (Exception e) {
                ReForge.LOGGER.warn("Error during ReForgine: " + e.toString());
            }
        }
    }
}


