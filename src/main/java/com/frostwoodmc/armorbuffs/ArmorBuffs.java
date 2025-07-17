package com.frostwoodmc.armorbuffs;

import java.util.Map;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.frostwoodmc.armorbuffs.config.ArmorSetLoader;
import com.frostwoodmc.armorbuffs.listeners.ArmorEquipListener;
import com.frostwoodmc.armorbuffs.model.ArmorEffect;
import com.frostwoodmc.armorbuffs.model.ArmorSet;

public class ArmorBuffs extends JavaPlugin {

    private Map<String, ArmorSet> armorSets;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        // Load armor sets from config.yml
        armorSets = ArmorSetLoader.loadArmorSets(getConfig());
        getLogger().info("Loaded " + armorSets.size() + " armor set(s).");

        // Register the command, tab completer, Listener 
        ArmorBuffsCommand cmd = new ArmorBuffsCommand(this);
        getCommand("armorbuffs").setExecutor(cmd);
        getCommand("armorbuffs").setTabCompleter(cmd);
        getServer().getPluginManager().registerEvents(new ArmorEquipListener(this), this);


    }

    @Override
    public void onDisable() {
        getLogger().info("ArmorBuffs has been disabled.");
    }
    
    public void loadArmorSets() {
        this.armorSets = ArmorSetLoader.loadArmorSets(getConfig());
        getLogger().info("Reloaded " + armorSets.size() + " armor set(s).");
    }


    public Map<String, ArmorSet> getArmorSets() {
        return armorSets;
    }

    private static ArmorBuffs instance;

    public static ArmorBuffs getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        instance = this;
    }
    
    public void setArmorSets(Map<String, ArmorSet> sets) {
        this.armorSets = sets;
    }
    
    //
    public void checkAndApplyEffects(Player player) {
        for (ArmorSet set : armorSets.values()) {
            if (isWearingFullSet(player, set.getsetId())) {
                for (ArmorEffect effect : set.getEffects()) {
                    PotionEffectType type = effect.getType();
                    int level = effect.getLevel();

                    PotionEffect current = player.getPotionEffect(type);
                    boolean needsRefresh = current == null || current.getAmplifier() != level;

                    if (needsRefresh) {
                        player.addPotionEffect(new PotionEffect(type, Integer.MAX_VALUE, level, true, false), true);
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

        NamespacedKey key = new NamespacedKey(this, "armor_set");
        PersistentDataContainer container = meta.getPersistentDataContainer();

        return container.has(key, PersistentDataType.STRING)
            && expectedSetId.equals(container.get(key, PersistentDataType.STRING));
    }
    
}
