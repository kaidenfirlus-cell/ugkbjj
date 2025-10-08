package com.snoxier.lifesoul.items;

import com.snoxier.lifesoul.LifeSoulPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import java.util.List;

public class Items {

    public static ItemStack soul() {
        ItemStack it = new ItemStack(Material.PRISMARINE_CRYSTALS);
        ItemMeta m = it.getItemMeta();
        m.setDisplayName("§bSpirit");
        m.setLore(List.of("§7A faint essence from a fallen being."));
        m.getPersistentDataContainer().set(LifeSoulPlugin.KEY_SOUL, PersistentDataType.BYTE, (byte) 1);
        m.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        it.setItemMeta(m);
        return it;
    }

    public static ItemStack shard() {
        ItemStack it = new ItemStack(Material.NETHER_WART);
        ItemMeta m = it.getItemMeta();
        m.setDisplayName("§cHeart Shard");
        m.setLore(List.of("§7Four Spirits and Redstone Blocks combined."));
        m.getPersistentDataContainer().set(LifeSoulPlugin.KEY_SHARD, PersistentDataType.BYTE, (byte) 1);
        it.setItemMeta(m);
        return it;
    }

    public static ItemStack heart() {
        ItemStack it = new ItemStack(Material.NETHER_STAR);
        ItemMeta m = it.getItemMeta();
        m.setDisplayName("§cHeart");
        m.setLore(List.of("§7Right-click to gain +1 heart (up to your cap)."));
        m.getPersistentDataContainer().set(LifeSoulPlugin.KEY_HEART, PersistentDataType.BYTE, (byte) 1);
        it.setItemMeta(m);
        return it;
    }

    public static ItemStack dragonSoul() {
        ItemStack it = new ItemStack(Material.ECHO_SHARD);
        ItemMeta m = it.getItemMeta();
        m.setDisplayName("§bDragon Soul");
        m.setLore(List.of("§7A rare essence drawn from the Ender Dragon."));
        m.getPersistentDataContainer().set(LifeSoulPlugin.KEY_DRAGON_SOUL, PersistentDataType.BYTE, (byte) 1);
        m.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        it.setItemMeta(m);
        return it;
    }

    public static void registerRecipes() {
        // Heart Shard recipe
        NamespacedKey shardKey = new NamespacedKey(LifeSoulPlugin.get(), "heart_shard");
        ShapedRecipe shardR = new ShapedRecipe(shardKey, shard());
        shardR.shape("SRS", "RRR", "SRS");
        shardR.setIngredient('S', new org.bukkit.inventory.RecipeChoice.ExactChoice(soul()));
        shardR.setIngredient('R', Material.REDSTONE_BLOCK);
        Bukkit.addRecipe(shardR);

        // Heart recipe
        NamespacedKey heartKey = new NamespacedKey(LifeSoulPlugin.get(), "heart");
        ShapedRecipe heartR = new ShapedRecipe(heartKey, heart());
        heartR.shape("SSS", "SBS", "SSS");
        heartR.setIngredient('S', new org.bukkit.inventory.RecipeChoice.ExactChoice(shard()));
        heartR.setIngredient('B', Material.BEACON);
        Bukkit.addRecipe(heartR);
    }
}