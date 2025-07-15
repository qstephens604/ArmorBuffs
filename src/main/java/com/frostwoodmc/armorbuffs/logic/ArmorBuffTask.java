package com.frostwoodmc.armorbuffs.logic;

import com.frostwoodmc.armorbuffs.ArmorBuffs;
import com.frostwoodmc.armorbuffs.model.ArmorEffect;
import com.frostwoodmc.armorbuffs.model.ArmorSet;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Map;

public class ArmorBuffTask implements Runnable {

    private final Map<String, ArmorSet> armorSets;

    public ArmorBuffTask() {
        this.armorSets = ArmorBuffs.getInstance().getArmorSets();
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (ArmorSet set : armorSets.values()) {
                if (isWearingFullSet(player, set.getKeyword())) {
                    for (ArmorEffect effect : set.getEffects()) {
                        PotionEffectType type = effect.getType();
                        int level = effect.getLevel();
                        int durationTicks = effect.getDurationTicks();

                        if (effect.isPermanent() || shouldRefreshEffect(player, type, durationTicks, level)) {
                            player.addPotionEffect(new PotionEffect(
                                type,
                                effect.isPermanent() ? 80 : durationTicks,
                                level,
                                true,
                                false
                            ), true);
                        }
                    }
                }
            }
        }
    }

    private boolean isWearingFullSet(Player player, String keyword) {
        ItemStack[] armor = player.getInventory().getArmorContents();
        for (ItemStack piece : armor) {
            if (piece == null || !hasLoreKeyword(piece, keyword)) {
                return false;
            }
        }
        return true;
    }

    private boolean hasLoreKeyword(ItemStack item, String keyword) {
        if (!item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        if (!meta.hasLore()) return false;

        for (String line : meta.getLore()) {
            String stripped = ChatColor.stripColor(line);
            if (stripped != null && stripped.contains(keyword)) {
                return true;
            }
        }
        return false;
    }

    private boolean shouldRefreshEffect(Player player, PotionEffectType type, int durationTicks, int level) {
        PotionEffect current = player.getPotionEffect(type);
        return current == null || current.getAmplifier() != level || current.getDuration() < durationTicks / 2;
    }
}
