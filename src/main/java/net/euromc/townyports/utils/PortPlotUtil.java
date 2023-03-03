package net.euromc.townyports.utils;

import com.palmergames.bukkit.towny.object.TownBlock;
import com.palmergames.bukkit.towny.object.TownBlockType;

public class PortPlotUtil {

	public static boolean isPortPlot(TownBlock tb) {
		return isPortPlot(tb.getType());
	}

	public static boolean isPortPlot(TownBlockType type) {
		return type.getName().equalsIgnoreCase("port");
	}
}
