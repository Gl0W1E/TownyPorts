package net.euromc.townyports.listener;

import net.euromc.townyports.Main;
import com.palmergames.bukkit.towny.event.NewTownEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TownCreate implements Listener {
    @EventHandler
    public void onTown(NewTownEvent event) {
        String sUUID = event.getTown().getUUID().toString();
        if(Main.instance.getConfig().getString(sUUID) == null) {
            Main.instance.getConfig().createSection(sUUID);
            int iVal = Main.getCustomConfig().getInt("default-port-fee");
            Main.instance.getConfig().set(sUUID, Double.valueOf(iVal));
            Main.instance.saveConfig();
        }
    }
}
