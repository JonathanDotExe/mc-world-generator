package at.jojokobi.generator.biome.biomes;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.HeightMap;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator.ChunkData;

import at.jojokobi.generator.AbstractGenerator;
import at.jojokobi.generator.biome.CustomBiome;
import at.jojokobi.generator.biome.GenerationData;
import at.jojokobi.mcutil.generation.TerrainGenUtil;

public class Desert implements CustomBiome{

	public Desert() {
//		super(0, 0.3, 0.5, 1.5, 0.0, 0.5);
	}
	
	@Override
	public double getHeightMultiplier() {
		return 0.8;
	}

	@Override
	public void generateNoise(ChunkData chunk, int x, int z, GenerationData data, Random random) {
		for (int y = data.getStartHeight(); y < data.getHeight(); y++) {
			if (y >= data.getHeight() - 5) {
				chunk.setBlock(x, y, z, Material.SAND);
			} else if (y >= data.getHeight() - 10) {
				chunk.setBlock(x, y, z, Material.SANDSTONE);
			} else {
				chunk.setBlock(x, y, z, Material.STONE);
			}
		}
	}
	
	@Override
	public void generateSurface(ChunkData chunk, int x, int z, GenerationData data, Random random) {
		
	}
	
	@Override
	public Biome getBiome(int x, int y, int z, GenerationData data) {
		return Biome.DESERT;
	}

	@Override
	public void populate(Chunk chunk, Random random) {
		//Cactus
		{
			int count = random.nextInt(10);
			for (int i = 0; i < count; i++) {
				int x = random.nextInt(TerrainGenUtil.CHUNK_WIDTH - 2) + 1;
				int z = random.nextInt(TerrainGenUtil.CHUNK_LENGTH - 2) + 1;
				
				int height = chunk.getWorld().getHighestBlockYAt(chunk.getX() * AbstractGenerator.CHUNK_SIZE + x, chunk.getZ() * AbstractGenerator.CHUNK_SIZE + z, HeightMap.WORLD_SURFACE_WG);
				boolean broken = false;
				int cactusHeight = random.nextInt(3);
				if (height > 0) {
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
				
				int height = chunk.getWorld().getHighestBlockYAt(chunk.getX() * AbstractGenerator.CHUNK_SIZE + x, chunk.getZ() * AbstractGenerator.CHUNK_SIZE + z, HeightMap.WORLD_SURFACE_WG);
				if (height > chunk.getWorld().getMinHeight()) {
					chunk.getBlock(x, height, z).setType(Material.DEAD_BUSH, false);
				}
			}
		}
	}

}
