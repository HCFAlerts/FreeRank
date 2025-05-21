package me.hcfalerts.freerank;

import me.hcfalerts.freerank.commands.FreeRankAdminCommand;
import me.hcfalerts.freerank.commands.FreeRankCommand;
import me.hcfalerts.freerank.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class FreeRank extends JavaPlugin {
    private static FreeRank instance;
    public String ConfigRoute;
    public static FreeRank getInstance() {return instance;}
    private File dataFile;
    private FileConfiguration dataConfig;

    public void log(String s) {
        Bukkit.getConsoleSender().sendMessage(CC.translate(s));
    }

    @Override
    public void onEnable() {
        instance = this;
        registerDataConfig();
        registerConfig();
        log("&7&m------------------------");
        log("&aFreeRank Plugin &8- &fv" + getDescription().getVersion());
        log("");
        log("&2Configuration");
        log("&aDate&f: " + getConfig().getString("TIMEZONE"));
        log(" &a▶ &f" + getConfig().getString("DATE"));
        log("");
        log("&2Plugin Info");
        log("&aAuthor&f: " + getDescription().getAuthors());
        log("&aDiscord&f: " + getDescription().getWebsite());
        log("&7&m------------------------");
        new FreeRankCommand();
        new FreeRankAdminCommand();
    }

    public void registerConfig() {
        File config = new File(getDataFolder(), "config.yml");
        ConfigRoute = config.getPath();
        if (!config.exists()) {
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
        }
    }

    public void registerDataConfig() {
        dataFile = new File(getDataFolder(), "data.yml");
        if (!dataFile.exists()) {
            saveResource("data.yml", false);
        }
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
    }

    @Override
    public void onDisable() {
        saveDataConfig();
        shutdownMessage();
    }

    private void shutdownMessage() {
            log("&7&m------------------------");
            log("&cFreeRank Plugin &8- &fv" + getDescription().getVersion());
            log(" ");
            log("&cDisconnecting plugin...");
            log("&cJoin our discord for any errors.");
            log(" ");
            log("&c▶ &fhttps://dsc.gg/liteclubdevelopment");
            log("&7&m------------------------");
        }

    public FileConfiguration getDataConfig() {
        return dataConfig;
    }

    public void saveDataConfig() {
        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
