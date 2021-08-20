package at.jojokobi.generator.biome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.block.Biome;
import org.bukkit.generator.WorldInfo;

import at.jojokobi.generator.ValueGenerator;

public class HeightBiomeSystem extends BiomeSystem {
	
	private List<BiomeEntry> biomes = new ArrayList<BiomeEntry> ();

	private ValueGenerator generator;

	public HeightBiomeSystem(ValueGenerator generator) {
		super();
		this.generator = generator;
	}

	public void registerBiome(BiomeEntry biome) {
		biomes.add(biome);
	}

	@Override
	public CustomBiome getBiome(double x, double z) {
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
		return biome.getBiome();
	}
	
	@Override
	public Biome getBiome(WorldInfo info, int x, int y, int z) {
		double heightNoise = generator.getHeightNoise(x, z);
		int height = generator.getHeight(x, z);
		return getBiome(x, z).getBiome(x, y, z, height, heightNoise);
	}

	@Override
	public List<Biome> getBiomes(WorldInfo info) {
		return Arrays.asList(Biome.values());
	}
	
}
