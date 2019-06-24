package com.rpg.virus.pojo;

import java.io.Serializable;

/**
 * POJO class for a player's Aspirin power information.
 *
 */
public class Aspirin implements Serializable{

	private String aspirinName;
	private int strength;
	private int level;
	
	public String getAspirinName() {
		return aspirinName;
	}

	public void setAspirinName(String aspirinName) {
		this.aspirinName = aspirinName;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
