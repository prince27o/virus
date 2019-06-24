package com.rpg.virus.pojo;

import java.io.Serializable;
import java.util.List;

public class VirusParams implements Serializable{
	private boolean isVirusKilled;
	private boolean isGameInPlayingMode;
	private boolean aspirinNotProcessed;
	private int virusNameListCount;
	private List<String> virusNameList;
	
	

	public VirusParams(boolean isVirusKilled, boolean isGameInPlayingMode, int virusNameListCount, List<String> virusNameList) {
		this.isVirusKilled = isVirusKilled;
		this.isGameInPlayingMode =isGameInPlayingMode;
		this.virusNameListCount = virusNameListCount;
		this.virusNameList = virusNameList;
	}

	public boolean isVirusKilled() {
		return isVirusKilled;
	}

	public void setVirusKilled(boolean isVirusKilled) {
		this.isVirusKilled = isVirusKilled;
	}
	
	public boolean isGameInPlayingMode() {
		return isGameInPlayingMode;
	}

	public void setGameInPlayingMode(boolean isGameInPlayingMode) {
		this.isGameInPlayingMode = isGameInPlayingMode;
	}
	

	public boolean isAspirinNotProcessed() {
		return aspirinNotProcessed;
	}

	public void setAspirinNotProcessed(boolean aspirinNotProcessed) {
		this.aspirinNotProcessed = aspirinNotProcessed;
	}

	public int getVirusNameListCount() {
		return virusNameListCount;
	}

	public void setVirusNameListCount(int virusNameListCount) {
		this.virusNameListCount = virusNameListCount;
	}

	public List<String> getVirusNameList() {
		return virusNameList;
	}

	public void setVirusNameList(List<String> virusNameList) {
		this.virusNameList = virusNameList;
	}
}
