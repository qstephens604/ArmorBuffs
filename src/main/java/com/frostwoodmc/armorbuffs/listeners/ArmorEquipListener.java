package com.frostwoodmc.armorbuffs.listeners;

import com.frostwoodmc.armorbuffs.ArmorBuffs;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;


public class ArmorEquipListener implements Listener {

    private final ArmorBuffs plugin;

    public ArmorEquipListener(ArmorBuffs plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        plugin.checkAndApplyEffects(event.getPlayer());
    }

    @EventHandler
    public void onArmorEquip(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        if (event.getSlotType() == InventoryType.SlotType.ARMOR ||
            event.getClick().isShiftClick() || 
            event.getInventory().getType() == InventoryType.PLAYER) {

            // Delay the check by 1 tick to ensure armor state is updated
            Player player = (Player) event.getWhoClicked();
            plugin.getServer().getScheduler().runTask(plugin, () -> {
                plugin.checkAndApplyEffects(player);
            });
        }
    }
}
