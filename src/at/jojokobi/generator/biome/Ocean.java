package at.jojokobi.generator.biome;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator.ChunkData;

import at.jojokobi.generator.ValueGenerator;

public class Ocean implements CustomBiome{

	public Ocean() {
//		super(-0.5, 0, 0.4, 0.6, 0.2, 0.8);
	}

	@Override
	public Biome generate(ChunkData data, int x, int z, int startHeight, int height, double noiseHeight, Random random) {
		
		for (int y = startHeight; y < height; y++) {
			if (y >= height - 5) {
				data.setBlock(x, y, z, Material.SAND);
			}
			else {
				data.setBlock(x, y, z, Material.STONE);
			}
		}
		
		return height >= 64 ? Biome.BEACH : (height > 50 ? Biome.OCEAN : Biome.DEEP_OCEAN);
	}

	@Override
	public void populate(Chunk chunk, ValueGenerator generator, Random random) {
		
	}
	
}
