package com.frostwoodmc.armorbuffs.listeners;

import com.frostwoodmc.armorbuffs.ArmorBuffs;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseArmorEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class ArmorEquipListener implements Listener {

    private final ArmorBuffs plugin;

    public ArmorEquipListener(ArmorBuffs plugin) {
        this.plugin = plugin;
    }

    // On join (first load)
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        plugin.checkAndApplyEffects(event.getPlayer());
    }

    // On respawn (after death)
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Bukkit.getScheduler().runTaskLater(plugin, () ->
            plugin.checkAndApplyEffects(event.getPlayer()), 1L);
    }

    // On item break (armor breaking)
    @EventHandler
    public void onItemBreak(PlayerItemBreakEvent event) {
        plugin.getServer().getScheduler().runTask(plugin, () ->
            plugin.checkAndApplyEffects(event.getPlayer()));
    }

    // On armor equip via inventory
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        if (event.getClick() == ClickType.SHIFT_LEFT ||
        	    event.getSlot() >= 36 && event.getSlot() <= 39) { // Armor slots

        	    Bukkit.getScheduler().runTask(plugin, () ->
        	        plugin.checkAndApplyEffects(player));
        	}
    }

    // On armor equipped via dispenser
    @EventHandler
    public void onDispenserArmor(BlockDispenseArmorEvent event) {
        if (event.getTargetEntity() instanceof Player player) {
            Bukkit.getScheduler().runTask(plugin, () ->
                plugin.checkAndApplyEffects(player));
        }
    }

    // On death, clears effects
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        event.getEntity().getActivePotionEffects().forEach(pe ->
            event.getEntity().removePotionEffect(pe.getType()));
    }
    
 // Milk Fix
    @EventHandler
    public void onMilkDrink(PlayerItemConsumeEvent event) {
        if (event.getItem().getType() == Material.MILK_BUCKET) {
            Player player = event.getPlayer();
            // Reapply effects shortly after the milk clears them
            Bukkit.getScheduler().runTaskLater(plugin, () ->
                plugin.checkAndApplyEffects(player), 1L);
        }
    }

}
