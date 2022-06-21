package at.jojokobi.generator;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.WorldInfo;

import at.jojokobi.generator.biome.HeightBiomeEntry;
import at.jojokobi.generator.biome.BiomeSystem;
import at.jojokobi.generator.biome.HeightBiomeSystem;
import at.jojokobi.generator.biome.biomes.ArcticOcean;
import at.jojokobi.generator.biome.biomes.Desert;
import at.jojokobi.generator.biome.biomes.Forest;
import at.jojokobi.generator.biome.biomes.Jungle;
import at.jojokobi.generator.biome.biomes.Mountains;
import at.jojokobi.generator.biome.biomes.Ocean;
import at.jojokobi.generator.biome.biomes.Plains;
import at.jojokobi.generator.biome.biomes.SnowyPlains;
import at.jojokobi.generator.biome.biomes.VolcanoMountains;
import at.jojokobi.generator.noise.NoiseValueGenerator;
import at.jojokobi.generator.noise.ValueGenerator;
import at.jojokobi.generator.populators.BiomePopulator;
import at.jojokobi.generator.populators.ore.OrePopulator;

public class CustomGenerator extends AbstractGenerator{

//	public static final int EDGE_SIZE = 32;
	
	public CustomGenerator() {
		
	}
	
	@Override
	public void generateNoise(WorldInfo world, Random random, int x, int z, ChunkData data) {
		for (int i = 0; i < CHUNK_SIZE; i++) {
			for (int j = 0; j < CHUNK_SIZE; j++) {
				//Generate Water
				for (int y = 0; y < WATER_HEIGHT; y++) {
					if (data.getType(i, y, j) == Material.AIR) {
						data.setBlock(i, y, j, Material.WATER);
					}
				}
				//Generate Lava
				for (int y = 0; y < 11; y++) {
					if (data.getType(i, y, j) == Material.CAVE_AIR) {
						data.setBlock(i, y, j, Material.LAVA);
					}
				}
				//Bedrock
				data.setBlock(i, 0, j, Material.BEDROCK);
			}
		}
	}
	
//	private double[][][] generateCaveNoises (NoiseGenerator caveGen, int totalX, int totalZ) {
//		double[][][] noises = new double[TerrainGenUtil.CHUNK_WIDTH][TerrainGenUtil.CHUNK_HEIGHT][TerrainGenUtil.CHUNK_LENGTH];
//		for (int x = 0; x < noises.length; x++) {
//			for (int y = 0; y < noises[x].length; y++) {
//				for (int z = 0; z < noises[x][y].length; z++) {
//					noises[x][y][z] = caveGen.noise((totalX + x) * 0.05, y * 0.05, (totalZ +z) * 0.05);
//				}
//			}
//		}
//		return noises;
//	}
	
	@Override
	public List<BlockPopulator> getDefaultPopulators(World world) {
		return Arrays.asList((BlockPopulator) new BiomePopulator(this, this),(BlockPopulator)  new OrePopulator());
	}
	
	public static double interpolate (double val1, double val2, double progress) {
		return val1 * (1-progress) + val2 * progress;
	}
	
	@Override
	public ValueGenerator createValueGenerator (long seed) {
		return new NoiseValueGenerator(seed);
	}

	@Override
	public BiomeSystem createBiomeSystem(long seed) {
		HeightBiomeSystem system = new HeightBiomeSystem(createValueGenerator(seed));
		system.registerBiome(new HeightBiomeEntry(new Plains(), 0.1, 0.3, 0.4, 0.7, 0.3, 0.6, 9971));
		system.registerBiome(new HeightBiomeEntry(new Desert(), 0, 0.3, 0.5, 1.5, 0.0, 0.5, -64235));
		system.registerBiome(new HeightBiomeEntry(new Mountains(), 0.4, 0.8, 0.2, 0.5, 0.3, 0.5, 2031198));
		system.registerBiome(new HeightBiomeEntry(new Ocean(), -0.5, 0, 0.4, 0.6, 0.2, 0.8, 3220012));
		system.registerBiome(new HeightBiomeEntry(new VolcanoMountains(), 0.5, 1, 0.7, 1.0, 0.2, 0.5, 34562));
		system.registerBiome(new HeightBiomeEntry(new SnowyPlains(), 0, 0.2, 0.0, 0.4, 0.2, 0.6, 8790));
		system.registerBiome(new HeightBiomeEntry(new Forest(), 0.1, 0.3, 0.4, 0.7, 0.4, 0.7, 5));
		system.registerBiome(new HeightBiomeEntry(new ArcticOcean(), -0.7, -0.1, 0.0, 0.4, 0.0, 0.5, 300256971));
		system.registerBiome(new HeightBiomeEntry(new Jungle(), 0.1, 0.3, 0.7, 1.0, 0.7, 1.1, 458198911917l));
		return system;
	}
	
//	public static double getBiomeMultiplier (Biome biome) {
//		double multiplier = 20;
//		switch (biome) {
//		case PLAINS:
//		case MUTATED_PLAINS:
//		case SAVANNA:
//		case MUTATED_SAVANNA:
//		case SWAMPLAND:
//		case MUTATED_SWAMPLAND:
//			multiplier = 5;
//			break;
//		case EXTREME_HILLS:
//		case MUTATED_EXTREME_HILLS:
//			multiplier = 100;
//			break;
//		case FOREST_HILLS:
//		case BIRCH_FOREST_HILLS:
//		case MUTATED_BIRCH_FOREST_HILLS:
//		case DESERT_HILLS:
//			multiplier = 30;
//			break;
//		case MESA:
//		case MESA_CLEAR_ROCK:
//		case MESA_ROCK:
//		case MUTATED_MESA:
//		case MUTATED_MESA_CLEAR_ROCK:
//		case MUTATED_MESA_ROCK:
//			multiplier = 60;
//			break;
//		case RIVER:
//		case FROZEN_RIVER:
//			multiplier = -7;
//		case OCEAN: 
//		case FROZEN_OCEAN:
//			multiplier = -30;
//		case DEEP_OCEAN:
//			multiplier = -40;
//
//		default:
//			break;
//		}
//		return multiplier;
//	}
	
//	public static Material getBiomeSurface (Biome biome) {
//		Material ground = Material.GRASS;
//		switch (biome) {
//		case EXTREME_HILLS:
//		case MUTATED_EXTREME_HILLS:
//			ground = Material.STONE;
//			break;
//		case DESERT:
//		case DESERT_HILLS:
//		case MUTATED_DESERT:
//			ground = Material.SAND;
//			break;
//		case OCEAN:
//		case DEEP_OCEAN:
//			ground = Material.SAND;
//			break;
//		case RIVER:
//			ground = Material.SAND;
//			break;
//		case FROZEN_RIVER:
//		case FROZEN_OCEAN:
//			ground = Material.ICE;
//			break;
//		case MUSHROOM_ISLAND:
//		case MUSHROOM_ISLAND_SHORE:
//			ground = Material.MYCEL;
//			break;
//		case MUTATED_ICE_FLATS:
//			ground = Material.SNOW_BLOCK;
//			break;
//
//		default:
//			break;
//		}
//		return ground;
//	}
	
	
//	public static Material getBiomeGround (Biome biome) {
//		Material ground = Material.DIRT;
//		switch (biome) {
//		case EXTREME_HILLS:
//		case MUTATED_EXTREME_HILLS:
//			ground = Material.STONE;
//			break;
//		case DESERT:
//			ground = Material.SAND;
//			break;
//		case OCEAN:
//		case DEEP_OCEAN:
//		case FROZEN_OCEAN:
//			ground = Material.SAND;
//			break;
//		case RIVER:
//		case FROZEN_RIVER:
//			ground = Material.SAND;
//			break;
//		default:
//			break;
//		}
//		return ground;
//	}

}
