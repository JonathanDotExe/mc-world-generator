package at.jojokobi.generator.biome;

import org.bukkit.generator.BiomeProvider;

public abstract class BiomeSystem extends BiomeProvider {

	public abstract BiomeGenerator getBiome (int x, int z);
	
}
