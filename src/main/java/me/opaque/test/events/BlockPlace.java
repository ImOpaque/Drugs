package me.opaque.test.events;

import me.opaque.test.Strain;
import me.opaque.test.Test;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Map;

public class BlockPlace implements Listener {

    private Test plugin;

    public BlockPlace(Test plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (event.isCancelled()) {
            return;
        }

        for (Map.Entry<String, Strain> strainTypes : this.plugin.strains.entrySet()) {
            Strain strain = strainTypes.getValue();
            if (player.getItemInHand().isSimilar(strain.getItemStack())) {
                //player.sendMessage("yes");
                strain.addLocation(block.getLocation());
            }
        }
    }
}
