package com.frostwoodmc.armorbuffs.model;

import org.bukkit.potion.PotionEffectType;

public class ArmorEffect {
	private final PotionEffectType type;
	private final int level;
	private final int durationSeconds;
	
	public ArmorEffect(PotionEffectType type, int level, int durationSeconds) {
		this.type = type;
		this.level = level;
		this.durationSeconds = durationSeconds;
	}
	
	public PotionEffectType getType() {
		return type;
	}
	
	public int getLevel() {
		return level;
	}
	
	public int getDurationTicks() {
		return durationSeconds * 20;
	}
	public boolean isPermanent() {
		return durationSeconds == -1;
	}
	public int getDurationSeconds() {
		return durationSeconds;
	}

}
