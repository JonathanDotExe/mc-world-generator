package at.jojokobi.generator.biome;

import org.bukkit.generator.BiomeProvider;

public abstract class BiomeSystem extends BiomeProvider {

	public abstract CustomBiome getBiome (double x, double z);
	
}
