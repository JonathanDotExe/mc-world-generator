package at.jojokobi.generator.biome.biomes;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator.ChunkData;

import at.jojokobi.generator.biome.CustomBiome;
import at.jojokobi.generator.biome.GenerationData;

public class VolcanoMountains implements CustomBiome{

	public VolcanoMountains() {
//		super(0.5, 1, 0.7, 1.0, 0.2, 0.5);
	}

	@Override
	public double getHeightMultiplier() {
		return 2;
	}
	
	@Override
	public void generateNoise(ChunkData chunk, int x, int z, GenerationData data, Random random) {
		for (int y = data.getStartHeight(); y < data.getHeight(); y++) {
			//Lava
			if (data.getHeightNoise() * data.getHeightMultiplier() > 1) {
				if (y < data.getHeight() - 3) {
					chunk.setBlock(x, y, z, Material.LAVA);
				}
			}
			else {
				chunk.setBlock(x, y, z, Material.STONE);
			}
		}
	}
	
	@Override
	public void generateSurface(ChunkData chunk, int x, int z, GenerationData data, Random random) {
		for (int y = data.getStartHeight(); y < data.getHeight(); y++) {
			//Lava
			if (data.getHeightNoise() * data.getHeightMultiplier() > 1) {
				if (y < data.getHeight() - 3) {
					chunk.setBlock(x, y, z, Material.LAVA);
				}
			}
			else {
				chunk.setBlock(x, y, z, Material.STONE);
			}
		}
	}
	
	@Override
	public Biome getBiome(int x, int y, int z, GenerationData data) {
		return Biome.STONY_PEAKS;
	}

	@Override
	public void populate(Chunk chunk, Random random) {
		
	}
	
}
