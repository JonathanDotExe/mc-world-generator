package at.jojokobi.generator.biome.biomes;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Biome;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.generator.ChunkGenerator.ChunkData;
import org.bukkit.util.noise.NoiseGenerator;
import org.bukkit.util.noise.SimplexNoiseGenerator;

import at.jojokobi.generator.biome.CustomBiome;
import at.jojokobi.generator.noise.ValueGenerator;
import at.jojokobi.mcutil.generation.TerrainGenUtil;

public class Jungle implements CustomBiome{
	
	private static final TreeType[] TREE_TYPES = {TreeType.JUNGLE_BUSH, TreeType.COCOA_TREE, TreeType.SMALL_JUNGLE, TreeType.SMALL_JUNGLE, TreeType.TREE};

	public Jungle() {
//		super(0.1, 0.3, 0.7, 1.0, 0.7, 1.1);
	}

	@Override
	public void generate(ChunkData data, int x, int z, int startHeight, int height, double noiseHeight, Random random) {
		
		for (int y = startHeight; y < height; y++) {
			if (y == height - 1) {
				data.setBlock(x, y, z, random.nextInt(64) == 0 ? Material.WATER : Material.GRASS_BLOCK);
			}
			else if (y >= height - 5) {
				data.setBlock(x, y, z, Material.DIRT);
			}
			else {
				data.setBlock(x, y, z, Material.STONE);
			}
		}
	}
	
	public Biome getBiome(int x, int y, int z, int height, double heightNoise) {
		return Biome.JUNGLE;
	}
	
	@Override
	public void populate(Chunk chunk, ValueGenerator generator, Random random) {
		//Trees
		{
			int count = random.nextInt(10);
			for (int i = 0; i < count; i++) {
				int x = random.nextInt(TerrainGenUtil.CHUNK_WIDTH - 2) + 1;
				int z = random.nextInt(TerrainGenUtil.CHUNK_LENGTH - 2) + 1;
				
				int height = generator.getHeight(TerrainGenUtil.CHUNK_WIDTH * chunk.getX() + x, TerrainGenUtil.CHUNK_LENGTH * chunk.getZ() + z);
				chunk.getWorld().generateTree(chunk.getBlock(x, height, z).getLocation(), TREE_TYPES[random.nextInt(TREE_TYPES.length)]);
			}
		}
		//Leafy ground
		NoiseGenerator gen = new SimplexNoiseGenerator(chunk.getWorld().getSeed() - 5894);
		for (int x = 0; x < TerrainGenUtil.CHUNK_WIDTH; x++) {
			for (int z = 0; z < TerrainGenUtil.CHUNK_LENGTH; z++) {
				int totalX = TerrainGenUtil.CHUNK_WIDTH * chunk.getX() + x;
				int totalZ = TerrainGenUtil.CHUNK_LENGTH * chunk.getZ() + z;
				
				int height = generator.getHeight(totalX, totalZ);
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
		}
		//Bushes
		{
			int count = random.nextInt(20);
			for (int i = 0; i < count; i++) {
				int x = random.nextInt(TerrainGenUtil.CHUNK_WIDTH );
				int z = random.nextInt(TerrainGenUtil.CHUNK_LENGTH);
				
				int height = generator.getHeight(TerrainGenUtil.CHUNK_WIDTH * chunk.getX() + x, TerrainGenUtil.CHUNK_LENGTH * chunk.getZ() + z);
				
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
				
				int height = generator.getHeight(TerrainGenUtil.CHUNK_WIDTH * chunk.getX() + x, TerrainGenUtil.CHUNK_LENGTH * chunk.getZ() + z);
				
				if (chunk.getBlock(x, height, z).getType() == Material.AIR && chunk.getBlock(x, height - 1, z).getType() != Material.AIR) {
					chunk.getBlock(x, height, z).setType(Material.MELON, false);
				}
			}
		}
	}

}
