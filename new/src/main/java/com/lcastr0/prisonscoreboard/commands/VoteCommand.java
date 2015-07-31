package com.lcastr0.prisonscoreboard.commands;

import com.lcastr0.prisonscoreboard.PrisonScoreboard;
import com.lcastr0.prisonscoreboard.events.VoteChangeEvent;
import com.lcastr0.prisonscoreboard.objects.Vote;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VoteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        boolean hasPermission = false;

        Vote receiver = PrisonScoreboard.getInstance().getReceiver();

        if(sender instanceof Player){
            if(sender.isOp() || sender.hasPermission("prisonscoreboard.command.vote"))
                hasPermission = true;
        } else {
            hasPermission = true;
        }

        if(hasPermission){

            if(args.length == 0){
                sendUsage(sender);
            } else if(args.length == 1){
                if(args[0].equalsIgnoreCase("clear") || args[0].equalsIgnoreCase("reset")){
                    receiver.setVotes(0);
                    Bukkit.getPluginManager().callEvent(new VoteChangeEvent());
                    sender.sendMessage(ChatColor.GREEN + "Votes were set to 0!");
                } else if(args[0].equalsIgnoreCase("increase")){
                    receiver.increaseVotes();
                    Bukkit.getPluginManager().callEvent(new VoteChangeEvent());
                    sender.sendMessage(ChatColor.GREEN + "Votes were increased by 1!");
                } else if(args[0].equalsIgnoreCase("decrease")){
                    receiver.removeVotes(1);
                    Bukkit.getPluginManager().callEvent(new VoteChangeEvent());
                    sender.sendMessage(ChatColor.GREEN + "Votes were decreased by 1!");
                } else {
                    sendUsage(sender);
                }
            } else if(args.length == 2){
                if(args[0].equalsIgnoreCase("add")){
                    if(StringUtils.isNumeric(args[1])){
                        int toAdd = Integer.valueOf(args[1]);
                        receiver.addVotes(toAdd);
                        Bukkit.getPluginManager().callEvent(new VoteChangeEvent());
                        sender.sendMessage(ChatColor.GREEN + "Votes were increased by " + args[1] + "!");
                    } else {
                        sender.sendMessage(ChatColor.RED + "Second argument needs to be a number!");
                    }
                } else if(args[0].equalsIgnoreCase("remove")){
                    if(StringUtils.isNumeric(args[1])){
                        int toRemove = Integer.valueOf(args[1]);
                        receiver.removeVotes(toRemove);
                        Bukkit.getPluginManager().callEvent(new VoteChangeEvent());
                        sender.sendMessage(ChatColor.GREEN + "Votes were decreased by " + args[1] + "!");
                    } else {
                        sender.sendMessage(ChatColor.RED + "Second argument needs to be a number!");
                    }
                } else if(args[0].equalsIgnoreCase("set")){
                    if(StringUtils.isNumeric(args[1])){
                        int toSet = Integer.valueOf(args[1]);
                        receiver.setVotes(toSet);
                        Bukkit.getPluginManager().callEvent(new VoteChangeEvent());
                        sender.sendMessage(ChatColor.GREEN + "Votes were set to " + args[1] + "!");
                    } else {
                        sender.sendMessage(ChatColor.RED + "Second argument needs to be a number!");
                    }
                } else {
                    sendUsage(sender);
                }
            } else {
                sendUsage(sender);
            }

        } else {
            sender.sendMessage(ChatColor.RED + "You don't have permission for that!");
        }

        return true;

    }

    private void sendUsage(CommandSender sender){
        sender.sendMessage(ChatColor.RED + "Usage: ");
        sender.sendMessage(ChatColor.RED + "/psvote increase - Increases votes by 1");
        sender.sendMessage(ChatColor.RED + "/psvote decrease - Decreases votes by 1");
        sender.sendMessage(ChatColor.RED + "/psvote add <votes> - Increases votes by <votes>");
        sender.sendMessage(ChatColor.RED + "/psvote remove <votes> - Decreases votes by <votes>");
        sender.sendMessage(ChatColor.RED + "/psvote set <votes> - Set votes to <votes>");
    }

}
