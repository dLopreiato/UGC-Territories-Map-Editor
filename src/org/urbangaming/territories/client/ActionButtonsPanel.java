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
	private JButton OpenButton_ = null;
	private JButton SaveButton_ = null;
	private JButton DrawButton_ = null;
	private String DataDirectory_ = "";
	private String DataFile_ = "";
	private String OutputImageDirectory_ = "";
	private String OutputImageFile_ = "";
	private FileDialog FileDialog_ = null;
	private ActionListener UpdateAction_ = null;
	private static final String MAP_FILE_ENDING = ".trmp";
	private static final long serialVersionUID = 3L;
	// END DATA MEMBERS
	
	/**
	 * Constructs a panel that contains all of the necessary processes to make the basic buttons work.
	 * @param parentFrame The parent frame that the FileDialog is attached to.
	 * @param updateAction The action listener to be informed when a button asks for a process.
	 */
	ActionButtonsPanel(Frame parentFrame, ActionListener updateAction) {
		this.UpdateAction_ = updateAction;
		this.setLayout(new FlowLayout());
		OpenButton_ = new JButton("Open");
		SaveButton_ = new JButton("Save");
		DrawButton_ = new JButton("Draw");
		this.add(OpenButton_);
		this.add(SaveButton_);
		this.add(DrawButton_);
		FileDialog_ = new FileDialog(parentFrame);
		FileDialog_.setFilenameFilter(new TerritoriesMapFileFilter());
		FileDialog_.setMultipleMode(false);
		FileDialog_.setAlwaysOnTop(true);
		FileDialog_.setVisible(false);
		FileDialog_.setEnabled(false);
		
		OpenButton_.addActionListener(new ActionButtonEvent());
		SaveButton_.addActionListener(new ActionButtonEvent());
		DrawButton_.addActionListener(new ActionButtonEvent());
	} // END ActionButtonsPanel
	
	private class ActionButtonEvent implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (event.getSource().equals(OpenButton_)) {
				FileDialog_.setMode(java.awt.FileDialog.LOAD);
				FileDialog_.setTitle("Choose a map");
				FileDialog_.setEnabled(true);
				FileDialog_.setVisible(true);
				if (FileDialog_.getFile() != null) { //only continue on if a file was actually selected
					DataDirectory_ = FileDialog_.getDirectory();
					DataFile_ = FileDialog_.getFile();
					UpdateAction_.actionPerformed(new ActionEvent(this, 0, "LOAD"));
				}
			}
			
			if (event.getSource().equals(SaveButton_)) {
				FileDialog_.setMode(java.awt.FileDialog.SAVE);
				FileDialog_.setTitle("Save this map");
				FileDialog_.setDirectory(DataDirectory_);
				FileDialog_.setFile(DataFile_);
				FileDialog_.setEnabled(true);
				FileDialog_.setVisible(true);
				if (FileDialog_.getFile() != null) { //Only continue on if a file was actually selected
					DataDirectory_ = FileDialog_.getDirectory();
					DataFile_ = FileDialog_.getFile();
					UpdateAction_.actionPerformed(new ActionEvent(this, 1, "SAVE"));
				}
			}
			
			if (event.getSource().equals(DrawButton_)) {
				FileDialog_.setMode(java.awt.FileDialog.SAVE);
				FileDialog_.setTitle("Draw this map");
				FileDialog_.setDirectory(OutputImageDirectory_);
				FileDialog_.setFile(OutputImageFile_);
				FileDialog_.setEnabled(true);
				FileDialog_.setVisible(true);
				if (FileDialog_.getFile() != null) { //Only continue on if a file was actually selected
					OutputImageDirectory_ = FileDialog_.getDirectory();
					OutputImageFile_ = FileDialog_.getFile();
					UpdateAction_.actionPerformed(new ActionEvent(this, 2, "DRAW"));
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
		UpdateAction_ = listener;
	} // END SetAction
	
	/**
	 * Gives the path for the territories map file.
	 * @return String containing path.
	 */
	public String GetDataPath() {
		return DataDirectory_ + DataFile_;
	} // END GetDataDirectory
	
	/**
	 * Gives the path for the processed image file.
	 * @return String containing path.
	 */
	public String GetImagePath() {
		return OutputImageDirectory_ + OutputImageFile_;
	} // END GetImageDirectory
	
} // END ActionButtonsPanel
