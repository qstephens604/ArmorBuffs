package com.frostwoodmc.armorbuffs;

import com.frostwoodmc.armorbuffs.config.ArmorSetLoader;
import com.frostwoodmc.armorbuffs.model.ArmorSet;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Map;

public class ArmorBuffsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            ArmorBuffs.getInstance().reloadConfig();
            Map<String, ArmorSet> reloaded = ArmorSetLoader.loadArmorSets(ArmorBuffs.getInstance().getConfig());
            ArmorBuffs.getInstance().setArmorSets(reloaded);
            sender.sendMessage("§aArmorBuffs config reloaded.");
            return true;
        }

        sender.sendMessage("§cUsage: /armorbuffs reload");
        return true;
    }
}
