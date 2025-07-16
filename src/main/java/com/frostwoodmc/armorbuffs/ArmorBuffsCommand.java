package com.frostwoodmc.armorbuffs;

import com.frostwoodmc.armorbuffs.model.ArmorSet;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArmorBuffsCommand implements CommandExecutor, TabCompleter {

    private final ArmorBuffs plugin;

    public ArmorBuffsCommand(ArmorBuffs plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
            plugin.loadArmorSets();
            sender.sendMessage("§aArmorBuffs config reloaded.");
            return true;
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("tag")) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("Only players can use this command.");
                return true;
            }

            String setId = args[1];
            if (!plugin.getArmorSets().containsKey(setId)) {
                sender.sendMessage("§cUnknown armor set: §e" + setId);
                return true;
            }

            ItemStack item = player.getInventory().getItemInMainHand();
            if (item == null || !item.hasItemMeta()) {
                sender.sendMessage("§cYou must hold an item with metadata.");
                return true;
            }

            ItemMeta meta = item.getItemMeta();
            NamespacedKey key = new NamespacedKey(plugin, "armor_set");
            meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, setId);
            item.setItemMeta(meta);

            sender.sendMessage("§aTagged item with armor set: §e" + setId);
            return true;
        }

        sender.sendMessage("§cUsage: /armorbuffs <reload|tag <setId>>");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> base = new ArrayList<>();
            base.add("reload");
            base.add("tag");
            return base;
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("tag")) {
            return new ArrayList<>(plugin.getArmorSets().keySet());
        }

        return Collections.emptyList();
    }
}
