package com.rpg.virus.game;

import static com.rpg.virus.constants.VirusConstants.ASPIRIN_INJECTED_MESSAGE;
import static com.rpg.virus.constants.VirusConstants.ASPIRIN_BALANCE_MESSAGE;
import static com.rpg.virus.constants.VirusConstants.CHOOSE_OPTION;
import static com.rpg.virus.constants.VirusConstants.CREATE_PLAYER_MESSAGE;
import static com.rpg.virus.constants.VirusConstants.PLAYER_KILLED_MESSAGE;
import static com.rpg.virus.constants.VirusConstants.CREATE_PLAYER_NAME;
import static com.rpg.virus.constants.VirusConstants.DEFAULT_ASPIRIN;
import static com.rpg.virus.constants.VirusConstants.DEFAULT_HEALTH_STATUS;
import static com.rpg.virus.constants.VirusConstants.DELAY;
import static com.rpg.virus.constants.VirusConstants.HUNDRED;
import static com.rpg.virus.constants.VirusConstants.INVALID_CHOICE_MESSAGE;
import static com.rpg.virus.constants.VirusConstants.NO_SAVED_GAME_MESSAGE;
import static com.rpg.virus.constants.VirusConstants.MAIN_MENU;
import static com.rpg.virus.constants.VirusConstants.NO_ASPIRIN_MESSAGE;
import static com.rpg.virus.constants.VirusConstants.NO_ASPIRIN_STRENGTH_MESSAGE;
import static com.rpg.virus.constants.VirusConstants.ONE;
import static com.rpg.virus.constants.VirusConstants.SKILL_JUNIOR_DOCTOR;
import static com.rpg.virus.constants.VirusConstants.SKILL_SENIOR_DOCTOR;
import static com.rpg.virus.constants.VirusConstants.SKILL_EXPERT_DOCTOR;
import static com.rpg.virus.constants.VirusConstants.TEN;
import static com.rpg.virus.constants.VirusConstants.SIX;
import static com.rpg.virus.constants.VirusConstants.NINE;
import static com.rpg.virus.constants.VirusConstants.THREAD_INTERRUPTED;
import static com.rpg.virus.constants.VirusConstants.THREE;
import static com.rpg.virus.constants.VirusConstants.ZERO;
import static com.rpg.virus.constants.VirusConstants.TWO;
import static com.rpg.virus.constants.VirusConstants.FOUR;
import static com.rpg.virus.constants.VirusConstants.EIGHT;
import static com.rpg.virus.constants.VirusConstants.FIVE;
import static com.rpg.virus.constants.VirusConstants.DOT;
import static com.rpg.virus.constants.VirusConstants.SPACE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.rpg.virus.databuilder.AspirinGenerator;
import com.rpg.virus.databuilder.PlayerBuilder;
import com.rpg.virus.databuilder.VirusGenerator;
import com.rpg.virus.log.Logger;
import com.rpg.virus.pojo.Aspirin;
import com.rpg.virus.pojo.GameData;
import com.rpg.virus.pojo.PlayerInfo;
import com.rpg.virus.pojo.VirusInfo;
import com.rpg.virus.pojo.VirusParams;
import com.rpg.virus.util.CommonUtils;

public class VirusGameRunnerImpl implements GameRunner {

	private static final Logger LOG = new Logger();

	private CommonUtils utils;
	private AspirinGenerator aspirinGenerator;
	private VirusGenerator virusGenerator;
	private SaveAndLoadGame saveAndLoadGame;

	public VirusGameRunnerImpl(CommonUtils utils, AspirinGenerator aspirinGenerator, VirusGenerator virusGenerator, SaveAndLoadGame saveAndLoadGame) {
		this.utils = utils;
		this.aspirinGenerator = aspirinGenerator;
		this.virusGenerator = virusGenerator;
		this.saveAndLoadGame = saveAndLoadGame;
	}

	@Override
	public String startNewGame() {
		utils.clearTerminal();
		PlayerInfo playerInfo = loadPlayerInfo();
		LOG.info("Dr." + playerInfo.getPlayerName() + " welcome to the game virus");		
		GameData data = new GameData();
		data.setPlayerInfo(playerInfo);
		return playGame(data, null);
	}

	@Override
	public String loadSavedGame() {
		Map<String, GameData> selectedGame = saveAndLoadGame.loadSavedGame();		
		if(selectedGame == null){
			utils.pressOneToContinue();
			return MAIN_MENU;
		}	
		Set<String>fileNameSet = selectedGame.keySet();
		Iterator<String > fileIterator = fileNameSet.iterator();
		String fileName = fileIterator.next();
		return playGame(selectedGame.get(fileName), fileName);
	}

	/**
	 * Method for playing the game
	 * 
	 * @param playerInfo
	 */
	private String playGame(GameData gameData, String playingFileName) {
		utils.clearTerminal();		
		PlayerInfo playerInfo = gameData.getPlayerInfo();
		showPlayerInfo(playerInfo);
		utils.pressOneToContinue();
		VirusParams virusParams = null;
		if(gameData.getVirusParams()!= null){
			virusParams = gameData.getVirusParams();
		} else {
		virusParams = new VirusParams(Boolean.TRUE, Boolean.TRUE, ZERO,
				new ArrayList(virusGenerator.getVirusNameSet()));
		}
		VirusInfo virusInfo = gameData.getVirusInfo();

		while (virusParams.isGameInPlayingMode() | isGameCompleted(playerInfo)) {
			if (virusParams.isVirusKilled()) {
				virusInfo = getNextVirus(virusParams);
				if (virusInfo == null) {
					break;
				}
				virusParams.setVirusKilled(Boolean.FALSE);
			}
			showPlayingInstruction();
			int userChoice =ZERO;			
			userChoice = utils.getConsoleInput();
			
			switch (userChoice) {
			
			case 1:
				String fightResult = startFighting(playerInfo, virusInfo, virusParams);
				if (fightResult != null && MAIN_MENU.equals(fightResult)) {
					virusParams.setGameInPlayingMode(Boolean.FALSE);
					return fightResult;
				}
				break;
			case 2:
				injectAspirinToVirus(playerInfo, virusInfo, virusParams);
				checkAndUpdateSkill(playerInfo);
				break;
			case 3:
				showPlayerInfo(playerInfo);
				break;
			case 4:	
				showGameStatus(virusInfo, playerInfo);
				break;
			case 5:
				saveGame(gameData, playerInfo, virusInfo, virusParams, playingFileName);
				if(continueGameAfteSave()){
					break;
				}
				return MAIN_MENU;				
			case 6:				
				virusParams.setGameInPlayingMode(Boolean.FALSE);
				return MAIN_MENU;
				
			default:
				LOG.info(INVALID_CHOICE_MESSAGE);
				break;
			}
			
			if(isGameCompleted(playerInfo)){
				LOG.info("      Hurray..!!    ");
				LOG.info("You have won the Game");
				LOG.info("Congratulations Dr."+playerInfo.getPlayerName()+ " you have made the city virus free");
				utils.pressOneToContinue();
				return MAIN_MENU;
			}
			
		}

		return null;
	}
	private String startFighting(PlayerInfo playerInfo, VirusInfo virusInfo, VirusParams virusParams){
		utils.clearTerminal();
		String fightResult = startFightingWithVirus(playerInfo, virusInfo, virusParams);
		checkAndUpdateSkill(playerInfo);
		return fightResult;
	}
	private void saveGame(GameData gameData, PlayerInfo playerInfo, VirusInfo virusInfo, VirusParams virusParams, String playingFileName){
		gameData = new GameData();
		gameData.setPlayerInfo(playerInfo);
		gameData.setVirusInfo(virusInfo);
		gameData.setVirusParams(virusParams);

		saveAndLoadGame.saveGame(gameData, playingFileName);
	}
	
	private boolean continueGameAfteSave() {
		int userInput = ZERO;
		LOG.info("");
		LOG.info(CHOOSE_OPTION);
		LOG.info("1. Continue");
		LOG.info("2. Main Menu");
		while (!isAValidContinueGameOption(userInput)) {
			userInput = utils.getConsoleInput();
			if (!isAValidContinueGameOption(userInput)) {
				LOG.info(INVALID_CHOICE_MESSAGE);
			}
		}
		if (userInput == ONE) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	private boolean isAValidContinueGameOption(int userInput){
		if((userInput == ONE | userInput == TWO)){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	private void injectAspirinToVirus(PlayerInfo playerInfo, VirusInfo virusInfo, VirusParams virusParams) {
		int aspirinCount = playerInfo.getAspirinList().size();
		if (aspirinCount >= ONE) {
			virusParams.setAspirinNotProcessed(Boolean.TRUE);
			processMultipleAspirin(playerInfo, virusInfo, virusParams);			
		} else {
			LOG.info(NO_ASPIRIN_MESSAGE);
		}
	}

	private void processMultipleAspirin(PlayerInfo playerInfo, VirusInfo virusInfo, VirusParams virusParams) {
		Map<Integer, String> aspirinOptionMap = new HashMap<>();
		utils.clearTerminal();
		while (virusParams.isAspirinNotProcessed()) {			
			LOG.info(CHOOSE_OPTION);
			AtomicInteger counter = new AtomicInteger(ZERO);
			showAspirinAndUpdateAspirinMap(playerInfo, counter, aspirinOptionMap);
			int backToPlayMenuOptionNumber = counter.incrementAndGet();
			LOG.info(backToPlayMenuOptionNumber + ". Back to Play Menu");
			int userInput = ZERO;
			userInput = utils.getConsoleInput();
			String aspirinName = aspirinOptionMap.get(userInput);
			if (userInput == backToPlayMenuOptionNumber) {
				virusParams.setAspirinNotProcessed(Boolean.FALSE);
			} else if (aspirinName != null && playerInfo.getAspirinList().stream()
					.anyMatch(aspirin -> aspirinName.equals(aspirin.getAspirinName()))) {
				injectAspirin(playerInfo, virusInfo, aspirinName);
				virusParams.setAspirinNotProcessed(Boolean.FALSE);
			} else {
				LOG.info(INVALID_CHOICE_MESSAGE);
			}

		}
	}

	private void showAspirinAndUpdateAspirinMap(PlayerInfo playerInfo, AtomicInteger counter,
			Map<Integer, String> aspirinOptionMap) {
		playerInfo.getAspirinList().forEach(aspirin -> {
			int optionNumbercounter = counter.incrementAndGet();
			LOG.info(+optionNumbercounter + DOT + SPACE+ aspirin.getAspirinName() + "(Strenght :" + aspirin.getStrength()
					+ ")");
			aspirinOptionMap.put(optionNumbercounter, aspirin.getAspirinName());
		});
	}

	private void injectAspirin(PlayerInfo playerInfo, VirusInfo virusInfo, String aspirinName) {
		Aspirin aspirin = playerInfo.getAspirinList().stream()
				.filter(aspirinObj -> aspirinName.equals(aspirinObj.getAspirinName())).findFirst().get();
		if (aspirin.getStrength() == ZERO) {
			LOG.info(NO_ASPIRIN_STRENGTH_MESSAGE);
		} else {
			int newVirusHealth = virusInfo.getHealthStatus() - TEN;
			virusInfo.setHealthStatus((newVirusHealth > ZERO ? newVirusHealth : ZERO));

			int newAspirinStrenght = aspirin.getStrength() - TEN;
			aspirin.setStrength((newAspirinStrenght > ZERO ? newAspirinStrenght : ZERO));
			updatePlayerInfoWithNewAspirinStrength(playerInfo, aspirin);
			LOG.info(ASPIRIN_INJECTED_MESSAGE);
			LOG.info("");
			LOG.info(ASPIRIN_BALANCE_MESSAGE+ aspirin.getStrength());
			showHealth(playerInfo, virusInfo);
			utils.pressOneToContinue();
		}
	}

	private void updatePlayerInfoWithNewAspirinStrength(PlayerInfo playerInfo, Aspirin aspirin) {
		playerInfo.getAspirinList().forEach(aspirinObj -> {
			if (aspirinObj.getAspirinName().equals(aspirin.getAspirinName())) {
				aspirinObj.setStrength(aspirin.getStrength());
			}
		});
	}

	private void checkAndUpdateSkill(PlayerInfo playerInfo) {		
		Aspirin aspirinToBeUpdated = null;
		List<Aspirin> aspirinListWithEmptyStrength = playerInfo.getAspirinList().stream()
				.filter(aspirin -> aspirin.getStrength() == ZERO).collect(Collectors.toList());
		if(utils.isNotEmpty(aspirinListWithEmptyStrength)) {
		 aspirinToBeUpdated = aspirinListWithEmptyStrength.stream()
											  .min(Comparator.comparing(Aspirin::getStrength)).get();
		 
		}
		if(aspirinToBeUpdated != null) {
			handleAspirinWithZeroStrength(aspirinToBeUpdated, playerInfo);
		}
		
		if (playerInfo.getVirusKilled()!= ZERO && (playerInfo.getVirusKilled() % FOUR == 0 | (aspirinToBeUpdated!= null && playerInfo.getVirusKilled() > aspirinToBeUpdated.getLevel()))) {			
			updateAspirin(playerInfo,aspirinToBeUpdated);
			
			if(playerInfo.getAspirinList().size() == TWO && playerInfo.getSkillLevel().equals(SKILL_JUNIOR_DOCTOR)) {
				playerInfo.setSkillLevel(SKILL_SENIOR_DOCTOR);
				LOG.info("Congratulations !! You have promoted to a Senior doctor and one addition virus killed bonus point");
			} else {
				if(playerInfo.getAspirinList().size() == THREE && playerInfo.getSkillLevel().equals(SKILL_SENIOR_DOCTOR)) {
				playerInfo.setSkillLevel(SKILL_EXPERT_DOCTOR);
				LOG.info("Congratulations !! You have promoted to an Expert doctor and one addition virus killed bonus point");
				}
			}
			playerInfo.setVirusKilled(playerInfo.getVirusKilled()+1);
		}	

	}
	
	
	private void handleAspirinWithZeroStrength(Aspirin aspirin, PlayerInfo playerInfo) {
		
		LOG.info("Your Aspirin " + aspirin.getAspirinName() + "'s strenght has reached Zero");
		LOG.info(CHOOSE_OPTION + " for update strength or remove aspirin");
		LOG.info("1.update Aspirin");
		LOG.info("2. Remove Aspirin");
		int userChoice = ZERO;
		while (!(userChoice > ZERO & userChoice <= TWO)) {
			userChoice = utils.getConsoleInput();
			if (!(userChoice > ZERO & userChoice <= TWO)) {
				LOG.info(INVALID_CHOICE_MESSAGE);
			}
		}

		switch (userChoice) {
		case 1:
			int newVirusKilledCount = playerInfo.getVirusKilled() - aspirin.getLevel();
			playerInfo.setVirusKilled(newVirusKilledCount < ZERO ? ZERO : newVirusKilledCount);
			if (newVirusKilledCount < aspirin.getLevel()) {
				if (aspirin.getLevel() <= THREE) {
					playerInfo.setSkillLevel(SKILL_JUNIOR_DOCTOR);
				} else if (aspirin.getLevel() <= SIX && aspirin.getLevel() > THREE) {
					playerInfo.setSkillLevel(SKILL_SENIOR_DOCTOR);
				} else if (aspirin.getLevel() <= NINE && aspirin.getLevel() > SIX) {
					playerInfo.setSkillLevel(SKILL_EXPERT_DOCTOR);
				}
				LOG.info("You Skill level got reduced to "+ playerInfo.getSkillLevel());
				updateAspirin(playerInfo, aspirin);
			}
			break;
		case 2:
			List<Aspirin> aspirinList = playerInfo.getAspirinList().stream()
			.filter(aspirinObject -> aspirinObject.getStrength() != ZERO).collect(Collectors.toList());
			playerInfo.setAspirinList(aspirinList);
			break;
		default:
			break;
		}
	}
	
	private void updateAspirin(PlayerInfo playerInfo, Aspirin aspirinToBeUpdated){
		Aspirin newAspirin = null;	

		if (aspirinToBeUpdated != null ) {
			newAspirin = aspirinGenerator.getAspirinObject(aspirinToBeUpdated.getAspirinName());
			updatePlayerInfoWithNewAspirinStrength(playerInfo,newAspirin);
			LOG.info("Congratulations !! Your Aspirin Strength got updated" + newAspirin.getAspirinName());
		} else {
			Set<String> aspirinNameList = aspirinGenerator.getAspirinNameSet();
			Optional<String> newAspirinLoaded = aspirinNameList.stream()
					.filter(aspirinName -> playerInfo.getAspirinList().stream().noneMatch(
							playerAspirin -> aspirinName.equalsIgnoreCase(playerAspirin.getAspirinName())))
					.findFirst();
			if(newAspirinLoaded.isPresent()){
				newAspirin = aspirinGenerator.getAspirinObject(newAspirinLoaded.get());
				playerInfo.getAspirinList().add(newAspirin);
				LOG.info("Congratulations !! You have earned a new Aspirin " +newAspirin.getAspirinName());
			}
		}

	}

	/**
	 * Method for fighting with virus, reduce health by attack
	 * 
	 * @param playerInfo
	 * @param virusInfo
	 * @param virusParams
	 */
	private String startFightingWithVirus(PlayerInfo playerInfo, VirusInfo virusInfo, VirusParams virusParams) {
		
		int randomNumber = utils.getAction().nextInt(HUNDRED);
		if (randomNumber <= virusInfo.getStrength()) {
		//if (randomNumber == 0) {
			updatePlayerHealth(playerInfo);
		} else {
			updateVirusHealth(virusInfo);
		}

		showHealth(playerInfo, virusInfo);
		if (isVirusKilled(playerInfo, virusInfo)) {
			LOG.info("");
			LOG.info(
					"Well done Dr." + playerInfo.getPlayerName() + " you killed the virus " + virusInfo.getVirusName());
			playerInfo.setVirusKilled(playerInfo.getVirusKilled() + ONE);
			virusParams.setVirusKilled(Boolean.TRUE);
			int newHealth = playerInfo.getHealthStatus() + FIVE;
			playerInfo.setHealthStatus(newHealth > HUNDRED ? HUNDRED : newHealth);
			if (newHealth <= HUNDRED) {
				LOG.info("");
				LOG.info("You have earned " + FIVE + " bonus health");
			}
			utils.pressOneToContinue();
		} else if (isPlayerLost(playerInfo, virusInfo)) {
			LOG.info("");
			LOG.info(PLAYER_KILLED_MESSAGE);
			return afterFailOptions(virusParams);

		}
		return null;
	}

	/**
	 * Method provide choice after player failed
	 * 
	 * @param virusParams
	 * @return
	 */
	private String afterFailOptions(VirusParams virusParams) {
		LOG.info("");
		LOG.info(CHOOSE_OPTION);
		LOG.info("1. Go to Main Menu");
		LOG.info("2. Exit");
		int usrChoice = utils.getConsoleInput();
		switch (usrChoice) {
		case 1:
			return MAIN_MENU;
		case 2:			
			utils.showExitMessage();
			virusParams.setGameInPlayingMode(Boolean.FALSE);
			break;
		default:
			utils.clearTerminal();
			LOG.info(INVALID_CHOICE_MESSAGE);
			afterFailOptions(virusParams);
			break;
		}
		return null;
	}

	/**
	 * Method to check virus killed
	 * 
	 * @param playerInfo
	 * @param virusInfo
	 * @return
	 */
	private boolean isVirusKilled(PlayerInfo playerInfo, VirusInfo virusInfo) {
		return (playerInfo.getHealthStatus() > ZERO & virusInfo.getHealthStatus() == ZERO);
	}

	/**
	 * Method to check whether player loose all his health
	 * 
	 * @param playerInfo
	 * @param virusInfo
	 * @return
	 */
	private boolean isPlayerLost(PlayerInfo playerInfo, VirusInfo virusInfo) {
		return (playerInfo.getHealthStatus() == ZERO & virusInfo.getHealthStatus() > ZERO);
	}

	/**
	 * Method update player health based on the random selection
	 * 
	 * @param playerInfo
	 * @param randomFetch
	 */
	private void updatePlayerHealth(PlayerInfo playerInfo) {
		utils.clearTerminal();
		int health = playerInfo.getHealthStatus();
		int newHealth = utils.getAction().nextInt(health <= 1 ? 1 : health - 1);
		playerInfo.setHealthStatus(newHealth);
		LOG.info("Dr." + playerInfo.getPlayerName() + " you have been attacked by virus");
	}

	/**
	 * Method update virus health based on the random selection
	 * 
	 * @param virusInfo
	 * @param randomFetch
	 */
	private void updateVirusHealth(VirusInfo virusInfo) {
		utils.clearTerminal();
		int health = virusInfo.getHealthStatus();
		int newHealth = utils.getAction().nextInt(health <= 1 ? 1 : health - 1);
		virusInfo.setHealthStatus(newHealth);
		LOG.info("You attacked the virus " + virusInfo.getVirusName());
	}

	/**
	 * Method shows the Instructions & options
	 */
	private void showPlayingInstruction() {
		LOG.info("");
		LOG.info(CHOOSE_OPTION);
		LOG.info("1. Fight with Virus ");
		LOG.info("2. Inject Aspirin to virus");
		LOG.info("3. Show Player Information");
		LOG.info("4. Show Game Status");
		LOG.info("5. Save Game");
		LOG.info("6. Go to Main Menu");

	}

	/**
	 * Method fetch the virusInfo object from virusGenerator class corresponding
	 * to the virus name
	 * 
	 * @param VirusParams
	 * @return VirusInfo
	 */
	private VirusInfo getNextVirus(VirusParams virusParams) {
		
		if (virusParams.getVirusNameListCount() >= virusParams.getVirusNameList().size()) {
			virusParams.setVirusNameListCount(ZERO);
		}
		int count = virusParams.getVirusNameListCount();

		String virusName = virusParams.getVirusNameList().get(virusParams.getVirusNameListCount());
		virusParams.setVirusNameListCount(++count);

		LOG.info("   Alert !!!");
		LOG.info(virusName + " virus attack has been encontered");
		return virusGenerator.getVirusObject(virusName);
	}

	/**
	 * Method check whether Player cleared all the levels
	 * 
	 * @param playerInfo
	 * @return Boolean (TRUE/FALSE)
	 */
	private boolean isGameCompleted(PlayerInfo playerInfo) {
		return (playerInfo.getVirusKilled() >= TEN & playerInfo.getAspirinList().size() >= THREE);
	}

	/**
	 * Method display the Player Information
	 * 
	 * @param playerInfo
	 */
	private void showPlayerInfo(PlayerInfo playerInfo) {
		utils.clearTerminal();
		LOG.info("_________________________________________________________");
		LOG.info("         Dr." + playerInfo.getPlayerName() + " Info");
		LOG.info("   Health         -:" + playerInfo.getHealthStatus());
		LOG.info("   Skill Level    -:" + playerInfo.getSkillLevel());
		LOG.info("   Virus Killed   -:" + playerInfo.getVirusKilled());
		LOG.info("   	Aspirin Details");
		playerInfo.getAspirinList().forEach(aspirin -> {
			LOG.info("   Aspirin Name   -:" + aspirin.getAspirinName()+ (" (Strength :"+aspirin.getStrength()+")"));			
		});
		LOG.info("_________________________________________________________");
	}

	/**
	 * Method display the Game status by displaying player information and virus Information
	 * 
	 * @param playerInfo
	 */
	private void showGameStatus(VirusInfo virusInfo, PlayerInfo playerInfo) {
		utils.clearTerminal();
		LOG.info("_________________________________________________________");
		LOG.info("             * GAME Status *                             ");
		LOG.info("_________________________________________________________");
		LOG.info("         Dr." + playerInfo.getPlayerName() + " Info");
		LOG.info("   Health         -:" + playerInfo.getHealthStatus());
		LOG.info("   Virus Killed   -:" + playerInfo.getVirusKilled());
		LOG.info("   	Aspirin Details");
		playerInfo.getAspirinList().forEach(aspirin -> {
			LOG.info("   Aspirin Name   -:" + aspirin.getAspirinName()+ (" (Strength :"+aspirin.getStrength()+")"));			
		});		
		LOG.info("_________________________________________________________");
		LOG.info("");
		LOG.info("_________________________________________________________");
		LOG.info("   	Virus "+virusInfo.getVirusName()+" Info");
		LOG.info("   Health         -:"+virusInfo.getHealthStatus());
		LOG.info("_________________________________________________________");
	}
	/**
	 * Method read input from the console for player name and create player
	 * information
	 * 
	 * @param consoleInput
	 * @return PlayerInfo
	 */
	private PlayerInfo loadPlayerInfo() {
		LOG.info(CREATE_PLAYER_MESSAGE);
		try {
			Thread.sleep(DELAY);
		} catch (InterruptedException e) {
			LOG.info(THREAD_INTERRUPTED);
		}
		LOG.info("");
		LOG.info(CREATE_PLAYER_NAME);
		String playerName = utils.getConsoleInputAsString();
		
		return buildNewPlayerIformation(playerName);
	}
	
	/**
	 * Method build PlayerInfo with default values and inputs
	 * 
	 * @param name
	 * @return PlayerInfo
	 */
	private PlayerInfo buildNewPlayerIformation(String name) {
		return new PlayerBuilder().setPlayerName(name).setHealthStatus(DEFAULT_HEALTH_STATUS)
				.setSkillLevel(SKILL_JUNIOR_DOCTOR).setVirusKilled(ZERO)
				.setAspirinList(getAspirinList()) 
																				
				.build();
	}

	/**
	 * Method create a list of Aspirin with default Aspirin Object
	 * 
	 * @return List<Aspirin>
	 */
	private List<Aspirin> getAspirinList() {
		List<Aspirin> aspirinList = new ArrayList<>();
		Aspirin aspirin = aspirinGenerator.getAspirinObject(DEFAULT_ASPIRIN);
		if (aspirin != null) {
			aspirinList.add(aspirin);
		}
		return aspirinList;
	}

	/**
	 * Method to show health of both Player and Virus
	 * 
	 * @param playerInfo
	 * @param virusInfo
	 */
	private void showHealth(PlayerInfo playerInfo, VirusInfo virusInfo) {
		LOG.info("");
		LOG.info("Dr." + playerInfo.getPlayerName() + " health : " + playerInfo.getHealthStatus());
		LOG.info(virusInfo.getVirusName() + " health   : " + virusInfo.getHealthStatus());
		LOG.info("");
	}	
	
}
