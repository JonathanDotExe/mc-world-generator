package at.jojokobi.generator.biome.biomes;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Biome;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.generator.ChunkGenerator.ChunkData;

import at.jojokobi.generator.AbstractGenerator;
import at.jojokobi.generator.biome.CustomBiome;
import at.jojokobi.mcutil.generation.TerrainGenUtil;

public class Jungle implements CustomBiome{
	
	private static final TreeType[] TREE_TYPES = {TreeType.JUNGLE_BUSH, TreeType.COCOA_TREE, TreeType.SMALL_JUNGLE, TreeType.SMALL_JUNGLE, TreeType.TREE};

	public Jungle() {
//		super(0.1, 0.3, 0.7, 1.0, 0.7, 1.1);
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
		return Biome.JUNGLE;
	}
	
	@Override
	public void populate(Chunk chunk, Random random) {
		//Leafy ground
		/*NoiseGenerator gen = new SimplexNoiseGenerator(chunk.getWorld().getSeed() - 5894);
		for (int x = 0; x < TerrainGenUtil.CHUNK_WIDTH; x++) {
			for (int z = 0; z < TerrainGenUtil.CHUNK_LENGTH; z++) {
				int totalX = TerrainGenUtil.CHUNK_WIDTH * chunk.getX() + x;
				int totalZ = TerrainGenUtil.CHUNK_LENGTH * chunk.getZ() + z;
				
				int height = chunk.getWorld().getHighestBlockYAt(chunk.getX() * AbstractGenerator.CHUNK_SIZE + x, chunk.getZ() * AbstractGenerator.CHUNK_SIZE + z);
				if (chunk.getBlock(x, height - 1, z).getType() != Material.AIR) {
					int leafHeight = (int) (Math.round(gen.noise(totalX * 0.05, totalZ * 0.05) + 1));
					for (int y = 0; y < leafHeight; y++) {
						if (!chunk.getBlock(x, height + y, z).getType().isSolid()) {
							Leaves leaves = (Leaves) Material.JUNGLE_LEAVES.createBlockData();
							leaves.setPersistent(true);
							chunk.getBlock(x, height + y, z).setBlockData(leaves, false);
						}
					}
				}
			}
		}*/
		//Trees
		{
			int count = random.nextInt(10);
			for (int i = 0; i < count; i++) {
				int x = random.nextInt(TerrainGenUtil.CHUNK_WIDTH - 2) + 1;
				int z = random.nextInt(TerrainGenUtil.CHUNK_LENGTH - 2) + 1;
				
				int height = chunk.getWorld().getHighestBlockYAt(chunk.getX() * AbstractGenerator.CHUNK_SIZE + x, chunk.getZ() * AbstractGenerator.CHUNK_SIZE + z) + 1;
//				chunk.getBlock(x, height - 1, z).setType(Material.GRASS_BLOCK);
//				chunk.getBlock(x + 1, height - 1, z).setType(Material.GRASS_BLOCK);
//				chunk.getBlock(x, height - 1, z + 1).setType(Material.GRASS_BLOCK);
//				chunk.getBlock(x + 1, height - 1, z + 1).setType(Material.GRASS_BLOCK);
				chunk.getWorld().generateTree(chunk.getBlock(x, height, z).getLocation(), TREE_TYPES[random.nextInt(TREE_TYPES.length)]);
			}
		}
		//Bushes
		{
			int count = random.nextInt(20);
			for (int i = 0; i < count; i++) {
				int x = random.nextInt(TerrainGenUtil.CHUNK_WIDTH );
				int z = random.nextInt(TerrainGenUtil.CHUNK_LENGTH);
				
				int height = chunk.getWorld().getHighestBlockYAt(chunk.getX() * AbstractGenerator.CHUNK_SIZE + x, chunk.getZ() * AbstractGenerator.CHUNK_SIZE + z) + 1;
				
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
				
				int height = chunk.getWorld().getHighestBlockYAt(chunk.getX() * AbstractGenerator.CHUNK_SIZE + x, chunk.getZ() * AbstractGenerator.CHUNK_SIZE + z) + 1;
				
				if (chunk.getBlock(x, height, z).getType() == Material.AIR && chunk.getBlock(x, height - 1, z).getType() != Material.AIR) {
					chunk.getBlock(x, height, z).setType(Material.MELON, false);
				}
			}
		}
	}

}
