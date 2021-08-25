package io.github.spaery.simplerhomes;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Home implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            HomeFile h = new HomeFile();
            try {
                h.home(player, args[0]);
            } catch (ArrayIndexOutOfBoundsException e) {
                player.sendMessage("Please specify home as such: '/home (NameOfHome)'");
            }            
        }
        return true;
    }
    
}
