package com.frostwoodmc.armorbuffs.model;

import java.util.List;

public class ArmorSet {
	
	private final String setId;
	private final List<ArmorEffect> effects;
	
	public ArmorSet(String setId, List<ArmorEffect> effects) {
		this.setId = setId;
		this.effects = effects;
	}
	
	public String getsetId() {
		return setId;
	}
	
	public List<ArmorEffect> getEffects() {
		return effects;
	}

}
