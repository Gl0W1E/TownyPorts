package net.euromc.townyports.commands;

import net.euromc.townyports.Main;
import net.euromc.townyports.utils.LocationUtil;
import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.confirmations.Confirmation;
import com.palmergames.bukkit.towny.object.*;
import com.palmergames.util.MathUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class PortCommand implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            int warmup = Main.getCustomConfig().getInt("port-travel-warmup-in-ticks");
            Player p = (Player) sender;
            if(args.length > 0) {
                    if (TownyAPI.getInstance().getResident(p.getName()).hasTown()) {
                        Town t = TownyAPI.getInstance().getResident(p.getName()).getTownOrNull();
                        if (t.hasNation()) {
                            if (TownyAPI.getInstance().isWilderness(p.getLocation())) {
                                p.sendMessage("§6[TownyPorts]§c You cannot teleport to a port from the wilderness.");
                                return true;
                            } else {
                                if (TownyAPI.getInstance().getTownBlock(p.getLocation()).getType().equals(TownBlockTypeHandler.getType("port"))) {
                                    if (TownyAPI.getInstance().getTown(args[0]) != null) {
                                        Town destination = TownyAPI.getInstance().getTown(args[0]);
                                        if (destination.hasNation()) {
                                            if (!destination.getNationOrNull().hasEnemy(t.getNationOrNull())) {
                                                if (destination.getTownBlockTypeCache().getNumTownBlocks(TownBlockTypeHandler.getType("port"), TownBlockTypeCache.CacheType.ALL) > 0) {
                                                    for (TownBlock tb : destination.getTownBlocks()) {
                                                        if (!tb.getTypeName().equalsIgnoreCase("port")) {
                                                            continue;
                                                        }
                                                        WorldCoord wc = tb.getWorldCoord();
                                                        if (MathUtil.distance(TownyAPI.getInstance().getTownBlock(p.getLocation()).getWorldCoord(), wc) <= 2750) {
                                                            World world = Bukkit.getWorld(wc.getWorldName());
                                                            int X = wc.getX() * 16 + 8;
                                                            int Z = wc.getZ() * 16 + 8;
                                                            int safeY = world.getHighestBlockAt(X, Z).getY();
                                                            Location loc3 = world.getBlockAt(X, safeY + 1, Z).getLocation();
                                                            if (LocationUtil.isSafe(loc3)) {
                                                                final TownBlock loc = TownyAPI.getInstance().getTownBlock(p.getLocation());
                                                                p.sendMessage("§6[TownyPorts]§a Travelling to this port...");
                                                                if (Main.getCustomConfig().getBoolean("uses-economy")) {
                                                                    p.sendMessage("§6[TownyPorts]§a This will cost " + Main.instance.getConfig().getString(destination.getUUID().toString()) + Main.getCustomConfig().getString("currency-sign") + "...");
                                                                }
                                                                Confirmation.runOnAccept(()->{
                                                                    int secTime = Main.getCustomConfig().getInt("port-travel-warmup-in-ticks")*20;
                                                                    p.sendMessage("§6[TownyPorts]§a You accepted the costs of this trip. You will depart in §b" + secTime + " seconds§a.");
                                                                    Bukkit.getScheduler().runTaskLater(Main.instance, new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            if (p.isOnline()) {
                                                                                String cost = Main.instance.getConfig().getString(destination.getUUID().toString());
                                                                                double costDouble = Double.parseDouble(cost);
                                                                                if (TownyAPI.getInstance().getResident(p.getName()).getAccount().canPayFromHoldings(costDouble)) {
                                                                                    if (loc == TownyAPI.getInstance().getTownBlock(p.getLocation())) {
                                                                                        if (Main.getCustomConfig().getBoolean("uses-economy")) {
                                                                                            TownyAPI.getInstance().getResident(p.getName()).getAccount().payTo(costDouble, destination.getAccount(), "Travelled to port.");
                                                                                        }
                                                                                        p.teleport(loc3);
                                                                                        p.sendMessage("§6[TownyPorts]§a Arrived at the port.");
                                                                                    } else {
                                                                                        p.sendMessage("§6[TownyPorts]§c You have moved away from the port while waiting, teleportation denied.");
                                                                                    }
                                                                                } else {
                                                                                    p.sendMessage("§6[TownyPorts]§c You cannot afford to travel to this port");
                                                                                }
                                                                            } else {
                                                                                Bukkit.getLogger().info("§f[§4ALERT§f] §e" + p.getName() + " has tried to teleport to " + destination.getName() + "'s port while being offline.");
                                                                            }
                                                                        }
                                                                    }, warmup);
                                                                }).runOnCancel(()-> {
                                                                    p.sendMessage("§6[TownyPorts]§c Your trip has been canceled.");
                                                                }).sendTo(p.getPlayer());

                                                            } else {
                                                                p.sendMessage("§6[TownyPorts]§c The destination port's location is not safe.");
                                                                return true;
                                                            }

                                                        } else {
                                                            p.sendMessage("§6[TownyPorts]§c The port is too far away.");
                                                            return true;
                                                        }
                                                    }
                                                } else {
                                                    p.sendMessage("§6[TownyPorts]§c That town does not have a port.");
                                                    return true;

                                                }
                                            } else {
                                                if (Main.getCustomConfig().getBoolean("port-travel-denies-for-enemies")) {
                                                    p.sendMessage("§6[TownyPorts]§c You cannot teleport to an enemy nation's ports.");
                                                    return true;
                                                }
                                            }
                                        } else {
                                            p.sendMessage("§6[TownyPorts]§c The destination town does not have a nation.");
                                            return true;
                                        }
                                    } else {
                                        p.sendMessage("§6[TownyPorts]§c This town does not exist.");
                                        return true;
                                    }
                                } else {
                                    p.sendMessage("§6[TownyPorts]§c You can only go to another port starting from a port plot.");
                                    return true;
                                }
                            }
                            return false;
                        } else {
                            p.sendMessage("§6[TownyPorts]§c You do not belong to a nation.");
                            return true;
                        }
                    } else {
                        p.sendMessage("§6[TownyPorts]§c You do not belong to a town.");
                        return true;
                    }
            } else {
                p.sendMessage("§6[TownyPorts]§d Correct usage: `/port <destination-town>`.");
                return true;
                }
            } else {
                Bukkit.getLogger().info("[ERROR] You cannot use this command as the console / non player.");
                return true;
            }
        }
    }
