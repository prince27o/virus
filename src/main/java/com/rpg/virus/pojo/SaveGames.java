package com.rpg.virus.pojo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SaveGames implements Serializable {

	private Map<String, GameData> savedGamesMap = new LinkedHashMap<>();

	public Map<String, GameData> getSavedGamesMap() {
		return savedGamesMap;
	}

	public void setSavedGamesMap(Map<String, GameData> savedGamesMap) {
		this.savedGamesMap = savedGamesMap;
	}

	

}
