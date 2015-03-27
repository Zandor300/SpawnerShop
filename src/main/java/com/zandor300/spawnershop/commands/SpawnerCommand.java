package com.zandor300.spawnershop.commands;

import com.zandor300.spawnershop.SpawnerShop;
import com.zandor300.zsutilities.commandsystem.Command;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.SpawnEgg;

import java.util.Arrays;

/**
 * Created by Zandor on 3/27/15.
 */
public class SpawnerCommand extends Command {

	public SpawnerCommand() {
		super("spawner", "Buy spawners from a GUI.");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(!(sender instanceof Player))
			return;
		Player player = (Player) sender;

		Inventory inventory = Bukkit.createInventory(null, 54, ChatColor.DARK_AQUA + "Spawner Shop");
		int i = 0;
		for(EntityType type : EntityType.values()) {
			try {
				SpawnEgg egg = new SpawnEgg();
				egg.setSpawnedType(type);
				ItemStack item = new ItemStack(Material.MONSTER_EGG, 1, egg.getData());
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName(ChatColor.WHITE + egg.getSpawnedType().getName() + " spawnegg");
				int price = SpawnerShop.getCustomConfig().getInt("price." + egg.getSpawnedType().getName().toLowerCase());
				meta.setLore(Arrays.asList(ChatColor.GREEN + "Price: $" + price));
				item.setItemMeta(meta);
				inventory.setItem(i, item);
				i++;
			} catch(Exception e) {}
		}
		player.openInventory(inventory);
	}
}
