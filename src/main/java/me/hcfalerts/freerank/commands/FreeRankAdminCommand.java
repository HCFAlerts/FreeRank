package me.hcfalerts.freerank.commands;

import me.hcfalerts.freerank.FreeRank;
import me.hcfalerts.freerank.utils.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class FreeRankAdminCommand implements CommandExecutor {
    public FreeRankAdminCommand() {
        FreeRank.getInstance().getCommand("freerankadmin").setExecutor(this);
    }

    private final FreeRank plugin = FreeRank.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("freerank.admin")) {
            sender.sendMessage(CC.translate("&cYou don't have permission to use this command."));
            return true;
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("reset")) {
            if (args[1].equalsIgnoreCase("all")) {
                plugin.getDataConfig().set("CLAIMED", null);
                plugin.saveDataConfig();
                sender.sendMessage(CC.translate("&aAll free rank claims have been successfully reset."));
            } else {
                String targetUUID = args[1];
                plugin.getDataConfig().set("CLAIMED." + targetUUID, null);
                plugin.saveDataConfig();
                sender.sendMessage(CC.translate("&aThe free rank claim for &e" + targetUUID + " &ahas been successfully reset."));
            }
            return true;
        }

        sender.sendMessage(CC.translate("&cUsage: /freerankadmin reset <player|all>"));
        return true;
    }
}
