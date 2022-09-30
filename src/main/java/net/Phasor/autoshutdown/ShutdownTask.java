package net.Phasor.autoshutdown;

import java.util.TimerTask;

import net.Phasor.autoshutdown.misc.Log;

import org.bukkit.Server;
import org.bukkit.World;

public class ShutdownTask extends TimerTask {
	protected AutoShutdownPlugin plugin = null;
	protected Log log = null;

	ShutdownTask(AutoShutdownPlugin instance) {
		plugin = instance;
		log = plugin.log;
	}

	public void run() {
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				log.info("Shutdown in progress.");

				plugin.getServer().savePlayers();
				Server server = plugin.getServer();

				server.savePlayers();

				for (World world : server.getWorlds()) {
					world.save();
					server.unloadWorld(world, true);
				}

				server.shutdown();
			}
		});
	}
}