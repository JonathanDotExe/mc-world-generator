package at.jojokobi.generator.biome;

import java.util.Random;

import org.bukkit.block.Biome;
import org.bukkit.util.noise.SimplexNoiseGenerator;

import at.jojokobi.mcutil.generation.TerrainGenUtil;

@Deprecated
public class BiomeSystemOld {

	public static final int REGION_SIZE = 128;
	public static final int RIVER_DISTANCE = 32;
	public static final int RIVER_THICKNESS = 2;
	
	private long seed;
	private SimplexNoiseGenerator generator;
	
	public BiomeSystemOld(long seed) {
		this.seed = seed;
		generator = new SimplexNoiseGenerator(seed+5847);
	}
	
	public BiomeCenter getBiomeForRegion (int x, int z) {
		Random random = new Random(TerrainGenUtil.generateValueBasedSeed(seed, x, 1, z));
		return new BiomeCenter(Biome.values()[random.nextInt(Biome.values().length)], x*REGION_SIZE + random.nextInt(REGION_SIZE), z*REGION_SIZE + random.nextInt(REGION_SIZE));
	}
	
	public BiomeCenter[] getRiversForRegion (int x, int z) {
		Random random = new Random(TerrainGenUtil.generateValueBasedSeed(seed, x, 2, z));
		BiomeCenter[] rivers = new BiomeCenter[1];
		for (int i = 0; i < rivers.length; i++) {
			rivers[i] = new BiomeCenter(Biome.RIVER, x*REGION_SIZE + random.nextInt(REGION_SIZE), z*REGION_SIZE + random.nextInt(REGION_SIZE));
		}
		return rivers;
	}
	
	public Biome getBiome (int x, int z) {
		int regX = x/REGION_SIZE;
		int regZ = z/REGION_SIZE;
		BiomeCenter biome = null;
		double distance = 0;
		for (int i = regX - 1; i <= regX+1; i++) {
			for (int j = regZ - 1; j <= regZ+1; j++) {
				BiomeCenter temp = getBiomeForRegion(i, j);
				if (biome == null || squaredDistance(x, z, temp.getX(), temp.getZ()) < distance) {
					biome = temp;
					distance = squaredDistance(x, z, temp.getX(), temp.getZ());
				}
			}
		}
		return biome.getBiome();
	}
	
	public boolean isRiver (int x, int z) {
//		int regX = x/REGION_SIZE;
//		int regZ = z/REGION_SIZE;
//		boolean river = false;
//		List<BiomeCenter> rivers = new ArrayList<>();
//		for (int i = regX - 1; i <= regX+1; i++) {
//			for (int j = regZ - 1; j <= regZ+1; j++) {
//				rivers.addAll(Arrays.asList(getRiversForRegion(regX, regZ)));
//			}
//		}
//		rivers.sort((r1,r2) -> (int) Math.round(squaredDistance(x, z, r2.getX(), r2.getZ()) - (int) Math.round(squaredDistance(x, z, r1.getX(), r1.getZ()))));
//		if (rivers.size() >= 2) {
//			BiomeCenter river1 = rivers.get(0);
//			BiomeCenter river2 = rivers.get(1);
//			int maxX = Math.max(river1.getX(), river2.getX());
//			int maxZ = Math.max(river1.getZ(), river2.getZ());
//			river = (maxX - RIVER_THICKNESS < x || maxX + RIVER_THICKNESS > x) && (maxZ - RIVER_THICKNESS < z || maxZ + RIVER_THICKNESS > z);
//		}
		return generator.noise(x*0.01, z*0.01) < -0.5;
	}
	
	public static double squaredDistance (int x1, int z1, int x2, int z2) {
		return Math.pow(x1 - x2, 2) + Math.pow(z1 - z2, 2);
	}
	
	public static class BiomeCenter {
		private Biome biome;
		private int x;
		private int z;
		
		public BiomeCenter(Biome biome, int x, int z) {
			super();
			this.biome = biome;
			this.x = x;
			this.z = z;
		}

		public Biome getBiome() {
			return biome;
		}

		public int getX() {
			return x;
		}

		public int getZ() {
			return z;
		}
	}

}
