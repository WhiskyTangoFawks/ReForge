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
    private final Boolean hasAnyTileEntities;
    private final Boolean hasTileEntitiesBelowMaxY;

    public QuarksGenerator(IWorld world, ChunkGenerator gen, BlockPos pos, boolean hasAnyTileEntities, boolean hasTileEntitiesBelowMaxY) {
        this.worldIn = world;
        this.generator = gen;
        this.pos = pos;
        this.hasAnyTileEntities = hasAnyTileEntities;
        this.hasTileEntitiesBelowMaxY = hasTileEntitiesBelowMaxY;
    }

    public void doGen() {
        generate(GenerationStage.Decoration.UNDERGROUND_ORES);

        if (!hasTileEntitiesBelowMaxY) { //If the chunk has any tile entities already, skip structure generation
            generate(GenerationStage.Decoration.UNDERGROUND_STRUCTURES);
            generate(GenerationStage.Decoration.UNDERGROUND_DECORATION);
        }
        if (!hasAnyTileEntities) { //If the chunk has any tile entities already, skip structure generation
            generate(GenerationStage.Decoration.SURFACE_STRUCTURES);
            generate(GenerationStage.Decoration.VEGETAL_DECORATION);
        }
    }

    public void generate(GenerationStage.Decoration stage) {
        try {
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
        } catch (Exception e) {
            ReForge.LOGGER.warn("Error during Quark ReForging " + stage.getName() + ": " + e.toString());
        }
    }

}



