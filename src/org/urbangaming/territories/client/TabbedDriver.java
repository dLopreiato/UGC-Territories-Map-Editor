package org.urbangaming.territories.client;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;
import org.urbangaming.territories.core.Team;
import org.urbangaming.territories.core.TerritoriesMap;
import org.urbangaming.territories.core.Territory;
import org.urbangaming.territories.core.TerritoryException;

/**
 * This class contains the entry point for the tabbed version of the user interface.
 * @author Andrew Lopreiato
 * @version 1.1.2 12/30/2013
 */
public class TabbedDriver {

	// DATA MEMBERS
	private JFrame MainWindow_;
	private JMenuBar Menus_;
	private OwnershipTab OwnershipTab_;
	private TeamTab TeamTab_;
	private TerritoriesTab TerritoriesTab_;
	private ConnectionsTab ConnectionsTab_;
	private FileDialog FileDialog_;
	private Path MapDataPath_;
	private Path OutputImagePath_;
	private TerritoriesMap TerritoriesMap_;
	// END DATA MEMBERS
	
	public static void main(String[] args) {
		TabbedDriver tb = new TabbedDriver();
		tb.Run();
	}

	public TabbedDriver() {
		InitializeFrame();
		InitializeMenuBar();
		InitializeTabbedPane();
		FileDialog_ = new FileDialog(MainWindow_);
		MapDataPath_ = new Path();
		OutputImagePath_ = new Path("", "OutputMap.png");
		TerritoriesMap_ = null;
	}
	
	public void Run() {
		MainWindow_.setVisible(true);
	}
	
	private void InitializeFrame() {
		// initialize the frame and its defaults
		MainWindow_ = new JFrame();
		MainWindow_.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		MainWindow_.setTitle("Territories Map Creator");
		MainWindow_.setLocation(0, 0);
		MainWindow_.setSize(640, 480);
		// END initialize the frame and its defaults
	}
	
	private void InitializeMenuBar() {
		// initialize the menu bar and its defaults
		Menus_ = new JMenuBar();
		
		// set the file menu
		JMenu fileMenu = new JMenu("File");
		JMenuItem openOption = new JMenuItem("Open Map...");
		JMenuItem saveMapOption = new JMenuItem("Save Map");
		JMenuItem saveMapAsOption = new JMenuItem("Save Map As...");
		JMenuItem drawMapOption = new JMenuItem("Draw Map");
		JMenuItem drawMapAsOption = new JMenuItem("Draw Map As...");
		fileMenu.add(openOption);
		fileMenu.addSeparator();
		fileMenu.add(saveMapOption);
		fileMenu.add(saveMapAsOption);
		fileMenu.addSeparator();
		fileMenu.add(drawMapOption);
		fileMenu.add(drawMapAsOption);
		openOption.addActionListener(new OpenListener());
		saveMapOption.addActionListener(new SaveListener(false));
		saveMapAsOption.addActionListener(new SaveListener(true));
		drawMapOption.addActionListener(new DrawListener(false));
		drawMapAsOption.addActionListener(new DrawListener(true));
		openOption.setAccelerator(KeyStroke.getKeyStroke('O', InputEvent.CTRL_DOWN_MASK));
		saveMapOption.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK));
		saveMapAsOption.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.ALT_DOWN_MASK));
		drawMapOption.setAccelerator(KeyStroke.getKeyStroke('D', InputEvent.CTRL_DOWN_MASK));
		drawMapAsOption.setAccelerator(KeyStroke.getKeyStroke('D', InputEvent.ALT_DOWN_MASK));
		
		// set the teams menu
		JMenu teamMenu = new JMenu("Teams");
		JMenuItem addTeamOption = new JMenuItem("Add New Team");
		JMenuItem deleteAllOption = new JMenuItem("Delete All Teams");
		JMenuItem cullTeamsOption = new JMenuItem("Cull Teams");
		JMenuItem normalizeAlphaOption = new JMenuItem("Normalize Transparency");
		teamMenu.add(addTeamOption);
		teamMenu.add(deleteAllOption);
		teamMenu.add(cullTeamsOption);
		teamMenu.add(normalizeAlphaOption);
		addTeamOption.addActionListener(GetAddTeamListener());
		deleteAllOption.addActionListener(new DeleteAllTeamsListener());
		cullTeamsOption.addActionListener(new CullTeamsListener());
		normalizeAlphaOption.addActionListener(new NormalizeAlphaListener());
		addTeamOption.setAccelerator(KeyStroke.getKeyStroke('T', InputEvent.CTRL_DOWN_MASK));
		normalizeAlphaOption.setAccelerator(KeyStroke.getKeyStroke('N', InputEvent.CTRL_DOWN_MASK));
		
		
		// set the help menu
		JMenu helpMenu = new JMenu("Help");
		JMenuItem aboutOption = new JMenuItem("About");
		helpMenu.add(aboutOption);
		aboutOption.addActionListener(new AboutListener());
		
		// add all the menus
		Menus_.add(fileMenu);
		Menus_.add(teamMenu);
		Menus_.add(helpMenu);
		MainWindow_.setJMenuBar(Menus_);
		// END initialize the menu bar and its defaults
	}
	
	private void InitializeTabbedPane() {
		JTabbedPane tabbedPane = new JTabbedPane();

		OwnershipTab_ = new OwnershipTab();
		tabbedPane.addTab("Ownership", OwnershipTab_);

		TeamTab_ = new TeamTab();
		tabbedPane.addTab("Teams", TeamTab_);

		TerritoriesTab_ = new TerritoriesTab();
		tabbedPane.addTab("Territories", TerritoriesTab_);
		
		ConnectionsTab_ = new ConnectionsTab();
		tabbedPane.addTab("Connections", ConnectionsTab_);
		
		MainWindow_.add(tabbedPane);
	}
	
	private class OpenListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			try {
				FileDialog_.setMode(FileDialog.LOAD);
				FileDialog_.setTitle("Choose A Map");
				FileDialog_.setDirectory(MapDataPath_.Directory);
				FileDialog_.setFile(MapDataPath_.File);
				FileDialog_.setEnabled(true);
				FileDialog_.setVisible(true);
				if (FileDialog_.getFile() != null) { //only continue on if a file was actually selected
					MapDataPath_  = new Path(FileDialog_.getDirectory(), FileDialog_.getFile(), ".trmp");
					MainWindow_.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					TerritoriesMap_ = TerritoriesMap.Deserialize(MapDataPath_.toString());
					SynchronizeInterface();
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(MainWindow_, ex.getLocalizedMessage());
			}
			MainWindow_.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}
	
	private class SaveListener implements ActionListener {
		private Boolean UseDialog_;
		public SaveListener(Boolean useDialog) {
			UseDialog_ = useDialog;
		}
		@Override
		public void actionPerformed(ActionEvent ae) {
			try {
				if (UseDialog_) {
					FileDialog_.setMode(FileDialog.SAVE);
					FileDialog_.setTitle("Save Map As");
					FileDialog_.setDirectory(MapDataPath_.Directory);
					FileDialog_.setFile(MapDataPath_.File);
					FileDialog_.setEnabled(true);
					FileDialog_.setVisible(true);
				}
				if (FileDialog_.getFile() != null) { //only continue on if a file was actually selected
					MainWindow_.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					MapDataPath_ = new Path(FileDialog_.getDirectory(), FileDialog_.getFile(), ".trmp");
					TerritoriesMap_.Serialize(MapDataPath_.toString());
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(MainWindow_, ex.getLocalizedMessage());
			}
			MainWindow_.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}
	
	private class DrawListener implements ActionListener {
		private Boolean UseDialog_;
		public DrawListener(Boolean useDialog) {
			UseDialog_ = useDialog;
		}
		@Override
		public void actionPerformed(ActionEvent ae) {
			try {
				if (UseDialog_) {
					FileDialog_.setMode(FileDialog.SAVE);
					FileDialog_.setTitle("Choose An Output");
					FileDialog_.setDirectory(OutputImagePath_.Directory);
					FileDialog_.setFile(OutputImagePath_.File);
					FileDialog_.setEnabled(true);
					FileDialog_.setVisible(true);
				}
				if (FileDialog_.getFile() != null) { //only continue on if a file was actually selected
					MainWindow_.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					OutputImagePath_ = new Path(FileDialog_.getDirectory(), FileDialog_.getFile(), ".png");
					TerritoriesMap_.Draw(OutputImagePath_.toString());
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(MainWindow_, ex.getLocalizedMessage());
			}
			MainWindow_.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}
	
	private class AboutListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			JOptionPane.showMessageDialog(MainWindow_, "Created by Andrew Lopreiato." +
					System.lineSeparator() + "Find the latest version at http://github.com/dlopreiato/");
		}
	}
	
	private class OwnershipListener implements ItemListener {
		private Territory Territory_;
		public OwnershipListener(Territory territory) {
			Territory_ = territory;
		}
		@Override
		public void itemStateChanged(ItemEvent ie) {
			TerritoriesMap_.SetTerritoriesTeam(Territory_, (Team)ie.getItem());
		}
	}
	
	public ItemListener GetOwnershipListener(Territory territory) {
		return new OwnershipListener(territory);
	}
	
	private class DeleteTeamListener implements ActionListener {
		private Team Team_;
		public DeleteTeamListener(Team team) {
			Team_ = team;
		}
		@Override
		public void actionPerformed(ActionEvent ae) {
			try {
				TerritoriesMap_.RemoveTeam(Team_);
				SynchronizeInterface();
			} catch (TerritoryException te) {
				JOptionPane.showMessageDialog(MainWindow_, te.getLocalizedMessage());
			}
		}
	}
	
	public ActionListener GetDeleteTeamListener(Team team) {
		return new DeleteTeamListener(team);
	}
	
	private class AddTeamListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			TerritoriesMap_.AddTeam(new Team("New Team"));
			SynchronizeInterface();
		}
	}
	
	public ActionListener GetAddTeamListener() {
		return new AddTeamListener();
	}
	
	private class TeamColorListener implements ActionListener {
		private Team Team_;
		public TeamColorListener(Team team) {
			Team_ = team;
		}
		@Override
		public void actionPerformed(ActionEvent ae) {
			String frameTitle = "Choose the color for " + Team_.Name;
			Color newColor = JColorChooser.showDialog(MainWindow_, frameTitle, Team_.Color);
			if (newColor != null) {
				Team_.Color = newColor;
			}
		}
	}
	
	public ActionListener GetTeamColorListener(Team team) {
		return new TeamColorListener(team);
	}
	
	private class NormalizeAlphaListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			for (int i = 1; i < TerritoriesMap_.GetAmountOfTeams(); i++) {
				Color newColor = new Color(
						TerritoriesMap_.GetTeam(i).Color.getRed(),
						TerritoriesMap_.GetTeam(i).Color.getGreen(),
						TerritoriesMap_.GetTeam(i).Color.getBlue(),
						180);
				TerritoriesMap_.GetTeam(i).Color = newColor; 
			}
		}
	}
	
	private class DeleteAllTeamsListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			Team unowned = TerritoriesMap_.GetTeam(0);
			for (int i = 0; i < TerritoriesMap_.GetAmountOfTerritories(); i++) {
				TerritoriesMap_.SetTerritoriesTeam(TerritoriesMap_.GetTerritory(i), unowned);
			}
			while (TerritoriesMap_.GetAmountOfTeams() >= 1) {
				try {
					TerritoriesMap_.RemoveTeam(TerritoriesMap_.GetTeam(1));
				} catch (TerritoryException e) {
					JOptionPane.showMessageDialog(MainWindow_, "Fatal Error: Couldn't delete all teams.");
				}
			}
		}
	}

	private class CullTeamsListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			for (int i = 0; i < TerritoriesMap_.GetAmountOfTeams();) {
				try {
					TerritoriesMap_.RemoveTeam(TerritoriesMap_.GetTeam(1));
				} catch (TerritoryException e) {
					i++;
				}
			}
		}
	}
	
	private class HoverMouseListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			MainWindow_.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
	}
	
	public ActionListener GetHoverMouseListener() {
		return new HoverMouseListener();
	}
	
	private class DefaultMouseListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			MainWindow_.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}
	
	public ActionListener GetDefaultMouseListener() {
		return new DefaultMouseListener();
	}
	
	private void SynchronizeInterface() {
		// changes the interface
		OwnershipTab_.ReflectTerritoriesMap(TerritoriesMap_, this);
		TeamTab_.ReflectTerritoriesMap(TerritoriesMap_, this);
		TerritoriesTab_.ReflectTerritoriesMap(TerritoriesMap_, this);
		ConnectionsTab_.ReflectTerritoriesMap(TerritoriesMap_, this);
	}
}
