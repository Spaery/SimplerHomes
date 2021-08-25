package io.github.spaery.simplerhomes;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
/**
 * This class is for accessing and modifying the file holding all player homes
 */

public class HomeFile {
    SimplerHomes instance = SimplerHomes.getInstance();
    File homesFile = new File("plugins/SimplerHomes", "homes.yml");
    FileConfiguration homes = YamlConfiguration.loadConfiguration(homesFile);

    /**
     * Writes the coords for pl into YAML file
     * @param pl the player sending the command
     * @param homeName the name of the specified home to create
     */
    public void setHome(Player pl, String homeName){
        Location loc = pl.getLocation();
        String configPath = pl.getDisplayName() + "." + homeName;
        int i = 0;
        // Checks to ensure home limit is not reached.
        // home limit is set the the config.yml
        if (homes.getConfigurationSection(pl.getDisplayName()) != null){
            for (String key : homes.getConfigurationSection(pl.getDisplayName()).getKeys(false)){
                i++;
                if(key.equals(homeName)){
                    throw new ArithmeticException();
                }
                if(i >= instance.getConfig().getInt("NumberOfHomes")){
                    throw new IllegalStateException();
                } else {
                    continue;
                }
            }
        }
        homes.set(configPath + ".World", pl.getWorld().getName());
        homes.set(configPath + ".X",loc.getX());
        homes.set(configPath + ".Y",loc.getY());
        homes.set(configPath + ".Z",loc.getZ());
        homes.set(configPath + ".Yaw",loc.getYaw());
        homes.set(configPath + ".Pitch",loc.getPitch());
        pl.sendMessage("Home '" + homeName + "' was sucessfully created.");
        pl.playSound(pl.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, (float) 0.5, (float) 1.5);
        saveFile(homesFile);
    }
    /**
     * Shows availabe homes to the player
     * @param pl the player sending the command
     */
    public void homes(Player pl){
        String homesList = new String();
        if (homes.getConfigurationSection(pl.getDisplayName()) != null){
            for (String key : homes.getConfigurationSection(pl.getDisplayName()).getKeys(false)){
                homesList = homesList + key + " ";
            }
            pl.sendMessage(homesList);
        } else{
            pl.sendMessage("You have no homes.");
        }
    }
    /**
     * Sends the player to the specified home
     * @param pl the player sending the command
     * @param homeName the name of the specified home to go to
     */
    public void home(Player pl, String homeName){
        String configPath = pl.getDisplayName() + "." + homeName;
        try {
            Location loc = new Location(
                Bukkit.getWorld(homes.getString(configPath + ".World")),
                homes.getDouble(configPath + ".X"), 
                homes.getDouble(configPath + ".Y"), 
                homes.getDouble(configPath + ".Z"),
                (float) homes.getDouble(configPath + ".Yaw"),
                (float) homes.getDouble(configPath + ".Pitch"));
            Bukkit.getScheduler().runTaskTimer(instance, new Consumer<BukkitTask>(){
                int i = instance.getConfig().getInt("TimeToWait");
                @Override
                public void accept(BukkitTask task){
                    if(i <= 0){
                        pl.teleport(loc);
                        task.cancel();
                    } else {
                        pl.sendMessage("You will be teleported in " + i);
                        pl.playSound(pl.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, (float) 0.5, 1);
                        i--;
                    }
                }
            }, 0, 20L);
            
        } catch (IllegalArgumentException e) {
            pl.sendMessage("Home '" + homeName + "' is invalid or cannot be found");
        }
    }
    /**
     * Deletes the specified home from the players list of homes
     * @param pl the player sending the command
     * @param homeName the name of the specified home to delete
     */
    public void delHome(Player pl, String homeName){
        String configPath = pl.getDisplayName() + "." + homeName;
        try {
            if(homes.contains(configPath)){
                homes.set(configPath, null);
                saveFile(homesFile);
                pl.playSound(pl.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, (float) 0.5, (float) 0.5);
                pl.sendMessage("Home '" + homeName + "' was successfully deleted!");
            } else {
                pl.sendMessage("Home '" + homeName + "' couldn't be found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveFile(File f){
        try {
            homes.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
