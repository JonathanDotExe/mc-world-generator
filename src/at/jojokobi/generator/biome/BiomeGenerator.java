package at.jojokobi.generator.biome;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator.ChunkData;

public interface BiomeGenerator {
	
	public void generate (ChunkData data, int x, int z);
	
	public void populate (Chunk chunk, Random random);
	
	public Biome getBiome(int y);

}
