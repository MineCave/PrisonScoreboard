package com.lcastr0.PrisonScoreboard;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VoteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(label.equalsIgnoreCase("psvote")){
            if(!(sender instanceof Player) || ((Player)sender).hasPermission("psvote.command")) {
                if (args.length == 0) {
                    sender.sendMessage(ChatColor.GREEN + "Total registered votes: " + PrisonScoreboard.getInstance().receiver.getVotes());
                } else if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("add")) {
                        sender.sendMessage(ChatColor.RED + "Usage: /psvote add <votes>");
                    } else if (args[0].equalsIgnoreCase("remove")) {
                        sender.sendMessage(ChatColor.RED + "Usage: /psvote remove <votes>");
                    } else if (args[0].equalsIgnoreCase("set")) {
                        sender.sendMessage(ChatColor.RED + "Usage: /psvote set <votes>");
                    } else {
                        sender.sendMessage(ChatColor.RED + "Usage: /psvote [add|remove|set] [<votes>]");
                    }
                } else if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("add")) {
                        PrisonScoreboard.getInstance().receiver.addVotes(Integer.valueOf(args[1]));
                        int votes = PrisonScoreboard.getInstance().receiver.getVotes();
                        sender.sendMessage(ChatColor.GREEN + "Added " + args[1] + " votes! Total votes now: " + votes);
                    } else if (args[0].equalsIgnoreCase("remove")) {
                        PrisonScoreboard.getInstance().receiver.removeVotes(Integer.valueOf(args[1]));
                        int votes = PrisonScoreboard.getInstance().receiver.getVotes();
                        sender.sendMessage(ChatColor.GREEN + "Removed " + args[1] + " votes! Total votes now: " + votes);
                    } else if (args[0].equalsIgnoreCase("set")) {
                        PrisonScoreboard.getInstance().receiver.setVotes(Integer.valueOf(args[1]));
                        int votes = PrisonScoreboard.getInstance().receiver.getVotes();
                        sender.sendMessage(ChatColor.GREEN + "Votes set to: " + votes);
                    } else {
                        sender.sendMessage(ChatColor.RED + "Usage: /psvote [add|remove|set] [<votes>]");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Usage: /psvote [add|remove|set] [<votes>]");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "You don't have permissions to run this command!");
            }
        }
        return false;
    }
}
