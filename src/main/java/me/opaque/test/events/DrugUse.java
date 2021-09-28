package me.opaque.test.events;

import me.opaque.test.Strain;
import me.opaque.test.Test;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.junit.rules.TestRule;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.color.ParticleColor;

import java.awt.*;
import java.util.Map;

public class DrugUse implements Listener {

    private Test plugin;

    public DrugUse(Test plugin) {
        this.plugin = plugin;
    }

    public void smokeEffect(Player player) {
        Location start = player.getEyeLocation();
        Vector increase = start.getDirection();
        for (int i = 0; i < 3; i++) {
            Location point = start.add(increase);
            ParticleEffect.CLOUD.display(point);
            ParticleEffect.CLOUD.display(point);
            ParticleEffect.CLOUD.display(point);
        }
    }

    @EventHandler
    public void onDrugUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        int amount = player.getItemInHand().getAmount();

        if (Bukkit.getWorld(player.getWorld().getName()) == null) {
            return;
        }

        if (player.getItemInHand() == null) {
            return;
        }
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
            for (Map.Entry<String, Strain> strainTypes : this.plugin.strains.entrySet()) {
                Strain strain = strainTypes.getValue();
                if (player.getItemInHand().isSimilar(strain.getItemStack2())) {
                    //player.sendMessage("yes");
                    player.getItemInHand().setAmount(amount - 1);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 1));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 300, 1));
                    smokeEffect(player);
                    //Bukkit.getWorld(player.getWorld().getName()).spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, x, y, z, 1);
                }
            }
        }
    }
}

