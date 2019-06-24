package com.rpg.virus.game;

import java.util.Map;

import com.rpg.virus.pojo.GameData;
import com.rpg.virus.pojo.SaveGames;

public interface SaveAndLoadGame {

	public void saveGame(GameData gameDate, String playingFileName);

	public Map<String, GameData> loadSavedGame();

	public SaveGames getSaveGames();

}
