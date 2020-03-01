package at.jojokobi.generator;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.NoiseGenerator;
import org.bukkit.util.noise.SimplexNoiseGenerator;

import at.jojokobi.mcutil.generation.TerrainGenUtil;

public class CaveTestGenerator extends ChunkGenerator{

	public CaveTestGenerator() {
		
	}
	
	@Override
	public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome) {
		ChunkData data = Bukkit.createChunkData(world);
		NoiseGenerator gen = new SimplexNoiseGenerator(world.getSeed());
		for (int x = 0; x < TerrainGenUtil.CHUNK_WIDTH; x++) {
			for (int z = 0; z < TerrainGenUtil.CHUNK_LENGTH; z++) {
				int totalX = TerrainGenUtil.CHUNK_WIDTH * chunkX + x;
				int totalZ = TerrainGenUtil.CHUNK_LENGTH * chunkZ + z;
				for (int y = 0; y < TerrainGenUtil.CHUNK_HEIGHT; y++) {
					double noise = gen.noise(totalX * 0.05, y * 0.05, totalZ * 0.05);
					if (noise < -0.4) {
						data.setBlock(x, y, z, Material.STONE);
					}
					else {
						data.setBlock(x, y, z, Material.AIR);
					}
				}
			}
		}
		return data;
	}

}
