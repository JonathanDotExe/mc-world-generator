package at.jojokobi.generator.biome;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator.ChunkData;

import at.jojokobi.generator.ValueGenerator;
import at.jojokobi.mcutil.generation.TerrainGenUtil;

public class Desert implements CustomBiome{

	public Desert() {
//		super(0, 0.3, 0.5, 1.5, 0.0, 0.5);
	}

	@Override
	public Biome generate(ChunkData data, int x, int z, int startHeight, int height, double noiseHeight, Random random) {

		for (int y = startHeight; y < height; y++) {
			if (y >= height - 5) {
				data.setBlock(x, y, z, Material.SAND);
			} else if (y >= height - 10) {
				data.setBlock(x, y, z, Material.SANDSTONE);
			} else {
				data.setBlock(x, y, z, Material.STONE);
			}
		}

		return Biome.DESERT;
	}

	@Override
	public void populate(Chunk chunk, ValueGenerator generator, Random random) {
		//Cactus
		{
			int count = random.nextInt(10);
			for (int i = 0; i < count; i++) {
				int x = random.nextInt(TerrainGenUtil.CHUNK_WIDTH - 2) + 1;
				int z = random.nextInt(TerrainGenUtil.CHUNK_LENGTH - 2) + 1;
				
				int height = generator.getHeight(TerrainGenUtil.CHUNK_WIDTH * chunk.getX() + x, TerrainGenUtil.CHUNK_LENGTH * chunk.getZ() + z);
				boolean broken = false;
				int cactusHeight = random.nextInt(3);
				if (chunk.getBlock(x, height - 1, z).getType() != Material.AIR) {
					for (int j = 0; j <= cactusHeight && !broken; j++) {
						if (chunk.getBlock(x - 1, height + j, z).getType() == Material.AIR
								&& chunk.getBlock(x, height + j, z - 1).getType() == Material.AIR
								&& chunk.getBlock(x + 1, height + j, z).getType() == Material.AIR
								&& chunk.getBlock(x, height + j, z + 1).getType() == Material.AIR) {
							chunk.getBlock(x, height + j, z).setType(Material.CACTUS, false);
						}
						else {
							broken = true;
						}
					}
				}
			}
		}
		// Dead Bush
		{
			int count = random.nextInt(5);
			for (int i = 0; i < count; i++) {
				int x = random.nextInt(TerrainGenUtil.CHUNK_WIDTH);
				int z = random.nextInt(TerrainGenUtil.CHUNK_LENGTH);
				
				int height = generator.getHeight(TerrainGenUtil.CHUNK_WIDTH * chunk.getX() + x, TerrainGenUtil.CHUNK_LENGTH * chunk.getZ() + z);
				if (chunk.getBlock(x, height - 1, z).getType() != Material.AIR) {
					chunk.getBlock(x, height, z).setType(Material.DEAD_BUSH, false);
				}
			}
		}
	}

}
