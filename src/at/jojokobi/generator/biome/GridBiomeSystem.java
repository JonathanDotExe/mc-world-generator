package at.jojokobi.generator.biome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.block.Biome;
import org.bukkit.generator.WorldInfo;

import at.jojokobi.mcutil.generation.TerrainGenUtil;

public class GridBiomeSystem extends BiomeSystem {
	
	private static final List<Biome> BIOMES = new ArrayList<>();
	
	static {
		BIOMES.addAll(Arrays.asList(Biome.values()));
		BIOMES.remove(Biome.CUSTOM);
	}
	
	private List<BiomeEntry> biomes = new ArrayList<BiomeEntry> ();
	private ValueGenerator generator;
	private long seed;
	private int gridSize = 256;

	public GridBiomeSystem(long seed, int minHeight, int maxHeight) {
		super();
		this.seed = seed;
		this.generator = new NoiseValueGenerator(seed, minHeight, maxHeight);
	}

	public void registerBiome(BiomeEntry biome) {
		biomes.add(biome);
	}
	
	private GridBiomePoint getGridBiome(int gridX, int gridZ) {
		Random random = new Random(TerrainGenUtil.generateValueBasedSeed(seed, gridX, 0, gridZ));
		int pointX = random.nextInt(gridSize);
		int pointZ = random.nextInt(gridSize);
		int x = gridX * gridSize + pointX;
		int z = gridZ * gridSize + pointZ;
		
		double temperature = generator.getTemperature(x, z);
		double moisture = generator.getTemperature(x, z);
		int height = generator.getHeight(x, z);
		int startHeight = generator.getStartHeight(x, z);
		double heightNoise = generator.getHeightNoise(x, z);
		
		double difference = 0;
		BiomeEntry biome = null;
		for (BiomeEntry b : biomes) {
			double temp = b.getDifference(heightNoise, temperature, moisture);
			if (biome == null || temp < difference) {
				biome = b;
				difference = temp;
			}
		}
		
		return new GridBiomePoint(biome.getBiome(), x, z, startHeight, startHeight, heightNoise);
	}
	
	@Override
	public BiomeGenerator getBiome(int x, int z) {
		double temperature = generator.getTemperature(x, z);
		double moisture = generator.getTemperature(x, z);
		int height = generator.getHeight(x, z);
		int startHeight = generator.getStartHeight(x, z);
		double heightNoise = generator.getHeightNoise(x, z);
		
		
		return new DefaultBiomeGenerator(biome.getBiome(), x, z, startHeight, height, heightNoise);
	}
	
	@Override
	public Biome getBiome(WorldInfo info, int x, int y, int z) {
		//FIXME ?
		return getBiome(x, z).getBiome(y);
	}

	@Override
	public List<Biome> getBiomes(WorldInfo info) {
		return BIOMES;
	}
	
	private static class GridBiomePoint {
		
		private CustomBiome biome;
		private int x;
		private int z;
		private int startHeight;
		private int height;
		private double heightNoise;
		
		public GridBiomePoint(CustomBiome biome, int x, int z, int startHeight, int height, double heightNoise) {
			super();
			this.biome = biome;
			this.x = x;
			this.z = z;
			this.startHeight = startHeight;
			this.height = height;
			this.heightNoise = heightNoise;
		}

		public CustomBiome getBiome() {
			return biome;
		}

		public void setBiome(CustomBiome biome) {
			this.biome = biome;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getZ() {
			return z;
		}

		public void setZ(int z) {
			this.z = z;
		}

		public int getStartHeight() {
			return startHeight;
		}

		public void setStartHeight(int startHeight) {
			this.startHeight = startHeight;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public double getHeightNoise() {
			return heightNoise;
		}

		public void setHeightNoise(double heightNoise) {
			this.heightNoise = heightNoise;
		}
		
	}
	
}


