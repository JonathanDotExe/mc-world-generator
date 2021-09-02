package at.jojokobi.generator.biome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.block.Biome;
import org.bukkit.generator.WorldInfo;

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
		return new DefaultBiomeGenerator(biome.getBiome(), x, z, startHeight, height, heightNoise);
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

