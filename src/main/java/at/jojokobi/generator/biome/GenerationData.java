package at.jojokobi.generator.biome;

public class GenerationData {
	private int startHeight;
	private int height;
	private double heightNoise;
	private long seed;
	private int seaLevel;
	private double heightMultiplier;
	
	
	
	public GenerationData(int startHeight, int height, double heightNoise, long seed, int seaLevel,
			double heightMultiplier) {
		super();
		this.startHeight = startHeight;
		this.height = height;
		this.heightNoise = heightNoise;
		this.seed = seed;
		this.seaLevel = seaLevel;
		this.heightMultiplier = heightMultiplier;
	}
	public int getStartHeight() {
		return startHeight;
	}
	public void setStartHeight(int startHeight) {
		this.startHeight = startHeight;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public double getHeightNoise() {
		return heightNoise;
	}
	public void setHeightNoise(double heightNoise) {
		this.heightNoise = heightNoise;
	}
	public long getSeed() {
		return seed;
	}
	public void setSeed(long seed) {
		this.seed = seed;
	}
	public int getSeaLevel() {
		return seaLevel;
	}
	public void setSeaLevel(int seaLevel) {
		this.seaLevel = seaLevel;
	}
	public double getHeightMultiplier() {
		return heightMultiplier;
	}
	public void setHeightMultiplier(double heightMultiplier) {
		this.heightMultiplier = heightMultiplier;
	}
	
}
