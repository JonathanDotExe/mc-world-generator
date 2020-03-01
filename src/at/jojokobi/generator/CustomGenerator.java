package at.jojokobi.generator;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

import at.jojokobi.generator.biome.ArcticOcean;
import at.jojokobi.generator.biome.BiomeSystem;
import at.jojokobi.generator.biome.Desert;
import at.jojokobi.generator.biome.Forest;
import at.jojokobi.generator.biome.HeightBiomeSystem;
import at.jojokobi.generator.biome.Jungle;
import at.jojokobi.generator.biome.Mountains;
import at.jojokobi.generator.biome.Ocean;
import at.jojokobi.generator.biome.Plains;
import at.jojokobi.generator.biome.SnowyPlains;
import at.jojokobi.generator.biome.VolcanoMountains;
import at.jojokobi.generator.populators.BiomePopulator;
import at.jojokobi.generator.populators.ore.OrePopulator;

public class CustomGenerator extends AbstractGenerator{

//	public static final int EDGE_SIZE = 32;
	
	public CustomGenerator() {
		
	}
	
	@Override
	public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid grid) {
		ChunkData data = super.generateChunkData(world, random, x, z, grid);
//		SimplexNoiseGenerator generator = new SimplexNoiseGenerator(world.getSeed());
//		SimplexNoiseGenerator generator2 = new SimplexNoiseGenerator(world.getSeed() + 469);
//		SimplexNoiseGenerator generator3 = new SimplexNoiseGenerator(world.getSeed() - 89);
//		ValueGenerator generator = createValueGenerator(world.getSeed());
//		NoiseGenerator caveGen = new SimplexNoiseGenerator(world.getSeed() + 486);
		
//		double[][][] caveNoises = generateCaveNoises(caveGen, x * CHUNK_SIZE, z * CHUNK_SIZE);
		
		for (int i = 0; i < CHUNK_SIZE; i++) {
			for (int j = 0; j < CHUNK_SIZE; j++) {
//				int totalX = x*CHUNK_SIZE + i;
//				int totalZ = z*CHUNK_SIZE + j;
				//Calc Height
//				double noise = (0.5 + generator.noise(totalX*0.005, totalZ*0.005)*0.5) ;
//				double noise2 = (0.5 + generator2.noise(totalX*0.005, totalZ*0.005)*0.5);
//				double noise3 = (0.5 + generator3.noise(totalX*0.005, totalZ*0.005)*0.5);
				
//				int startHeight = generator.getStartHeight(totalX, totalZ);
//				int height = generator.getHeight(totalX, totalZ);
//				
//				CustomBiome biome = system.getBiome(totalX, totalZ);
//				if (startHeight < height) {
//					grid.setBiome(i, j, biome.generate(data, i, j, startHeight, height, random));
//				}
//				else {
//					grid.setBiome(i, j, Biome.THE_VOID);
//				}
				//Generate Water
				for (int y = 0; y < WATER_HEIGHT; y++) {
					if (data.getType(i, y, j) == Material.AIR) {
						data.setBlock(i, y, j, Material.WATER);
					}
				}
//				//Generate caves
//				for (int y = 0; y < data.getMaxHeight(); y++) {
//					if (data.getType(i, y, j).isSolid() && caveNoises[i][y][j] < -0.5
//							&& (caveNoises[Math.max(0, i - 4)][y][j] < -0.7
//							||  caveNoises[Math.min(TerrainGenUtil.CHUNK_WIDTH - 1, i + 4)][y][j] < -0.7
//							||  caveNoises[i][y][Math.max(0, j - 4)] < -0.7
//							||  caveNoises[i][y][Math.min(TerrainGenUtil.CHUNK_LENGTH - 1, j + 4)] < -0.7)) {
//						data.setBlock(i, y, j, Material.CAVE_AIR);
//					}
//				}
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
		/*		SimplexNoiseGenerator generator = new SimplexNoiseGenerator(world.getSeed());
		SimplexNoiseGenerator generator2 = new SimplexNoiseGenerator(world.getSeed()+654);
		SimplexNoiseGenerator generator3 = new SimplexNoiseGenerator(world.getSeed()-44);
		BiomeSystemOld system = new BiomeSystemOld(world.getSeed());
		double topLeft = getBiomeMultiplier(system.getBiome(x*CHUNK_SIZE, z*CHUNK_SIZE));
		double topRight = getBiomeMultiplier(system.getBiome((x + 1)*CHUNK_SIZE - 1, z*CHUNK_SIZE));
		double bottomLeft = getBiomeMultiplier(system.getBiome(x*CHUNK_SIZE, (z + 1)*CHUNK_SIZE - 1));
		double bottomRight = getBiomeMultiplier(system.getBiome((x + 1)*CHUNK_SIZE - 1, (z + 1)*CHUNK_SIZE - 1));
		for (int i = 0; i < CHUNK_SIZE; i++) {
			for (int j = 0; j < CHUNK_SIZE; j++) {
				biome.setBiome(i, j, system.getBiome(x*CHUNK_SIZE + i, z*CHUNK_SIZE + j));
				//Calc Height
				double noise = 0.5 + generator.noise((CHUNK_SIZE*x + i)*0.01, (CHUNK_SIZE*z + j)*0.01)*0.5;
				noise += 0.5 + generator2.noise((CHUNK_SIZE*x + i)*0.01, (CHUNK_SIZE*z + j)*0.01)*0.5;
				noise += 0.5 + generator3.noise((CHUNK_SIZE*x + i)*0.01, (CHUNK_SIZE*z + j)*0.01)*0.5;
				noise /= 3;
				//Interpolate
				double top = interpolate(topLeft, topRight, (double)i/CHUNK_SIZE);
				double bottom = interpolate(bottomLeft, bottomRight, (double)i/CHUNK_SIZE);
				
				int height = WATER_HEIGHT + (int) Math.round(noise * interpolate(top, bottom, (double)j/CHUNK_SIZE));
				//Place Blocks
				Material ground = getBiomeGround(system.getBiome(x*CHUNK_SIZE + i, z*CHUNK_SIZE + j));
				Material surface = getBiomeSurface(system.getBiome(x*CHUNK_SIZE + i, z*CHUNK_SIZE + j));
				data.setBlock(i, 0, j, Material.BEDROCK);
				for (int k = 1; k < Math.max(WATER_HEIGHT, height); k++) {
					if (k < height - 5) {
						data.setBlock(i, k, j, Material.STONE);
					}
					else if (k < height - 1) {
						data.setBlock(i, k, j, ground);
					}
					else if (k >= height) {
						data.setBlock(i, k, j, Material.WATER);
					}
					else {
						data.setBlock(i, k, j, surface);
					}
				}
			}
		}*/
		return data;
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
		BiomeSystem system = new HeightBiomeSystem(createValueGenerator(seed));
		system.registerBiome(new Plains());
		system.registerBiome(new Desert());
		system.registerBiome(new Mountains());
		system.registerBiome(new Ocean());
		system.registerBiome(new VolcanoMountains());
		system.registerBiome(new SnowyPlains());
		system.registerBiome(new Forest());
		system.registerBiome(new ArcticOcean());
		system.registerBiome(new Jungle());
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
