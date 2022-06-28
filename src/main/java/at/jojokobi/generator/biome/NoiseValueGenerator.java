package at.jojokobi.generator.biome;

import org.bukkit.util.noise.NoiseGenerator;
import org.bukkit.util.noise.SimplexNoiseGenerator;

import at.jojokobi.mcutil.VectorUtil;

public class NoiseValueGenerator implements ValueGenerator{
	
	private NoiseGenerator[] heightGenerators = new NoiseGenerator[3];
	private NoiseGenerator[] variationHeightGenerators = new NoiseGenerator[3];
	
	private NoiseGenerator temperatureGenerator;
	private NoiseGenerator moistureGenerator;
	
	private double heightMultiplier = 0.0025;
	private double variationHeightMultiplier = 0.5;
	private double temperatureMultiplier = 0.0005;
	private double moistureMultiplier = 0.0005;
	
	private int minHeight = 10;
	private int seaLevel = 64;
	private int maxHeight = 150;
	
	public NoiseValueGenerator(long seed, int minHeight, int maxHeight) {
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
		for (int i = 0; i < heightGenerators.length; i++) {
			heightGenerators[i] = new SimplexNoiseGenerator(seed + i * (i % 2 == 0 ? 57 : -82));
		}
		for (int i = 0; i < variationHeightGenerators.length; i++) {
			variationHeightGenerators[i] = new SimplexNoiseGenerator(seed + i * (i % 2 == 0 ? 8612 : -3285));
		}
		
		temperatureGenerator = new SimplexNoiseGenerator(seed + 654);
		moistureGenerator = new SimplexNoiseGenerator(seed - 44);
	}

	@Override
	public double getMoisture(double x, double z) {
		return 0.5 * moistureGenerator.noise(x*moistureMultiplier, z*moistureMultiplier) + 0.5;
	}

	@Override
	public double getTemperature(double x, double z) {
		return 0.5 * temperatureGenerator.noise(x*temperatureMultiplier, z*temperatureMultiplier) + 0.5;
	}

	@Override
	public double getHeightNoise(double x, double z) {
		double noise = 0;
		double multipliers = 0;
		double multiplier = 1;
		for (int i = 0; i < heightGenerators.length; i++) {
			multipliers += multiplier;
			noise += heightGenerators[i].noise(x*heightMultiplier*(1/multiplier), z*heightMultiplier*(1/multiplier)) * multiplier;
			multiplier *= 0.5;
		}
		
		multiplier = 0.015;
		for (int i = 0; i < variationHeightGenerators.length; i++) {
			multipliers += multiplier;
			noise += variationHeightGenerators[i].noise(x*variationHeightMultiplier*(0.015/multiplier), z*variationHeightMultiplier*(0.015/multiplier)) * multiplier;
			multiplier *= 0.5;
		}
		
		return noise/multipliers;
	}
	
	@Override
	public int getHeight (double x, double z, double noise) {
		int height = 0;
		if (noise < 0) {
			height = (int) Math.round(VectorUtil.interpolate(minHeight, seaLevel, 1 + noise));
		}
		else {
			height = (int) Math.round(VectorUtil.interpolate(seaLevel, maxHeight, noise));
		}
		return height;
	}

	public int getMinHeight() {
		return minHeight;
	}

	public void setMinHeight(int minHeight) {
		this.minHeight = minHeight;
	}

	public int getMaxHeight() {
		return maxHeight;
	}

	public void setMaxHeight(int maxHeight) {
		this.maxHeight = maxHeight;
	}

	public double getHeightMultiplier() {
		return heightMultiplier;
	}

	public void setHeightMultiplier(double heightMultiplier) {
		this.heightMultiplier = heightMultiplier;
	}

	public double getTemperatureMultiplier() {
		return temperatureMultiplier;
	}

	public void setTemperatureMultiplier(double temperatureMultiplier) {
		this.temperatureMultiplier = temperatureMultiplier;
	}

	public double getMoistureMultiplier() {
		return moistureMultiplier;
	}

	public void setMoistureMultiplier(double moistureMultiplier) {
		this.moistureMultiplier = moistureMultiplier;
	}

	public int getSeaLevel() {
		return seaLevel;
	}

	public void setSeaLevel(int seaLevel) {
		this.seaLevel = seaLevel;
	}

	public double getVariationHeightMultiplier() {
		return variationHeightMultiplier;
	}

	public void setVariationHeightMultiplier(double variationHeightMultiplier) {
		this.variationHeightMultiplier = variationHeightMultiplier;
	}

}
