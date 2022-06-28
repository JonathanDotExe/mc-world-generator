package at.jojokobi.generator.biome;

public interface ValueGenerator {
	
	public double getMoisture (double x, double z);
	
	public double getTemperature (double x, double z);
	
	public double getHeightNoise (double x, double z);
	
	public int getHeight (double x, double z, double noise);
	
	public int getSeaLevel();
	
	public default int getStartHeight (double x, double z) {
		return 0;
	}

	public default boolean canPopulate (double x, double z, double noise) {
		return getHeight(x, z, noise) > getStartHeight(x, z);
	}
	
}
