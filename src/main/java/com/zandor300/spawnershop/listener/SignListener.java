package com.zandor300.spawnershop.listener;

import com.zandor300.spawnershop.SpawnerShop;
import com.zandor300.spawnershop.handlers.BuyHandler;
import com.zandor300.zsutilities.utilities.string.StringUtilities;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.Sign;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * Created by Zandor on 3/26/15.
 */
public class SignListener implements Listener {

	/*
	 * [Spawner]
	 * <amount>
	 * <type>
	 * <moneyz>
	 */

	@EventHandler
	public void onSignCreation(SignChangeEvent event) {
		System.out.println("1");

		if(!event.getLine(0).toLowerCase().contains("[spawner]"))
			return;

		System.out.println("3");

		event.setLine(0, ChatColor.DARK_AQUA + "[Spawner]");
		event.setLine(1, ChatColor.GREEN + event.getLine(1));
		event.setLine(2, ChatColor.RED + event.getLine(2));
		event.setLine(3, "$" + event.getLine(3));

		System.out.println("4");
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
			return;

		Block block = event.getClickedBlock();
		if(!block.getType().equals(Material.SIGN) && !block.getType().equals(Material.SIGN_POST)
				&& !block.getType().equals(Material.WALL_SIGN))
			return;

		Sign sign = (Sign) block.getState();
		if(!sign.getLine(0).toLowerCase().contains("[spawner]"))
			return;

		int amount = 0;
		try {
			amount = Integer.valueOf(sign.getLine(1).replaceAll(ChatColor.GREEN + "", ""));
		} catch(Exception e) {
			SpawnerShop.getChat().sendMessage(player, "Invalid amount " + StringUtilities.removeChatColors(sign.getLine(1)));
			return;
		}

		EntityType type = EntityType.valueOf(
				sign.getLine(2).replaceAll(ChatColor.RED + "", "").replaceAll(" ", "_").toUpperCase());
		if(type == null) {
			SpawnerShop.getChat().sendMessage(player, "Invalid mobtype " + sign.getLine(2));
			return;
		}

		int money = 0;
		try {
			money = Integer.valueOf(sign.getLine(3).replaceAll("\\$", ""));
		} catch(Exception e) {
			SpawnerShop.getChat().sendMessage(player, "Invalid money " + sign.getLine(3));
			return;
		}

		if(!player.hasPermission("spawnershop.sign")) {
			SpawnerShop.getChat().sendMessage(player, ChatColor.RED +
					"You don't have permission to buy spawners from signs.");
			return;
		}

		BuyHandler.buy(player, type, amount, money);
		SpawnerShop.getChat().sendMessage(player, "Bought " + amount + " " + type.getName() + " spawner for $" + money + ".");
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if(!event.getBlock().getType().equals(Material.MOB_SPAWNER))
			return;

		if(event.getPlayer().getItemInHand().getItemMeta().getLore().size() <= 0)
			return;

		EntityType type;
		try {
			type = EntityType.valueOf(event.getPlayer().getItemInHand().getItemMeta().getLore().get(0).toUpperCase());
		} catch(Exception e) {
			return;
		}

		CreatureSpawner spawner = (CreatureSpawner) event.getBlock().getState();
		spawner.setSpawnedType(type);
	}
}
