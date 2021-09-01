package at.jojokobi.generator;

import at.jojokobi.generator.biome.biomes.BiomeSystem;

public interface BiomeSystemBuilder {

	public BiomeSystem createBiomeSystem (long seed);
	
}
