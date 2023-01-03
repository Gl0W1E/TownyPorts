package net.euromc.townyports.listener;

import com.palmergames.bukkit.towny.event.PlotPreChangeTypeEvent;
import com.palmergames.bukkit.towny.object.TownBlockTypeCache;
import com.palmergames.bukkit.towny.object.TownBlockTypeHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;


public class AlreadyHavePort implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onTwoPorts(PlotPreChangeTypeEvent event) {
        if(event.getNewType().equals(TownBlockTypeHandler.getType("port"))) {
            if(event.getTownBlock().getTownOrNull().getTownBlockTypeCache().getNumTownBlocks(TownBlockTypeHandler.getType("port"), TownBlockTypeCache.CacheType.ALL) == 1) {
                event.setCancelled(true);
                event.setCancelMessage("§cA town cannot have more than one port.");
            }

        }
    }
}
