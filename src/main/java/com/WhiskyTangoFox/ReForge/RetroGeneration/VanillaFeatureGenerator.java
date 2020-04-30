package com.WhiskyTangoFox.ReForge.RetroGeneration;

import com.WhiskyTangoFox.ReForge.Config.ReforgeConfig;
import com.WhiskyTangoFox.ReForge.ReForge;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.*;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.BiomeDictionary;

public class VanillaFeatureGenerator {

    private final IWorld world;
    private final IChunk chunk;
    private final ChunkGenerator generator;
    private final BlockPos pos;
    private final Boolean hasTileEntities;

    private Feature fossil = Feature.FOSSIL;
    private Structure oceanRuin = Feature.OCEAN_RUIN;
    private Structure wreck = Feature.SHIPWRECK;
    private Structure mine = Feature.MINESHAFT;
    private Structure oceanMonument = Feature.OCEAN_MONUMENT;
    private Structure stronghold = Feature.STRONGHOLD;
    private Structure treasure = Feature.BURIED_TREASURE;

    private Structure hut = Feature.SWAMP_HUT;
    private Structure mansion = Feature.WOODLAND_MANSION;
    private Structure pyramid = Feature.DESERT_PYRAMID;
    private Structure pillager = Feature.PILLAGER_OUTPOST;

    private Feature iceberg = Feature.ICEBERG;
    private Feature dungeon = Feature.MONSTER_ROOM;

    private Structure[] structure = {oceanRuin, wreck, mine, oceanMonument, stronghold, treasure, hut, mansion, pyramid, pillager};

    VanillaFeatureGenerator(IWorld world, IChunk chunk, ChunkGenerator gen, BlockPos pos, boolean hasTiles) {
        this.world = world;
        this.chunk = chunk;
        this.generator = gen;
        this.pos = pos;
        this.hasTileEntities = hasTiles;
    }

    void generate() {
        //try {
            if (hasTileEntities) {
                ReForge.LOGGER.info("Tile Entities Found- skipping gen " + pos.toString());
                return;
            }
        fixHeightMaps();
        generateStructures();
        try {
            int fossilChance = BiomeDictionary.hasType(world.getBiome(pos), BiomeDictionary.Type.MESA) ? ReforgeConfig.fossilChance / 2 : ReforgeConfig.fossilChance;
            if (ReforgeConfig.fossilChance > 0 && world.getRandom().nextInt(fossilChance) == 0 && fossil.place(world, generator, world.getRandom(), pos, new NoFeatureConfig())) {
                ReForge.LOGGER.info("Fossil Placed " + pos.toString());
            }
        } catch (Exception e) {
            ReForge.LOGGER.error("Fossil generation failed " + e.toString());
        }
        try {
            if (ReforgeConfig.icebergChance > 0 && world.getRandom().nextInt(ReforgeConfig.icebergChance) == 0
                    && BiomeDictionary.hasType(world.getBiome(pos), BiomeDictionary.Type.OCEAN)
                    && BiomeDictionary.hasType(world.getBiome(pos), BiomeDictionary.Type.COLD)
                    && iceberg.place(world, generator, world.getRandom(), pos, new NoFeatureConfig())) {
                ReForge.LOGGER.info("Fossil Placed " + pos.toString());
            }
        } catch (Exception e) {
            ReForge.LOGGER.error("Iceberg generation failed " + e.toString());
        }
        try {
            if (ReforgeConfig.dungeonChance > 0 && world.getRandom().nextInt(ReforgeConfig.dungeonChance) == 0
                    && dungeon.place(world, generator, world.getRandom(), pos, new NoFeatureConfig())) {
                ReForge.LOGGER.info("dungeon Placed " + pos.toString());
            }
        } catch (Exception e) {
            ReForge.LOGGER.error("dungeon generation failed " + e.toString());
        }

        //} catch (Exception e) {
        //    ReForge.LOGGER.warn("Error during ReForging: " + e.toString());
        // }

    }

    public void generateStructures() {
        if (!(world instanceof ServerWorld)){
            return;
        }

        TemplateManager templateManager = ((ServerWorld)this.world).getSaveHandler().getStructureTemplateManager();
        BiomeManager biomeManager = world.getBiomeManager();

        for(Structure<?> structure : structure) {
            try {
                if (generator.getBiomeProvider().hasStructure(structure)) {
                    StructureStart structurestart = chunk.getStructureStart(structure.getStructureName());
                    int i = structurestart != null ? structurestart.func_227457_j_() : 0;
                    SharedSeedRandom sharedseedrandom = new SharedSeedRandom();
                    ChunkPos chunkpos = chunk.getPos();
                    StructureStart structurestart1 = StructureStart.DUMMY;
                    Biome biome = biomeManager.getBiome(new BlockPos(chunkpos.getXStart() + 9, 0, chunkpos.getZStart() + 9));
                    if (structure.func_225558_a_(biomeManager, generator, sharedseedrandom, chunkpos.x, chunkpos.z, biome)) {
                        StructureStart structurestart2 = structure.getStartFactory().create(structure, chunkpos.x, chunkpos.z, MutableBoundingBox.getNewBoundingBox(), i, generator.getSeed());
                        structurestart2.init(generator, templateManager, chunkpos.x, chunkpos.z, biome);
                        structurestart1 = structurestart2.isValid() ? structurestart2 : StructureStart.DUMMY;
                    }

                    chunk.putStructureStart(structure.getStructureName(), structurestart1);
                }
            }catch (Exception e){
                ReForge.LOGGER.error("ReForging failed while generating " + structure.getStructureName());
                e.printStackTrace();
            }
        }
        generator.generateStructureStarts(world, chunk);
        if (ReforgeConfig.stronghold && stronghold.place(world, generator, world.getRandom(), pos, new NoFeatureConfig())) {
            ReForge.LOGGER.info("Stronghold Placed " + pos.toString());
        }
        if (ReforgeConfig.oceanMonument && oceanMonument.place(world, generator, world.getRandom(), pos, new NoFeatureConfig())) {
            ReForge.LOGGER.info("OceanMonument Placed " + pos.toString());
        }
        OceanRuinStructure.Type ruinType = BiomeDictionary.hasType(world.getBiome(pos), BiomeDictionary.Type.COLD) ? OceanRuinStructure.Type.COLD : OceanRuinStructure.Type.WARM;
        if (ReforgeConfig.oceanRuin && oceanRuin.place(world, generator, world.getRandom(), pos, new OceanRuinConfig(ruinType, 0.5F, 0.5F))) {
            ReForge.LOGGER.info("Ocean ruin Placed " + pos.toString());
        }
        if (ReforgeConfig.shipWreck && wreck.place(world, generator, world.getRandom(), pos, new ShipwreckConfig(true))) {
            ReForge.LOGGER.info("Beached Shipwreck Placed " + pos.toString());
        }
        if (ReforgeConfig.shipWreck && wreck.place(world, generator, world.getRandom(), pos, new ShipwreckConfig(false))) {
            ReForge.LOGGER.info("Shipwreck Placed " + pos.toString());
        }
        if (ReforgeConfig.treasure && treasure.place(world, generator, world.getRandom(), pos, new BuriedTreasureConfig(1F))) {
            ReForge.LOGGER.info("Buried Treasure Placed " + pos.toString());
        }
        MineshaftStructure.Type mineType = BiomeDictionary.hasType(world.getBiome(pos), BiomeDictionary.Type.MESA) ? MineshaftStructure.Type.MESA : MineshaftStructure.Type.NORMAL;
        if (ReforgeConfig.mineShaft && mine.place(world, generator, world.getRandom(), pos, new MineshaftConfig(1f, mineType))) {
            ReForge.LOGGER.info("Mineshaft Placed " + pos.toString());
        }
        if (ReforgeConfig.swampHut && hut.place(world, generator, world.getRandom(), pos, new NoFeatureConfig())) {
            ReForge.LOGGER.info("SwampHut Placed " + pos.toString());
        }
        if (ReforgeConfig.woodlandMansion && mansion.place(world, generator, world.getRandom(), pos, new NoFeatureConfig())) {
            ReForge.LOGGER.info("Woodland Mansion Placed " + pos.toString());
        }
        if (ReforgeConfig.pyramid && pyramid.place(world, generator, world.getRandom(), pos, new NoFeatureConfig())) {
            ReForge.LOGGER.info("Desert Pyramid Placed " + pos.toString());
        }
        if (ReforgeConfig.pillagerOutpost && pillager.place(world, generator, world.getRandom(), pos, new NoFeatureConfig())) {
            ReForge.LOGGER.info("Pillager Outpost Placed " + pos.toString());
        }
    }

    private void fixHeightMaps() {
        Chunk chunky = (Chunk) chunk;
        ((Chunk) chunk).heightMap.put(Heightmap.Type.OCEAN_FLOOR_WG, chunky.heightMap.get(Heightmap.Type.OCEAN_FLOOR));
    }

}
