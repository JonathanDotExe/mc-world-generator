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

public class VolcanoMountains implements CustomBiome{

	public VolcanoMountains() {
//		super(0.5, 1, 0.7, 1.0, 0.2, 0.5);
	}

	@Override
	public double getHeightMultiplier() {
		return 2;
	}
	
	@Override
	public void generateNoise(ChunkData chunk, int x, int z, GenerationData data, Random random) {
		double noiseHeight = data.getHeightNoise() * data.getHeightMultiplier();
		for (int y = data.getStartHeight(); y < data.getHeight(); y++) {
			//Lava
			if (noiseHeight > 1) {
				if (y < data.getHeight() - 3) {
					chunk.setBlock(x, y, z, Material.LAVA);
				}
			}
			else if (noiseHeight < 0.4 && y == data.getHeight() - 1) { //TODO Borders with noise
				chunk.setBlock(x, y, z, Material.GRASS_BLOCK);
			}
			else if (noiseHeight < 0.4 && y >= data.getHeight() - 5) {
				chunk.setBlock(x, y, z, Material.DIRT);
			}
			else {
				chunk.setBlock(x, y, z, Material.STONE);
			}
		}
	}
	
	@Override
	public void generateSurface(ChunkData chunk, int x, int z, GenerationData data, Random random) {
		for (int y = data.getStartHeight(); y < data.getHeight(); y++) {
			//Lava
			if (data.getHeightNoise() * data.getHeightMultiplier() > 1) {
				if (y < data.getHeight() - 3) {
					chunk.setBlock(x, y, z, Material.LAVA);
				}
			}
			else {
				chunk.setBlock(x, y, z, Material.STONE);
			}
		}
	}
	
	@Override
	public Biome getBiome(int x, int y, int z, GenerationData data) {
		return Biome.STONY_PEAKS;
	}

	@Override
	public void populate(Chunk chunk, Random random) {
		if (random.nextBoolean()) {
			int x = random.nextInt(TerrainGenUtil.CHUNK_WIDTH );
			int z = random.nextInt(TerrainGenUtil.CHUNK_LENGTH);
			
			int height = chunk.getWorld().getHighestBlockYAt(chunk.getX() * AbstractGenerator.CHUNK_SIZE + x, chunk.getZ() * AbstractGenerator.CHUNK_SIZE + z, HeightMap.OCEAN_FLOOR_WG) + 1;
			chunk.getBlock(x, height, z).setType(Material.AIR);;
			chunk.getWorld().generateTree(chunk.getBlock(x, height, z).getLocation(), TreeType.TREE);
		}
		// Dead Bush
		{
			int count = random.nextInt(5);
			for (int i = 0; i < count; i++) {
				int x = random.nextInt(TerrainGenUtil.CHUNK_WIDTH);
				int z = random.nextInt(TerrainGenUtil.CHUNK_LENGTH);
				
				int height = chunk.getWorld().getHighestBlockYAt(chunk.getX() * AbstractGenerator.CHUNK_SIZE + x, chunk.getZ() * AbstractGenerator.CHUNK_SIZE + z, HeightMap.WORLD_SURFACE_WG);
				if (height > 0) {
					switch (random.nextInt(5)) {
					case 0:
					case 1:
					case 2:
						chunk.getBlock(x, height, z).setType(Material.DEAD_BUSH, false);
						break;
					case 4:
						chunk.getBlock(x, height, z).setType(Material.RED_MUSHROOM, false);
						break;
					case 5:
						chunk.getBlock(x, height, z).setType(Material.BROWN_MUSHROOM, false);
						break;
					}
				}
			}
		}
	}
	
}
