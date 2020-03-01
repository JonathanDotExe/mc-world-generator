package at.jojokobi.generator.biome;

import java.util.ArrayList;
import java.util.List;

import at.jojokobi.generator.ValueGenerator;

public class HeightBiomeSystem implements BiomeSystem {
	
	private List<CustomBiome> biomes = new ArrayList<CustomBiome> ();

	private ValueGenerator generator;

	public HeightBiomeSystem(ValueGenerator generator) {
		super();
		this.generator = generator;
	}

	@Override
	public void registerBiome(CustomBiome biome) {
		biomes.add(biome);
	}

	@Override
	public CustomBiome getBiome(double x, double z) {
		double temperature = generator.getTemperature(x, z);
		double moisture = generator.getTemperature(x, z);
		double height = generator.getHeightNoise(x, z);
		
		double difference = 0;
		CustomBiome biome = null;
		for (CustomBiome b : biomes) {
			double temp = b.getDifference(height, temperature, moisture);
			if (biome == null || temp < difference) {
				biome = b;
				difference = temp;
			}
		}
		return biome;
	}
	
}
