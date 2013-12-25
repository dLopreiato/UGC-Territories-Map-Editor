package org.urbangaming.territories.core;
import java.io.Serializable;

/**
 * Encapsulates the necessary data for a line. This type should be used specifically for lines that connect the
 * territories. If a line has wrapping enabled, it should represent a connection that is to wrap around the world.
 * @author Andrew Lopreiato
 * @version 1.3.1 12/24/13
 */
public class ConnectionLine implements Serializable {
	
	// DATA MEMBERS
	public int X1;
	public int Y1;
	public int X2;
	public int Y2;
	public Boolean Wrapping;
	private static final long serialVersionUID = 4L;
	// END DATA MEMBERS
	
	/**
	 * Constructs a line of length zero, and points (0,0), with no wrapping.
	 */
	public ConnectionLine() {
		X1 = 0;
		Y1 = 0;
		X2 = 0;
		Y2 = 0;
		Wrapping = false;
	} // END ConnectionLine
	
	/**
	 * Constructs a line with no wrapping.
	 * @param x1	The X coordinate of the first point.
	 * @param y1	The Y coordinate of the first point.
	 * @param x2	The X coordinate of the second point.
	 * @param y2	The Y coordinate of the second point.
	 */
	public ConnectionLine(int x1, int y1, int x2, int y2) {
		X1 = x1;
		Y1 = y1;
		X2 = x2;
		Y2 = y2;
		Wrapping = false;
	} // END ConnectionLine
	
	/**
	 * Constructs a line with a specified wrapping setting.
	 * @param x1		The X coordinate of the first point.
	 * @param y1 		The Y coordinate of the first point.
	 * @param x2 		The X coordinate of the second point.
	 * @param y2 		The Y coordinate of the second point.
	 * @param wrapping	If it wraps around the edges.
	 */
	public ConnectionLine(int x1, int y1, int x2, int y2, Boolean wrapping) {
		X1 = x1;
		Y1 = y1;
		X2 = x2;
		Y2 = y2;
		Wrapping = wrapping;
	} // END ConnectionLine
	
	/**
	 * Returns if the two connection lines are of equal values.
	 * @param other	The other connection line.
	 * @return		Boolean representation of comparison.
	 */
	@Override
	public boolean equals(Object object) {
		if (object.getClass() != this.getClass())
			return false;
		ConnectionLine other = (ConnectionLine)object;
		return (other.Wrapping.equals(this.Wrapping)) && (other.X1 == this.X1) && (other.X2 == this.X2) && 
				(other.Y1 == this.Y1) && (other.Y2 == this.Y2); 
	} // END equals
}
