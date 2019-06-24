package com.rpg.virus;

import com.rpg.virus.databuilder.AspirinGenerator;
import com.rpg.virus.databuilder.VirusGenerator;
import com.rpg.virus.game.VirusGameRunnerImpl;
import com.rpg.virus.game.PlayGame;
import com.rpg.virus.game.VirusPlayGameImpl;
import com.rpg.virus.game.SaveAndLoadGame;
import com.rpg.virus.game.VirusSaveAndLoadGameImpl;
import com.rpg.virus.util.CommonUtils;

/**
 * This Class is the starting point of the game. Contains the main method.
 */
public class Virus {

	public static void main(String args[]) {
	
			loadTheGameVirus().playGame();
		
	}

	/**
	 * Method inject all the dependencies and return PlayVirus object
	 * 
	 * @return PlayVirus
	 */
	private static PlayGame loadTheGameVirus() {
		final CommonUtils utils = new CommonUtils();
		final AspirinGenerator aspirinGenerator = new AspirinGenerator(utils);
		final VirusGenerator virusGenerator = new VirusGenerator(utils);
		final SaveAndLoadGame saveAndLoadGame = new VirusSaveAndLoadGameImpl(utils);
		return new VirusPlayGameImpl(utils, new VirusGameRunnerImpl(utils, aspirinGenerator, virusGenerator, saveAndLoadGame));
	}
}
