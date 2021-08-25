package io.github.spaery.simplerhomes;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Sethome implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            HomeFile h = new HomeFile();
            if(args.length > 1) return false;
            try {
                h.setHome(player, args[0]);
            } catch (ArrayIndexOutOfBoundsException e) {
                player.sendMessage("Please specify name of home as such: '/sethome (NameOfHome)'");
            } catch (IllegalStateException e){
                player.sendMessage("You have reached the maximum number of homes.");
            }
        } else return false;
        return true;
    }
    
}
