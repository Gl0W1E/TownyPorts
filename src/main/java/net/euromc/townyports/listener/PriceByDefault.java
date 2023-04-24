package net.euromc.townyports.listener;

import net.euromc.townyports.Main;
import net.euromc.townyports.utils.PortPlotUtil;

import com.palmergames.bukkit.towny.event.PlotChangeTypeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PriceByDefault implements Listener {
    @EventHandler
    public void doSomething(PlotChangeTypeEvent event) {
        if(!PortPlotUtil.isPortPlot(event.getTownBlock()))
            return;
        String sUUID = event.getTownBlock().getTownOrNull().getUUID().toString();
        if(Main.instance.getConfig().getString(sUUID) == null) {
            int iVal = Main.getCustomConfig().getInt("default-port-fee");
            Main.instance.getConfig().createSection(sUUID);
            Main.instance.getConfig().set(sUUID, Double.valueOf(iVal));
            Main.instance.saveConfig();
        }
    }
}
