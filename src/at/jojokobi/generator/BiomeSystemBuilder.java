package at.jojokobi.generator;

import org.bukkit.generator.WorldInfo;

import at.jojokobi.generator.biome.biomes.BiomeSystem;

public interface BiomeSystemBuilder {

	public BiomeSystem createBiomeSystem (WorldInfo info);
	
}
