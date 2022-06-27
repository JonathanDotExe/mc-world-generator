package at.jojokobi.generator.biome.biomes;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator.ChunkData;

import at.jojokobi.generator.biome.CustomBiome;
import at.jojokobi.generator.biome.GenerationData;

public class Ocean implements CustomBiome{

	public Ocean() {
//		super(-0.5, 0, 0.4, 0.6, 0.2, 0.8);
	}

	@Override
	public void generateNoise(ChunkData chunk, int x, int z, GenerationData data, Random random) {
		for (int y = data.getStartHeight(); y < data.getHeight(); y++) {
			if (y >= data.getHeight() - 5) {
				chunk.setBlock(x, y, z, Material.SAND);
			}
			else {
				chunk.setBlock(x, y, z, Material.STONE);
			}
		}
	}
	
	@Override
	public void generateSurface(ChunkData chunk, int x, int z, GenerationData data, Random random) {
		
	}
	
	@Override
	public Biome getBiome(int x, int y, int z, GenerationData data) {
		return data.getHeight() >= data.getSeaLevel() ? Biome.BEACH : (data.getHeight() > data.getSeaLevel() - 15 ? Biome.OCEAN : Biome.DEEP_OCEAN);
	}

	@Override
	public void populate(Chunk chunk, Random random) {
		
	}
	
}
