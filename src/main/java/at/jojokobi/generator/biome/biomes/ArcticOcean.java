package at.jojokobi.generator.biome.biomes;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator.ChunkData;
import org.bukkit.util.noise.NoiseGenerator;
import org.bukkit.util.noise.SimplexNoiseGenerator;

import at.jojokobi.generator.biome.CustomBiome;
import at.jojokobi.generator.biome.GenerationData;

public class ArcticOcean implements CustomBiome{

	public ArcticOcean() {

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
		NoiseGenerator noise = new SimplexNoiseGenerator(data.getSeed() + 134482);
		if (data.getSeaLevel() - 6 > data.getHeight()) {
			double mul = 0.005;
			if (noise.noise(x * mul, z * mul) > 0.7) {
				chunk.setBlock(x, data.getSeaLevel(), z, Material.ICE);
			}
		}
	}
	
	@Override
	public Biome getBiome(int x, int y, int z, GenerationData data) {
		return data.getHeightNoise() >= 0 ? Biome.BEACH : Biome.COLD_OCEAN;
	}

	@Override
	public void populate(Chunk chunk, Random random) {
		
	}
	
}
