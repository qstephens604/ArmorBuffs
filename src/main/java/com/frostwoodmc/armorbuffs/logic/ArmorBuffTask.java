package com.frostwoodmc.armorbuffs.logic;

import com.frostwoodmc.armorbuffs.ArmorBuffs;
import com.frostwoodmc.armorbuffs.model.ArmorEffect;
import com.frostwoodmc.armorbuffs.model.ArmorSet;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

public class ArmorBuffTask extends BukkitRunnable {

    private final ArmorBuffs plugin;
    private final Map<String, ArmorSet> armorSets;

    public ArmorBuffTask(ArmorBuffs plugin) {
        this.plugin = plugin;
        this.armorSets = plugin.getArmorSets();
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (ArmorSet set : armorSets.values()) {
                if (isWearingFullSet(player, set.getsetId())) {
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

    private boolean isWearingFullSet(Player player, String setId) {
        ItemStack[] armor = player.getInventory().getArmorContents();
        for (ItemStack piece : armor) {
            if (piece == null || !hasArmorBuffTag(piece, setId)) {
                return false;
            }
        }
        return true;
    }

    private boolean hasArmorBuffTag(ItemStack item, String expectedSetId) {
        if (!item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();

        NamespacedKey key = new NamespacedKey(plugin, "armor_set");
        PersistentDataContainer container = meta.getPersistentDataContainer();

        return container.has(key, PersistentDataType.STRING) &&
               expectedSetId.equals(container.get(key, PersistentDataType.STRING));
    }

    private boolean shouldRefreshEffect(Player player, PotionEffectType type, int durationTicks, int level) {
        PotionEffect current = player.getPotionEffect(type);
        return current == null || current.getAmplifier() != level || current.getDuration() < durationTicks / 2;
    }
}
