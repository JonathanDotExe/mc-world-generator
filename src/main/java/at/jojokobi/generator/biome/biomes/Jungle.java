package at.jojokobi.generator.biome.biomes;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.HeightMap;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Biome;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.generator.ChunkGenerator.ChunkData;

import at.jojokobi.generator.AbstractGenerator;
import at.jojokobi.generator.biome.CustomBiome;
import at.jojokobi.generator.biome.GenerationData;
import at.jojokobi.mcutil.generation.TerrainGenUtil;

public class Jungle implements CustomBiome{
	
	private static final TreeType[] TREE_TYPES = {TreeType.JUNGLE_BUSH, TreeType.COCOA_TREE, TreeType.SMALL_JUNGLE, TreeType.SMALL_JUNGLE, TreeType.TREE};

	public Jungle() {
//		super(0.1, 0.3, 0.7, 1.0, 0.7, 1.1);
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
		return Biome.JUNGLE;
	}
	
	@Override
	public void populate(Chunk chunk, Random random) {
		//Trees
		{
			int count = random.nextInt(10);
			for (int i = 0; i < count; i++) {
				int x = random.nextInt(TerrainGenUtil.CHUNK_WIDTH - 2) + 1;
				int z = random.nextInt(TerrainGenUtil.CHUNK_LENGTH - 2) + 1;
				
				int height = chunk.getWorld().getHighestBlockYAt(chunk.getX() * AbstractGenerator.CHUNK_SIZE + x, chunk.getZ() * AbstractGenerator.CHUNK_SIZE + z, HeightMap.WORLD_SURFACE_WG) + 1;
				chunk.getWorld().generateTree(chunk.getBlock(x, height, z).getLocation(), TREE_TYPES[random.nextInt(TREE_TYPES.length)]);
			}
		}
		//Bushes
		{
			int count = random.nextInt(20);
			for (int i = 0; i < count; i++) {
				int x = random.nextInt(TerrainGenUtil.CHUNK_WIDTH );
				int z = random.nextInt(TerrainGenUtil.CHUNK_LENGTH);
				
				int height = chunk.getWorld().getHighestBlockYAt(chunk.getX() * AbstractGenerator.CHUNK_SIZE + x, chunk.getZ() * AbstractGenerator.CHUNK_SIZE + z, HeightMap.WORLD_SURFACE_WG) + 1;
				
				if (chunk.getBlock(x, height, z).getType() == Material.AIR && chunk.getBlock(x, height - 1, z).getType() != Material.AIR) {
					Leaves leaves = (Leaves) Material.JUNGLE_LEAVES.createBlockData();
					leaves.setPersistent(true);
					chunk.getBlock(x, height, z).setBlockData(leaves, false);
				}
			}
		}
		//Melons
		if (random.nextInt(10) == 0) {
			int count = random.nextInt(10);
			for (int i = 0; i < count; i++) {
				int x = random.nextInt(TerrainGenUtil.CHUNK_WIDTH );
				int z = random.nextInt(TerrainGenUtil.CHUNK_LENGTH);
				
				int height = chunk.getWorld().getHighestBlockYAt(chunk.getX() * AbstractGenerator.CHUNK_SIZE + x, chunk.getZ() * AbstractGenerator.CHUNK_SIZE + z, HeightMap.WORLD_SURFACE_WG) + 1;
				
				if (chunk.getBlock(x, height, z).getType() == Material.AIR && chunk.getBlock(x, height - 1, z).getType() != Material.AIR) {
					chunk.getBlock(x, height, z).setType(Material.MELON, false);
				}
			}
		}
	}

}
