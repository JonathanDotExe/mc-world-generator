package at.jojokobi.generator.biome.height;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.block.Biome;
import org.bukkit.generator.WorldInfo;
import org.bukkit.generator.ChunkGenerator.ChunkData;

import at.jojokobi.generator.biome.BiomeGenerator;
import at.jojokobi.generator.biome.BiomeSystem;
import at.jojokobi.generator.biome.CustomBiome;
import at.jojokobi.generator.biome.GenerationData;
import at.jojokobi.generator.biome.ValueGenerator;

public class HeightBiomeSystem extends BiomeSystem {
	
	private List<BiomeEntry> biomes = new ArrayList<BiomeEntry> ();

	private ValueGenerator generator;
	private long seed;
	
	private static final List<Biome> BIOMES = new ArrayList<>();
	
	static {
		BIOMES.addAll(Arrays.asList(Biome.values()));
		BIOMES.remove(Biome.CUSTOM);
	}

	public HeightBiomeSystem(ValueGenerator generator, long seed) {
		super();
		this.generator = generator;
		this.seed = seed;
	}

	public void registerBiome(BiomeEntry biome) {
		biomes.add(biome);
	}

	@Override
	public BiomeGenerator getBiome(int x, int z) {
		double temperature = generator.getTemperature(x, z);
		double moisture = generator.getTemperature(x, z);
		double height = generator.getHeightNoise(x, z);
		
		double difference = 0;
		BiomeEntry biome = null;
		for (BiomeEntry b : biomes) {
			double temp = b.getDifference(height, temperature, moisture);
			if (biome == null || temp < difference) {
				biome = b;
				difference = temp;
			}
		}
		int heightInt = generator.getHeight(x, z, height);
		GenerationData data = new GenerationData(generator.getStartHeight(x, z), heightInt, height, seed, generator.getSeaLevel(), 1);
		CustomBiome b= biome.getBiome();
		return new BiomeGenerator() {
			
			@Override
			public void populate(Chunk chunk, Random random) {
				b.populate(chunk, random);
			}
			
			@Override
			public Biome getBiome(int y) {
				return b.getBiome(x, y, z, data);
			}
			
			@Override
			public int getBaseHeight() {
				return heightInt;
			}
			
			@Override
			public void generateSurface(ChunkData chunk, int x, int z, Random random) {
				b.generateSurface(chunk, x, z, data, random);
			}
			
			@Override
			public void generateNoise(ChunkData chunk, int x, int z, Random random) {
				b.generateNoise(chunk, x, z, data, random);				
			}
		};
	}
	
	@Override
	public Biome getBiome(WorldInfo info, int x, int y, int z) {
		double heightNoise = generator.getHeightNoise(x, z);
		return generator.canPopulate(x, z, heightNoise) ? getBiome(x, z).getBiome(y) : Biome.THE_VOID;
	}

	@Override
	public List<Biome> getBiomes(WorldInfo info) {
		return BIOMES;
	}
	
}
