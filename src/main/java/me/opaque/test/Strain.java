package me.opaque.test;

import me.opaque.test.Utils.Util;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Strain {

    public String name;
    public ItemStack itemStack;
    public ItemStack itemStack2;
    public List<String> locList = new ArrayList<>();

    public Strain(String name, ItemStack itemStack, ItemStack itemStack2) {
        this.name = name;
        this.itemStack = itemStack;
        this.itemStack2 = itemStack2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack2() {
        return itemStack2;
    }

    public void setItemStack2(ItemStack itemStack2) {
        this.itemStack2 = itemStack2;
    }

    public void addLocation(Location location) {
        if (isValidLocation(location)) {
            return;
        }
        locList.add(convertLocation(location));
    }

    public boolean isValidLocation(Location location) {
        return locList.contains(convertLocation(location));
    }

    private String convertLocation(Location location) {
        String world = location.getWorld().getName();
        String x = String.valueOf(location.getBlockX());
        String y = String.valueOf(location.getBlockY());
        String z = String.valueOf(location.getBlockZ());

        return world + ", " + x + ", " + y + ", " + z;
    }

    public void removeLocation(Location location) {
        if (isValidLocation(location)) {
            locList.remove(convertLocation(location));
        }
    }

    public void setLocList(List<String> locList) {
        this.locList = locList;
    }

    public boolean canCrossBreed(Strain strain, Location location) {
        Block north = location.getBlock().getRelative(BlockFace.NORTH);
        Block east = location.getBlock().getRelative(BlockFace.EAST);
        Block south = location.getBlock().getRelative(BlockFace.SOUTH);
        Block west = location.getBlock().getRelative(BlockFace.WEST);

        Location northBlock = north.getLocation();
        Location eastBlock = east.getLocation();
        Location southBlock = south.getLocation();
        Location westBlock = west.getLocation();

        ArrayList<Location> locs = new ArrayList<>();

        locs.add(northBlock);
        locs.add(eastBlock);
        locs.add(southBlock);
        locs.add(westBlock);

        Util.debug("added blocks around right clicked block to locs list");
        for (Location loc : locs) {
            if (Util.isFullyGrown(loc.getBlock())) {
                if (locList.contains(convertLocation(loc))) {
                    Util.debug(strain.getName() + " is in the loc list");
                    return true;
                }
            }
        }
        return false;
    }
}
