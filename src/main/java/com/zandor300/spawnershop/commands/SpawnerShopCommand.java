package com.zandor300.spawnershop.commands;

import com.zandor300.spawnershop.SpawnerShop;
import com.zandor300.zsutilities.commandsystem.Command;
import org.bukkit.command.CommandSender;

/**
 * Created by Zandor on 3/26/15.
 */
public class SpawnerShopCommand extends Command {

	public SpawnerShopCommand() {
		super("spawnershop", "Show info.");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		SpawnerShop.getChat().sendMessage(sender, "SpawnerShop 1.0.0 by Zandor300");
	}
}
