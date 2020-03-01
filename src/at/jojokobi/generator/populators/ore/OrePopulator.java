package at.jojokobi.generator.populators.ore;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

public class OrePopulator extends BlockPopulator{
	
	private List<Ore> ores = new ArrayList<Ore> ();

	public OrePopulator() {
		ores.add(new Ore(Material.COAL_ORE, 17, 20, 128));
		ores.add(new Ore(Material.IRON_ORE, 9, 20, 64));
		ores.add(new Ore(Material.GOLD_ORE, 9, 2, 32));
		ores.add(new Ore(Material.EMERALD_ORE, 1, 3, 32));
		ores.add(new Ore(Material.REDSTONE_ORE, 8, 8, 16));
		ores.add(new Ore(Material.DIAMOND, 9, 1, 16));
		ores.add(new Ore(Material.LAPIS_ORE, 7, 1, 32));
	}

	@Override
	public void populate(World world, Random random, Chunk chunk) {
		for (Ore ore : ores) {
			ore.generate(chunk, random);
		}
	}

}
