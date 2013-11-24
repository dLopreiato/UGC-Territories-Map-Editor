package org.urbangaming.territories.core;

import java.io.Serializable;

/**
 * Encapsulates the necessary data for a line. This type should be used specifically for lines that connect the
 * territories.
 * @author Andrew Lopreiato
 * @version 1.1 10/16/13
 */
public class ConnectionLine implements Serializable {
	
	// DATA MEMBERS
	public int X1;
	public int Y1;
	public int X2;
	public int Y2;
	// END DATA MEMBERS
	
	/**
	 * Constructs a line of length zero, and points (0,0).
	 */
	public ConnectionLine() {
		X1 = 0;
		Y1 = 0;
		X2 = 0;
		Y2 = 0;
	} // END ConnectionLine
	
	/**
	 * Constructs a line.
	 * @param x1 The X coordinate of the first point.
	 * @param y1 The Y coordinate of the first point.
	 * @param x2 The X coordinate of the second point.
	 * @param y2 The Y coordinate of the second point.
	 */
	public ConnectionLine(int x1, int y1, int x2, int y2) {
		X1 = x1;
		Y1 = y1;
		X2 = x2;
		Y2 = y2;
	} // END ConnectionLine
	
	/** Serialization version as of last update **/
	private static final long serialVersionUID = 1L;
}
