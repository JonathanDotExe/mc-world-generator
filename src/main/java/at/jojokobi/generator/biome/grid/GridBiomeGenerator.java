package at.jojokobi.generator.biome.grid;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator.ChunkData;

import at.jojokobi.generator.biome.BiomeGenerator;
import at.jojokobi.generator.biome.CustomBiome;
import at.jojokobi.generator.biome.GenerationData;

class GridBiomeGenerator implements BiomeGenerator {

	private CustomBiome biome;
	private int x;
	private int z;
	private GenerationData data;

	public GridBiomeGenerator(CustomBiome biome, int x, int z, GenerationData data) {
		super();
		this.biome = biome;
		this.x = x;
		this.z = z;
		this.data = data;
	}

	@Override
	public int getBaseHeight() {
		return data.getHeight();
	}

	@Override
	public void generateNoise(ChunkData data, int x, int z, Random random) {
		if (this.data.getHeight() > this.data.getStartHeight()) {
			biome.generateNoise(data, x, z, this.data, random);
		}
	}

	@Override
	public void generateSurface(ChunkData data, int x, int z, Random random) {
		if (this.data.getHeight() > this.data.getStartHeight()) {
			biome.generateSurface(data, x, z, this.data, random);
		}
	}

	@Override
	public void populate(Chunk chunk, Random random) {
		if (data.getHeight() > data.getHeightMultiplier()) {
			biome.populate(chunk, random);
		}
	}

	@Override
	public Biome getBiome(int y) {
		return biome.getBiome(x, y, z, data);
	}

}