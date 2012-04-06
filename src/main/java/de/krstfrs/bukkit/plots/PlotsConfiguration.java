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

import org.bukkit.configuration.file.FileConfiguration;

public class PlotsConfiguration {

	private int minWidth;
	private int maxWidth;
	private boolean lengthAsRatio;
	private int minLength;
	private int maxLength;

	public PlotsConfiguration(FileConfiguration config) {

		minWidth = config.getInt("min-width");
		maxWidth = config.getInt("max-width");
		lengthAsRatio = config.getBoolean("length-as-ratio");
		minLength = config.getInt("min-length");
		maxLength = config.getInt("max-length");

	}

	public int getMinWidth() {

		return minWidth;

	}

	public int getMaxWidth() {

		return maxWidth;

	}

	public boolean isLengthAsRatio() {

		return lengthAsRatio;

	}

	public int getMinLength() {

		return minLength;

	}

	public int getMaxLength() {

		return maxLength;

	}

}
