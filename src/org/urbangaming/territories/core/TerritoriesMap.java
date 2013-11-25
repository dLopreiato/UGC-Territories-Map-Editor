package org.urbangaming.territories.core;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.Serializable;

import javax.imageio.ImageIO;

/**
 * The object representing the state and behavior of map for territories. This includes the list of territories, teams,
 * and connections between territories. Serialization has also been written into the class, making reading and writing
 * to disk easy.
 * @author Andrew Lopreiato
 * @version 1.1 11/24/13
 */
public class TerritoriesMap implements Serializable {
	
	// DATA MEMBERS
	private ArrayList<Team> Teams;
	private ArrayList<Territory> Territories;
	private ArrayList<ConnectionLine> Connections;
	private HashMap<Territory, Team> OwnersMap;
	// END DATA MEMBERS

	/**
	 * Constructs a map with no territories, teams, or connections.
	 */
	public TerritoriesMap() {	
		Teams = new ArrayList<Team>();
		Territories = new ArrayList<Territory>();
		Connections = new ArrayList<ConnectionLine>();
		OwnersMap = new HashMap<Territory, Team>();
	} // END TerritoriesMap
	
	/**
	 * Adds a territory and with a given team.
	 * @param territory
	 * @param team
	 */
	public void AddTerritory(Territory territory, Team team) {
		OwnersMap.put(territory, team);
		Territories.add(territory);
	}
	
	/**
	 * Adds a team.
	 * @param team
	 */
	public void AddTeam(Team team) {
		Teams.add(team);
	}
	
	/**
	 * Adds a connection line.
	 * @param cLine
	 */
	public void AddConnectionLine(ConnectionLine cLine) {
		Connections.add(cLine);
	}
	
	/**
	 * Changes a territories owner to a given team.
	 * @param territory
	 * @param team
	 */
	public void SetTerritoriesTeam(Territory territory, Team team) {
		OwnersMap.put(territory, team);
	}
	
	/**
	 * Removes a territory.
	 * @param territory
	 */
	public void RemoveTerritory(Territory territory) {
		OwnersMap.remove(territory);
		Territories.remove(territory);
	}
	
	/**
	 * Removes a team. Will throw an exception if this team owns a territory.
	 * @param team
	 * @throws TerritoryException
	 */
	public void RemoveTeam(Team team) throws TerritoryException {
		if (OwnersMap.containsValue(team)) {
			throw new TerritoryException("This team owns territory, and cannot be deleted.");
		}
		Teams.remove(team);
	}
	
	/**
	 * Removes a connection line.
	 * @param cLine
	 */
	public void RemoveConnectionLine(ConnectionLine cLine) {
		Connections.remove(cLine);
	}
	
	/**
	 * Gives the number of teams.
	 * @return
	 */
	public int GetAmountOfTeams() {
		return Teams.size();
	}
	
	/**
	 * Gives the number of territories.
	 * @return
	 */
	public int GetAmountOfTerritories() {
		return Territories.size();
	}
	
	/**
	 * Gives the number of connection lines.
	 * @return
	 */
	public int GetAmountOfConnectionLines() {
		return Connections.size();
	}
	
	/**
	 * Gives the team at an index.
	 * @param index
	 * @return
	 */
	public Team GetTeam(int index) {
		return Teams.get(index);
	}
	
	/**
	 * Gives the territory at an index.
	 * @param index
	 * @return
	 */
	public Territory GetTerritory(int index) {
		return Territories.get(index);
	}
	
	/**
	 * Checks if a team exists within the this territories map.
	 * @param team
	 * @return
	 */
	public Boolean TeamExists(Team team) {
		return Teams.contains(team);
	}
	
	/**
	 * Checks if a territory exists within this territories map.
	 * @param territory
	 * @return
	 */
	public Boolean TerritoryExists(Territory territory) {
		return Territories.contains(territory);
	}
	
	/**
	 * Checks if a connection line exists within this territories map.
	 * @param cLine
	 * @return
	 */
	public Boolean ConnectionLineExists(ConnectionLine cLine) {
		return Connections.contains(cLine);
	}
	
	/**
	 * Gets the owner of a given territory.
	 * @param territory
	 * @return
	 */
	public Team GetTerritoryOwner(Territory territory) {
		if (OwnersMap.containsKey(territory)) {
			return OwnersMap.get(territory);
		} else {
			return null;
		}
	}
	
	/**
	 * Gets the territories of a given team.
	 * @param team
	 * @return
	 */
	public ArrayList<Territory> GetOwnedTerritories(Team team) {
		ArrayList<Territory> returnValue = new ArrayList<Territory>();
		for (int i = 0; i < Territories.size(); i++) {
			if (OwnersMap.get(Territories.get(i)).equals(team)) {
				returnValue.add(Territories.get(i));
			}
		}
		return returnValue;
	}
	
	/**
	 * Gives an unbounded territories within this territories map.
	 * @return
	 */
	public ArrayList<Territory> GetUnboundedTerritories() {
		ArrayList<Territory> returnValue = new ArrayList<Territory>();
		for (int i = 0; i < Territories.size(); i++) {
			if (!OwnersMap.containsValue(Territories.get(i))) {
				returnValue.add(Territories.get(i));
			}
		}
		return returnValue;
	}
	
	/**
	 * Checks if there are any unbounded territories in this territories map.
	 * @return
	 */
	public Boolean HasUnboundedTerritories() {
		return !GetUnboundedTerritories().isEmpty();
	}
	
	/**
	 * Renders this territories map on top of a given base map.
	 * @param inputFilename		file name of the base map.
	 * @param outputFilename	file name of the output map.
	 * @throws IOException		if an error occurred in the reading/writing process.
	 */
	public void Draw(String inputFilename, String outputFilename) throws IOException {
		// read in
		BufferedImage storedImage = ImageIO.read(new File(inputFilename));
		Graphics2D imageManipulator = storedImage.createGraphics();
		
		//set antialiasing on to make it look less awful
		imageManipulator.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// create & set the stroke of an outline
		BasicStroke territoryOutline = new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND);
		imageManipulator.setStroke(territoryOutline);
		
		// draw each territory 1 by 1
		for (int i = 0; i < Teams.size(); i++) {
			Color teamColor = Teams.get(i).Color;
			ArrayList<Territory> territories = GetOwnedTerritories(Teams.get(i));
			for (int j = 0; j < territories.size(); j++) {
				Polygon currentRegion = territories.get(j).Region;
				// first the region
				imageManipulator.setColor(teamColor);
				imageManipulator.fillPolygon(currentRegion);
				
				// then the outline
				imageManipulator.setColor(Color.BLACK);
				imageManipulator.drawPolygon(currentRegion);
			}
		}
		
		// draw connections last
		imageManipulator.setColor(Color.GRAY);
		for (int i = 0; i < Connections.size(); i++) {
			ConnectionLine currentLine = Connections.get(i);
			imageManipulator.drawLine(currentLine.X1, currentLine.Y1, currentLine.X2, currentLine.Y2);
		}
		
		// render
		File outputFile = new File(outputFilename);
		ImageIO.write(storedImage, "png", outputFile);
	} // END Draw
	
	
	/**
	 * Serializes this map to a specified file.
	 * @param outputFilename	name of the output file.
	 * @throws IOException		if an error occurred in the reading/writing process.
	 */
	public void Serialize(String outputFilename) throws IOException {
		FileOutputStream fileOut = new FileOutputStream(outputFilename);
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(this);
		out.close();
		fileOut.close();
	} // END Serialize
	
	/**
	 * Deserializes and returns a TerritoriesMap object of the specified file.
	 * @param inputFilename				name of the input file.
	 * @return							the object read in.
	 * @throws IOException				if an error occurred in the reading/writing process.
	 * @throws ClassNotFoundException	if no definition of this class has been found in the file.
	 */
	public static TerritoriesMap Deserialize(String inputFilename) throws IOException, ClassNotFoundException {
		FileInputStream fileIn = new FileInputStream(inputFilename);
		ObjectInputStream in = new ObjectInputStream(fileIn);
		TerritoriesMap returnValue = (TerritoriesMap) in.readObject();
		in.close();
		fileIn.close();
		return returnValue;
	} // END Deserialize
	
	/** Serialization version as of 11/24/2013 **/
	private static final long serialVersionUID = 2L;
}
