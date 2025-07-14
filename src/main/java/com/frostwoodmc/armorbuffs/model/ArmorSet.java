package com.frostwoodmc.armorbuffs.model;

import java.util.List;

public class ArmorSet {
	
	private final String keyword;
	private final List<ArmorEffect> effects;
	
	public ArmorSet(String keyword, List<ArmorEffect> effects) {
		this.keyword = keyword;
		this.effects = effects;
	}
	
	public String getKeyword() {
		return keyword;
	}
	
	public List<ArmorEffect> getEffects() {
		return effects;
	}

}
