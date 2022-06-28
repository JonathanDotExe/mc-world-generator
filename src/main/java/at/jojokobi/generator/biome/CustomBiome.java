package at.jojokobi.generator.biome;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator.ChunkData;

public interface CustomBiome {
	
	public abstract void generateNoise (ChunkData chunk, int x, int z, GenerationData data, Random random);
	
	public abstract void generateSurface (ChunkData chunk, int x, int z, GenerationData data, Random random);
	
	public abstract Biome getBiome(int x, int y, int z, GenerationData data);
	
	public abstract void populate (Chunk chunk, Random random);
	
	public default double getHeightMultiplier() {
		return 1.0;
	}

}
