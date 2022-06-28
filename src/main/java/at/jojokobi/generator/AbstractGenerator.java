package at.jojokobi.generator;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.bukkit.World.Environment;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import at.jojokobi.generator.biome.BiomeGenerator;
import at.jojokobi.generator.biome.BiomeSystem;

public abstract class AbstractGenerator extends ChunkGenerator implements BiomeSystemBuilder{

	public static final int CHUNK_SIZE = 16;
	public static final int WATER_HEIGHT = 64;
	
	private LoadingCache<WorldInfoCacheEntry, BiomeSystem> biomeSystemCache;

	public AbstractGenerator() {
		this.biomeSystemCache = CacheBuilder.newBuilder()
			.maximumSize(500)
			.expireAfterAccess(10, TimeUnit.MINUTES)
			.build(new CacheLoader<WorldInfoCacheEntry, BiomeSystem>() {
				@Override
				public BiomeSystem load(WorldInfoCacheEntry key) throws Exception {
					return createBiomeSystem(key);
				}
			});
	}
	
	@Override
	public void generateNoise(WorldInfo world, Random random, int x, int z, ChunkData data) {
		BiomeSystem system = getBiomeSystem(world);
		
		for (int i = 0; i < CHUNK_SIZE; i++) {
			for (int j = 0; j < CHUNK_SIZE; j++) {
				int totalX = x*CHUNK_SIZE + i;
				int totalZ = z*CHUNK_SIZE + j;
				BiomeGenerator biome = system.getBiome(totalX, totalZ);
				biome.generateNoise(data, i, j, random);
			}
		}
	}
	
	@Override
	public void generateSurface(WorldInfo world, Random random, int x, int z, ChunkData data) {
		BiomeSystem system = getBiomeSystem(world);
		
		for (int i = 0; i < CHUNK_SIZE; i++) {
			for (int j = 0; j < CHUNK_SIZE; j++) {
				int totalX = x*CHUNK_SIZE + i;
				int totalZ = z*CHUNK_SIZE + j;
				BiomeGenerator biome = system.getBiome(totalX, totalZ);
				biome.generateSurface(data, i, j, random);
			}
		}
	}
	
	public BiomeSystem getBiomeSystem(WorldInfo world) {
		try {
			return biomeSystemCache.get(new WorldInfoCacheEntry(world));
		} catch (ExecutionException e) {
			e.printStackTrace();
			return createBiomeSystem(world);
		}
	}
	
	@Override
	public BiomeProvider getDefaultBiomeProvider(WorldInfo world) {
		return getBiomeSystem(world);
	}
	
}

class WorldInfoCacheEntry implements WorldInfo {

	private Environment environment;
	private int maxHeight;
	private int minHeight;
	private String name;
	private long seed;
	private UUID uuid;
	
	public WorldInfoCacheEntry(Environment environment, int maxHeight, int minHeight, String name, long seed,
			UUID uuid) {
		super();
		this.environment = environment;
		this.maxHeight = maxHeight;
		this.minHeight = minHeight;
		this.name = name;
		this.seed = seed;
		this.uuid = uuid;
	}
	
	public WorldInfoCacheEntry(WorldInfo info) {
		this(info.getEnvironment(), info.getMaxHeight(), info.getMinHeight(), info.getName(), info.getSeed(), info.getUID());
	}

	@Override
	public Environment getEnvironment() {
		return environment;
	}

	@Override
	public int getMaxHeight() {
		return maxHeight;
	}

	@Override
	public int getMinHeight() {
		return minHeight;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public long getSeed() {
		return seed;
	}

	@Override
	public UUID getUID() {
		return uuid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
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
		WorldInfoCacheEntry other = (WorldInfoCacheEntry) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}
	
}
