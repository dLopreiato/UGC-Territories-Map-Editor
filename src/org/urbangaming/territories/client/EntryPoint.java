package org.urbangaming.territories.client;
import org.urbangaming.territories.core.TerritoriesMap;
import java.awt.Cursor;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 * This is the class that should be used by the territories admin. This class is the entry point for users wanting to
 * use a graphical interface. A map file must already be in existence for this driver to work.
 * @author Andrew Lopreiato
 * @version 1.3.1 12/6/13
 */
public class EntryPoint {
	// DATA MEMBERS
	private static TerritoriesMap TerritoriesMap_;
	private static UserInterface UserInterface_;
	private static FileDialog FileDialog_;
	private static Path MapDataPath_;
	private static Path InputImagePath_;
	private static Path OutputImagePath_;
	// END DATA MEMBERS
	
	/**
	 * The main running method.
	 * @param args No options available.
	 */
	public static void main(String[] args) {
		UserInterface_ = new UserInterface();
		TerritoriesMap_ = new TerritoriesMap();
		
		MapDataPath_ = new Path();
		InputImagePath_ = new Path("", "BaseMap.png");
		OutputImagePath_ = new Path("", "OutputMap.png");
		
		FileDialog_ = new FileDialog(UserInterface_);
		
		UserInterface_.setVisible(true);
	} // END main
	
	public static class OpenListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				FileDialog_.setMode(FileDialog.LOAD);
				FileDialog_.setTitle("Choose A Map");
				FileDialog_.setDirectory(MapDataPath_.Directory);
				FileDialog_.setFile(MapDataPath_.File);
				FileDialog_.setEnabled(true);
				FileDialog_.setVisible(true);
				if (FileDialog_.getFile() != null) { //only continue on if a file was actually selected
					MapDataPath_  = new Path(FileDialog_.getDirectory(), FileDialog_.getFile(), ".trmp");
					UserInterface_.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					TerritoriesMap_ = TerritoriesMap.Deserialize(MapDataPath_.toString());
					UserInterface_.UpdateTerritoriesPanel(TerritoriesMap_);
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(UserInterface_, ex.getLocalizedMessage());
			}
			UserInterface_.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}
	
	public static class SaveListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				UserInterface_.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				SyncUIToTerritoriesMap();
				TerritoriesMap_.Serialize(MapDataPath_.toString());
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(UserInterface_, ex.getLocalizedMessage());
			}
			UserInterface_.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}
	
	public static class SaveAsListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				FileDialog_.setMode(FileDialog.SAVE);
				FileDialog_.setTitle("Save Map As");
				FileDialog_.setDirectory(MapDataPath_.Directory);
				FileDialog_.setFile(MapDataPath_.File);
				FileDialog_.setEnabled(true);
				FileDialog_.setVisible(true);
				if (FileDialog_.getFile() != null) { //only continue on if a file was actually selected
					UserInterface_.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					MapDataPath_ = new Path(FileDialog_.getDirectory(), FileDialog_.getFile(), ".trmp");
					SyncUIToTerritoriesMap();
					TerritoriesMap_.Serialize(MapDataPath_.toString());
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(UserInterface_, ex.getLocalizedMessage());
			}
			UserInterface_.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}
	
	public static class DrawListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				UserInterface_.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				SyncUIToTerritoriesMap();
				TerritoriesMap_.Draw(InputImagePath_.toString(), OutputImagePath_.toString());
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(UserInterface_, ex.getLocalizedMessage());
			}
			UserInterface_.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}

	public static class DrawAsListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				FileDialog_.setMode(FileDialog.SAVE);
				FileDialog_.setTitle("Choose An Output");
				FileDialog_.setDirectory(OutputImagePath_.Directory);
				FileDialog_.setFile(OutputImagePath_.File);
				FileDialog_.setEnabled(true);
				FileDialog_.setVisible(true);
				if (FileDialog_.getFile() != null) { //only continue on if a file was actually selected
					UserInterface_.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					OutputImagePath_ = new Path(FileDialog_.getDirectory(), FileDialog_.getFile(), ".png");
					SyncUIToTerritoriesMap();
					TerritoriesMap_.Draw(InputImagePath_.toString(), OutputImagePath_.toString());
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(UserInterface_, ex.getLocalizedMessage());
			}
			UserInterface_.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}

	public static class SetBaseListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				FileDialog_.setMode(FileDialog.LOAD);
				FileDialog_.setTitle("Choose An Output");
				FileDialog_.setDirectory(InputImagePath_.Directory);
				FileDialog_.setFile(InputImagePath_.File);
				FileDialog_.setEnabled(true);
				FileDialog_.setVisible(true);
				if (FileDialog_.getFile() != null) { //only continue on if a file was actually selected
					InputImagePath_ = new Path(FileDialog_.getDirectory(), FileDialog_.getFile(), ".png");
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(UserInterface_, ex.getLocalizedMessage());
			}
		}
	}
	
	public static class AboutListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			JOptionPane.showMessageDialog(UserInterface_, "Created by Andrew Lopreiato." +
					System.lineSeparator() + "Find the latest version at http://github.com/dlopreiato/");
		}
	}
	
	private static void SyncUIToTerritoriesMap() {
		for (int i = 0; i < TerritoriesMap_.GetAmountOfTerritories(); i++) {
			TerritoriesMap_.SetTerritoriesTeam(TerritoriesMap_.GetTerritory(i),
			TerritoriesMap_.GetTeam(UserInterface_.GetTerritoryChoice(i)));
		}
	}
} // END MainDriver
