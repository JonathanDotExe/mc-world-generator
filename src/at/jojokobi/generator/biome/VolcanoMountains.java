package at.jojokobi.generator.biome;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator.ChunkData;

import at.jojokobi.generator.ValueGenerator;

public class VolcanoMountains implements CustomBiome{

	public VolcanoMountains() {
//		super(0.5, 1, 0.7, 1.0, 0.2, 0.5);
	}

	@Override
	public Biome generate(ChunkData data, int x, int z, int startHeight, int height, double noiseHeight, Random random) {
		for (int y = startHeight; y < height; y++) {
			//Lava
			if (noiseHeight > 0.85) {
				if (y < height - 3) {
					data.setBlock(x, y, z, Material.LAVA);
				}
			}
			else {
				data.setBlock(x, y, z, Material.STONE);
			}
		}
		
		return Biome.GRAVELLY_MOUNTAINS;
	}

	@Override
	public void populate(Chunk chunk, ValueGenerator generator, Random random) {
		
	}
	
}
