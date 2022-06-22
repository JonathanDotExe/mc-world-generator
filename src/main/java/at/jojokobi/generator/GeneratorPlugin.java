package at.jojokobi.generator;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class GeneratorPlugin extends JavaPlugin implements Listener{

	private CustomGenerator generator;
	
	public GeneratorPlugin() {
		generator = new CustomGenerator();
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
		return generator;
	}

}
