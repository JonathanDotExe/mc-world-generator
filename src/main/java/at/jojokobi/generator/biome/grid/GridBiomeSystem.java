package at.jojokobi.generator.biome.grid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.bukkit.block.Biome;
import org.bukkit.generator.WorldInfo;
import org.bukkit.util.noise.NoiseGenerator;
import org.bukkit.util.noise.SimplexNoiseGenerator;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import at.jojokobi.generator.biome.BiomeGenerator;
import at.jojokobi.generator.biome.BiomeSystem;
import at.jojokobi.generator.biome.NoiseValueGenerator;
import at.jojokobi.generator.biome.ValueGenerator;
import at.jojokobi.mcutil.generation.TerrainGenUtil;

public class GridBiomeSystem extends BiomeSystem {
	
	
	private static final List<Biome> BIOMES = new ArrayList<>();
	
	static {
		BIOMES.addAll(Arrays.asList(Biome.values()));
		BIOMES.remove(Biome.CUSTOM);
	}
	
	private List<GridBiomeEntry> biomes = new ArrayList<GridBiomeEntry> ();
	private List<GridBiomeEntry> oceanBiomes = new ArrayList<GridBiomeEntry> ();
	private ValueGenerator generator;
	private long seed;
	private int gridSize = 256;
	
	//Cache for saving already generated biomes
	private LoadingCache<GridBiomeCacheKey, GridBiomePoint> biomeCache;

	public GridBiomeSystem(long seed, int minHeight, int maxHeight) {
		super();
		this.seed = seed;
		this.generator = new NoiseValueGenerator(seed, minHeight, maxHeight);
		this.biomeCache = CacheBuilder.newBuilder()
				.maximumSize(10000)
				.expireAfterAccess(3, TimeUnit.MINUTES)
				.build(new CacheLoader<GridBiomeCacheKey, GridBiomePoint>() {
					@Override
					public GridBiomePoint load(GridBiomeCacheKey key) throws Exception {
						return createGridBiome(key.getGridX(), key.getGridZ(), key.isOcean());
					}
				});
	}

	public void registerBiome(GridBiomeEntry biome) {
		biomes.add(biome);
	}
	
	public void registerOceanBiome(GridBiomeEntry biome) {
		oceanBiomes.add(biome);
	}
	
	private GridBiomePoint createGridBiome(int gridX, int gridZ, boolean ocean) {
		//Biomes list
		List<GridBiomeEntry> biomes = ocean ? this.biomes : oceanBiomes;
		//Point position
		Random random = new Random(TerrainGenUtil.generateValueBasedSeed(seed, gridX, 0, gridZ));
		int pointX = random.nextInt(gridSize);
		int pointZ = random.nextInt(gridSize);
		double pointWeight = 0.5 + random.nextDouble();
		int x = gridX * gridSize + pointX;
		int z = gridZ * gridSize + pointZ;
		
		//Noise values
		double temperature = generator.getTemperature(x, z);
		double moisture = generator.getMoisture(x, z);
		double heightNoise = generator.getHeightNoise(x, z);
		
		GridBiomeEntry biome = null;
		List<GridBiomeEntry> possibleBiomes = new ArrayList<>();
		//Land
		for (GridBiomeEntry b : biomes) {
			if (temperature >= b.getMinTemperature() && temperature <= b.getMaxTemperature() && moisture >= b.getMinMoisture() && moisture <= b.getMaxMoisture()) {
				possibleBiomes.add(b);
			}
		}
		
		if (possibleBiomes.isEmpty()) {
			throw new RuntimeException("No biome found for " + heightNoise + "/" + temperature + "/" + moisture);
		}
		
		NoiseGenerator gen = new SimplexNoiseGenerator(seed);
		double factor = 0.5;
		double noise = (gen.noise(factor * gridX, factor * gridZ) * 0.5 + 0.5) * 0.99999;
		biome = possibleBiomes.get((int) (noise * (possibleBiomes.size())));
		return new GridBiomePoint(biome.getBiome(), x, z, pointWeight);
	}
	
	private GridBiomePoint getGridBiome(int gridX, int gridZ, boolean ocean) {
		try {
			return biomeCache.get(new GridBiomeCacheKey(ocean, gridX, gridZ));
		} catch (ExecutionException e) {
			e.printStackTrace();
			return createGridBiome(gridX, gridZ, ocean);
		}
	}
	
	@Override
	public BiomeGenerator getBiome(int x, int z) {
		int gridX = (int) Math.floor((double) x/gridSize);
		int gridZ = (int) Math.floor((double) z/gridSize);
		
		double heightNoise = generator.getHeightNoise(x, z);
		boolean ocean = heightNoise >= 0;
		
		//Get biomes around
		GridBiomePoint tl = getGridBiome(gridX, gridZ, ocean);
		GridBiomePoint tr = getGridBiome(gridX + 1, gridZ, ocean);
		GridBiomePoint bl = getGridBiome(gridX, gridZ + 1, ocean);
		GridBiomePoint br = getGridBiome(gridX + 1, gridZ + 1, ocean);
		
		GridBiomePoint biome = null;
		double distance = Double.MAX_VALUE;
		double heightFactor = 0.0;
		double totalDistance = 0.0;
		for (GridBiomePoint p : Arrays.asList(tl, tr,bl, br)) {
			double d = Math.pow(x - p.getX(), 2) + Math.pow(z - p.getZ(), 2);
			d *= p.getPointWeight();
			if (d < distance) {
				distance = d;
				biome = p;
			}
			//Height factor
			double dstRamp = (0.5 * gridSize)/Math.max(0.000001, d);
			totalDistance += dstRamp;
			heightFactor += p.getBiome().getHeightMultiplier() * dstRamp;
		}
		heightFactor /= totalDistance;
		
		int height = generator.getHeight(x, z, heightNoise * heightFactor);
		int startHeight = generator.getStartHeight(x, z);
		
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
	
	static class GridBiomeCacheKey {
		private boolean ocean;
		private int gridX;
		private int gridZ;
		
		public GridBiomeCacheKey(boolean ocean, int gridX, int gridZ) {
			super();
			this.ocean = ocean;
			this.gridX = gridX;
			this.gridZ = gridZ;
		}

		public int getGridX() {
			return gridX;
		}
		public void setGridX(int gridX) {
			this.gridX = gridX;
		}
		public int getGridZ() {
			return gridZ;
		}
		public void setGridZ(int gridZ) {
			this.gridZ = gridZ;
		}
		

		public boolean isOcean() {
			return ocean;
		}

		public void setOcean(boolean ocean) {
			this.ocean = ocean;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + gridX;
			result = prime * result + gridZ;
			result = prime * result + (ocean ? 1231 : 1237);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			GridBiomeCacheKey other = (GridBiomeCacheKey) obj;
			if (gridX != other.gridX)
				return false;
			if (gridZ != other.gridZ)
				return false;
			if (ocean != other.ocean)
				return false;
			return true;
		}
		
		
	}
	
}


