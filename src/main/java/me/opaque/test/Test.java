package me.opaque.test;

import me.opaque.test.Utils.Util;
import me.opaque.test.commands.Drug;
import me.opaque.test.events.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Test extends JavaPlugin {

    public HashMap<String, Strain> strains = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getServer().getLogger().info(Util.color("&bTest Enabled 1.0"));
        Bukkit.getPluginCommand("drug").setExecutor(new Drug(this));
        registerEvents();
        generateConfig();
        generateMessagesConfig();
        loadConfig();
        new BukkitRunnable() {
            public void run() {
                saveToDisk();
            }

        }.runTaskTimer(this, 12000, 12000);
    }

    @Override
    public void onDisable() {
        saveToDisk();
        // Plugin shutdown logic
    }

    public void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getPluginManager().registerEvents(new Interact(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlace(this), this);
        Bukkit.getPluginManager().registerEvents(new DrugUse(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreak(this), this);
        Bukkit.getPluginManager().registerEvents(new CrossBreed(this), this);
    }

    public void generateConfig() {
        File directory = new File(String.valueOf(this.getDataFolder()));
        if (!directory.exists() && !directory.isDirectory()) {
            directory.mkdir();
        }

        File config = new File(this.getDataFolder() + File.separator + "config.yml");
        if (!config.exists()) {
            try {
                config.createNewFile();
                try (InputStream in = Test.class.getClassLoader().getResourceAsStream("config.yml")) {
                    OutputStream out = new FileOutputStream(config);
                    byte[] buffer = new byte[1024];
                    int length = in.read(buffer);
                    while (length != -1) {
                        out.write(buffer, 0, length);
                        length = in.read(buffer);
                    }
                } catch (IOException e) {
                    super.getLogger().severe("Failed to create config.");
                    e.printStackTrace();
                    super.getLogger().severe("...please delete the Drugs directory and try RESTARTING (not reloading).");
                }
            } catch (IOException e) {
                super.getLogger().severe("Failed to create config.");
                e.printStackTrace();
                super.getLogger().severe(Util.color("&c...please delete the Drugs directory and try RESTARTING (not reloading)."));
            }
        }
    }

    public void generateMessagesConfig() {
        File directory = new File(String.valueOf(this.getDataFolder()));
        if (!directory.exists() && !directory.isDirectory()) {
            directory.mkdir();
        }

        File config = new File(this.getDataFolder() + File.separator + "messages.yml");
        if (!config.exists()) {
            try {
                config.createNewFile();
                try (InputStream in = Test.class.getClassLoader().getResourceAsStream("messages.yml")) {
                    OutputStream out = new FileOutputStream(config);
                    byte[] buffer = new byte[1024];
                    int length = in.read(buffer);
                    while (length != -1) {
                        out.write(buffer, 0, length);
                        length = in.read(buffer);
                    }
                } catch (IOException e) {
                    super.getLogger().severe("Failed to create messages.");
                    e.printStackTrace();
                    super.getLogger().severe("...please delete the Drugs directory and try RESTARTING (not reloading).");
                }
            } catch (IOException e) {
                super.getLogger().severe("Failed to create messages.");
                e.printStackTrace();
                super.getLogger().severe(Util.color("&c...please delete the Drugs directory and try RESTARTING (not reloading)."));
            }
        }
    }

    public void loadConfig() {
        File file = new File(getDataFolder() + File.separator + "players" + ".yml");
        YamlConfiguration data = null;
        if (file.exists()) {
            data = YamlConfiguration.loadConfiguration(file);
        }
        strains.clear();
        for (String s : data.getConfigurationSection("players").getKeys(false)) {

            ItemStack is1 = Util.getItemStack("strains." + s, this);
            ItemStack is2 = Util.getItemStack("strains." + s + ".item-output", this);

            Strain strain = new Strain(s, is1, is2);

            this.strains.put(s, strain);

            if (data != null && data.contains("locations." + s)) {
                Bukkit.getLogger().info(Util.color("&4&lDEBUG DEBUG DEBUG DEBUG DEBUG DEBUG DEBUG DEBUG "));
                List<String> str = data.getStringList("locations." + s);
                strain.setLocList(str);
            }
        }
    }

    public void saveToDisk() {
        Bukkit.getLogger().info(Util.color("&5[DRUGS] &e&oSaving data..."));
        File file = new File(getDataFolder() + File.separator + "locations" + ".yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        YamlConfiguration data = YamlConfiguration.loadConfiguration(file);
        //data.set("quest-progress", null);
        for (Map.Entry<String, Strain> strainTypes : strains.entrySet()) {
            data.set("locations." + strainTypes.getKey(), strainTypes.getValue().locList);
        }
        try {
            data.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
