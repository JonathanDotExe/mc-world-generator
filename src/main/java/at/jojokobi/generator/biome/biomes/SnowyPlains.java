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

public class SnowyPlains implements CustomBiome{

	public SnowyPlains() {
//		super(0, 0.2, 0.0, 0.4, 0.2, 0.6);
	}
	
	@Override
	public double getHeightMultiplier() {
		return 0.45;
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
		chunk.setBlock(x, data.getHeight(), z, Material.SNOW);
	}
	
	@Override
	public Biome getBiome(int x, int y, int z, GenerationData data) {
		return Biome.SNOWY_TAIGA;
	}
	
	@Override
	public void populate(Chunk chunk, Random random) {
		//Trees
		if (random.nextBoolean()) {
			int x = random.nextInt(TerrainGenUtil.CHUNK_WIDTH - 2) + 1;
			int z = random.nextInt(TerrainGenUtil.CHUNK_LENGTH - 2) + 1;
			
			int height = chunk.getWorld().getHighestBlockYAt(chunk.getX() * AbstractGenerator.CHUNK_SIZE + x, chunk.getZ() * AbstractGenerator.CHUNK_SIZE + z, HeightMap.OCEAN_FLOOR_WG) + 1;
			chunk.getBlock(x, height, z).setType(Material.AIR);
			chunk.getWorld().generateTree(chunk.getBlock(x, height, z).getLocation(), TreeType.TREE);
		}
		//Bushes
		{
			int count = random.nextInt(10);
			for (int i = 0; i < count; i++) {
				int x = random.nextInt(TerrainGenUtil.CHUNK_WIDTH );
				int z = random.nextInt(TerrainGenUtil.CHUNK_LENGTH);
				
				int height = chunk.getWorld().getHighestBlockYAt(chunk.getX() * AbstractGenerator.CHUNK_SIZE, chunk.getZ() * AbstractGenerator.CHUNK_SIZE, HeightMap.OCEAN_FLOOR_WG) + 1;
				if (height > 0) {
					chunk.getBlock(x, height - 1, z).setType(Material.SNOW_BLOCK, false);
				}
			}
		}
	}

}
