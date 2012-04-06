// Plots
// Copyright (C) 2012 Gian Perrone / Krstfrs
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program. If not, see <http://www.gnu.org/licenses/>.

package de.krstfrs.bukkit.plots;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import de.krstfrs.bukkit.plots.util.CommandException;

public class Plots extends JavaPlugin {

	private Logger log;
	private PluginManager pm;

	private WorldGuardPlugin worldGuard;
	private WorldEditPlugin worldEdit;

	@Override
	public void onEnable() {

		log = this.getLogger();
		pm = this.getServer().getPluginManager();

		worldGuard = getWorldGuard();

		if (worldGuard == null) {
			log.log(Level.SEVERE, "WorldGuard not found, disabling Plots.");
			pm.disablePlugin(this);
		}

		worldEdit = getWorldEdit();

		if (worldEdit == null) {
			log.log(Level.SEVERE, "WorldEdit not found, disabling Plots.");
			pm.disablePlugin(this);
		}

	}

	@Override
	public void onDisable() {

	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		try {

			if (command.getName().equalsIgnoreCase("claimplot"))
				commandClaimPlot(sender, args);

		} catch (CommandException e) {
			sender.sendMessage(ChatColor.RED + e.getMessage());
		}

		return true;

	}

	private void commandClaimPlot(CommandSender sender, String[] args)
			throws CommandException {

		Player player = getPlayer(sender);

		if (player == null)
			throw new CommandException("Can only be executed by a player.");

		Selection sel = worldEdit.getSelection(player);

		if (sel == null)
			throw new CommandException("Select a region with WorldEdit first.");

		if (!(sel instanceof CuboidSelection))
			throw new CommandException("Only cuboid selections are supported.");

		CuboidSelection cuboid = (CuboidSelection) sel;

		if (cuboid.getHeight() != cuboid.getWorld().getMaxHeight())
			throw new CommandException(
					"Expand your selection vertically first.");

	}

	private WorldGuardPlugin getWorldGuard() {

		Plugin plugin = pm.getPlugin("WorldGuard");

		if ((plugin == null) || !(plugin instanceof WorldGuardPlugin))
			return null;

		return (WorldGuardPlugin) plugin;

	}

	private WorldEditPlugin getWorldEdit() {

		Plugin plugin = pm.getPlugin("WorldEdit");

		if ((plugin == null) || !(plugin instanceof WorldEditPlugin))
			return null;

		return (WorldEditPlugin) plugin;

	}

	private Player getPlayer(CommandSender sender) {

		if ((sender == null) || !(sender instanceof Player))
			return null;

		return (Player) sender;

	}

}
