package at.jojokobi.generator.biome.biomes;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.generator.ChunkGenerator.ChunkData;

import at.jojokobi.generator.AbstractGenerator;
import at.jojokobi.generator.biome.CustomBiome;
import at.jojokobi.mcutil.generation.TerrainGenUtil;

public class Plains implements CustomBiome{

	public Plains() {
//		super(0.1, 0.3, 0.4, 0.7, 0.3, 0.6);
	}

	@Override
	public double getHeightMultiplier() {
		return 0.3;
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

	}
	
	public Biome getBiome(int x, int y, int z, int height, double heightNoise) {
		return Biome.PLAINS;
	}
	
	@Override
	public void populate(Chunk chunk, Random random) {
		for (int x = 0; x < TerrainGenUtil.CHUNK_WIDTH; x++) {
			for (int z = 0; z < TerrainGenUtil.CHUNK_LENGTH; z++) {
				int chance = random.nextInt(128);
				int height = chunk.getWorld().getHighestBlockYAt(chunk.getX() * AbstractGenerator.CHUNK_SIZE + x, chunk.getZ() * AbstractGenerator.CHUNK_SIZE + z) + 1;
				
				if (chunk.getBlock(x, height - 1, z).getType() != Material.AIR) {
					if (chance < 20) {
						chunk.getBlock(x, height, z).setType(Material.GRASS);
					}
					else if (chance < 22) {
						chunk.getBlock(x, height, z).setType(Material.FERN);
					}
					else if (chance < 24) {
						chunk.getBlock(x, height, z).setType(Material.DANDELION);
					}
					else if (chance < 26) {
						chunk.getBlock(x, height, z).setType(Material.POPPY);
					}
					//Bush
					else if (chance < 27) {
						Leaves leaves = (Leaves) Material.OAK_LEAVES.createBlockData();
						leaves.setPersistent(true);
						chunk.getBlock(x, height, z).setBlockData(leaves);
					}
				}
			}
		}
	}

}
