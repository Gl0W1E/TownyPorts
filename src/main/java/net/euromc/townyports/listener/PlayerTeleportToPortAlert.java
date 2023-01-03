package net.euromc.townyports.listener;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownyMessaging;
import net.euromc.townyports.Main;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerTeleportToPortAlert implements Listener {
    @EventHandler
    public void onTP(PlayerTeleportEvent event) {

        if (!Main.getCustomConfig().getBoolean("enable-port-arrival-alert")) {
            return;
        }

        if(event.getCause().equals(PlayerTeleportEvent.TeleportCause.PLUGIN)) {
            Location to = event.getTo();
            Location from = event.getFrom();
            if(!TownyAPI.getInstance().isWilderness(to) && !TownyAPI.getInstance().isWilderness(from)) {
                if(TownyAPI.getInstance().getTownBlock(to).getType().getName().equalsIgnoreCase("Port") && TownyAPI.getInstance().getTownBlock(from).getType().getName().equalsIgnoreCase("Port")) {
                    TownyMessaging.sendPrefixedTownMessage(TownyAPI.getInstance().getTown(to), "§4" + event.getPlayer().getName() + " has arrived at the town port.");
                }
            }
        }
    }
}
