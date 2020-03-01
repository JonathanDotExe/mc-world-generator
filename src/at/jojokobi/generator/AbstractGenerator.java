package at.jojokobi.generator;

import java.util.Random;

import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;

import at.jojokobi.generator.biome.BiomeSystem;
import at.jojokobi.generator.biome.CustomBiome;

public abstract class AbstractGenerator extends ChunkGenerator implements ValueGeneratorBuilder, BiomeSystemBuilder{

	public static final int CHUNK_SIZE = 16;
	public static final int WATER_HEIGHT = 64;
	
	@Override
	public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid grid) {
		ChunkData data = createChunkData(world);
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
				Biome b = generator.canPopulate(totalX, totalZ) ? biome.generate(data, i, j, startHeight, height, heightNoise, random) : Biome.THE_VOID;
				
				for (int k = 0; k < 256; k++) {
					grid.setBiome(i, k, j, b);
				}
			}
		}
		return data;
	}
	
	
}
