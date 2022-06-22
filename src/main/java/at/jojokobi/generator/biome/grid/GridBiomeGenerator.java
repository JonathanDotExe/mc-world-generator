package at.jojokobi.generator.biome.grid;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator.ChunkData;

import at.jojokobi.generator.biome.BiomeGenerator;
import at.jojokobi.generator.biome.CustomBiome;

class GridBiomeGenerator implements BiomeGenerator {

	private CustomBiome biome;
	private int x;
	private int z;
	private int startHeight;
	private int height;
	private double heightNoise;

	public GridBiomeGenerator(CustomBiome biome, int x, int z, int startHeight, int height, double heightNoise) {
		super();
		this.biome = biome;
		this.x = x;
		this.z = z;
		this.startHeight = startHeight;
		this.height = height;
		this.heightNoise = heightNoise;
	}

	@Override
	public int getBaseHeight() {
		return height;
	}

	@Override
	public void generateNoise(ChunkData data, int x, int z, Random random) {
		if (height > startHeight) {
			biome.generateNoise(data, x, z, startHeight, height, heightNoise, random);
		}
	}

	@Override
	public void generateSurface(ChunkData data, int x, int z, Random random) {
		if (height > startHeight) {
			biome.generateSurface(data, x, z, startHeight, height, heightNoise, random);
		}
		
	}

	@Override
	public void populate(Chunk chunk, Random random) {
		if (height > startHeight) {
			biome.populate(chunk, random);
		}
	}

	@Override
	public Biome getBiome(int y) {
		return biome.getBiome(x, y, z, height, heightNoise);
	}

}