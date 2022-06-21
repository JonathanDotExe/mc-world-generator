package at.jojokobi.generator.noise;

public interface ValueGenerator {
	
	public double getMoisture (double x, double z);
	
	public double getTemperature (double x, double z);
	
	public double getHeightNoise (double x, double z);
	
	public int getHeight (double x, double z);
	
	public default int getStartHeight (double x, double z) {
		return 0;
	}

	public default boolean canPopulate (double x, double z) {
		return getHeight(x, z) > getStartHeight(x, z);
	}
	
}
