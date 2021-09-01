package at.jojokobi.generator.biome;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator.ChunkData;

public interface CustomBiome {
	
	public abstract void generateNoise (ChunkData data, int x, int z, int startHeight, int height, double noiseHeight, Random random);
	
	public abstract void generateSurface (ChunkData data, int x, int z, int startHeight, int height, double noiseHeight, Random random);
	
	public abstract Biome getBiome(int x, int y, int z, int height, double heightNoise);
	
	public abstract void populate (Chunk chunk, Random random);

}
