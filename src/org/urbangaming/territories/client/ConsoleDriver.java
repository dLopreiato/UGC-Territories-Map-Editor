package org.urbangaming.territories.client;
import java.io.*;
import java.util.*;
import org.urbangaming.territories.core.*;

/**
 * This is the class that should be used by the territories admin. This class only allows editing of teams through the
 * console. A map file must already be in existence for this driver to work.
 * @author Andrew Lopreiato
 * @version 1.6 12/8/13
 */
public class ConsoleDriver {

	// CONSTANTS
	public static final String MAP_FILE = "Fall2013.trmp";
	public static final String OUTPUT_MAP = "OutputMap.png";
	// END CONSTANTS
	
	/**
	 * The main running method.
	 * @param args No options available.
	 */
	public static void main(String[] args) {
		// open up the console
		Scanner inputScanner = new Scanner(System.in);
		
		// initialize map
		TerritoriesMap territoriesFallMap = null;
		
		//create list of changing territories
		ArrayList<Territory> changingTerritories = new ArrayList<Territory>();
		
		try {
			territoriesFallMap = TerritoriesMap.Deserialize(MAP_FILE);
			
			System.out.println("The following teams are available: ");
			DisplayTeamList(territoriesFallMap);
			
			PauseUntilConfirmation(inputScanner);
			
			// check for differences
			for (int i = 0; i < territoriesFallMap.GetAmountOfTerritories(); i++) {
				Territory relevantTerritory = territoriesFallMap.GetTerritory(i);
				Team currentOwner = territoriesFallMap.GetTerritoryOwner(relevantTerritory);
				
				// if the saved owner is no longer the current owner
				if (!ReaffirmTeamsOwnership(inputScanner, relevantTerritory, currentOwner)) {
					changingTerritories.add(territoriesFallMap.GetTerritory(i));
				}
			}
			
			// create the differences
			for (int i = 0; i < changingTerritories.size(); i++) {
				SetTerritoryOwner(inputScanner, territoriesFallMap, changingTerritories.get(i));
			}
			
			// draw the map
			System.out.println("Drawing map...");
			territoriesFallMap.Draw(OUTPUT_MAP);
			System.out.println("Map drawn.");
			
			// save the map
			System.out.println("Saving map...");
			territoriesFallMap.Serialize(MAP_FILE);
			System.out.println("Map saved.");
			
		} 
		catch (ClassNotFoundException e) {
			System.out.println("This is not a valid file to read.");
		} 
		catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
		}
		
	} // END main
	
	/**
	 * Will display to the user the teams and their corresponding numbers.
	 * @param teamsList The list to read the teams from.
	 */
	private static void DisplayTeamList (TerritoriesMap tMap) {
		for (int i = 0; i < tMap.GetAmountOfTeams(); i++) {
			System.out.println(i + ": " + (tMap.GetTeam(i).Name));
		}
	} // END DisplayTeamList
	
	/**
	 * Will prompt the user which team they would like to set the given territory to, and then adds that territory to
	 * the team's territory list.
	 * @param input Scanner to read the input.
	 * @param territory Territory to prompt the user about.
	 * @param teamsList The available teams.
	 */
	private static void SetTerritoryOwner(Scanner input, TerritoriesMap tMap, Territory territory) {
		int userInput = -1;
		while (userInput <= -1) {
			System.out.print("What team owns " + territory.Name + "? ");
			try {
				userInput = input.nextInt();
				if (userInput >= tMap.GetAmountOfTeams() || userInput < 0) {
					throw new IOException("Team " + userInput + "does not exist. ");
				}
			} catch( InputMismatchException e) {
				System.out.print("That is not a number, please try again. ");
				input.nextLine();
				userInput = -1;
			} catch ( IOException e) {
				System.out.print(e.getMessage());
				userInput = -1;
			}
			
		}
		tMap.SetTerritoriesTeam(territory, tMap.GetTeam(userInput));
	} // END GetTerritoryOwner
	
	/**
	 * Will prompt the user if the given team still owns the given territory.
	 * @param input Scanner to read the input.
	 * @param territory Territory to prompt the user about.
	 * @param owner Owner to prompt the user with.
	 * @return If the territory still belongs to the team or not.
	 */
	private static Boolean ReaffirmTeamsOwnership(Scanner input, Territory territory, Team owner) {
		System.out.println("Does " + owner.Name + " still own " + territory.Name + "?");
		String answer = null;
		while (answer == null) {
			try {
				answer = input.nextLine();
				answer = answer.toLowerCase();
				if (!(answer.equals("n") || answer.equals("no") || answer.equals("y") || answer.equals("yes") )) {
					throw new IOException("That is not an acceptable answer. Please try again.");
				}
			}
			catch (IOException e) {
				System.out.print(e.getMessage());
				answer = null;
			}
			catch(InputMismatchException e) {
				System.out.print(e.getMessage());
				answer = null;
			}
		}
		if (answer.equals("y") || answer.equals("yes")) {
			return true;
		}
		else {
			return false;
		}
	} // END ReaffirmTeamsOwnership
	
	/**
	 * Will hold and display three dots until the user presses enter.
	 * @param input Scanner to read the input.
	 */
	private static void PauseUntilConfirmation(Scanner input) {
		System.out.print("...");
		input.nextLine();
	} // END PauseUntilConfirmation

} // END MainDriver
