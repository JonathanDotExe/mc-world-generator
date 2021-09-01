package at.jojokobi.generator.biome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.block.Biome;
import org.bukkit.generator.WorldInfo;

import at.jojokobi.generator.biome.biomes.BiomeEntry;
import at.jojokobi.generator.biome.biomes.BiomeSystem;

public class HeightBiomeSystem extends BiomeSystem {
	
	private List<BiomeEntry> biomes = new ArrayList<BiomeEntry> ();

	private ValueGenerator generator;
	
	private static final List<Biome> BIOMES = new ArrayList<>();
	
	static {
		BIOMES.addAll(Arrays.asList(Biome.values()));
		BIOMES.remove(Biome.CUSTOM);
	}

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
		return generator.canPopulate(x, z) ? getBiome(x, z).getBiome(x, y, z, height, heightNoise) : Biome.THE_VOID;
	}

	@Override
	public List<Biome> getBiomes(WorldInfo info) {
		return BIOMES;
	}
	
}
