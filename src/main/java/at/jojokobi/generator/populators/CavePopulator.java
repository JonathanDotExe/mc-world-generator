package at.jojokobi.generator.populators;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.util.noise.NoiseGenerator;
import org.bukkit.util.noise.SimplexNoiseGenerator;

import at.jojokobi.mcutil.generation.TerrainGenUtil;

public class CavePopulator extends BlockPopulator {

	@Override
	public void populate(World world, Random random, Chunk chunk) {
		NoiseGenerator gen = new SimplexNoiseGenerator(world.getSeed() + 486);
		int chunkX = chunk.getBlock(0, 0, 0).getX();
		int chunkZ = chunk.getBlock(0, 0, 0).getZ();
		for (int x = 0; x < TerrainGenUtil.CHUNK_WIDTH; x++) {
			for (int z = 0; z < TerrainGenUtil.CHUNK_LENGTH; z++) {
				int totalX = chunkX + x;
				int totalZ = chunkZ + z;
				for (int y = 0; y < TerrainGenUtil.CHUNK_HEIGHT; y++) {
					double noise = gen.noise(totalX * 0.05, y * 0.05, totalZ * 0.05);
//					System.out.println(totalX + "/" + y + "/" + totalZ + ":" + noise);
					if (noise < -0.4 && chunk.getBlock(x, y, z).getType().isSolid()) {
						chunk.getBlock(x, y, z).setType(Material.CAVE_AIR, false);
					}
				}
			}
		}
	}

}
