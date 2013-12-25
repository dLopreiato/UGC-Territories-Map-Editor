package org.urbangaming.territories.core;
import java.awt.Color;
import java.io.Serializable;

/**
 * Encapsulates necessary data for an abstract Team. This includes a name, which will be displayed to the user at
 * runtime; and a color, which will decide the color drawn on the map of the territories it owns.
 * @author Andrew Lopreiato
 * @version 1.4.2 12/24/13
 */
public class Team implements Serializable {

	// DATA MEMBERS
	public String Name;
	public Color Color;
	private static final long serialVersionUID = 5L;
	// END DATA MEMEBERS
	
	/**
	 * Constructs a team with a black color, and no territories.
	 * @param name	The name of the team.
	 */
	public Team(String name) {
		Name = name;
		Color = new Color(0, 0, 0, 0);
	} // END Team
	
	/**
	 * Constructs a team with no territories.
	 * @param name	The name of the team.
	 * @param color	The color of the team.
	 */
	public Team(String name, Color color) {
		Name = name;
		Color = color;
	} // END Team
	
	/**
	 * Returns if the two teams are of equal values.
	 * @param other	The other team.
	 * @return		Boolean representation of comparison.
	 */
	@Override
	public boolean equals(Object object) {
		if (object.getClass() != this.getClass())
			return false;
		Team other = (Team)object;
		return (other.Name.equals(this.Name)) && (other.Color.equals(this.Color));
	} // END equals
	
	/**
	 * Returns the team name.
	 * @return 	String representation.
	 */
	@Override
	public String toString() {
		return Name;
	} // END toString
}
