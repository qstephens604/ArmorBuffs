package com.frostwoodmc.armorbuffs.config;

import com.frostwoodmc.armorbuffs.model.ArmorEffect;
import com.frostwoodmc.armorbuffs.model.ArmorSet;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class ArmorSetLoader {

    public static Map<String, ArmorSet> loadArmorSets(FileConfiguration config) {
        Map<String, ArmorSet> armorSets = new HashMap<>();

        ConfigurationSection setsSection = config.getConfigurationSection("armor-sets");
        if (setsSection == null) {
            return armorSets; // No armor-sets defined
        }

        for (String setKey : setsSection.getKeys(false)) {
            ConfigurationSection setSection = setsSection.getConfigurationSection(setKey);
            if (setSection == null) continue;

            String keyword = setSection.getString("keyword");
            if (keyword == null) continue;

            List<ArmorEffect> effects = new ArrayList<>();
            List<Map<?, ?>> rawEffects = setSection.getMapList("effects");

            for (Map<?, ?> effectMap : rawEffects) {
                try {
                    String typeName = (String) effectMap.get("type");
                    int level = (int) effectMap.get("level");
                    int duration = (int) effectMap.get("duration");

                    PotionEffectType type = PotionEffectType.getByName(typeName.toUpperCase());
                    if (type == null) {
                        System.err.println("[ArmorBuffs] Unknown potion effect: " + typeName);
                        continue;
                    }

                    effects.add(new ArmorEffect(type, level, duration));

                } catch (Exception e) {
                    System.err.println("[ArmorBuffs] Failed to load effect for set '" + setKey + "': " + e.getMessage());
                }
            }

            armorSets.put(setKey, new ArmorSet(keyword, effects));
        }

        return armorSets;
    }
}
