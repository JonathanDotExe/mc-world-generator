package at.jojokobi.generator.biome.biomes;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.HeightMap;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator.ChunkData;

import at.jojokobi.generator.AbstractGenerator;
import at.jojokobi.generator.biome.CustomBiome;
import at.jojokobi.generator.biome.GenerationData;
import at.jojokobi.mcutil.generation.TerrainGenUtil;

public class DarkForest implements CustomBiome{
	
	private static final TreeType[] TREE_TYPES = {TreeType.TREE, TreeType.BIG_TREE, TreeType.DARK_OAK, TreeType.DARK_OAK, TreeType.DARK_OAK, TreeType.BROWN_MUSHROOM, TreeType.RED_MUSHROOM};

	public DarkForest() {
//		super(0.1, 0.3, 0.4, 0.7, 0.4, 0.7);
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
		return Biome.DARK_FOREST;
	}
	
	@Override
	public void populate(Chunk chunk, Random random) {
		//Trees
		{
			int count = random.nextInt(15);
			for (int i = 0; i < count; i++) {
				int x = random.nextInt(TerrainGenUtil.CHUNK_WIDTH - 3) + 1;
				int z = random.nextInt(TerrainGenUtil.CHUNK_LENGTH - 3) + 1;
				
				int height = chunk.getWorld().getHighestBlockYAt(chunk.getX() * AbstractGenerator.CHUNK_SIZE + x, chunk.getZ() * AbstractGenerator.CHUNK_SIZE + z, HeightMap.WORLD_SURFACE_WG) + 1;
				chunk.getWorld().generateTree(chunk.getBlock(x, height, z).getLocation(), TREE_TYPES[random.nextInt(TREE_TYPES.length)]);
			}
		}
	}

}
