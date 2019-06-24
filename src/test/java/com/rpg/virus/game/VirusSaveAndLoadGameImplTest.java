package com.rpg.virus.game;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.rpg.virus.BaseTest;
import com.rpg.virus.pojo.GameData;
import com.rpg.virus.pojo.SaveGames;
import com.rpg.virus.util.CommonUtils;

@RunWith(MockitoJUnitRunner.class)
public class VirusSaveAndLoadGameImplTest extends BaseTest {
	
	@Mock
	CommonUtils utils;
	
	@InjectMocks
	VirusSaveAndLoadGameImpl virusSaveAndLoadGameImpl; 

	
	
	@Test
	public void testSaveGameForFirstTime(){		
		GameData gameData = getGameData(); 
		SaveGames saveGames = new SaveGames();		
		virusSaveAndLoadGameImpl.setSaveGames(saveGames);		
		virusSaveAndLoadGameImpl.saveGame(gameData, "testFile");
		Mockito.verify(utils, Mockito.times(1)).saveToFile(any(), anyString());
		
	}
	
	@Test
	public void testSaveGameWhenLoadedGameGotSaved(){		
		GameData gameData = getGameData(); 
		SaveGames saveGames = getSaveGames();		
		virusSaveAndLoadGameImpl.setSaveGames(saveGames);		
		virusSaveAndLoadGameImpl.saveGame(gameData, "1_testFile");
		Mockito.verify(utils, Mockito.times(1)).saveToFile(any(), anyString());		
	}
	
	@Test
	public void testSaveGameWhenReachTheLimitOfSaved(){		
		GameData gameData = getGameData(); 
		SaveGames saveGames = getSaveGamesWithMaximumSaves();		
		virusSaveAndLoadGameImpl.setSaveGames(saveGames);		
		when(utils.getConsoleInput()).thenReturn(1);
		virusSaveAndLoadGameImpl.saveGame(gameData, "11_testFile");
		Mockito.verify(utils, Mockito.times(1)).saveToFile(any(), anyString());		
	}
	
	@Test
	public void testLoadSavedGameWhenSavedGamesMapIsEmpty(){	
		SaveGames saveGames = new SaveGames();
		virusSaveAndLoadGameImpl.setSaveGames(saveGames);
		Map<String,GameData> resultMap = virusSaveAndLoadGameImpl.loadSavedGame();
		assertNull(resultMap);
	}
	
	
	@Test
	public void testFirstSaveAndThenLoadGameWhenSavedGamesMapIsMaximum(){	
		SaveGames saveGames = getSaveGamesWithMaximumSaves();
		virusSaveAndLoadGameImpl.setSaveGames(saveGames);
		virusSaveAndLoadGameImpl.setSaveGames(saveGames);		
		when(utils.getConsoleInput()).thenReturn(7);
		virusSaveAndLoadGameImpl.saveGame(getGameData(), "11_testFile");
		Map<String,GameData> resultMap = virusSaveAndLoadGameImpl.loadSavedGame();
		assertTrue(resultMap.keySet().contains("7_testUser"));
	}
}
