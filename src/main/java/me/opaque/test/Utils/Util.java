package me.opaque.test.Utils;

import me.opaque.test.Test;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Util {

    public static String color(String msg) {
        return msg == null ? null : ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static boolean isFullyGrown(Block block) {
        BlockData blockData = block.getBlockData();
        if (blockData instanceof Ageable) {
            Ageable age = (Ageable) block.getBlockData();
            Util.debug(String.valueOf(age.getAge()));
            Util.debug(String.valueOf(age.getMaximumAge()));
            return age.getAge() == age.getMaximumAge();
        }
        return false;
    }

    public static ItemStack getItemStack(String location, Test plugin) {

        ItemStack is = new ItemStack(Material.valueOf(plugin.getConfig().getString(location + ".material")));
        String name = Util.color(plugin.getConfig().getString(location + ".name"));
        List<String> lore = plugin.getConfig().getStringList(location + ".lore");
        List<String> newLore = new ArrayList<>();
        for (String str : lore) {
            newLore.add(Util.color(str));
        }
        ItemMeta itemMeta = is.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(newLore);
        is.setItemMeta(itemMeta);
        return is;
    }

    public static String message(String s, Test plugin) {
        File file = new File(plugin.getDataFolder() + File.separator + "messages" + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        if (config.getString(s) == null) {
            return "PLEASE CONFIG MESSAGES";
        }
        return color(config.getString(s));
    }

    public static void debug(String message) {
        Bukkit.getLogger().info(color(message));
    }

}
