package at.jojokobi.generator.biome;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator.ChunkData;

import at.jojokobi.generator.ValueGenerator;
import at.jojokobi.mcutil.VectorUtil;

public abstract class CustomBiome {
	
	private double minHeight;
	private double maxHeight;
	private double minTemperature;
	private double maxTemperature;
	private double minMoisture;
	private double maxMoisture;

	public CustomBiome(double minHeight, double maxHeight, double minTemperature, double maxTemperature,
			double minMoisture, double maxMoisture) {
		super();
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
		this.minTemperature = minTemperature;
		this.maxTemperature = maxTemperature;
		this.minMoisture = minMoisture;
		this.maxMoisture = maxMoisture;
	}
	
	public abstract Biome generate (ChunkData data, int x, int z, int startHeight, int height, double heightNoise, Random random);
	
	public abstract void populate (Chunk chunk, ValueGenerator generator, Random random);
	
	public double getDifference (double height, double temperature, double moisture) {
		double difference = 0;
		
		//Height
		difference += calcValueDifference(minHeight, maxHeight, height);
		//Temperature
		difference += calcValueDifference(minTemperature, maxTemperature, temperature);
		//Moisture
		difference += calcValueDifference(minMoisture, maxMoisture, moisture);
		
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
	
	public double getHeight () {
		return (minHeight + maxHeight) / 2;
	}
	
	public double getTemperature () {
		return (minTemperature + maxTemperature) / 2;
	}
	
	public double getMoisture () {
		return (minMoisture + maxMoisture) / 2;
	}

	public double getMinHeight() {
		return minHeight;
	}

	public void setMinHeight(double minHeight) {
		this.minHeight = minHeight;
	}

	public double getMaxHeight() {
		return maxHeight;
	}

	public void setMaxHeight(double maxHeight) {
		this.maxHeight = maxHeight;
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
