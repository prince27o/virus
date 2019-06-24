package com.rpg.virus.databuilder;

import java.util.Date;
import java.util.List;

import com.rpg.virus.pojo.Aspirin;
import com.rpg.virus.pojo.PlayerInfo;

/**
 * POJO Class for building Players Informations
 *
 */
public class PlayerBuilder {
	private String playerName;
	private int healthStatus;
	private String skillLevel;
	private int virusKilled;
	private List<Aspirin> aspirinList;

	public String getPlayerName() {
		return playerName;
	}

	public PlayerBuilder setPlayerName(String playerName) {
		this.playerName = playerName;
		return this;
	}

	public int getHealthStatus() {
		return healthStatus;
	}

	public PlayerBuilder setHealthStatus(int healthStatus) {
		this.healthStatus = healthStatus;
		return this;
	}

	public String getSkillLevel() {
		return skillLevel;
	}

	public PlayerBuilder setSkillLevel(String skillLevel) {
		this.skillLevel = skillLevel;
		return this;
	}

	public int getVirusKilled() {
		return virusKilled;
	}

	public PlayerBuilder setVirusKilled(int virusKilled) {
		this.virusKilled = virusKilled;
		return this;
	}

	public List<Aspirin> getAspirinList() {
		return aspirinList;
	}

	public PlayerBuilder setAspirinList(List<Aspirin> aspirinList) {
		this.aspirinList = aspirinList;
		return this;
	}

	public PlayerInfo build() {
		return new PlayerInfo(playerName, healthStatus, skillLevel, virusKilled, aspirinList);
	}

}

