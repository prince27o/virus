package com.rpg.virus.game;

import static com.rpg.virus.constants.VirusConstants.ABOUT_GAME;
import static com.rpg.virus.constants.VirusConstants.DOT;
import static com.rpg.virus.constants.VirusConstants.GAME_MENU;
import static com.rpg.virus.constants.VirusConstants.INVALID_CHOICE_MESSAGE;
import static com.rpg.virus.constants.VirusConstants.MAIN_MENU;
import static com.rpg.virus.constants.VirusConstants.ZERO;

import java.util.Arrays;
import java.util.List;

import com.rpg.virus.log.Logger;
import com.rpg.virus.util.CommonUtils;

/**
 * Class contains all the major logics regarding the game
 */
public class VirusPlayGameImpl implements PlayGame {

	private static final Logger LOG = new Logger();

	private CommonUtils utils;
	private VirusGameRunnerImpl gameRunner;

	public VirusPlayGameImpl(CommonUtils utils, VirusGameRunnerImpl gameRunner) {
		this.utils = utils;
		this.gameRunner = gameRunner;
	}

	@Override
	public void playGame() {
		utils.clearTerminal();
		int selectedChoice = getUserSelectedValue(MAIN_MENU);
		switch (selectedChoice) {
		case 1:
			utils.clearTerminal();
			String gameResult = startGame();
			if(gameResult != null & MAIN_MENU.equals(gameResult)) {
				playGame();
			}
			break;
		case 2:
			showAboutGame();
			playGame();
			break;
		case 3:
			utils.showExitMessage();
			break;
		default:
			playGame();
			break;
		}
	}

	private void showAboutGame(){
		utils.clearTerminal();
		LOG.info("________________________ About Game_________________________");
		for(int counter = ZERO; counter < ABOUT_GAME.length();counter+=1) {		
			
			LOG.info(ABOUT_GAME.charAt(counter));
			if(DOT.charAt(0) == ABOUT_GAME.charAt(counter)){
				LOG.info("");
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				
			}
		}
		LOG.info("");
		utils.pressOneToContinue();
	}
	
	private String startGame() {
		int gameChoice = getUserSelectedValue(GAME_MENU);
		switch (gameChoice) {
		case 1:
			utils.clearTerminal();
			return gameRunner.startNewGame();
		case 2:			
			return gameRunner.loadSavedGame();
		case 3:			
			return MAIN_MENU;		
		default:
			startGame();
			break;
		}
		return null;
	}

	/**
	 * Method logs the game options
	 */
	private void showGameOptions() {
		utils.clearTerminal();
		LOG.info("____________________________________________");
		LOG.info("| 1) Start New Game                         |");
		LOG.info("| 2) Load Saved Game                        |");
		LOG.info("| 3) Main Menu		                      |");
		LOG.info("|___________________________________________|");
	}

	/**
	 * Method logs the main menu
	 */
	private void showMainMenu() {
		utils.clearTerminal();
		LOG.info("_____________________________________________");
		LOG.info("|            WELCOME TO VIRUS               |");
		LOG.info("|     Game based on command Line            |");
		LOG.info("|*******************************************|");
		LOG.info("|                                           |");
		LOG.info("|        Please choose any option           |");
		LOG.info("|             and press enter.              |");
		LOG.info("|                                           |");
		LOG.info("| 1) Start Game                             |");
		LOG.info("| 2) About Game                             |");
		LOG.info("| 3) Exit                                   |");
		LOG.info("|___________________________________________|");
	}
	
	/**
	 * Method checks whether choice read from console is valid.
	 * 
	 * @param value
	 * @param choices
	 * @return
	 */
	private boolean isAValidChoice(int value, Integer[] choices) {
		List<Integer> mainChoice = Arrays.asList(choices);
		return mainChoice.contains(value) ? Boolean.TRUE : Boolean.FALSE;
	}
	
	/**
	 * Method read the input value from the console from Game options/ Menu Option
	 * @return
	 */
	private int getUserSelectedValue(String optionName) {
		int optedValue = 0;
		boolean showOptions = Boolean.TRUE;
		do {
			if (showOptions) {
				if (MAIN_MENU.equals(optionName)) {
					showMainMenu();
				} else if (GAME_MENU.equals(optionName)) {
					showGameOptions();
				}

			} else {
				LOG.info(INVALID_CHOICE_MESSAGE);
			}
			optedValue = utils.getConsoleInput();
			showOptions = Boolean.FALSE;
		} while (!isAValidChoice(optedValue, new Integer[] { 1, 2, 3 }));

		return optedValue;
	}
	
}
