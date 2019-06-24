package com.rpg.virus.pojo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GameData implements Serializable {
	private PlayerInfo playerInfo;
	private VirusInfo virusInfo;
	private VirusParams virusParams;
	private Date savedDate;

	public PlayerInfo getPlayerInfo() {
		return playerInfo;
	}

	public void setPlayerInfo(PlayerInfo playerInfo) {
		this.playerInfo = playerInfo;
	}

	public VirusInfo getVirusInfo() {
		return virusInfo;
	}

	public void setVirusInfo(VirusInfo virusInfo) {
		this.virusInfo = virusInfo;
	}

	public VirusParams getVirusParams() {
		return virusParams;
	}

	public void setVirusParams(VirusParams virusParams) {
		this.virusParams = virusParams;
	}

	public Date getSavedDate() {
		return savedDate;
	}

	public void setSavedDate(Date savedDate) {
		this.savedDate = savedDate;
	}	
}
