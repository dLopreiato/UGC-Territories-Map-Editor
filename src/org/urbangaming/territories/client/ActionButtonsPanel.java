package org.urbangaming.territories.client;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Isolates all of the data and behavior that would be seen in the action buttons portion of the main user
 * interface. The action buttons are the buttons that allow the user to load a map file, save map file changes, and
 * draw the edited data from the map file onto an image.
 * @author Andrew Lopreiato
 * @version 1.2 12/1/23
 */
public class ActionButtonsPanel extends JPanel {
	
	// DATA MEMBERS
	private JButton OpenButton = null;
	private JButton SaveButton = null;
	private JButton DrawButton = null;
	private String DataDirectory = "";
	private String DataFile = "";
	private String OutputImageDirectory = "";
	private String OutputImageFile = "";
	private FileDialog FileDialog = null;
	private ActionListener UpdateAction = null;
	private static final String MAP_FILE_ENDING = ".trmp";
	private static final long serialVersionUID = 3L;
	// END DATA MEMBERS
	
	/**
	 * Constructs a panel that contains all of the necessary processes to make the basic buttons work.
	 * @param parentFrame The parent frame that the FileDialog is attached to.
	 * @param updateAction The action listener to be informed when a button asks for a process.
	 */
	ActionButtonsPanel(Frame parentFrame, ActionListener updateAction) {
		this.UpdateAction = updateAction;
		this.setLayout(new FlowLayout());
		OpenButton = new JButton("Open");
		SaveButton = new JButton("Save");
		DrawButton = new JButton("Draw");
		this.add(OpenButton);
		this.add(SaveButton);
		this.add(DrawButton);
		FileDialog = new FileDialog(parentFrame);
		FileDialog.setFilenameFilter(new TerritoriesMapFileFilter());
		FileDialog.setMultipleMode(false);
		FileDialog.setAlwaysOnTop(true);
		FileDialog.setVisible(false);
		FileDialog.setEnabled(false);
		
		OpenButton.addActionListener(new ActionButtonEvent());
		SaveButton.addActionListener(new ActionButtonEvent());
		DrawButton.addActionListener(new ActionButtonEvent());
	} // END ActionButtonsPanel
	
	private class ActionButtonEvent implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (event.getSource().equals(OpenButton)) {
				FileDialog.setMode(java.awt.FileDialog.LOAD);
				FileDialog.setTitle("Choose a map");
				FileDialog.setEnabled(true);
				FileDialog.setVisible(true);
				if (FileDialog.getFile() != null) { //only continue on if a file was actually selected
					DataDirectory = FileDialog.getDirectory();
					DataFile = FileDialog.getFile();
					UpdateAction.actionPerformed(new ActionEvent(this, 0, "LOAD"));
				}
			}
			
			if (event.getSource().equals(SaveButton)) {
				FileDialog.setMode(java.awt.FileDialog.SAVE);
				FileDialog.setTitle("Save this map");
				FileDialog.setDirectory(DataDirectory);
				FileDialog.setFile(DataFile);
				FileDialog.setEnabled(true);
				FileDialog.setVisible(true);
				if (FileDialog.getFile() != null) { //Only continue on if a file was actually selected
					DataDirectory = FileDialog.getDirectory();
					DataFile = FileDialog.getFile();
					UpdateAction.actionPerformed(new ActionEvent(this, 1, "SAVE"));
				}
			}
			
			if (event.getSource().equals(DrawButton)) {
				FileDialog.setMode(java.awt.FileDialog.SAVE);
				FileDialog.setTitle("Draw this map");
				FileDialog.setDirectory(OutputImageDirectory);
				FileDialog.setFile(OutputImageFile);
				FileDialog.setEnabled(true);
				FileDialog.setVisible(true);
				if (FileDialog.getFile() != null) { //Only continue on if a file was actually selected
					OutputImageDirectory = FileDialog.getDirectory();
					OutputImageFile = FileDialog.getFile();
					UpdateAction.actionPerformed(new ActionEvent(this, 2, "DRAW"));
				}
			}
		}
	} // END ActionButtonEvent
	
	/**
	 * Class containing methods used for determining if a loaded territories map file has the correct file ending.
	 */
	public static class TerritoriesMapFileFilter implements FilenameFilter {
		public boolean accept (File dir, String name) {
			return isValid(name);
		}
		
		public static boolean isValid(String name) {
			return name.endsWith(MAP_FILE_ENDING);
		}
	} // END TerritoriesMapFileFilter
	
	/**
	 * Updates the listener for when a button is pressed and the dialog has finished.
	 * @param listener The action.
	 */
	public void SetAction(ActionListener listener) {
		UpdateAction = listener;
	} // END SetAction
	
	/**
	 * Gives the path for the territories map file.
	 * @return String containing path.
	 */
	public String GetDataPath() {
		return DataDirectory + DataFile;
	} // END GetDataDirectory
	
	/**
	 * Gives the path for the processed image file.
	 * @return String containing path.
	 */
	public String GetImagePath() {
		return OutputImageDirectory + OutputImageFile;
	} // END GetImageDirectory
	
} // END ActionButtonsPanel
