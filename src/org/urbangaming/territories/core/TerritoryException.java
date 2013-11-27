package org.urbangaming.territories.core;

/**
 * This is the exception class thrown if an error regarding territories occurs.
 * @author Andrew Lopreiato
 * @version 1.0 11/24/13
 */
public class TerritoryException extends Exception {
	
	/**
	 * Constructs a territory except with a given message.
	 * @param message	The message to throw.
	 */
	public TerritoryException(String message) {
		super(message);
	}
	
	/** Serialization version as of last update **/
	private static final long serialVersionUID = 1L;

}
