package at.jojokobi.generator.biome.biomes;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator.ChunkData;

import at.jojokobi.generator.biome.CustomBiome;

public class Ocean implements CustomBiome{

	public Ocean() {
//		super(-0.5, 0, 0.4, 0.6, 0.2, 0.8);
	}

	@Override
	public void generateNoise(ChunkData chunk, int x, int z, int startHeight, int height, double noiseHeight, Random random) {

		for (int y = startHeight; y < height; y++) {
			if (y >= height - 5) {
				chunk.setBlock(x, y, z, Material.SAND);
			}
			else {
				chunk.setBlock(x, y, z, Material.STONE);
			}
		}
	}
	
	@Override
	public void generateSurface(ChunkData chunk, int x, int z, int startHeight, int height, double noiseHeight, Random random) {
		
	}
	
	@Override
	public Biome getBiome(int x, int y, int z, int height, double heightNoise) {
		return height >= 64 ? Biome.BEACH : (height > 50 ? Biome.OCEAN : Biome.DEEP_OCEAN);
	}

	@Override
	public void populate(Chunk chunk, Random random) {
		
	}
	
}
