package com.rpg.virus.game;

import static com.rpg.virus.constants.VirusConstants.CHOOSE_OPTION;
import static com.rpg.virus.constants.VirusConstants.DATE_TIME_FORMAT;
import static com.rpg.virus.constants.VirusConstants.DELETE_GAME_MESSAGE;
import static com.rpg.virus.constants.VirusConstants.GAME_SAVED_MESSAGE;
import static com.rpg.virus.constants.VirusConstants.INVALID_CHOICE_MESSAGE;
import static com.rpg.virus.constants.VirusConstants.NO_SAVED_GAME_MESSAGE;
import static com.rpg.virus.constants.VirusConstants.ONE;
import static com.rpg.virus.constants.VirusConstants.SAVED_FILE;
import static com.rpg.virus.constants.VirusConstants.SAV_FILE_EXTENTION;
import static com.rpg.virus.constants.VirusConstants.TEN;
import static com.rpg.virus.constants.VirusConstants.UNDERSCORE;
import static com.rpg.virus.constants.VirusConstants.ZERO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.rpg.virus.log.Logger;
import com.rpg.virus.pojo.GameData;
import com.rpg.virus.pojo.SaveGames;
import com.rpg.virus.util.CommonUtils;;

public class VirusSaveAndLoadGameImpl implements SaveAndLoadGame {

	Logger LOG = new Logger();

	private CommonUtils utils;
	private SaveGames saveGames;

	public VirusSaveAndLoadGameImpl(CommonUtils utils) {
		this.utils = utils;
		loadSavedGames();
	}

	/**
	 * Method to save Game.
	 */
	@Override
	public void saveGame(GameData gameData, String playingFileName) {
		startSaveGame(gameData, playingFileName);
		LOG.info(GAME_SAVED_MESSAGE);
	}

	/**
	 * Method to retrieve as saved game.
	 */
	@Override
	public Map<String, GameData> loadSavedGame() {
		if(saveGames.getSavedGamesMap().size() != 0) { 
			showListOfSavedGames(saveGames.getSavedGamesMap());
			LOG.info(CHOOSE_OPTION);
			int userChoice = getUserSelectedGameOption(saveGames.getSavedGamesMap().size() );
			return getSelectedGame(userChoice, saveGames.getSavedGamesMap());
		}else {
			LOG.info(NO_SAVED_GAME_MESSAGE);
			
		}
		return null;
		
	}

	/**
	 * Method to start saving game.
	 * @param gameData
	 * @param playingFileName
	 */
	private void startSaveGame(GameData gameData, String playingFileName) {
		Map<String, GameData> savedGamesMap = saveGames.getSavedGamesMap();
		int deletedIndexNumber = ZERO;
		String fileNameWithIdentifier = ((savedGamesMap.size() + 1) + UNDERSCORE
													+ gameData.getPlayerInfo().getPlayerName());
		gameData.setSavedDate(new Date());

		SaveGames saveGames = getSaveGames() != null ? getSaveGames() : new SaveGames();

		if (saveGames != null) {
			savedGamesMap = saveGames.getSavedGamesMap();
			
			if (!savedGamesMap.containsKey(playingFileName) & savedGamesMap.size() == TEN) {
				deletedIndexNumber = processDeleteGame(savedGamesMap);
				if(deletedIndexNumber!= ZERO){
					fileNameWithIdentifier = deletedIndexNumber + UNDERSCORE
							+ gameData.getPlayerInfo().getPlayerName();
				}
			} else if(savedGamesMap.containsKey(playingFileName)){
				fileNameWithIdentifier = playingFileName;
			}
			saveGames.setSavedGamesMap(updateSavedGamesMap(savedGamesMap, fileNameWithIdentifier, deletedIndexNumber, gameData));
			utils.saveToFile(saveGames, SAVED_FILE + SAV_FILE_EXTENTION);
		}
	}

	/**
	 * Method to update Map before saving.
	 * @param savedGamesMap
	 * @param fileNameWithIdentifier
	 * @param deletedIndexNumber
	 * @param gameData
	 * @return
	 */
	private Map<String, GameData> updateSavedGamesMap(Map<String, GameData> savedGamesMap,
			String fileNameWithIdentifier, int deletedIndexNumber, GameData gameData) {
		Map<String, GameData> newSavedGamesMap = new LinkedHashMap<>();
		int count = ONE;
		if (savedGamesMap.size() > deletedIndexNumber) {
			for (Entry<String, GameData> entrySet : savedGamesMap.entrySet()) {

				if (count == deletedIndexNumber) {
					newSavedGamesMap.put(fileNameWithIdentifier, gameData);
				}
				newSavedGamesMap.put(entrySet.getKey(), entrySet.getValue());
				count++;
			}
			return newSavedGamesMap;
		} else {
			savedGamesMap.put(fileNameWithIdentifier, gameData);
			newSavedGamesMap.putAll(savedGamesMap);
		}
		return newSavedGamesMap;
	}
	
	/**
	 * Method to handle game on delete.
	 * @param savedGameMap
	 * @return
	 */
	private int processDeleteGame(Map<String, GameData> savedGameMap) {
		LOG.info(DELETE_GAME_MESSAGE);
		showListOfSavedGames(savedGameMap);
		LOG.info(CHOOSE_OPTION);
		int userChoice = getUserSelectedGameOption(savedGameMap.size());
		deleteSelectedGame(userChoice, savedGameMap);
		return userChoice;
	}

	/**
	 * Method to identify and delete seleted game.
	 * @param userChoice
	 * @param savedGamesMap
	 * @return
	 */
	private boolean deleteSelectedGame(int userChoice, Map<String, GameData> savedGamesMap) {
		Set<String> savedFileNameSet = savedGamesMap.keySet();
		
		int counter = ONE;
		for (String fileName : savedFileNameSet) {
			if (counter == userChoice) {
				savedGamesMap.remove(fileName);
				return Boolean.TRUE;
			}
			++counter;
		}
		return Boolean.FALSE;
	}

	/**
	 * Method to select a game.
	 * @param userChoice
	 * @param savedGamesMap
	 * @return
	 */
	private Map<String, GameData> getSelectedGame(int userChoice, Map<String, GameData> savedGamesMap) {
		Set<String> savedFileNameSet = savedGamesMap.keySet();
		Map<String, GameData> selectedGame = new HashMap<>();
		int counter = ONE;
		for (String fileName : savedFileNameSet) {
			if (counter == userChoice) {
				selectedGame.put(fileName, savedGamesMap.get(fileName));
				break;
			}
			++counter;
		}
		return selectedGame;
	}

	/**
	 * Method to read user choice.
	 * @param maxValue
	 * @return
	 */
	private int getUserSelectedGameOption(int maxValue) {
		int userChoice = ZERO;
		while (!(userChoice > ZERO & userChoice <= maxValue)){

			userChoice = utils.getConsoleInput();
			if (!(userChoice > ZERO & userChoice <= maxValue)) {
				LOG.info(INVALID_CHOICE_MESSAGE);
			}
		}
		return userChoice;
	}

	/**
	 * Method to show saved games.
	 * @param savedGamesMap
	 */
	private void showListOfSavedGames(Map<String, GameData> savedGamesMap) {
		Set<String> savedFileNameSet = savedGamesMap.keySet();
		LOG.info("Option|   FileName   | SavedDate");
		savedFileNameSet.forEach(fileName -> {
			String[] fileNameArray = fileName.split("_");
			LOG.info(
					fileNameArray[ZERO] + "         "
							 + fileNameArray[ONE] + "      "
							 + new SimpleDateFormat(DATE_TIME_FORMAT).format(savedGamesMap.get(fileName).getSavedDate()));
		});
	}

	@Override
	public SaveGames getSaveGames() {
		return saveGames;
	}

	public void setSaveGames(SaveGames saveGames) {
		this.saveGames = saveGames;
	}

	/**
	 * Method to load saved games from file.
	 */
	private void loadSavedGames() {
		Object object = utils.readFile(SAVED_FILE + SAV_FILE_EXTENTION);
		if (object != null & object instanceof SaveGames) {
			setSaveGames((SaveGames) object);
		} else{
		 setSaveGames(new SaveGames());
		}
	}
}