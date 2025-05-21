package me.hcfalerts.freerank.commands;

import me.hcfalerts.freerank.FreeRank;
import me.hcfalerts.freerank.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class FreeRankCommand implements CommandExecutor {
    public FreeRankCommand() {
        FreeRank.getInstance().getCommand("freerank").setExecutor(this);
    }

    private final FreeRank plugin = FreeRank.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(CC.translate("&aFreeRank Command &cis only for players."));
        } else {
            Player player = (Player) sender;
            String uuid = player.getUniqueId().toString();

            if (plugin.getDataConfig().getBoolean("CLAIMED." + uuid, false)) {
                player.sendMessage(CC.translate(plugin.getConfig().getString("LANG.ALREADY_CLAIMED")));
                return true;
            }

            SimpleDateFormat loadFormat = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
            loadFormat.setTimeZone(TimeZone.getTimeZone(plugin.getConfig().getString("TIMEZONE")));
            try {
                long endDate = loadFormat.parse(plugin.getConfig().getString("DATE")).getTime();
                long diff = endDate - new Date().getTime();

                if (diff <= 0) {
                    player.sendMessage(CC.translate(plugin.getConfig().getString("LANG.DISABLED")));
                } else {
                    long sec = diff / 1000;
                    String commandTemplate = plugin.getConfig().getString("COMMAND");
                    String playerName = player.getName();

                    String finalCommand = commandTemplate.replace("{player}", playerName);
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), finalCommand);

                    player.sendMessage(CC.translate(plugin.getConfig().getString("LANG.CLAIMED")));

                    plugin.getDataConfig().set("CLAIMED." + uuid, true);
                    plugin.saveDataConfig();
                }
                return true;
            } catch (ParseException ignored) {
                Bukkit.getConsoleSender().sendMessage(CC.translate("&cFailed to load time. Is it in the correct format?"));
            }
        }
        return false;
    }
}