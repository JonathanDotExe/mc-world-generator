package at.jojokobi.generator.biome.grid;

import at.jojokobi.generator.biome.CustomBiome;

class GridBiomePoint {
	
	private CustomBiome biome;
	private int x;
	private int z;
	private double pointWeight;
	
	
	public GridBiomePoint(CustomBiome biome, int x, int z, double pointWeight) {
		super();
		this.biome = biome;
		this.x = x;
		this.z = z;
		this.pointWeight = pointWeight;
	}

	public double getPointWeight() {
		return pointWeight;
	}

	public void setPointWeight(double pointWeight) {
		this.pointWeight = pointWeight;
	}

	public CustomBiome getBiome() {
		return biome;
	}

	public void setBiome(CustomBiome biome) {
		this.biome = biome;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}
	
}