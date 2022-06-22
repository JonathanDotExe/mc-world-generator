package at.jojokobi.generator;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
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
	
	private LoadingCache<UUID, BiomeSystem> biomeSystemCache;

	public AbstractGenerator() {
		this.biomeSystemCache = CacheBuilder.newBuilder()
			.maximumSize(500)
			.expireAfterAccess(10, TimeUnit.MINUTES)
			.build(new CacheLoader<UUID, BiomeSystem>() {
				@Override
				public BiomeSystem load(UUID key) throws Exception {
					return createBiomeSystem(Bukkit.getWorld(key)); //FIXME world might not be available now?
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
			return biomeSystemCache.get(world.getUID());
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
