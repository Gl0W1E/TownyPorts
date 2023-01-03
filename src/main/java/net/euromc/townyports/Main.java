package net.euromc.townyports;

import net.euromc.townyports.commands.*;
import net.euromc.townyports.listener.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

public final class Main extends JavaPlugin {

    public static Main instance;
    private File customConfigFile;
    private FileConfiguration customConfig;


    private static Logger log = Bukkit.getLogger();
    public static String PREFIX;

    @Override
    public void onEnable() {
        instance = this;
        PREFIX = "§6[TownyPorts]§r ";
        createCustomConfig();
        loadConfig();
        asciiText();
        setupListeners();
        setupCommands();

        Bukkit.broadcastMessage(PREFIX + "Plugin has been loaded.");
    }




    private void setupListeners() {
        getServer().getPluginManager().registerEvents(new PlotChangeType(), this);
        getServer().getPluginManager().registerEvents(new AlreadyHavePort(), this);
        getServer().getPluginManager().registerEvents(new TownCreate(), this);
        getServer().getPluginManager().registerEvents(new PriceByDefault(), this);
        getServer().getPluginManager().registerEvents(new PlayerTeleportToPortAlert(), this);
    }

    private void setupCommands() {
        Objects.requireNonNull(getCommand("port")).setExecutor(new PortCommand());
        Objects.requireNonNull(getCommand("port")).setTabCompleter(new PortTabCommand());
        Objects.requireNonNull(getCommand("setportprice")).setExecutor(new PortPrice());
        Objects.requireNonNull(getCommand("portprice")).setExecutor(new GetPortPriceCommand());
        Objects.requireNonNull(getCommand("townuuid")).setExecutor(new TownUuid());
        Objects.requireNonNull(getCommand("townuuid")).setTabCompleter(new PortTabCommand());
    }

    private void asciiText() {
        log.info("§e█████████████████████████████████████████████████");
        log.info("§e██████████████ §a TownyPorts §e██████████████████");
        log.info("§e█████████████████████████████████████████████████");
        log.info("");
        log.info("§a████████  ██████  ██     ██ ███    ██ ██    ██ ");
        log.info("§a   ██    ██    ██ ██     ██ ████   ██  ██  ██  ");
        log.info("§a   ██    ██    ██ ██  █  ██ ██ ██  ██   ████   ");
        log.info("§a   ██    ██    ██ ██ ███ ██ ██  ██ ██    ██    ");
        log.info("§a   ██     ██████   ███ ███  ██   ████    ██    ");
        log.info("");
        log.info("§6    ██████   ██████  ██████  ████████ ███████  ");
        log.info("§6    ██   ██ ██    ██ ██   ██    ██    ██       ");
        log.info("§6    ██████  ██    ██ ██████     ██    ███████  ");
        log.info("§6    ██      ██    ██ ██   ██    ██         ██  ");
        log.info("§6    ██       ██████  ██   ██    ██    ███████  ");
        log.info("                    §5by 0xBit & darthpeti       ");
        log.info("");
        log.info("§e█████████████████████████████████████████████████");
    }

    public void createCustomConfig() {
        customConfigFile = new File(getDataFolder(), "settings.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            saveResource("settings.yml", false);
        }

        customConfig = new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }



    public static FileConfiguration getCustomConfig() {
        return instance.customConfig;
    }

    @Override
    public void onDisable() {
        Bukkit.broadcastMessage(PREFIX + "Plugin has been unloaded.");
        saveConfig();
    }



    public void loadConfig() {
        instance.getConfig().options().copyDefaults(false);
        instance.saveDefaultConfig();
    }
}


