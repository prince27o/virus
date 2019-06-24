package com.rpg.virus.util;

import static com.rpg.virus.constants.VirusConstants.COMMA_WITH_SINGLESPACE;
import static com.rpg.virus.constants.VirusConstants.INVALID_CHOICE_MESSAGE;
import static com.rpg.virus.constants.VirusConstants.PROPERTIES_FILE;
import static com.rpg.virus.constants.VirusConstants.GAME_ERROR_MESSAGE;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import com.rpg.virus.log.Logger;

public class CommonUtils {
	
	Logger LOG = new Logger();
	private static Scanner consoleInput = null;
	private Random randomFetch = new Random(); 
	
	public Random getAction(){
		return randomFetch;
	}
	/*
	 * Method clears the console by running either the 'cls' (Windows CMD) or
	 * 'clear' (Other terminals) command. If any interrupt occur, the terminal
	 * will be brute-force cleared
	 */
	public void clearTerminal() {
		 try {
		 if (System.getProperty("os.name").contains("Windows"))
		 new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		 else
		 Runtime.getRuntime().exec("clear");
		 } catch (IOException | InterruptedException exception) {
		 for (int i = 1; i < 50; i++)
		 System.out.println("/n");
		 }
	}

	/**
	 * Method to create Scanner object for reading console
	 * 
	 * @return Scanner
	 */
	public int getConsoleInput() {
		int choice = 0;
		while(choice == 0){
			try{
			choice = getConsoleInputScanner().nextInt();
		} catch (NoSuchElementException e) {
			LOG.info(INVALID_CHOICE_MESSAGE);
			try{
			getConsoleInputScanner().nextLine();
			} catch (NoSuchElementException ex) {
				LOG.info(GAME_ERROR_MESSAGE);
				System.exit(0);
			}
			
		}
		}
		return choice;
	}
	/**
	 * Method to create Scanner object for reading console
	 * 
	 * @return Scanner
	 */
	public String getConsoleInputAsString() {
		return getConsoleInputScanner().next();
	}
	
	public Scanner getConsoleInputScanner() {
		if (consoleInput == null) {
			consoleInput = new Scanner(System.in);
		}
		return consoleInput;
	}


	/**
	 * Method to save object to file
	 * 
	 * @param object
	 * @param filepath
	 * @return Boolean (True/False)
	 */
	public  boolean saveToFile(Object object, String filepath) {
		try (OutputStream file = new FileOutputStream(filepath);
				OutputStream buffer = new BufferedOutputStream(file);
				ObjectOutput output = new ObjectOutputStream(buffer);) {
			output.writeObject(object);
			return Boolean.TRUE;
		} catch (IOException ex) {
			return Boolean.FALSE;
		}
	}

	/**
	 * Method to read object from file
	 * 
	 * @param object
	 * @param filepath
	 * @return Object
	 */
	public Object readFile(String filepath) {
		Object object = null;
		try (FileInputStream file = new FileInputStream(filepath);
				ObjectInputStream input = new ObjectInputStream(file);) {
			return input.readObject();
		} catch (ClassNotFoundException | IOException ex) {
		}
		return object;
	}

	/**
	 * Method read data from file and covert to Object after deserializing
	 * 
	 * @param filepath
	 * @return Object
	 */
	public Object readDataFromFile(String filepath) {
		Object object = null;
		try (InputStream buffer = new BufferedInputStream(getClass().getClassLoader().getResourceAsStream(filepath));
				ObjectInput input = new ObjectInputStream(buffer);) {
			return input.readObject();
		} catch (ClassNotFoundException | IOException ex) {

		}
		return object;
	}

	/**
	 * Method load's comma seperated string values given in the properties file
	 * 
	 * @return
	 */
	public Set<String> loadProperties(String property) {
		Set<String> propertySet = new HashSet<>();
			try {
			Properties properties = new Properties();

			properties.load(CommonUtils.class.getResourceAsStream(PROPERTIES_FILE));
			String propertyStringWithComma = properties.getProperty(property);
			if (propertyStringWithComma != null) {
				String[] propertyStringArray = propertyStringWithComma.split(COMMA_WITH_SINGLESPACE);
				if (propertyStringArray != null && propertyStringArray.length > 0) {
					propertySet = Arrays.stream(propertyStringArray).collect(Collectors.toSet());
				}
			}

		} catch (Exception e) {
		}
		return propertySet;
	}

	public void pressOneToContinue() {
		String inputNumber;
		do {
			LOG.info("Press 1 to continue");
			inputNumber = getConsoleInputAsString();
		} while (!"1".equals(inputNumber));
	}
	
	
	

	/**
	 * Method logs the exit message
	 */
	public void showExitMessage() {
		clearTerminal();
		LOG.info("*********************************************");
		LOG.info("**************  Good Bye  *******************");
		LOG.info("*********************************************");
	}

	/**
	 * Method to check whether List is not null or empty
	 * 
	 * @param arraylist
	 * @return
	 */
	public boolean isNotEmpty(List<?> arraylist) {
		if (arraylist != null && arraylist.size() > 0) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}		
}
