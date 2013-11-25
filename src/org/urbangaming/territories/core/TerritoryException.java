package org.urbangaming.territories.core;

/**
 * This is the exception class thrown if an error regarding territories occurs.
 * @author Andrew Lopreiato
 * @version 1.0 11/24/13
 */
public class TerritoryException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public TerritoryException(String message) {
		super(message);
	}

}
