package org.urbangaming.territories.core;
import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Encapsulates necessary data for an abstract Team. This includes a name, which will be displayed to the user at
 * runtime; a color, which will decide the color drawn on the map of the territories it owns; and a list of the
 *  territories that it owns.
 * @author Andrew Lopreiato
 * @version 1.2 10/16/13
 */
public class Team implements Serializable {

	// DATA MEMBERS
	public String Name;
	public Color Color;
	public ArrayList<Territory> OwnedTerritories;
	// END DATA MEMEBERS
	
	/**
	 * Constructs a team with a black color, and no territories.
	 * @param name The name of the team.
	 */
	public Team(String name) {
		Name = name;
		Color = new Color(0, 0, 0, 0);
		OwnedTerritories = new ArrayList<Territory>();
	} // END Team
	
	/**
	 * Constructs a team with no territories.
	 * @param name The name of the team.
	 * @param color The color of the team.
	 */
	public Team(String name, Color color) {
		Name = name;
		Color = color;
		OwnedTerritories = new ArrayList<Territory>();
	} // END Team
	
	/** Serialization version as of last update **/
	private static final long serialVersionUID = 1L;
}
