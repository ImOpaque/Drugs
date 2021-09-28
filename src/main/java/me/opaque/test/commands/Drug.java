package me.opaque.test.commands;

import me.opaque.test.Strain;
import me.opaque.test.Test;
import me.opaque.test.Utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Map;

public class Drug implements CommandExecutor {

    private Test plugin;

    public Drug(Test plugin) {
        this.plugin = plugin;
    }

    FileConfiguration config = new YamlConfiguration();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("drug")) {
            if (!(sender.hasPermission("drugs.admin"))) {
                sender.sendMessage(Util.message("no-permission", plugin));
                return true;
            }
            if (args.length != 4) {
                sender.sendMessage(Util.message("incorrect-usage", plugin));
                return true;
            }
            if (args[0].equalsIgnoreCase("list")) {
                for (Map.Entry<String, Strain> strainTypes : this.plugin.strains.entrySet()) {
                    String strains = strainTypes.getKey();
                    sender.sendMessage(Util.color(strains));
                    return true;
                }
            } else if (args[0].equalsIgnoreCase("give")) {
                if (Bukkit.getPlayer(args[1]) == null) {
                    sender.sendMessage(Util.message("offline-player", plugin));
                    return true;
                }
                if (!(this.plugin.strains.containsKey(args[2]))) {
                    sender.sendMessage("invalid-strain");
                    return true;
                }
                try {
                    Integer.parseInt(args[3]);
                } catch (Exception e) {
                    sender.sendMessage("not an int");
                    return true;
                }
                Strain strain = this.plugin.strains.get(args[2]);
                Bukkit.getPlayer(args[1]).getInventory().addItem(strain.getItemStack());
                Bukkit.getPlayer(args[1]).sendMessage("you got some weed");
                sender.sendMessage("uSer " + Bukkit.getPlayer(args[1]).getName() + " has been given weed");
            }
        }
        return true;
    }
}
