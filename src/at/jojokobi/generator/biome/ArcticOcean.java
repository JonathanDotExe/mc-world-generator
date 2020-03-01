package at.jojokobi.generator.biome;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator.ChunkData;

import at.jojokobi.generator.ValueGenerator;

public class ArcticOcean extends CustomBiome{

	public ArcticOcean() {
		super(-0.7, -0.1, 0.0, 0.4, 0.0, 0.5);
	}

	@Override
	public Biome generate(ChunkData data, int x, int z, int startHeight, int height, double heightNoise, Random random) {
		
		for (int y = startHeight; y < height; y++) {
			if (y >= height - 5) {
				data.setBlock(x, y, z, Material.SAND);
			}
			else {
				data.setBlock(x, y, z, Material.STONE);
			}
		}
		
		//Ice Shards
		if (height < 0.2) {
			for (int y = 0; y < -heightNoise * 20; y++) {
				data.setBlock(x, 64 + y, z, Material.ICE);
			}
		}
		
		return heightNoise >= 0 ? Biome.BEACH : (height < 0.2 ? Biome.ICE_SPIKES : Biome.COLD_OCEAN);
	}

	@Override
	public void populate(Chunk chunk, ValueGenerator generator, Random random) {
		
	}
	
}
