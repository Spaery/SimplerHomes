package io.github.spaery.simplerhomes;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class file for this plugin
 * 
 */
public class SimplerHomes extends JavaPlugin
{
    private File homes;
    private FileConfiguration homesConfig;
    private static SimplerHomes plugin;

    @Override
    public void onEnable(){
        plugin = this;
        getLogger().info("For all your home setting needs!");
        FileConfiguration config = this.getConfig();
        config.addDefault("NumberOfHomes", 5);
        config.addDefault("TimeToWait", 3);
        config.options().copyDefaults(true);
        saveConfig();
        this.getCommand("sethome").setExecutor(new Sethome());
        this.getCommand("home").setExecutor(new Home());
        this.getCommand("homes").setExecutor(new Homes());
        this.getCommand("delhome").setExecutor(new Delhome());
        createhomes();
    }

    @Override
    public void onDisable(){
    }
    /**
     * Getter for the plugin instance
     * @return returns the SimplerHomes object for accessing configs
     */
    public static SimplerHomes getInstance(){
        return plugin;
    }
    /**
     * Getter for homes.yml
     * @return returns the file object for homes.yml
     */
    public FileConfiguration getHomesConfig(){
        return homesConfig;
    }
    /**
     * Creates homes.yml
     */
    private void createhomes(){
        homes = new File(getDataFolder(),"homes.yml");
        if(!homes.exists()){
            homes.getParentFile().mkdirs();
            saveResource("homes.yml", false);
        }
        homesConfig = new YamlConfiguration();
        try {
            homesConfig.load(homes);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
