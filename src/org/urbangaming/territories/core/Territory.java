package org.urbangaming.territories.core;

import java.awt.Polygon;
import java.io.Serializable;

/**
 * Encapsulates necessary data for an abstract Territory. This includes a name, which will be displayed to the user at
 * runtime; and a polygon, which will be what the application uses to actually draw the shape.
 * @author Andrew Lopreiato
 * @version 1.2 10/16/13
 */
public class Territory implements Serializable{
	
	// DATA MEMBERS
	public String Name;
	public Polygon Region;
	// END DATA MEMBERS
	
	/**
	 * Constructs a territory with a blank polygon.
	 * @param name The name that the end user sees.
	 */
	public Territory (String name) {
		Name = name;
		Region = new Polygon();
	} // END Territory
	
	/**
	 * Constructs a territory.
	 * @param name The name that the end user sees.
	 * @param region The polygon that defines this territory on the map.
	 */
	public Territory (String name, Polygon region) {
		Name = name;
		Region = region;
	} // END Territory
	
	/** Serialization version as of last update **/
	private static final long serialVersionUID = 1L;
}
