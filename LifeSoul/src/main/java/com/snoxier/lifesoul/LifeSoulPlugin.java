package com.snoxier.lifesoul;

import com.snoxier.lifesoul.commands.LSMaxCommand;
import com.snoxier.lifesoul.commands.WithdrawCommand;
import com.snoxier.lifesoul.items.Items;
import com.snoxier.lifesoul.listeners.DeathListener;
import com.snoxier.lifesoul.listeners.InteractListener;
import com.snoxier.lifesoul.listeners.MobSoulListener;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class LifeSoulPlugin extends JavaPlugin {
    private static LifeSoulPlugin instance;
    private HeartManager heartManager;

    public static NamespacedKey KEY_SOUL, KEY_SHARD, KEY_HEART, KEY_DRAGON_SOUL;

    public static LifeSoulPlugin get() { return instance; }

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        KEY_SOUL = new NamespacedKey(this, "soul");
        KEY_SHARD = new NamespacedKey(this, "heart_shard");
        KEY_HEART = new NamespacedKey(this, "heart");
        KEY_DRAGON_SOUL = new NamespacedKey(this, "dragon_soul");

        heartManager = new HeartManager(this);
        Items.registerRecipes();

        Bukkit.getPluginManager().registerEvents(new MobSoulListener(), this);
        Bukkit.getPluginManager().registerEvents(new DeathListener(heartManager), this);
        Bukkit.getPluginManager().registerEvents(new InteractListener(heartManager), this);

        getCommand("lsmax").setExecutor(new LSMaxCommand(heartManager));
        getCommand("withdraw").setExecutor(new WithdrawCommand(heartManager));

        Bukkit.getOnlinePlayers().forEach(p -> heartManager.applyStoredHearts(p));
    }

    @Override public void onDisable() { saveConfig(); }

    public FileConfiguration cfg(){ return getConfig(); }
}