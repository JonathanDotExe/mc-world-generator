package at.jojokobi.generator.biome.grid;

import at.jojokobi.generator.biome.CustomBiome;
import at.jojokobi.mcutil.VectorUtil;

public class GridBiomeEntry {
	
	private CustomBiome biome;
	private double minTemperature;
	private double maxTemperature;
	private double minMoisture;
	private double maxMoisture;
	
	public GridBiomeEntry(CustomBiome biome, double minTemperature,
			double maxTemperature, double minMoisture, double maxMoisture) {
		super();
		this.biome = biome;
		this.minTemperature = minTemperature;
		this.maxTemperature = maxTemperature;
		this.minMoisture = minMoisture;
		this.maxMoisture = maxMoisture;
	}

	public double getDifference (double temperature, double moisture) {
		return getDifference(temperature, moisture, 1, 1);
	}
	
	public double getDifference (double temperature, double moisture, double temperatureWeight, double moistureWeight) {
		double difference = 0;
		
		//Temperature
		difference += calcValueDifference(minTemperature, maxTemperature, temperature) * temperatureWeight;
		//Moisture
		difference += calcValueDifference(minMoisture, maxMoisture, moisture) * moistureWeight;
		
		return difference;
	}
	
	private double calcValueDifference (double min, double max, double val) {
		double average = (min + max) / 2;
		double difference = 0;
		if (val < average) {
			difference = VectorUtil.interpolate(0, 1, (average - val)/(average - min));
		}
		else {
			difference = VectorUtil.interpolate(0, 1, (val - average)/(max - average));
		}
		return difference;
	}
	
	public CustomBiome getBiome() {
		return biome;
	}
	
	public double getTemperature () {
		return (minTemperature + maxTemperature) / 2;
	}
	
	public double getMoisture () {
		return (minMoisture + maxMoisture) / 2;
	}
	
	public double getMinTemperature() {
		return minTemperature;
	}

	public void setMinTemperature(double minTemperature) {
		this.minTemperature = minTemperature;
	}

	public double getMaxTemperature() {
		return maxTemperature;
	}

	public void setMaxTemperature(double maxTemperature) {
		this.maxTemperature = maxTemperature;
	}

	public double getMinMoisture() {
		return minMoisture;
	}

	public void setMinMoisture(double minMoisture) {
		this.minMoisture = minMoisture;
	}

	public double getMaxMoisture() {
		return maxMoisture;
	}

	public void setMaxMoisture(double maxMoisture) {
		this.maxMoisture = maxMoisture;
	}

}
