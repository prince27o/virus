package com.rpg.virus.game;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.rpg.virus.BaseTest;
import com.rpg.virus.util.CommonUtils;

@RunWith(MockitoJUnitRunner.class)
public class VirusPlayGameImplTest extends BaseTest{

	@Mock
	private CommonUtils utils;	
	@Mock
	private VirusGameRunnerImpl gameRunner;
	
	@InjectMocks
	VirusPlayGameImpl playGameVirusImpl;

	/**
	 * Test verifies when PlayGame method called, then clearTerminal method
	 * should execute 4 times and gameRunner.startNewGame() should call
	 * once when MainMenu and Game option is 1
	 */
	@Test
	public void testToVerifyChoiceOnePlayGameAndStartGameIsExecuted() {
		when(utils.getConsoleInput()).thenReturn(1);
		doNothing().when(utils).clearTerminal();
		when(gameRunner.startNewGame()).thenReturn(null);

		playGameVirusImpl.playGame();
		Mockito.verify(gameRunner, Mockito.times(1)).startNewGame();
		Mockito.verify(utils, Mockito.times(5)).clearTerminal();
	}

	/**
	 * Test verifies when PlayGame method called, then clearTerminal method should execute once, when MainMenu is 2
	 */
	@Test
	public void testToVerifyChoiceTwoAboutGameIsExecuted() {
		when(utils.getConsoleInput()).thenReturn(2).thenReturn(3);
		doNothing().when(utils).clearTerminal();
		
		playGameVirusImpl.playGame();
		Mockito.verify(utils, Mockito.times(5)).clearTerminal();
	}
	
	/**
	 * Test verifies when PlayGame method called, then clearTerminal method should execute once, when option MainMenu is 3
	 */
	@Test
	public void testToVerifyChoiceThreeExitGameIsExecuted() {
		when(utils.getConsoleInput()).thenReturn(3);
		doNothing().when(utils).clearTerminal();

		playGameVirusImpl.playGame();
		Mockito.verify(utils, Mockito.times(2)).clearTerminal();
	}
	/**
	 * Test verifies when PlayGame method called, then clearTerminal method
	 * should execute 3 times and gameRunner.loadSavedGame() should call
	 * once when MainMenu is 1 and Game option is 2
	 */
	@Test
	public void testToVerifyChoiceOnePlayGameAndLoadGameIsExecuted() {
		when(utils.getConsoleInput()).thenReturn(1).thenReturn(2);
		doNothing().when(utils).clearTerminal();
		
		playGameVirusImpl.playGame();
		Mockito.verify(gameRunner, Mockito.times(1)).loadSavedGame();
		Mockito.verify(utils, Mockito.times(4)).clearTerminal();
	}
	
	/**
	 * Test verifies when PlayGame method called, then clearTerminal method
	 * should execute 3 times and gameRunner.loadSavedGame() should call
	 * once when MainMenu is 1 and Game option is 3
	 */
	@Test
	public void testToVerifyChoiceOnePlayGameAndMainMenuIsExecuted() {
		when(utils.getConsoleInput()).thenReturn(1).thenReturn(3);
		doNothing().when(utils).clearTerminal();
		
		playGameVirusImpl.playGame();
		Mockito.verify(utils, Mockito.times(1)).showExitMessage();
		Mockito.verify(utils, Mockito.times(6)).clearTerminal();
	}
}
