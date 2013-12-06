package org.urbangaming.territories.client;

/**
 * This is a class that encapsulates two strings to represent the directory and file of a path.
 * @author Andrew Lopreiato
 * @version 1.1 12/6/13
 */
public class Path {
	
	// DATA MEMBERS
	public String Directory;
	public String File;
	// END DATA MEMBERS
	
	/**
	 * Constructs a Path with empty strings.
	 */
	public Path() {
		Directory = "";
		File = "";
	}
	
	/**
	 * Constructs a path with the given directory and filename. 
	 * @param dir	Directory.
	 * @param file	File.
	 */
	public Path(String dir, String file) {
		Directory = dir;
		File = file;
	}
	
	/**
	 * Constructs a path with a given directory and filename only if the the path leads to a file with a given suffix.
	 * @param dir			Directory.
	 * @param file			File.
	 * @param fileSuffix	File ending.
	 */
	public Path(String dir, String file, String fileSuffix) {
		if (file.endsWith(fileSuffix)) {
			Directory = dir;
			File = file;
		} else {
			throw new RuntimeException("File is not a '" + fileSuffix + "' file.");
		}
	}
	
	/**
	 * Returns the full path as one string.
	 */
	public String toString() {
		return Directory + File;
	} // END toString
	
	/**
	 * Determines if the current path is to a file with a given ending.
	 * @param fileSuffix	String of the file ending including the dot.
	 * @return				Boolean representation of result.
	 */
	public Boolean IsFileType(String fileSuffix) {
		return File.endsWith(fileSuffix);
	}
} // END Path
