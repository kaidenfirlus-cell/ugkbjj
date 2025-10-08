package com.snoxier.lifesoul.commands;

import com.snoxier.lifesoul.HeartManager;
import com.snoxier.lifesoul.items.Items;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class WithdrawCommand implements CommandExecutor {

    private final HeartManager hearts;

    public WithdrawCommand(HeartManager hearts) {
        this.hearts = hearts;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        if (args.length != 1) {
            p.sendMessage("§eUsage: /" + label + " <amount>");
            return true;
        }

        int amount;
        try {
            amount = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            p.sendMessage("§cInvalid number: " + args[0]);
            return true;
        }

        if (amount <= 0) {
            p.sendMessage("§cAmount must be greater than zero.");
            return true;
        }

        int current = hearts.getCurrentHearts(p);
        int removable = Math.max(0, current - 1);

        if (amount > removable) {
            p.sendMessage("§cYou can only withdraw up to " + removable + " heart(s).");
            return true;
        }

        ItemStack heart = Items.heart();
        for (int i = 0; i < amount; i++) {
            p.getInventory().addItem(heart.clone());
        }

        hearts.addHearts(p, -amount);
        p.sendMessage("§aYou withdrew " + amount + " heart" + (amount == 1 ? "" : "s") +
                ". You now have §c" + hearts.getCurrentHearts(p) + "§a left.");
        return true;
    }
}
