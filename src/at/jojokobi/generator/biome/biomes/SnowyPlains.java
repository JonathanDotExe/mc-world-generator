package at.jojokobi.generator.biome.biomes;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator.ChunkData;

import at.jojokobi.generator.AbstractGenerator;
import at.jojokobi.generator.biome.CustomBiome;
import at.jojokobi.mcutil.generation.TerrainGenUtil;

public class SnowyPlains implements CustomBiome{

	public SnowyPlains() {
//		super(0, 0.2, 0.0, 0.4, 0.2, 0.6);
	}

	@Override
	public void generateNoise(ChunkData data, int x, int z, int startHeight, int height, double noiseHeight, Random random) {
		for (int y = startHeight; y < height; y++) {
			if (y == height - 1) {
				data.setBlock(x, y, z, Material.GRASS_BLOCK);
			}
			else if (y >= height - 5) {
				data.setBlock(x, y, z, Material.DIRT);
			}
			else {
				data.setBlock(x, y, z, Material.STONE);
			}
		}
	}
	
	@Override
	public void generateSurface(ChunkData data, int x, int z, int startHeight, int height, double noiseHeight, Random random) {
		data.setBlock(x, height, z, Material.SNOW);
	}
	
	
	public Biome getBiome(int x, int y, int z, int height, double heightNoise) {
		return Biome.ICE_SPIKES;
	}
	
	@Override
	public void populate(Chunk chunk, Random random) {
		//Trees
		if (random.nextBoolean()) {
			int x = random.nextInt(TerrainGenUtil.CHUNK_WIDTH - 2) + 1;
			int z = random.nextInt(TerrainGenUtil.CHUNK_LENGTH - 2) + 1;
			
			int height = chunk.getWorld().getHighestBlockYAt(chunk.getX() * AbstractGenerator.CHUNK_SIZE, chunk.getZ() * AbstractGenerator.CHUNK_SIZE);
			chunk.getWorld().generateTree(chunk.getBlock(x, height, z).getLocation(), TreeType.TREE);
		}
		//Bushes
		{
			int count = random.nextInt(10);
			for (int i = 0; i < count; i++) {
				int x = random.nextInt(TerrainGenUtil.CHUNK_WIDTH );
				int z = random.nextInt(TerrainGenUtil.CHUNK_LENGTH);
				
				int height = chunk.getWorld().getHighestBlockYAt(chunk.getX() * AbstractGenerator.CHUNK_SIZE, chunk.getZ() * AbstractGenerator.CHUNK_SIZE);
				if (chunk.getBlock(x, height - 1, z).getType() != Material.AIR) {
					chunk.getBlock(x, height, z).setType(Material.SNOW_BLOCK, false);
				}
			}
		}
	}

}
