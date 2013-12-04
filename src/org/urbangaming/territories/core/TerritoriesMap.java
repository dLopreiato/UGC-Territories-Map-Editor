package org.urbangaming.territories.core;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
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
 * @version 1.3 12/4/13
 */
public class TerritoriesMap implements Serializable {
	
	// DATA MEMBERS
	private ArrayList<Team> Teams_;
	private ArrayList<Territory> Territories_;
	private ArrayList<ConnectionLine> Connections_;
	private HashMap<Territory, Team> OwnersMap_;
	// END DATA MEMBERS

	/**
	 * Constructs a map with no territories, teams, or ConnectionLines.
	 */
	public TerritoriesMap() {	
		Teams_ = new ArrayList<Team>();
		Territories_ = new ArrayList<Territory>();
		Connections_ = new ArrayList<ConnectionLine>();
		OwnersMap_ = new HashMap<Territory, Team>();
	} // END TerritoriesMap
	
	/**
	 * Adds a territory and with a given team.
	 * @param territory	Relevant territory.
	 * @param team		Owning team.
	 */
	public void AddTerritory(Territory territory, Team team) {
		OwnersMap_.put(territory, team);
		Territories_.add(territory);
	} // END AddTerritory
	
	/**
	 * Adds a team.
	 * @param team	Relevant team.
	 */
	public void AddTeam(Team team) {
		Teams_.add(team);
	} // END AddTeam
	
	/**
	 * Adds a ConnectionLine.
	 * @param cLine	Relevant ConnectionLine.
	 */
	public void AddConnectionLine(ConnectionLine cLine) {
		Connections_.add(cLine);
	} // END AddConnectionLine
	
	/**
	 * Changes a territories owner to a given team.
	 * @param territory	Relevant territory.
	 * @param team		New team.
	 */
	public void SetTerritoriesTeam(Territory territory, Team team) {
		OwnersMap_.put(territory, team);
	} // END SetTerritoriesTeam
	
	/**
	 * Removes a territory.
	 * @param territory	Relevant territory.
	 */
	public void RemoveTerritory(Territory territory) {
		OwnersMap_.remove(territory);
		Territories_.remove(territory);
	} // END RemoveTerritory
	
	/**
	 * Removes a team.
	 * @param team					Relevant team.
	 * @throws TerritoryException	If the given team still owns a territory.
	 */
	public void RemoveTeam(Team team) throws TerritoryException {
		if (OwnersMap_.containsValue(team)) {
			throw new TerritoryException("This team owns territory, and cannot be deleted.");
		}
		Teams_.remove(team);
	} // END RemoveTeam
	
	/**
	 * Removes a ConnectionLine.
	 * @param cLine	Relevant ConnectionLine.
	 */
	public void RemoveConnectionLine(ConnectionLine cLine) {
		Connections_.remove(cLine);
	} // END RemoveConnectionLine
	
	/**
	 * Gives the number of teams.
	 * @return	Aforementioned query result.
	 */
	public int GetAmountOfTeams() {
		return Teams_.size();
	} // END GetAmountOfTeams
	
	/**
	 * Gives the number of territories.
	 * @return	Aforementioned query result.
	 */
	public int GetAmountOfTerritories() {
		return Territories_.size();
	} // END GetAmountOfTerritories
	
	/**
	 * Gives the number of connection lines.
	 * @return	Aforementioned query result.
	 */
	public int GetAmountOfConnectionLines() {
		return Connections_.size();
	} // END GetAmountOfConnectionLines
	
	/**
	 * Gives the team at an index.
	 * @param index	Relevant query index.
	 * @return		Team at said index.
	 */
	public Team GetTeam(int index) {
		return Teams_.get(index);
	} // END GetTeam
	
	/**
	 * Gives the territory at an index.
	 * @param index	Relevant query index.
	 * @return		Territory at said index.
	 */
	public Territory GetTerritory(int index) {
		return Territories_.get(index);
	} // END GetTerritory
	
	/**
	 * Checks if a team exists within the this TerritoriesMap.
	 * @param team	Relevant query team.
	 * @return		Boolean representation of aforementioned logic.
	 */
	public Boolean TeamExists(Team team) {
		return Teams_.contains(team);
	} // END TeamExists
	
	/**
	 * Checks if a territory exists within this TerritoriesMap.
	 * @param territory	Relevant query territory.
	 * @return			Boolean representation of aforementioned logic.
	 */
	public Boolean TerritoryExists(Territory territory) {
		return Territories_.contains(territory);
	} // END TerritoryExists
	
	/**
	 * Checks if a ConnectionLine exists within this TerritoriesMap.
	 * @param cLine	Relevant query ConnectionLine.
	 * @return		Boolean representation of aforementioned logic.
	 */
	public Boolean ConnectionLineExists(ConnectionLine cLine) {
		return Connections_.contains(cLine);
	} // END ConnectionLineExists
	
	/**
	 * Gets the owner of a given territory.
	 * @param territory	Relevant query territory.
	 * @return			Owning team.
	 */
	public Team GetTerritoryOwner(Territory territory) {
		if (OwnersMap_.containsKey(territory)) {
			return OwnersMap_.get(territory);
		} else {
			return null;
		}
	} // END GetTerritoryOwner
	
	/**
	 * Gets the territories of a given team.
	 * @param team	Relevant query team.
	 * @return		List of aforementioned territories.
	 */
	public ArrayList<Territory> GetOwnedTerritories(Team team) {
		ArrayList<Territory> returnValue = new ArrayList<Territory>();
		for (int i = 0; i < Territories_.size(); i++) {
			if (OwnersMap_.get(Territories_.get(i)).equals(team)) {
				returnValue.add(Territories_.get(i));
			}
		}
		return returnValue;
	} // END GetOwnedTerritories
	
	/**
	 * Gives an unbounded territories within this TerritoriesMap.
	 * @return	List of aforementioned territories.
	 */
	public ArrayList<Territory> GetUnboundedTerritories() {
		ArrayList<Territory> returnValue = new ArrayList<Territory>();
		for (int i = 0; i < Territories_.size(); i++) {
			if (!OwnersMap_.containsValue(Territories_.get(i))) {
				returnValue.add(Territories_.get(i));
			}
		}
		return returnValue;
	} // END GetUnboundedTerritories
	
	/**
	 * Checks if there are any unbounded territories in this TerritoriesMap.
	 * @return	Boolean representation of aforementioned logic.
	 */
	public Boolean HasUnboundedTerritories() {
		return !GetUnboundedTerritories().isEmpty();
	} // END HasUnboundedTerritories
	
	/**
	 * Renders this TerritoriesMap on top of a given base map.
	 * @param inputFilename		File name of the base map.
	 * @param outputFilename	File name of the output map.
	 * @throws IOException		If an error occurred in the reading/writing process.
	 */
	public void Draw(String inputFilename, String outputFilename) throws IOException {
		// start a temporary list of ConnectionLines to be used as containers for wrapping circles
		ArrayList<ConnectionLine> wrappingCircles = new ArrayList<ConnectionLine>();
		
		// read in
		BufferedImage storedImage = ImageIO.read(new File(inputFilename));
		Graphics2D imageManipulator = storedImage.createGraphics();
		
		//set antialiasing on to make it look less awful
		imageManipulator.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// create & set the stroke of an outline
		BasicStroke territoryOutline = new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND);
		imageManipulator.setStroke(territoryOutline);
		
		// draw each territory 1 by 1
		for (int i = 0; i < Teams_.size(); i++) {
			Color teamColor = Teams_.get(i).Color;
			ArrayList<Territory> territories = GetOwnedTerritories(Teams_.get(i));
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
		
		// draw the connecting lines
		imageManipulator.setColor(Color.GRAY);
		for (int i = 0; i < Connections_.size(); i++) {
			ConnectionLine currentLine = Connections_.get(i);
			if (currentLine.Wrapping) {
				// find slope of the lines
				final float length = 15;
				double deltaX = (double)(currentLine.X2 - currentLine.X1);
				double deltaY = (double)(currentLine.Y2 - currentLine.Y1);
				
				double reactiveX = (length * deltaX) / Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
				double reactiveY = (length * deltaY) / Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
				
				imageManipulator.drawLine(currentLine.X1, currentLine.Y1,
						currentLine.X1 - (int)reactiveX, currentLine.Y1 - (int)reactiveY);
				imageManipulator.drawLine(currentLine.X2, currentLine.Y2,
						currentLine.X2 + (int)reactiveX, currentLine.Y2 + (int)reactiveY);
				
				wrappingCircles.add(new ConnectionLine(
						currentLine.X1 - (int)reactiveX, currentLine.Y1 - (int)reactiveY,
						currentLine.X2 + (int)reactiveX, currentLine.Y2 + (int)reactiveY));
			} else {
				imageManipulator.drawLine(currentLine.X1, currentLine.Y1, currentLine.X2, currentLine.Y2);
			}
		}
		
		//draw the circular hubs
		for (int i = 0; i < wrappingCircles.size(); i++) {
			ConnectionLine cLine = wrappingCircles.get(i);
			Shape circle1 = new Ellipse2D.Double(cLine.X1 - 5, cLine.Y1 - 5, 10, 10);
			Shape circle2 = new Ellipse2D.Double(cLine.X2 - 5, cLine.Y2 - 5, 10, 10);
			
			// fill circles
			imageManipulator.setColor(Color.getHSBColor(((float)i/wrappingCircles.size()), 1.0f, 1.0f));
			imageManipulator.fill(circle1);
			imageManipulator.fill(circle2);
			
			// draw circles		
			imageManipulator.setColor(Color.GRAY);
			imageManipulator.draw(circle1);
			imageManipulator.draw(circle2);
		}
		
		// render
		File outputFile = new File(outputFilename);
		ImageIO.write(storedImage, "png", outputFile);
	} // END Draw
	
	
	/**
	 * Serializes this map to a specified file.
	 * @param outputFilename	Name of the output file.
	 * @throws IOException		If an error occurred in the reading/writing process.
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
	 * @param inputFilename				Name of the input file.
	 * @return							The object read in.
	 * @throws IOException				If an error occurred in the reading/writing process.
	 * @throws ClassNotFoundException	If no definition of this class has been found in the file.
	 */
	public static TerritoriesMap Deserialize(String inputFilename) throws IOException, ClassNotFoundException {
		FileInputStream fileIn = new FileInputStream(inputFilename);
		ObjectInputStream in = new ObjectInputStream(fileIn);
		TerritoriesMap returnValue = (TerritoriesMap) in.readObject();
		in.close();
		fileIn.close();
		return returnValue;
	} // END Deserialize
	
	/** Serialization version as of last update **/
	private static final long serialVersionUID = 3L;
}
