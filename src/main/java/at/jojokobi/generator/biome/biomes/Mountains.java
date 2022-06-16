package at.jojokobi.generator.biome.biomes;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator.ChunkData;

import at.jojokobi.generator.AbstractGenerator;
import at.jojokobi.generator.biome.CustomBiome;
import at.jojokobi.mcutil.generation.TerrainGenUtil;

public class Mountains implements CustomBiome{

	public Mountains() {
//		super(0.4, 0.8, 0.2, 0.5, 0.3, 0.5);
	}
	
	@Override
	public double getHeightMultiplier() {
		return 1.3;
	}

	@Override
	public void generateNoise(ChunkData data, int x, int z, int startHeight, int height, double noiseHeight, Random random) {
		for (int y = startHeight; y < height; y++) {
			if (noiseHeight < 0.6 && y == height - 1) {
				data.setBlock(x, y, z, Material.GRASS_BLOCK);
			}
			else if (noiseHeight > 0.6 && y >= height - 5) {
				data.setBlock(x, y, z, Material.DIRT);
			}
			else {
				data.setBlock(x, y, z, Material.STONE);
			}
		}
	}
	
	@Override
	public void generateSurface(ChunkData data, int x, int z, int startHeight, int height, double noiseHeight, Random random) {
		//Snow
		if (noiseHeight - random.nextDouble() * 0.5 > 0.8) {
			data.setBlock(x, height, z, Material.SNOW);
		}
	}
	
	public Biome getBiome(int x, int y, int z, int height, double heightNoise) {
		return height > 120 ? Biome.STONY_PEAKS : Biome.MEADOW;
	}
	
	
	@Override
	public void populate(Chunk chunk, Random random) {
		if (random.nextBoolean()) {
			int x = random.nextInt(TerrainGenUtil.CHUNK_WIDTH );
			int z = random.nextInt(TerrainGenUtil.CHUNK_LENGTH);
			
			int height = chunk.getWorld().getHighestBlockYAt(chunk.getX() * AbstractGenerator.CHUNK_SIZE + x, chunk.getZ() * AbstractGenerator.CHUNK_SIZE + z) + 1;
			chunk.getBlock(x, height, z).setType(Material.AIR);;
			chunk.getWorld().generateTree(chunk.getBlock(x, height, z).getLocation(), TreeType.TREE);
		}
	}
	
}