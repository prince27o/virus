package com.rpg.virus.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * POJO Class for the Players Informations
 *
 */
public class PlayerInfo implements Serializable {

	private String playerName;
	private int healthStatus;
	private String skillLevel;
	private int virusKilled;
	private List<Aspirin> aspirinList;	

	

	public PlayerInfo(String playerName, int healthStatus, String skillLevel, int virusKilled,
			List<Aspirin> aspirinList) {
		this.playerName = playerName;
		this.healthStatus = healthStatus;
		this.skillLevel = skillLevel;
		this.virusKilled = virusKilled;
		this.aspirinList = aspirinList;		
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
		
	}

	public int getHealthStatus() {
		return healthStatus;
	}

	public void setHealthStatus(int healthStatus) {
		this.healthStatus = healthStatus;
		
	}

	public String getSkillLevel() {
		return skillLevel;
	}

	public void setSkillLevel(String skillLevel) {
		this.skillLevel = skillLevel;
		
	}

	public int getVirusKilled() {
		return virusKilled;
	}

	public void setVirusKilled(int virusKilled) {
		this.virusKilled = virusKilled;
		
	}

	public List<Aspirin> getAspirinList() {
		return aspirinList;
	}

	public void setAspirinList(List<Aspirin> aspirinList) {
		this.aspirinList = aspirinList;
		
	}
	
}
