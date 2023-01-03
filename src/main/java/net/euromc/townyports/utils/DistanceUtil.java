package net.euromc.townyports.utils;

import com.palmergames.bukkit.towny.object.*;
import com.palmergames.util.MathUtil;

public class DistanceUtil {
    
    public double distanceBetweenTownAndTown(Town to,Town from){
        double distance = 0;
        if(from.getTownBlockTypeCache().getNumTownBlocks(TownBlockTypeHandler.getType("port"), TownBlockTypeCache.CacheType.ALL) == 1 && to.getTownBlockTypeCache().getNumTownBlocks(TownBlockTypeHandler.getType("port"), TownBlockTypeCache.CacheType.ALL) == 1){
            //Both Towns have a Port TownBlock.
            TownBlock toPortBlock = getPortTownBlock(to);
            TownBlock fromPortBlock = getPortTownBlock(from);
            MathUtil.distance(toPortBlock.getX(), fromPortBlock.getX(), toPortBlock.getZ(), fromPortBlock.getZ());
        }
        return distance;
    }

    public TownBlock getPortTownBlock(Town town){
            TownBlockType townBlockType = TownBlockTypeHandler.getType("port");
            town.getTownBlockTypeCache().getCache(TownBlockTypeCache.CacheType.ALL).getOrDefault(townBlockType, 0);
            TownBlock townBlocks = (TownBlock) town.getTownBlocks().stream().filter(townBlock -> townBlock.getType().equals(townBlockType));
        return townBlocks;
    }
}
