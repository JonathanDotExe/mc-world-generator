package at.jojokobi.generator.biome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.block.Biome;
import org.bukkit.generator.WorldInfo;
import org.bukkit.generator.ChunkGenerator.ChunkData;

import at.jojokobi.generator.biome.biomes.BiomeEntry;
import at.jojokobi.generator.biome.biomes.BiomeGenerator;
import at.jojokobi.generator.biome.biomes.BiomeSystem;

public class HeightBiomeSystem extends BiomeSystem {
	
	private List<BiomeEntry> biomes = new ArrayList<BiomeEntry> ();

	private ValueGenerator generator;
	
	private static final List<Biome> BIOMES = new ArrayList<>();
	
	static {
		BIOMES.addAll(Arrays.asList(Biome.values()));
		BIOMES.remove(Biome.CUSTOM);
	}

	public HeightBiomeSystem(long seed, int minHeight, int maxHeight) {
		super();
		this.generator = new NoiseValueGenerator(seed, minHeight, maxHeight);
	}

	public void registerBiome(BiomeEntry biome) {
		biomes.add(biome);
	}
	
	@Override
	public BiomeGenerator getBiome(int x, int z) {
		double temperature = generator.getTemperature(x, z);
		double moisture = generator.getTemperature(x, z);
		int height = generator.getHeight(x, z);
		int startHeight = generator.getStartHeight(x, z);
		double heightNoise = generator.getHeightNoise(x, z);
		
		double difference = 0;
		BiomeEntry biome = null;
		for (BiomeEntry b : biomes) {
			double temp = b.getDifference(heightNoise, temperature, moisture);
			if (biome == null || temp < difference) {
				biome = b;
				difference = temp;
			}
		}
		return new HeightBiomeGenerator(biome.getBiome(), x, z, height, startHeight);
	}
	
	@Override
	public Biome getBiome(WorldInfo info, int x, int y, int z) {
		//FIXME ?
		return getBiome(x, z).getBiome(y);
	}

	@Override
	public List<Biome> getBiomes(WorldInfo info) {
		return BIOMES;
	}
	
}

class HeightBiomeGenerator implements BiomeGenerator {
	
	private CustomBiome biome;
	private int x;
	private int z;
	private int startHeight;
	private int height;

	public HeightBiomeGenerator(CustomBiome biome, int x, int z, int startHeight, int height) {
		super();
		this.biome = biome;
		this.x = x;
		this.z = z;
		this.startHeight = startHeight;
		this.height = height;
	}

	@Override
	public int getBaseHeight() {
		return height;
	}

	@Override
	public void generateNoise(ChunkData data, int x, int z, Random random) {
		biome.generateNoise(data, x, z, startHeight, height, random);
	}

	@Override
	public void generateSurface(ChunkData data, int x, int z, Random random) {
		biome.generateSurface(data, x, z, startHeight, height, random);
		
	}

	@Override
	public void populate(Chunk chunk, Random random) {
		biome.populate(chunk, random);
	}

	@Override
	public Biome getBiome(int y) {
		return biome.getBiome(x, y, z, height);
	}
	
}
