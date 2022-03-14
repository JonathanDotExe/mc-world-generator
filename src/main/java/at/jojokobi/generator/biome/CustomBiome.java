package at.jojokobi.generator.biome;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator.ChunkData;

import at.jojokobi.generator.ValueGenerator;

public interface CustomBiome {
	
	public abstract void generate (ChunkData data, int x, int z, int startHeight, int height, double heightNoise, Random random);
	
	public abstract Biome getBiome(int x, int y, int z, int height, double heightNoise);
	
	public abstract void populate (Chunk chunk, ValueGenerator generator, Random random);

}
