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
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.*;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.BiomeDictionary;

import java.util.Map;

public class VanillaFeatureGenerator {

    private final IWorld world;
    private final IChunk chunk;
    private final ChunkGenerator generator;
    private final BlockPos pos;
    private final Boolean hasAnyTileEntities;
    private final Boolean hasTileEntitiesBelowMaxY;


    private static Feature fossil = Feature.FOSSIL;
    private static Feature dungeon = Feature.MONSTER_ROOM;

    private static Structure oceanRuin = Feature.OCEAN_RUIN;
    private static Structure wreck = Feature.SHIPWRECK;
    private static Structure mine = Feature.MINESHAFT;
    private static Structure oceanMonument = Feature.OCEAN_MONUMENT;
    private static Structure stronghold = Feature.STRONGHOLD;
    private static Structure treasure = Feature.BURIED_TREASURE;

    private static Structure hut = Feature.SWAMP_HUT;
    private static Structure mansion = Feature.WOODLAND_MANSION;
    private static Structure pyramid = Feature.DESERT_PYRAMID;
    private static Structure pillager = Feature.PILLAGER_OUTPOST;

    public static Structure[] structure = {oceanRuin, wreck, mine, oceanMonument, stronghold, treasure, hut, mansion, pyramid, pillager};

    VanillaFeatureGenerator(IWorld world, IChunk chunk, ChunkGenerator gen, BlockPos pos, Boolean hasAnyTileEntities, Boolean hasTileEntitiesBelowMaxY) {
        this.world = world;
        this.chunk = chunk;
        this.generator = gen;
        this.pos = pos;
        this.hasAnyTileEntities = hasAnyTileEntities;
        this.hasTileEntitiesBelowMaxY = hasTileEntitiesBelowMaxY;
    }

    void generate() {
        //try {

        generateStructures();

        try {
            int fossilChance = BiomeDictionary.hasType(world.getBiome(pos), BiomeDictionary.Type.MESA) ? ReforgeConfig.fossilChance / 2 : ReforgeConfig.fossilChance;
            if (!hasTileEntitiesBelowMaxY && ReforgeConfig.fossilChance > 0 && world.getRandom().nextInt(fossilChance) == 0 && fossil.place(world, generator, world.getRandom(), pos, new NoFeatureConfig())) {
                ReForge.LOGGER.info("Fossil Placed " + pos.toString());
            }
        } catch (Exception e) {
            ReForge.LOGGER.error("Fossil generation failed " + e.toString());
        }
    }

    public void generateStructures() {
        prepStructureStarts(world, chunk, generator);
        generateStructureStarts();
        placeStructures();
    }

    //I think this populates the keys
    public void generateStructureStarts() {
        int j = chunk.getPos().x;
        int k = chunk.getPos().z;
        int l = j << 4;
        int i1 = k << 4;

        //This seems to be iterating through adjacent chunks, iterating through their structures, and seeing if any starts intersect here
        for (int j1 = j - 8; j1 <= j + 8; ++j1) {
            for (int k1 = k - 8; k1 <= k + 8; ++k1) {
                long l1 = ChunkPos.asLong(j1, k1);
                //TODO- Restrict checks to inside the world boundary
                ChunkLoadEventHandler.adjacentCalls.add(l1);
                prepStructureStarts(world, world.getChunk(j1, k1), generator);
                for (Map.Entry<String, StructureStart> entry : world.getChunk(j1, k1).getStructureStarts().entrySet()) {
                    StructureStart structurestart = entry.getValue();
                    if (structurestart != StructureStart.DUMMY && structurestart.getBoundingBox().intersectsWith(l, i1, l + 15, i1 + 15)) {

                        chunk.addStructureReference(entry.getKey(), l1);
                        //DebugPacketSender.sendStructureStart(world, structurestart);
                    }
                }
            }
        }
    }

    void placeStructures() {
        if (!hasTileEntitiesBelowMaxY && ReforgeConfig.stronghold && stronghold.place(world, generator, world.getRandom(), pos, new NoFeatureConfig())) {
            ReForge.LOGGER.info("Stronghold Placed " + pos.toString());
        }
        if (!hasTileEntitiesBelowMaxY && ReforgeConfig.oceanMonument && oceanMonument.place(world, generator, world.getRandom(), pos, new NoFeatureConfig())) {
            ReForge.LOGGER.info("OceanMonument Placed " + pos.toString());
        }
        OceanRuinStructure.Type ruinType = BiomeDictionary.hasType(world.getBiome(pos), BiomeDictionary.Type.COLD) ? OceanRuinStructure.Type.COLD : OceanRuinStructure.Type.WARM;
        if (!hasTileEntitiesBelowMaxY && ReforgeConfig.oceanRuin && oceanRuin.place(world, generator, world.getRandom(), pos, new OceanRuinConfig(ruinType, 0.5F, 0.5F))) {
            ReForge.LOGGER.info("Ocean ruin Placed " + pos.toString());
        }
        if (!hasAnyTileEntities && ReforgeConfig.shipWreck && wreck.place(world, generator, world.getRandom(), pos, new ShipwreckConfig(true))) {
            ReForge.LOGGER.info("Beached Shipwreck Placed " + pos.toString());
        }
        if (!hasAnyTileEntities && ReforgeConfig.shipWreck && wreck.place(world, generator, world.getRandom(), pos, new ShipwreckConfig(false))) {
            ReForge.LOGGER.info("Shipwreck Placed " + pos.toString());
        }
        if (!hasTileEntitiesBelowMaxY && ReforgeConfig.treasure && treasure.place(world, generator, world.getRandom(), pos, new BuriedTreasureConfig(1F))) {
            ReForge.LOGGER.info("Buried Treasure Placed " + pos.toString());
        }
        MineshaftStructure.Type mineType = BiomeDictionary.hasType(world.getBiome(pos), BiomeDictionary.Type.MESA) ? MineshaftStructure.Type.MESA : MineshaftStructure.Type.NORMAL;
        if (!hasTileEntitiesBelowMaxY && ReforgeConfig.mineShaft && mine.place(world, generator, world.getRandom(), pos, new MineshaftConfig(0.004f, mineType))) {
            ReForge.LOGGER.info("Mineshaft Placed " + pos.toString());
        }
        if (!hasAnyTileEntities && ReforgeConfig.swampHut && hut.place(world, generator, world.getRandom(), pos, new NoFeatureConfig())) {
            ReForge.LOGGER.info("SwampHut Placed " + pos.toString());
        }
        if (!hasAnyTileEntities && ReforgeConfig.woodlandMansion && mansion.place(world, generator, world.getRandom(), pos, new NoFeatureConfig())) {
            ReForge.LOGGER.info("Woodland Mansion Placed " + pos.toString());
        }
        if (!hasAnyTileEntities && ReforgeConfig.pyramid && pyramid.place(world, generator, world.getRandom(), pos, new NoFeatureConfig())) {
            ReForge.LOGGER.info("Desert Pyramid Placed " + pos.toString());
        }
        if (!hasAnyTileEntities && ReforgeConfig.pillagerOutpost && pillager.place(world, generator, world.getRandom(), pos, new NoFeatureConfig())) {
            ReForge.LOGGER.info("Pillager Outpost Placed " + pos.toString());
        }

    }

    static public void prepStructureStarts(IWorld world, IChunk chunk, ChunkGenerator generator) {
        TemplateManager templateManager = ((ServerWorld) world).getSaveHandler().getStructureTemplateManager();
        BiomeManager biomeManager = world.getBiomeManager();
        for (Structure<?> structure : VanillaFeatureGenerator.structure) {
            if (world.getBiome(chunk.getPos().asBlockPos()).hasStructure(structure) && generator.getBiomeProvider().hasStructure(structure)) {
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
        }
    }

}
