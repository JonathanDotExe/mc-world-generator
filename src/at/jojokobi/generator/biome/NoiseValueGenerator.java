package at.jojokobi.generator.biome;

import org.bukkit.util.noise.NoiseGenerator;
import org.bukkit.util.noise.SimplexNoiseGenerator;

import at.jojokobi.mcutil.VectorUtil;

public class NoiseValueGenerator implements ValueGenerator{
	
	private NoiseGenerator[] heightGenerators = new NoiseGenerator[5];
	
	private NoiseGenerator temperatureGenerator;
	private NoiseGenerator moistureGenerator;
	
	private double heightMultiplier = 0.0025;
	private double temperatureMultiplier = 0.001;
	private double moistureMultiplier = 0.001;
	
	private int minHeight = 10;
	private int seaLevel = 64;
	private int maxHeight = 150;
	
	public NoiseValueGenerator(long seed) {
		for (int i = 0; i < heightGenerators.length; i++) {
			heightGenerators[i] = new SimplexNoiseGenerator(seed + i * (i % 2 == 0 ? 57 : -82));
		}
//		heightGenerator1 = new SimplexNoiseGenerator(seed);
//		heightGenerator2 = new SimplexNoiseGenerator(seed + 469);
//		heightGenerator3 = new SimplexNoiseGenerator(seed - 89);
		
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
		for (int i = 0; i < heightGenerators.length; i++) {
			double multiplier = (1.0/(1 + i));
			multipliers += multiplier;
			noise += heightGenerators[i].noise(x*heightMultiplier*(1+i), z*heightMultiplier*(1+i)) * multiplier;
		}
		
//		double noise = (0.5 + heightGenerator1.noise(x*heightMultiplier, z*heightMultiplier)*0.5) ;
//		double noise2 = (0.5 + heightGenerator2.noise(x*heightMultiplier*2, z*heightMultiplier*2)*0.5)/2;
//		double noise3 = (0.5 + heightGenerator3.noise(x*heightMultiplier*4, z*heightMultiplier*4)*0.5)/4;
		return noise/multipliers;
	}
	
	@Override
	public int getHeight (double x, double z) {
		double noise = getHeightNoise(x, z);
		
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

}
