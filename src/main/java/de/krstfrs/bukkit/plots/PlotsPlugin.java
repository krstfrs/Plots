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
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import de.krstfrs.bukkit.plots.commands.ClaimPlotCommand;
import de.krstfrs.bukkit.plots.commands.CommandException;

public class PlotsPlugin extends JavaPlugin {

	private Logger log;
	private PluginManager pm;

	private WorldGuardPlugin worldGuard;
	private WorldEditPlugin worldEdit;

	private PlotsConfiguration plotsConfig;

	private ClaimPlotCommand claimPlotCommand;

	@Override
	public void onEnable() {

		log = this.getLogger();
		pm = this.getServer().getPluginManager();

		worldGuard = loadWorldGuard();

		if (worldGuard == null) {
			log.log(Level.SEVERE, "WorldGuard not found, disabling Plots.");
			pm.disablePlugin(this);
		}

		worldEdit = loadWorldEdit();

		if (worldEdit == null) {
			log.log(Level.SEVERE, "WorldEdit not found, disabling Plots.");
			pm.disablePlugin(this);
		}

		FileConfiguration config = this.getConfig();
		config.options().copyDefaults(true);
		this.saveConfig();

		plotsConfig = new PlotsConfiguration(config);

		claimPlotCommand = new ClaimPlotCommand(this);

	}

	@Override
	public void onDisable() {

	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		try {

			if (command.getName().equalsIgnoreCase("claimplot"))
				claimPlotCommand.execute(sender, args);

		} catch (CommandException e) {
			sender.sendMessage(ChatColor.RED + e.getMessage());
		}

		return true;

	}

	public WorldGuardPlugin getWorldGuard() {

		return worldGuard;

	}

	public WorldEditPlugin getWorldEdit() {

		return worldEdit;

	}

	public PlotsConfiguration getPlotsConfig() {

		return plotsConfig;

	}

	private WorldGuardPlugin loadWorldGuard() {

		Plugin plugin = pm.getPlugin("WorldGuard");

		if ((plugin == null) || !(plugin instanceof WorldGuardPlugin))
			return null;

		return (WorldGuardPlugin) plugin;

	}

	private WorldEditPlugin loadWorldEdit() {

		Plugin plugin = pm.getPlugin("WorldEdit");

		if ((plugin == null) || !(plugin instanceof WorldEditPlugin))
			return null;

		return (WorldEditPlugin) plugin;

	}

}
