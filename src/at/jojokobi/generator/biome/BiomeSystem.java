package at.jojokobi.generator.biome;

public interface BiomeSystem {
	
	public void registerBiome (CustomBiome biome);

	public CustomBiome getBiome (double x, double z);
	
}
