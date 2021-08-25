package io.github.spaery.simplerhomes;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Hello world!
 *
 */
public class SimplerHomes extends JavaPlugin
{
    private File homes;
    private FileConfiguration homesConfig;
    private static SimplerHomes instance;

    @Override
    public void onEnable(){
        instance = this;
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

    public static SimplerHomes getInstance(){
        return instance;
    }

    public FileConfiguration getHomesConfig(){
        return homesConfig;
    }

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
