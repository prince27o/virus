package com.rpg.virus;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.mockito.MockitoAnnotations;

import com.rpg.virus.pojo.Aspirin;
import com.rpg.virus.pojo.GameData;
import com.rpg.virus.pojo.PlayerInfo;
import com.rpg.virus.pojo.SaveGames;
import com.rpg.virus.pojo.VirusInfo;
import com.rpg.virus.pojo.VirusParams;

public class BaseTest {

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * Test data for PlayerInfo
	 * 
	 * @return PlayerInfo
	 */
	public PlayerInfo getPlayerInfo() {
		Aspirin aspirin = getAspirin();
		List<Aspirin> aspirinList = new ArrayList<>();
		aspirinList.add(aspirin);
		PlayerInfo info = new PlayerInfo("testUser", 100, "Junior Doctor", 0, aspirinList);
		return info;
	}

	/**
	 * Test data for Aspirin
	 * 
	 * @return Aspirin
	 */
	public Aspirin getAspirin() {
		Aspirin aspirin = new Aspirin();
		aspirin.setAspirinName("testAspirin");
		aspirin.setLevel(3);
		aspirin.setStrength(10);

		return aspirin;
	}

	/**
	 * Test data for List Aspirin
	 * 
	 * @return Aspirin
	 */
	public List<Aspirin> getAspirinList() {
		List<Aspirin> asprinList = new ArrayList();

		asprinList.add(getTestAspirin1());
		asprinList.add(getTestAspirin3());
		asprinList.add(getTestAspirin2());
		return asprinList;
	}

	/**
	 * Test data for Aspirin1
	 * 
	 * @return Aspirin
	 */
	public Aspirin getTestAspirin1() {
		Aspirin aspirin1 = new Aspirin();
		aspirin1.setAspirinName("testAspirin1");
		aspirin1.setLevel(1);
		aspirin1.setStrength(20);
		return aspirin1;
	}

	/**
	 * Test data for Aspirin2
	 * 
	 * @return Aspirin
	 */
	public Aspirin getTestAspirin2() {
		Aspirin aspirin2 = new Aspirin();
		aspirin2.setAspirinName("testAspirin3");
		aspirin2.setLevel(3);
		aspirin2.setStrength(40);
		return aspirin2;
	}

	/**
	 * Test data for Aspirin3
	 * 
	 * @return Aspirin
	 */
	public Aspirin getTestAspirin3() {
		Aspirin aspirin3 = new Aspirin();
		aspirin3.setAspirinName("testAspirin3");
		aspirin3.setLevel(7);
		aspirin3.setStrength(60);
		return aspirin3;
	}

	/**
	 * Test data for VirusInfo
	 * 
	 * @return VirusInfo
	 */
	public VirusInfo getVirusInfo() {
		VirusInfo virusInfo = new VirusInfo();
		virusInfo.setHealthStatus(100);
		virusInfo.setStrength(20);
		virusInfo.setVirusName("testVirus");
		return virusInfo;

	}

	/**
	 * Test data for GameData
	 * 
	 * @return GameData
	 */
	public GameData getGameData() {
		GameData gameData = new GameData();
		gameData.setPlayerInfo(getPlayerInfo());
		gameData.setVirusInfo(getVirusInfo());
		gameData.setVirusParams(getVirusParams());
		gameData.setSavedDate(new Date());
		return gameData;

	}

	/**
	 * Test data for VirusParams
	 * 
	 * @return VirusParams
	 */
	public VirusParams getVirusParams() {
		List<String> virusNameList = new ArrayList<>();
		virusNameList.add("testVirus");
		VirusParams virusParams = new VirusParams(Boolean.FALSE, Boolean.TRUE, 1, virusNameList);
		return virusParams;
	}

	/**
	 * Test data for SaveGames
	 * 
	 * @return SaveGames
	 */
	public SaveGames getSaveGames() {
		SaveGames saveGames = new SaveGames();
		Map<String, GameData> savedGamesMap = new HashMap<>();
		savedGamesMap.put("1_testFile", getGameData());
		saveGames.setSavedGamesMap(savedGamesMap);
		return saveGames;
	}

	/**
	 * Test data for maximum save of SaveGames
	 * 
	 * @return SaveGames
	 */
	public SaveGames getSaveGamesWithMaximumSaves() {
		SaveGames saveGames = new SaveGames();
		Map<String, GameData> savedGamesMap = new LinkedHashMap<>();
		savedGamesMap.put("1_testFile", getGameData());
		savedGamesMap.put("2_testFile", getGameData());
		savedGamesMap.put("3_testFile", getGameData());
		savedGamesMap.put("4_testFile", getGameData());
		savedGamesMap.put("5_testFile", getGameData());
		savedGamesMap.put("6_testFile", getGameData());
		savedGamesMap.put("7_testFile", getGameData());
		savedGamesMap.put("8_testFile", getGameData());
		savedGamesMap.put("9_testFile", getGameData());
		savedGamesMap.put("10_testFile", getGameData());
		saveGames.setSavedGamesMap(savedGamesMap);
		return saveGames;
	}

	/**
	 * Test data for virusNameSet
	 * 
	 * @return Set<String>
	 */
	public Set<String> getVirusNameList() {
		Set<String> virusNameSet = new HashSet<>();
		virusNameSet.add("testVirus");
		return virusNameSet;
	}

	/**
	 * Test data for virusNameSet
	 * 
	 * @return Set<String>
	 */
	public Set<String> getAspirinNameSet() {
		Set<String> apririnNameSet = new HashSet<>();
		apririnNameSet.add("testAspirin1");
		apririnNameSet.add("testAspirin2");
		apririnNameSet.add("testAspirin3");
		return apririnNameSet;
	}

}
