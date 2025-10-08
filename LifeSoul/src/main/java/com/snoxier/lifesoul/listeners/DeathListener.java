package com.snoxier.lifesoul.listeners;

import com.snoxier.lifesoul.HeartManager;
import com.snoxier.lifesoul.LifeSoulPlugin;
import com.snoxier.lifesoul.items.Items;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {
    private final HeartManager hearts;
    public DeathListener(HeartManager hearts){ this.hearts = hearts; }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        Player p = e.getEntity();
        int current = hearts.getCurrentHearts(p);
        if (current <= 1){
            Bukkit.getBanList(BanList.Type.NAME)
                    .addBan(p.getName(), "Eliminated: ran out of hearts.", null, "LifeSoul");
            Bukkit.getScheduler().runTaskLater(LifeSoulPlugin.get(), () -> 
                p.kickPlayer("You are permanently banned: 0 hearts remaining."), 1L);
            return;
        }
        hearts.addHearts(p, -1);
        p.getWorld().dropItemNaturally(p.getLocation(), Items.heart());
    }
}
