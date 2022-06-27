package at.jojokobi.generator;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.WorldInfo;

import at.jojokobi.generator.biome.BiomeSystem;
import at.jojokobi.generator.biome.biomes.ArcticOcean;
import at.jojokobi.generator.biome.biomes.DarkForest;
import at.jojokobi.generator.biome.biomes.Desert;
import at.jojokobi.generator.biome.biomes.Forest;
import at.jojokobi.generator.biome.biomes.Jungle;
import at.jojokobi.generator.biome.biomes.Mountains;
import at.jojokobi.generator.biome.biomes.Ocean;
import at.jojokobi.generator.biome.biomes.Plains;
import at.jojokobi.generator.biome.biomes.Savanna;
import at.jojokobi.generator.biome.biomes.SnowyPlains;
import at.jojokobi.generator.biome.biomes.Taiga;
import at.jojokobi.generator.biome.biomes.VolcanoMountains;
import at.jojokobi.generator.biome.grid.GridBiomeEntry;
import at.jojokobi.generator.biome.grid.GridBiomeSystem;
import at.jojokobi.generator.populators.BiomePopulator;
import at.jojokobi.generator.populators.ore.OrePopulator;

public class CustomGenerator extends AbstractGenerator{

//	public static final int EDGE_SIZE = 32;
	
	public CustomGenerator() {
		
	}
	
	@Override
	public void generateNoise(WorldInfo world, Random random, int x, int z, ChunkData data) {
		super.generateNoise(world, random, x, z, data);
	}
	
	@Override
	public void generateSurface(WorldInfo world, Random random, int x, int z, ChunkData data) {
		super.generateSurface(world, random, x, z, data);
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
	
	@Override
	public void generateBedrock(WorldInfo worldInfo, Random random, int x, int z, ChunkData data) {
		super.generateBedrock(worldInfo, random, x, z, data);
		for (int i = 0; i < CHUNK_SIZE; i++) {
			for (int j = 0; j < CHUNK_SIZE; j++) {
				//Bedrock
				data.setBlock(i, worldInfo.getMinHeight(), j, Material.BEDROCK);
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
		return Arrays.asList((BlockPopulator) new BiomePopulator(this),(BlockPopulator)  new OrePopulator());
	}
	
	public static double interpolate (double val1, double val2, double progress) {
		return val1 * (1-progress) + val2 * progress;
	}

	@Override
	public BiomeSystem createBiomeSystem(WorldInfo info) {
		GridBiomeSystem system = new GridBiomeSystem(info.getSeed(), Math.max(10, info.getMinHeight()), Math.min(130,  info.getMaxHeight()));
		system.registerBiome(new GridBiomeEntry(new Plains(), 0.0, 0.6, 0.0, 0.6));
		system.registerBiome(new GridBiomeEntry(new Desert(), 0.5, 1, 0.0, 0.5));
		system.registerBiome(new GridBiomeEntry(new Mountains(), 0.0, 0.7, 0.0, 0.8));
		system.registerBiome(new GridBiomeEntry(new VolcanoMountains(), 0.5, 1.0, 0.0, 0.5));
		system.registerBiome(new GridBiomeEntry(new SnowyPlains(), 0.0, 0.5, 0.3, 1.0));
		system.registerBiome(new GridBiomeEntry(new Forest(), 0.4, 0.7, 0.2, 0.7));
		system.registerBiome(new GridBiomeEntry(new Jungle(), 0.5, 1.0, 0.5, 1.0));
		system.registerBiome(new GridBiomeEntry(new Taiga(), 0.1, 0.7, 0.0, 0.7));
		system.registerBiome(new GridBiomeEntry(new DarkForest(), 0.4, 0.85, 0.35, 0.9));
		system.registerBiome(new GridBiomeEntry(new Savanna(), 0.5, 1.0, 0.0, 0.6));
		
		system.registerOceanBiome(new GridBiomeEntry(new Ocean(), 0.0, 1.0, 0.0, 1.0));
		system.registerOceanBiome(new GridBiomeEntry(new ArcticOcean(), 0.0, 0.4, 0.0, 1.0));
		return system;
	}
	
	@Override
	public boolean shouldGenerateMobs() {
		return true;
	}
	
	@Override
	public boolean shouldGenerateStructures() {
		return true;
	}

}
