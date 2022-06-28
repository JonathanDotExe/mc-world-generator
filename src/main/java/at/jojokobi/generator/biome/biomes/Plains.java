package at.jojokobi.generator.biome.biomes;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.HeightMap;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.generator.ChunkGenerator.ChunkData;

import at.jojokobi.generator.AbstractGenerator;
import at.jojokobi.generator.biome.CustomBiome;
import at.jojokobi.generator.biome.GenerationData;
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
	public void generateNoise(ChunkData chunk, int x, int z, GenerationData data, Random random) {
		for (int y = data.getStartHeight(); y < data.getHeight(); y++) {
			if (y == data.getHeight() - 1) {
				chunk.setBlock(x, y, z, Material.GRASS_BLOCK);
			}
			else if (y >= data.getHeight() - 5) {
				chunk.setBlock(x, y, z, Material.DIRT);
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
		return Biome.PLAINS;
	}
	
	@Override
	public void populate(Chunk chunk, Random random) {
		for (int x = 0; x < TerrainGenUtil.CHUNK_WIDTH; x++) {
			for (int z = 0; z < TerrainGenUtil.CHUNK_LENGTH; z++) {
				int chance = random.nextInt(256);
				int height = chunk.getWorld().getHighestBlockYAt(chunk.getX() * AbstractGenerator.CHUNK_SIZE + x, chunk.getZ() * AbstractGenerator.CHUNK_SIZE + z, HeightMap.OCEAN_FLOOR_WG) + 1;
				
				if (height > 0) {
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
