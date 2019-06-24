package com.rpg.virus.pojo;

import java.io.Serializable;

/**
 * POJO Class for the virus Informations
 *
 */
public class VirusInfo implements Serializable {
	private String virusName;
	private int healthStatus;
	private int strength;

	public String getVirusName() {
		return virusName;
	}

	public void setVirusName(String virusName) {
		this.virusName = virusName;
	}

	public int getHealthStatus() {
		return healthStatus;
	}

	public void setHealthStatus(int healthStatus) {
		this.healthStatus = healthStatus;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}
	

}
