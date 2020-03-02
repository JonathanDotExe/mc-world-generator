package at.jojokobi.generator.biome;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Biome;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.generator.ChunkGenerator.ChunkData;

import at.jojokobi.generator.ValueGenerator;
import at.jojokobi.mcutil.generation.TerrainGenUtil;

public class Forest implements CustomBiome{
	
	private static final TreeType[] TREE_TYPES = {TreeType.TREE, TreeType.TREE, TreeType.TREE, TreeType.BIG_TREE, TreeType.BIRCH, TreeType.DARK_OAK};

	public Forest() {
//		super(0.1, 0.3, 0.4, 0.7, 0.4, 0.7);
	}

	@Override
	public Biome generate(ChunkData data, int x, int z, int startHeight, int height, double noiseHeight, Random random) {
		
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
		
		return Biome.FOREST;
	}
	
	@Override
	public void populate(Chunk chunk, ValueGenerator generator, Random random) {
		//Trees
		{
			int count = random.nextInt(5);
			for (int i = 0; i < count; i++) {
				int x = random.nextInt(TerrainGenUtil.CHUNK_WIDTH - 2) + 1;
				int z = random.nextInt(TerrainGenUtil.CHUNK_LENGTH - 2) + 1;
				
				int height = generator.getHeight(TerrainGenUtil.CHUNK_WIDTH * chunk.getX() + x, TerrainGenUtil.CHUNK_LENGTH * chunk.getZ() + z);
				chunk.getWorld().generateTree(chunk.getBlock(x, height, z).getLocation(), TREE_TYPES[random.nextInt(TREE_TYPES.length)]);
			}
		}
		//Bushes
		{
			int count = random.nextInt(10);
			for (int i = 0; i < count; i++) {
				int x = random.nextInt(TerrainGenUtil.CHUNK_WIDTH );
				int z = random.nextInt(TerrainGenUtil.CHUNK_LENGTH);
				
				int height = generator.getHeight(TerrainGenUtil.CHUNK_WIDTH * chunk.getX() + x, TerrainGenUtil.CHUNK_LENGTH * chunk.getZ() + z);
				
				if (chunk.getBlock(x, height, z).getType() == Material.AIR && chunk.getBlock(x, height - 1, z).getType() != Material.AIR) {
					Leaves leaves = (Leaves) Material.OAK_LEAVES.createBlockData();
					leaves.setPersistent(true);
					chunk.getBlock(x, height, z).setBlockData(leaves, false);
				}
			}
		}
	}

}
