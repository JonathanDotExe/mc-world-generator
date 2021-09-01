package at.jojokobi.generator.populators;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

import at.jojokobi.generator.AbstractGenerator;
import at.jojokobi.generator.biome.biomes.BiomeGenerator;
import at.jojokobi.generator.biome.biomes.BiomeSystem;
import at.jojokobi.mcutil.generation.TerrainGenUtil;

public class BiomePopulator extends BlockPopulator {

	private Set<ChunkLocation> chunks = new HashSet<>();
	private Set<ChunkLocation> unpopulated = new HashSet<>();

	private AbstractGenerator generator;


	public BiomePopulator(AbstractGenerator generator) {
		super();
		this.generator = generator;
	}

	@Override
	public void populate(World world, Random random, Chunk chunk) {
		unpopulated.add(new ChunkLocation(chunk.getX(), chunk.getZ()));
		chunks.add(new ChunkLocation(chunk.getX(), chunk.getZ()));

		for (Iterator<ChunkLocation> iter = unpopulated.iterator(); iter.hasNext();) {
			ChunkLocation chunkLocation = iter.next();
			if (chunkLocation.canPopulate(chunks)) {
				doPopulate(world, random, chunkLocation.getChunk(world));
				iter.remove();
			}
		}
	}

	private void doPopulate(World world, Random random, Chunk chunk) {
		BiomeSystem system = generator.getBiomeSystem(world);
		BiomeGenerator biome = system.getBiome(chunk.getX() * TerrainGenUtil.CHUNK_WIDTH,
				chunk.getZ() * TerrainGenUtil.CHUNK_LENGTH);
		biome.populate(chunk, random);
	}

}

class ChunkLocation {

	private int x;
	private int z;

	public ChunkLocation(int x, int z) {
		super();
		this.x = x;
		this.z = z;
	}

	public int getX() {
		return x;
	}

	public int getZ() {
		return z;
	}

	public ChunkLocation getRelative(int x, int z) {
		return new ChunkLocation(this.x + x, this.z + z);
	}
//
//	public boolean isGenerated(World world) {
//		return world.isChunkGenerated(x, z);
//	}

	public boolean canPopulate(Set<ChunkLocation> chunks) { //FIXME
		return chunks.containsAll(Arrays.asList(getRelative(-1, -1), getRelative(0, -1), getRelative(1, -1),
				getRelative(-1, 0), getRelative(0, 0), getRelative(1, 0), getRelative(-1, 1), getRelative(0, 1),
				getRelative(1, 1)));
	}

	public Chunk getChunk(World world) {
		return world.getChunkAt(x, z);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + z;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChunkLocation other = (ChunkLocation) obj;
		if (x != other.x)
			return false;
		if (z != other.z)
			return false;
		return true;
	}

}
