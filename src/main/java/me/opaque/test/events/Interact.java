package me.opaque.test.events;

import me.opaque.test.Strain;
import me.opaque.test.Test;
import me.opaque.test.Utils.Util;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class Interact implements Listener {

    private Test plugin;

    public Interact(Test plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        Block block = event.getClickedBlock();

        if (event.getHand() == EquipmentSlot.HAND) {
            if (action == (Action.RIGHT_CLICK_BLOCK)) {
                if (!(block == null)) {
                    if (block.getType().equals(Material.WHEAT)) {
                        if (Util.isFullyGrown(block)) {
                            for (Map.Entry<String, Strain> strainTypes : this.plugin.strains.entrySet()) {
                                Strain strain = strainTypes.getValue();
                                if (strain.isValidLocation(block.getLocation())) {
                                    block.setType(Material.WHEAT);
                                    player.getInventory().addItem(strain.getItemStack2());
                                    break;
                                }
                            }
                       }
                    }
                }
            }
        }
    }
}
