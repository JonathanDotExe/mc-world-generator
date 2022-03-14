package at.jojokobi.generator;

import at.jojokobi.generator.biome.BiomeSystem;

public interface BiomeSystemBuilder {

	public BiomeSystem createBiomeSystem (long seed);
	
}
