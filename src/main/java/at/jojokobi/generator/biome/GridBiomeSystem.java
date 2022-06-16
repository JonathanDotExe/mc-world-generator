package at.jojokobi.generator.biome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.block.Biome;
import org.bukkit.generator.WorldInfo;
import org.bukkit.util.noise.NoiseGenerator;
import org.bukkit.util.noise.SimplexNoiseGenerator;
import org.bukkit.generator.ChunkGenerator.ChunkData;

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
		double pointWeight = 0.5 + random.nextDouble();
		int x = gridX * gridSize + pointX;
		int z = gridZ * gridSize + pointZ;
		
		double temperature = generator.getTemperature(x, z);
		double moisture = generator.getMoisture(x, z);
		double heightNoise = generator.getHeightNoise(x, z);
		
		BiomeEntry biome = null;
		List<BiomeEntry> possibleBiomes = new ArrayList<>();
		for (BiomeEntry b : biomes) {
			if (temperature >= b.getMinTemperature() && temperature <= b.getMaxTemperature() && moisture >= b.getMinMoisture() && moisture <= b.getMaxMoisture() && heightNoise >= b.getMinHeight() && heightNoise <= b.getMaxHeight()) {
				possibleBiomes.add(b);
			}
		}
		
		if (possibleBiomes.isEmpty()) {
			throw new RuntimeException("No biome found for " + heightNoise + "/" + temperature + "/" + moisture);
		}
		
		NoiseGenerator gen = new SimplexNoiseGenerator(seed);
		double factor = 0.5;
		double noise = (gen.noise(factor * gridX, factor * gridZ) * 0.5 + 0.5) * 0.99999;
		//System.out.println("Noise: " + noise + "/" + (int) (noise * (possibleBiomes.size())) + "/" + possibleBiomes.size());
		biome = possibleBiomes.get((int) (noise * (possibleBiomes.size())));
		return new GridBiomePoint(biome.getBiome(), x, z, pointWeight);
	}
	
	@Override
	public BiomeGenerator getBiome(int x, int z) {
		int height = generator.getHeight(x, z);
		int startHeight = generator.getStartHeight(x, z);
		double heightNoise = generator.getHeightNoise(x, z);
		
		int gridX = (int) Math.floor((double) x/gridSize);
		int gridZ = (int) Math.floor((double) z/gridSize);
		
		GridBiomePoint tl = getGridBiome(gridX, gridZ);
		GridBiomePoint tr = getGridBiome(gridX + 1, gridZ);
		GridBiomePoint bl = getGridBiome(gridX, gridZ + 1);
		GridBiomePoint br = getGridBiome(gridX + 1, gridZ + 1);
		
		GridBiomePoint biome = null;
		double distance = Double.MAX_VALUE;
		for (GridBiomePoint p : Arrays.asList(tl, tr,bl, br)) {
			double d = Math.pow(x - p.getX(), 2) + Math.pow(z - p.getZ(), 2);
			d *= p.getPointWeight();
			if (d < distance) {
				distance = d;
				biome = p;
			}
		}
		
		return new GridBiomeGenerator(biome.getBiome(), x, z, startHeight, height, heightNoise);
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
	
	static class GridBiomePoint {
		
		private CustomBiome biome;
		private int x;
		private int z;
		private double pointWeight;
		
		
		public GridBiomePoint(CustomBiome biome, int x, int z, double pointWeight) {
			super();
			this.biome = biome;
			this.x = x;
			this.z = z;
			this.pointWeight = pointWeight;
		}

		public double getPointWeight() {
			return pointWeight;
		}

		public void setPointWeight(double pointWeight) {
			this.pointWeight = pointWeight;
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
		
	}
	
}

class GridBiomeGenerator implements BiomeGenerator {

	private CustomBiome biome;
	private int x;
	private int z;
	private int startHeight;
	private int height;
	private double heightNoise;

	public GridBiomeGenerator(CustomBiome biome, int x, int z, int startHeight, int height, double heightNoise) {
		super();
		this.biome = biome;
		this.x = x;
		this.z = z;
		this.startHeight = startHeight;
		this.height = height;
		this.heightNoise = heightNoise;
	}

	@Override
	public int getBaseHeight() {
		return height;
	}

	@Override
	public void generateNoise(ChunkData data, int x, int z, Random random) {
		if (height > startHeight) {
			biome.generateNoise(data, x, z, startHeight, height, heightNoise, random);
		}
	}

	@Override
	public void generateSurface(ChunkData data, int x, int z, Random random) {
		if (height > startHeight) {
			biome.generateSurface(data, x, z, startHeight, height, heightNoise, random);
		}
		
	}

	@Override
	public void populate(Chunk chunk, Random random) {
		if (height > startHeight) {
			biome.populate(chunk, random);
		}
	}

	@Override
	public Biome getBiome(int y) {
		return biome.getBiome(x, y, z, height, heightNoise);
	}

}
