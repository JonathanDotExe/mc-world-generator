package at.jojokobi.generator;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class GeneratorPlugin extends JavaPlugin implements Listener{

	public GeneratorPlugin() {
		
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
	}
	
	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		return new CustomGenerator();
	}
	
//	@EventHandler
//	public void onPlayerInteract (PlayerInteractEvent event) {
//		if (event.hasBlock()) {
//			NoiseGenerator gen = new SimplexNoiseGenerator(event.getClickedBlock().getWorld().getSeed() + 486);
//			int totalX = event.getClickedBlock().getLocation().getBlockX();
//			int y = event.getClickedBlock().getLocation().getBlockY();
//			int totalZ = event.getClickedBlock().getLocation().getBlockZ();
//			double noise = gen.noise(totalX * 0.05, y * 0.05, totalZ * 0.05);
//			event.getPlayer().sendMessage("Noise: " + noise);
//		}
//	}

}
