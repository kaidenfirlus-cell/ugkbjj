package com.snoxier.lifesoul.commands;

import com.snoxier.lifesoul.HeartManager;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class LSMaxCommand implements CommandExecutor {

    private final HeartManager hearts;

    public LSMaxCommand(HeartManager hearts) {
        this.hearts = hearts;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("lifesoul.admin")) {
            sender.sendMessage("§cYou don't have permission to use this command.");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage("§eUsage: /" + label + " <maxHearts> [player]");
            return true;
        }

        int max;
        try {
            max = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            sender.sendMessage("§cInvalid number: " + args[0]);
            return true;
        }

        if (args.length >= 2) {
            Player target = Bukkit.getPlayerExact(args[1]);
            if (target == null) {
                sender.sendMessage("§cPlayer not found: " + args[1]);
                return true;
            }

            hearts.setPlayerMaxHearts(target, max);
            sender.sendMessage("§aSet max hearts for " + target.getName() + " to " + max + ".");
            target.sendMessage("§eYour heart cap has been set to " + max + " by an admin.");
        } else {
            hearts.setGlobalMaxHearts(max);
            sender.sendMessage("§aSet global max hearts to " + max + ".");
        }
        return true;
    }
}
