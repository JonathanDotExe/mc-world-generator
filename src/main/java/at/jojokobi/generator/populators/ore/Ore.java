package at.jojokobi.generator.populators.ore;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;

import at.jojokobi.mcutil.generation.TerrainGenUtil;

public class Ore {
	
	private Material material;
	private int veinSize;
	private int tries;
	private int maxHeight;
	
	public Ore(Material material, int veinSize, int tries, int maxHeight) {
		super();
		this.material = material;
		this.veinSize = veinSize;
		this.tries = tries;
		this.maxHeight = maxHeight;
	}

	public void generate (Chunk chunk, Random random) {
		for (int i = 0; i < tries; i++) {
			int x = random.nextInt(TerrainGenUtil.CHUNK_WIDTH);
			int z = random.nextInt(TerrainGenUtil.CHUNK_LENGTH);
			int y = random.nextInt(maxHeight);
			
			for (int j = 0; j < veinSize; j++) {
				if (x >= 0 && x < TerrainGenUtil.CHUNK_WIDTH && z >= 0 && z < TerrainGenUtil.CHUNK_LENGTH && y >= 0 && y < TerrainGenUtil.CHUNK_HEIGHT && chunk.getBlock(x, y, z).getType() == Material.STONE) {
					chunk.getBlock(x, y, z).setType(material, false);
				}
				x += random.nextInt(3) - 1;
				y += random.nextInt(3) - 1;
				z += random.nextInt(3) - 1;
			}
		}
	}

}
