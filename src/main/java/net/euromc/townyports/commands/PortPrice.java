package net.euromc.townyports.commands;

import net.euromc.townyports.Main;
import com.palmergames.bukkit.towny.TownyAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PortPrice implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(!(sender instanceof Player)) {
            Bukkit.getLogger().info(Main.PREFIX + "You must be a player to run this command.");
            Bukkit.getLogger().info(Main.PREFIX + "If you want to see the price as an administrator, check config.yml");
            Bukkit.getLogger().info(Main.PREFIX + "You can use the ``/townuuid`` command to see the town's UUID");
            return true;
        }

        Player player = (Player) sender;

        if (!Main.getCustomConfig().getBoolean("uses-economy")) {
            player.sendMessage(Main.PREFIX + "§cEconomy is disabled for TownyPorts.");
            return true;
        }


            if (args.length >= 1) {
                if (args[0].matches("[0-9]+")) {
                    if (TownyAPI.getInstance().getResident(player.getName()).isMayor()) {
                        double dbl = Double.parseDouble(args[0]);
                        if (dbl >= Main.getCustomConfig().getInt("minimum-port-fee")) {
                            if (Main.getCustomConfig().getInt("maximum-port-fee") >= dbl) {
                                if (Main.instance.getConfig().getString(TownyAPI.getInstance().getResident(player.getName()).getTownOrNull().getUUID().toString()) != null) {
                                    Main.instance.getConfig().createSection(TownyAPI.getInstance().getResident(player.getName()).getTownOrNull().getUUID().toString());
                                }
                                Main.instance.getConfig().set(TownyAPI.getInstance().getResident(player.getName()).getTownOrNull().getUUID().toString(), dbl);
                                Main.instance.saveConfig();

                                player.sendMessage("§6[TownyPorts]§a Successfully set the port travel fee to " + dbl + Main.getCustomConfig().getString("currency-sign") + ".");
                            } else {
                                player.sendMessage("§6[TownyPorts]§c A port's travel fee must not be higher than " + Main.getCustomConfig().getInt("maximum-port-fee") + Main.getCustomConfig().getString("currency-sign"));
                            }
                        } else {
                            player.sendMessage("§6[TownyPorts]§c A port's travel fee must not be lower than " + + Main.getCustomConfig().getInt("minimum-port-fee") + Main.getCustomConfig().getString("currency-sign"));
                        }
                    } else {
                        player.sendMessage("§6[TownyPorts]§c You are not the mayor of your town.");
                        return true;
                    }
                } else {
                    player.sendMessage("§6[TownyPorts]§c That is not an integer.");
                    return true;
                }
            } else {
                player.sendMessage("§6[TownyPorts]§5 Correct usage: `/setportprice <amount>`.");
                return true;
            }
        return false;
    }
}
