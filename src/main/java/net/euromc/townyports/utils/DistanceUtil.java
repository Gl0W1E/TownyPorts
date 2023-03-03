package net.euromc.townyports.utils;

import com.palmergames.bukkit.towny.object.*;
import com.palmergames.util.MathUtil;

public class DistanceUtil {
    
    public double distanceBetweenTownAndTown(Town to,Town from){
        double distance = 0;
        if(PortPlotUtil.hasPortPlot(to) && PortPlotUtil.hasPortPlot(from)){
            //Both Towns have a Port TownBlock.
            TownBlock toPortBlock = PortPlotUtil.getPortPlot(to);
            TownBlock fromPortBlock = PortPlotUtil.getPortPlot(from);
            MathUtil.distance(toPortBlock.getX(), fromPortBlock.getX(), toPortBlock.getZ(), fromPortBlock.getZ());
        }
        return distance;
    }
}