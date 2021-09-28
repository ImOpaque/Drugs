package me.opaque.test.events;

import me.opaque.test.Strain;
import me.opaque.test.Test;
import me.opaque.test.Utils.Util;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.Map;

public class CrossBreed implements Listener {

    private Test plugin;

    public CrossBreed(Test plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        Block block = event.getClickedBlock();
        if (event.getHand() == EquipmentSlot.HAND) {
            if (action == (Action.RIGHT_CLICK_BLOCK)) {
                if (!(block == null)) {
                    if (block.getType() == (Material.WHEAT)) {
                        if (Util.isFullyGrown(block)) {
                            if (plugin.getConfig().getBoolean("debug")) {
                                Util.debug("successfuly ran the crossbreed check");
                            }
                            for (Map.Entry<String, Strain> strainTypes : this.plugin.strains.entrySet()) {
                                Strain strain = strainTypes.getValue();
                                if (strain.canCrossBreed(strain, block.getLocation())) {
                                    if (plugin.getConfig().getBoolean("debug")) {
                                        Util.debug("successfuly ran the crossbreed check");
                                    }
                                }
                            }
                        } else Util.debug("not fully grown");
                    }
                }
            }
        }
    }
}
