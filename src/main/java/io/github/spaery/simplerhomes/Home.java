package io.github.spaery.simplerhomes;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Home implements CommandExecutor {
    //Executes when player type /home
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            if(args.length > 1) return false;
            Player player = (Player) sender;
            HomeFile h = new HomeFile();
            try {
                h.home(player, args[0]);
            } catch (ArrayIndexOutOfBoundsException e) {
                player.sendMessage("Please specify home as such: '/home (NameOfHome)'");
            }            
        } else {
            sender.sendMessage("Command must be executed by a player.");
        }
        return true;
    }
}
