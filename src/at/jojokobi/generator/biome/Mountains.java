package at.jojokobi.generator.biome;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator.ChunkData;

import at.jojokobi.generator.ValueGenerator;
import at.jojokobi.mcutil.generation.TerrainGenUtil;

public class Mountains implements CustomBiome{

	public Mountains() {
//		super(0.4, 0.8, 0.2, 0.5, 0.3, 0.5);
	}

	@Override
	public void generate(ChunkData data, int x, int z, int startHeight, int height, double noiseHeight, Random random) {
		
		for (int y = startHeight; y < height; y++) {
			if (noiseHeight < 0.6 && y == height - 1) {
				data.setBlock(x, y, z, Material.GRASS_BLOCK);
			}
			else if (noiseHeight > 0.6 && y >= height - 5) {
				data.setBlock(x, y, z, Material.DIRT);
			}
			else if (noiseHeight > 0.75 && y == height - 1) {
				data.setBlock(x, y, z, Material.SNOW_BLOCK);
			}
			else {
				data.setBlock(x, y, z, Material.STONE);
			}
		}
		//Snow
		if (height > 95) {
			data.setBlock(x, height, z, Material.SNOW);
		}
		else if (height > 90 && random.nextBoolean()) {
			data.setBlock(x, height, z, Material.SNOW);
		}
	}
	
	public Biome getBiome(int x, int y, int z, int height, double heightNoise) {
		return height > 90 ? Biome.MOUNTAINS : Biome.MOUNTAIN_EDGE;
	}
	
	
	@Override
	public void populate(Chunk chunk, ValueGenerator generator, Random random) {
		if (random.nextBoolean()) {
			int x = random.nextInt(TerrainGenUtil.CHUNK_WIDTH );
			int z = random.nextInt(TerrainGenUtil.CHUNK_LENGTH);
			
			int height = generator.getHeight(TerrainGenUtil.CHUNK_WIDTH * chunk.getX() + x, TerrainGenUtil.CHUNK_LENGTH * chunk.getZ() + z);
			
			chunk.getWorld().generateTree(chunk.getBlock(x, height, z).getLocation(), TreeType.TREE);
		}
	}
	
}
