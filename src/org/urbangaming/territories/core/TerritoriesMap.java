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
import java.io.Serializable;

import javax.imageio.ImageIO;

/**
 * The object representing the state and behavior of map for territories. This includes the list of territories, teams,
 * and connections between territories. Serialization has also been written into the class, making reading and writing
 * to disk easy.
 * @author Andrew Lopreiato
 * @version 1.0 10/16/13
 */
public class TerritoriesMap implements Serializable{
	
	// DATA MEMBERS
	private ArrayList<Team> Teams;
	private ArrayList<Territory> Territories;
	private ArrayList<ConnectionLine> Connections;
	// END DATA MEMBERS

	/**
	 * Constructs a map with no territories, teams, or connections.
	 */
	public TerritoriesMap() {	
		Teams = new ArrayList<Team>();
		Territories = new ArrayList<Territory>();
		Connections = new ArrayList<ConnectionLine>();
	} // END TerritoriesMap
	
	/**
	 * Team list getter method.
	 * @return the list of teams.
	 */
	public ArrayList<Team> getTeams() {
		return Teams;
	} // END getTeams
	
	/**
	 * Territory list getter method.
	 * @return the list of territories.
	 */
	public ArrayList<Territory> getTerritories() {
		return Territories;
	} // END getTerritories
	
	/**
	 * ConnectionLine list getter method.
	 * @return the list of connections.
	 */
	public ArrayList<ConnectionLine> getConnections() {
		return Connections;
	} // END getConnections
	
	/**
	 * Team size getter method.
	 * @return amount of teams.
	 */
	public int getAmountOfTeams() {
		return Teams.size();
	} // END getAmountOfTeams
	
	/**
	 * Territory size getter method.
	 * @return amount of territories.
	 */
	public int getAmountOfTerritories() {
		return Territories.size();
	} // END getAmountOfTerritories
	
	/**
	 * Individual territory getter method. 
	 * @param i	index of the returned territory.
	 * @return	desired territory.
	 */
	public Territory getTerritory(int i) {
		return Territories.get(i);
	} // END getTerritory
	
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
			ArrayList<Territory> territories = Teams.get(i).OwnedTerritories;
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
	
	/** Serialization version as of 10/16/2013 **/
	private static final long serialVersionUID = 1L;
}
