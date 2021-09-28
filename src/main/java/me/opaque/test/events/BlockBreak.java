package me.opaque.test.events;

import me.opaque.test.Strain;
import me.opaque.test.Test;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Map;

public class BlockBreak implements Listener {

    private Test plugin;

    public BlockBreak(Test plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (event.isCancelled()) {
            return;
        }

        for (Map.Entry<String, Strain> strainTypes : plugin.strains.entrySet()) {
            Strain strain = strainTypes.getValue();
            String ID = strainTypes.getKey();
            if (strain.isValidLocation(block.getLocation())) {
                strain.removeLocation(block.getLocation());
            }
        }
    }
}
