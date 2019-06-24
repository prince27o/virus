package com.rpg.virus.game;

import static com.rpg.virus.constants.VirusConstants.HUNDRED;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.rpg.virus.BaseTest;
import com.rpg.virus.databuilder.AspirinGenerator;
import com.rpg.virus.databuilder.VirusGenerator;
import com.rpg.virus.util.CommonUtils;

@RunWith(MockitoJUnitRunner.class)
public class VirusGameRunnerImplTest extends BaseTest {
	@Mock
	CommonUtils utils;
	@Mock
	AspirinGenerator aspirinGenerator;
	@Mock
	VirusGenerator virusGenerator;
	@Mock
	SaveAndLoadGame saveAndLoadGame;

	@InjectMocks
	VirusGameRunnerImpl gameRunnerImpl;

	@Test
	public void testStartNewGameAndWonTheGame() {

		when(utils.getConsoleInputAsString()).thenReturn("testUser");
		when(utils.getConsoleInput()).thenReturn(1);
		when(virusGenerator.getVirusNameSet()).thenReturn(getVirusNameList());
		when(virusGenerator.getVirusObject(anyString())).thenReturn(getVirusInfo());

		when(aspirinGenerator.getAspirinNameSet()).thenReturn(getAspirinNameSet());

		when(aspirinGenerator.getAspirinObject(anyObject())).thenReturn(getTestAspirin2()).thenReturn(getTestAspirin3())
				.thenReturn(getTestAspirin2());

		Random random = Mockito.mock(Random.class);
		when(utils.getAction()).thenReturn(random);
		when(random.nextInt(HUNDRED)).thenReturn(60);
		doNothing().when(utils).pressOneToContinue();
		doNothing().when(utils).clearTerminal();
		String result = gameRunnerImpl.startNewGame();

		assertEquals(result, "mainmenu");
	}
	
	@Test
	public void testLoadSavedGameAndWonTheGame() {
			
		when(saveAndLoadGame.loadSavedGame()).thenReturn(getSaveGames().getSavedGamesMap());
		when(utils.getConsoleInput()).thenReturn(1);
		when(virusGenerator.getVirusObject(anyString())).thenReturn(getVirusInfo());

		when(aspirinGenerator.getAspirinNameSet()).thenReturn(getAspirinNameSet());

		when(aspirinGenerator.getAspirinObject(anyObject())).thenReturn(getTestAspirin2()).thenReturn(getTestAspirin3())
				.thenReturn(getTestAspirin2());

		Random random = Mockito.mock(Random.class);
		when(utils.getAction()).thenReturn(random);
		when(random.nextInt(HUNDRED)).thenReturn(60);
		doNothing().when(utils).pressOneToContinue();
		doNothing().when(utils).clearTerminal();
		String result = gameRunnerImpl.loadSavedGame();

		Mockito.verify(saveAndLoadGame,  Mockito.times(1)).loadSavedGame();
		assertEquals(result, "mainmenu");
	}
	
	@Test
	public void testLoadSavedGameWhenNoGameIsSaved() {
			
		when(saveAndLoadGame.loadSavedGame()).thenReturn(null);

		Random random = Mockito.mock(Random.class);
		String result = gameRunnerImpl.loadSavedGame();

		Mockito.verify(saveAndLoadGame,  Mockito.times(1)).loadSavedGame();
		assertEquals(result, "mainmenu");
	}


	@Test
	public void testGameWithOptionInjectAspirin() {

		when(utils.getConsoleInputAsString()).thenReturn("testUser");
		when(utils.getConsoleInput()).thenReturn(2).thenReturn(1).thenReturn(1);
		when(virusGenerator.getVirusNameSet()).thenReturn(getVirusNameList());
		when(virusGenerator.getVirusObject(anyString())).thenReturn(getVirusInfo());

		when(aspirinGenerator.getAspirinNameSet()).thenReturn(getAspirinNameSet());

		when(aspirinGenerator.getAspirinObject(anyString())).thenReturn(getTestAspirin1());

		Random random = Mockito.mock(Random.class);
		when(utils.getAction()).thenReturn(random);
		when(random.nextInt(HUNDRED)).thenReturn(60);
		doNothing().when(utils).pressOneToContinue();

		doNothing().when(utils).clearTerminal();
		String result = gameRunnerImpl.startNewGame();

		assertEquals(result, "mainmenu");
	}

	@Test
	public void testGameWithOptionInjectAspirinAndUpdateAspirin() {

		when(utils.getConsoleInputAsString()).thenReturn("testUser");
		when(utils.getConsoleInput()).thenReturn(2).thenReturn(1).thenReturn(2).thenReturn(1).thenReturn(1);
		when(virusGenerator.getVirusNameSet()).thenReturn(getVirusNameList());
		when(virusGenerator.getVirusObject(anyString())).thenReturn(getVirusInfo());
		when(utils.isNotEmpty(any())).thenReturn(Boolean.FALSE).thenReturn(Boolean.TRUE).thenReturn(Boolean.FALSE);
		when(aspirinGenerator.getAspirinNameSet()).thenReturn(getAspirinNameSet());

		when(aspirinGenerator.getAspirinObject(anyString())).thenReturn(getTestAspirin1());

		Random random = Mockito.mock(Random.class);
		when(utils.getAction()).thenReturn(random);
		when(random.nextInt(HUNDRED)).thenReturn(60);
		doNothing().when(utils).pressOneToContinue();

		doNothing().when(utils).clearTerminal();
		String result = gameRunnerImpl.startNewGame();

		assertEquals(result, "mainmenu");
	}

	@Test
	public void testGameWithOptionShowPlayerInormationAndGoToMainMenu() {

		when(utils.getConsoleInputAsString()).thenReturn("testUser");
		when(utils.getConsoleInput()).thenReturn(3).thenReturn(6);
		when(virusGenerator.getVirusNameSet()).thenReturn(getVirusNameList());
		when(virusGenerator.getVirusObject(anyString())).thenReturn(getVirusInfo());
		when(aspirinGenerator.getAspirinObject(anyString())).thenReturn(getTestAspirin1());

		doNothing().when(utils).pressOneToContinue();
		doNothing().when(utils).clearTerminal();

		String result = gameRunnerImpl.startNewGame();

		assertEquals(result, "mainmenu");
	}

	@Test
	public void testGameWithOptionSaveAndGoToMainMenu() {

		when(utils.getConsoleInputAsString()).thenReturn("testUser");
		when(utils.getConsoleInput()).thenReturn(5).thenReturn(1);
		when(virusGenerator.getVirusNameSet()).thenReturn(getVirusNameList());
		when(virusGenerator.getVirusObject(anyString())).thenReturn(getVirusInfo());

		when(aspirinGenerator.getAspirinNameSet()).thenReturn(getAspirinNameSet());

		when(aspirinGenerator.getAspirinObject(anyString())).thenReturn(getTestAspirin1());

		Random random = Mockito.mock(Random.class);
		when(utils.getAction()).thenReturn(random);
		when(random.nextInt(HUNDRED)).thenReturn(60);
		
		
		doNothing().when(utils).pressOneToContinue();
		doNothing().when(saveAndLoadGame).saveGame(anyObject(), anyObject());
		doNothing().when(utils).clearTerminal();
		String result = gameRunnerImpl.startNewGame();
		
		Mockito.verify(saveAndLoadGame,  Mockito.times(1)).saveGame(anyObject(), anyObject());
		assertEquals(result, "mainmenu");
	}

	@Test
	public void testGameWithOptionSaveAndGoToContinue() {

		when(utils.getConsoleInputAsString()).thenReturn("testUser");
		when(utils.getConsoleInput()).thenReturn(5).thenReturn(2).thenReturn(1);
		when(virusGenerator.getVirusNameSet()).thenReturn(getVirusNameList());
		when(virusGenerator.getVirusObject(anyString())).thenReturn(getVirusInfo());
		when(aspirinGenerator.getAspirinObject(anyString())).thenReturn(getTestAspirin1());

		doNothing().when(utils).pressOneToContinue();
		doNothing().when(saveAndLoadGame).saveGame(anyObject(), anyObject());
		doNothing().when(utils).clearTerminal();
		String result = gameRunnerImpl.startNewGame();
		
		Mockito.verify(saveAndLoadGame,  Mockito.times(1)).saveGame(anyObject(), anyObject());
		assertEquals(result, "mainmenu");
	}

}
