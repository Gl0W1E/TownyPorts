package net.euromc.townyports.listener;

import com.palmergames.bukkit.towny.TownySettings;
import com.palmergames.bukkit.towny.event.*;
import com.palmergames.bukkit.towny.object.TownBlockTypeHandler;
import com.palmergames.bukkit.towny.object.WorldCoord;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlotChangeType implements Listener {

    @EventHandler
    public void onPlotChangeType(PlotPreChangeTypeEvent event) {
        if (event.getNewType().equals(TownBlockTypeHandler.getType("port"))) {
            WorldCoord wc = event.getTownBlock().getWorldCoord();
            World world = wc.getBukkitWorld();
            int halfPlotSize = TownySettings.getTownBlockSize() / 2;
            Location loc = wc.getLowerMostCornerLocation().add(halfPlotSize, 61, halfPlotSize);
            Biome biome = world.getBiome(loc);
            if (!biome.name().contains("OCEAN")) {
                event.setCancelled(true);
                event.setCancelMessage("§cYou cannot set plots to port type outside of ocean biomes.");
            }
        }
    }
}


