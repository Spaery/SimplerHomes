package io.github.spaery.simplerhomes;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Sethome implements CommandExecutor {
    //Executes when player type /sethome
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            if(args.length > 1) return false;
            Player player = (Player) sender;
            UUID id = player.getUniqueId();
            HomeFile h = new HomeFile();
            try {
                h.setHome(player, args[0]);
            } catch (ArrayIndexOutOfBoundsException e) {
                player.sendMessage("Please specify name of home as such: '/sethome (NameOfHome)'");
            } catch (IllegalStateException e){
                player.sendMessage("You have reached the maximum number of homes.");
            } catch (ArithmeticException e){
                player.sendMessage("Home already exists");
            }
        } else {
            sender.sendMessage("Command must be executed by a player.");
        }
        return true;
    }
}
