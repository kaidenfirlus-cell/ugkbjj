package com.snoxier.lifesoul.listeners;

import com.snoxier.lifesoul.items.Items;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class MobSoulListener implements Listener {

    private boolean isBoss(Entity e){
        EntityType t = e.getType();
        return t == EntityType.WITHER || t == EntityType.WARDEN; // Ender Dragon handled separately
    }
    private boolean isHostile(Entity e){ return e instanceof Monster; }
    private boolean isPassive(Entity e){
        return e instanceof Animals || e instanceof WaterMob || e instanceof Ambient || e instanceof Golem;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e){
        LivingEntity dead = e.getEntity();
        Player killer = dead.getKiller();
        if (killer == null) return;

        // Ender Dragon: 2% chance to drop Dragon Soul (and skip boss logic)
        if (dead.getType() == EntityType.ENDER_DRAGON){
            if (Math.random() < 0.02){
                dead.getWorld().dropItemNaturally(dead.getLocation(), Items.dragonSoul());
            }
            return;
        }

        double chance;
        if (isBoss(dead)) chance = 0.15;          // bosses (wither/warden)
        else if (isHostile(dead)) chance = 0.02;  // hostile mobs
        else if (isPassive(dead)) chance = 0.005; // passive mobs
        else return;

        if (Math.random() < chance){
            dead.getWorld().dropItemNaturally(dead.getLocation(), Items.soul());
        }
    }
}