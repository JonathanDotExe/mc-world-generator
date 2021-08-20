package at.jojokobi.generator.biome;

import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.WorldInfo;

public abstract class BiomeSystem extends BiomeProvider {

	public abstract CustomBiome getBiome (double x, double z);
	
	@Override
	public Biome getBiome(WorldInfo arg0, int x, int y, int z) {
		return getBiome(x, z).;
	}
	
}
