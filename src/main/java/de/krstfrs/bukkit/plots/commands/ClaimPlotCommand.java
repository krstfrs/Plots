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

package de.krstfrs.bukkit.plots.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;

import de.krstfrs.bukkit.plots.PlotsConfiguration;
import de.krstfrs.bukkit.plots.PlotsPlugin;

public class ClaimPlotCommand extends AbstractCommand {

	private PlotsPlugin plots;
	private PlotsConfiguration config;

	public ClaimPlotCommand(PlotsPlugin plots) {

		this.plots = plots;
		this.config = plots.getPlotsConfig();

	}

	public void execute(CommandSender sender, String[] args)
			throws CommandException {

		Player player = commandSenderToPlayer(sender);

		if (player == null)
			throw new CommandException("Can only be executed by a player.");

		Selection sel = plots.getWorldEdit().getSelection(player);

		if (sel == null)
			throw new CommandException("Select a region with WorldEdit first.");

		if (!(sel instanceof CuboidSelection))
			throw new CommandException("Only cuboid selections are supported.");

		CuboidSelection cuboid = (CuboidSelection) sel;

		if (cuboid.getHeight() != cuboid.getWorld().getMaxHeight())
			throw new CommandException(
					"Expand your selection vertically first.");

		int width = cuboid.getWidth();
		int length = cuboid.getLength();

		if (width > length) {
			int temp = length;
			length = width;
			width = temp;
		}

		if (width < config.getMinWidth() || width > config.getMaxWidth())
			throw new CommandException("Width has to be between "
					+ config.getMinWidth() + " and " + config.getMaxWidth()
					+ ".");

		if (config.isLengthAsRatio()) {

			if (length * 100 < config.getMinLength() * width
					|| length * 100 > config.getMaxLength() * width)
				throw new CommandException("Length has to be between "
						+ config.getMinLength() + "% and "
						+ config.getMaxLength() + "% of the width.");

		} else {

			if (length < config.getMinLength()
					|| length > config.getMaxLength())
				throw new CommandException("Length has to be between "
						+ config.getMinLength() + " and "
						+ config.getMaxLength() + ".");

		}

	}

}
