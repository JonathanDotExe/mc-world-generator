package at.jojokobi.generator.biome.biomes;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator.ChunkData;

import at.jojokobi.generator.biome.CustomBiome;

public class ArcticOcean implements CustomBiome{

	public ArcticOcean() {
//		super(-0.7, -0.1, 0.0, 0.4, 0.0, 0.5);
	}

	@Override
	public void generateNoise(ChunkData data, int x, int z, int startHeight, int height, double heightNoise, Random random) {
		for (int y = startHeight; y < height; y++) {
			if (y >= height - 5) {
				data.setBlock(x, y, z, Material.SAND);
			}
			else {
				data.setBlock(x, y, z, Material.STONE);
			}
		}
	}
	
	@Override
	public void generateSurface(ChunkData data, int x, int z, int startHeight, int height, double heightNoise, Random random) {
		//TODO ice shards
	}
	
	@Override
	public Biome getBiome(int x, int y, int z, int height, double heightNoise) {
		return heightNoise >= 0 ? Biome.BEACH : (heightNoise < 0.2 ? Biome.ICE_SPIKES : Biome.COLD_OCEAN);
	}

	@Override
	public void populate(Chunk chunk, Random random) {
		
	}
	
}
