package at.jojokobi.generator.biome.biomes;

import org.bukkit.generator.BiomeProvider;

public abstract class BiomeSystem extends BiomeProvider {

	public abstract BiomeGenerator getBiome (double x, double z);
	
}
