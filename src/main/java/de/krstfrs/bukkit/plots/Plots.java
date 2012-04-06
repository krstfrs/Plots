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

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class Plots extends JavaPlugin {

	Logger log;
	PluginManager pm;

	WorldGuardPlugin worldGuard;

	@Override
	public void onEnable() {

		log = this.getLogger();
		pm = this.getServer().getPluginManager();

		worldGuard = getWorldGuard();

		if (worldGuard == null) {
			log.log(Level.SEVERE, "WorldGuard not found, disabling Plots");
			pm.disablePlugin(this);
		}

	}

	@Override
	public void onDisable() {

	}

	private WorldGuardPlugin getWorldGuard() {

		Plugin plugin = pm.getPlugin("WorldGuard");

		if ((plugin == null) || !(plugin instanceof WorldGuardPlugin))
			return null;

		return (WorldGuardPlugin) plugin;

	}

}
