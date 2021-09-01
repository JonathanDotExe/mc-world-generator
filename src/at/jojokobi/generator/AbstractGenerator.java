package at.jojokobi.generator;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.bukkit.HeightMap;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;

import at.jojokobi.generator.biome.BiomeSystem;
import at.jojokobi.generator.biome.CustomBiome;

//FIXME min and max heights from info
public abstract class AbstractGenerator extends ChunkGenerator implements ValueGeneratorBuilder, BiomeSystemBuilder{

	public static final int CHUNK_SIZE = 16;
	public static final int WATER_HEIGHT = 64;
	
	private Map<UUID, BiomeSystem> systems = new HashMap<UUID, BiomeSystem>();
	
	@Override
	public void generateNoise(WorldInfo world, Random random, int x, int z, ChunkData data) {
		ValueGenerator generator = createValueGenerator(world.getSeed());
		BiomeSystem system = createBiomeSystem(world.getSeed());
		
		for (int i = 0; i < CHUNK_SIZE; i++) {
			for (int j = 0; j < CHUNK_SIZE; j++) {
				int totalX = x*CHUNK_SIZE + i;
				int totalZ = z*CHUNK_SIZE + j;
				//Calc Height
				int startHeight = generator.getStartHeight(totalX, totalZ);
				int height = generator.getHeight(totalX, totalZ);
				double heightNoise = generator.getHeightNoise(totalX, totalZ);
				
				CustomBiome biome = system.getBiome(totalX, totalZ);
				if (generator.canPopulate(totalX, totalZ)) {
					biome.generate(data, i, j, startHeight, height, heightNoise, random);
				}
			}
		}
	}
	
	public BiomeSystem getBiomeSystem(WorldInfo world) {
		if (!systems.containsKey(world.getUID())) {
			systems.put(world.getUID(), createBiomeSystem(world.getSeed()));
		}
		return systems.get(world.getUID());
	}
	
	@Override
	public int getBaseHeight(WorldInfo world, Random random, int x, int z, HeightMap heightMap) {
		ValueGenerator generator = createValueGenerator(world.getSeed());
		return generator.getHeight(x, z);
	}
	
	@Override
	public BiomeProvider getDefaultBiomeProvider(WorldInfo world) {
		return createBiomeSystem(world.getSeed());
	}
	
}
