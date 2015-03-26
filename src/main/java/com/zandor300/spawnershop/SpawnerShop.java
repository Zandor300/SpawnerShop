package com.zandor300.spawnershop;

import com.zandor300.zsutilities.config.Config;
import com.zandor300.zsutilities.utilities.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import java.io.IOException;

/**
 * Created by Zandor on 3/26/15.
 */
public class SpawnerShop extends JavaPlugin {

	private static Chat chat = new Chat("SpawnerShop");
	private static SpawnerShop plugin;
	private static Config config;

	public static Chat getChat() {
		return chat;
	}

	public static SpawnerShop getPlugin() {
		return plugin;
	}

	public static Config getCustomConfig() {
		return config;
	}

	@Override
	public void onEnable() {
		chat.sendConsoleMessage("Setting things up...");

		plugin = this;
		config = new Config(this, "config.yml", true);
		PluginManager pm = Bukkit.getPluginManager();

		chat.sendConsoleMessage("Starting metrics...");
		try {
			new Metrics(this).start();
			chat.sendConsoleMessage("Submitted stats to MCStats.org.");
		} catch (IOException e) {
			chat.sendConsoleMessage("Couldn't submit stats to MCStats.org...");
		}

		chat.sendConsoleMessage("Registering events...");

		chat.sendConsoleMessage("Registered events.");

		chat.sendConsoleMessage("Registering commands...");

		chat.sendConsoleMessage("Registered commands.");

		chat.sendConsoleMessage("Everything is setup!");
		chat.sendConsoleMessage("Enabled.");
	}
}