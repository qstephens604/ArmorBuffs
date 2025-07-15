package com.frostwoodmc.armorbuffs;

import com.frostwoodmc.armorbuffs.logic.ArmorBuffTask;
import com.frostwoodmc.armorbuffs.config.ArmorSetLoader;
import com.frostwoodmc.armorbuffs.model.ArmorSet;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public class ArmorBuffs extends JavaPlugin {

    private Map<String, ArmorSet> armorSets;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        // Load armor sets from config.yml
        armorSets = ArmorSetLoader.loadArmorSets(getConfig());
        getLogger().info("Loaded " + armorSets.size() + " armor set(s).");
        getCommand("armorbuffs").setExecutor(new ArmorBuffsCommand());
        // We’ll add event listeners here later
        Bukkit.getScheduler().runTaskTimer(this, new ArmorBuffTask(), 0L, 60L); // every 3 seconds

    }

    @Override
    public void onDisable() {
        getLogger().info("ArmorBuffs has been disabled.");
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
}
