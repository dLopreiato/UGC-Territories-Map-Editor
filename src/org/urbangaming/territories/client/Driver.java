package org.urbangaming.territories.client;
import org.urbangaming.territories.core.TerritoriesMap;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

/**
 * This is the class that should be used by the territories admin. This class is the entry point for users wanting to
 * use a graphical interface. A map file must already be in existence for this driver to work.
 * @author Andrew Lopreiato
 * @version 1.2 12/1/13
 */
public class Driver {
	// DATA MEMBERS
	private static TerritoriesMap TerritoriesMap_ = null;
	private static TerritoriesPanel TerritoriesPanel_ = null;
	private static ActionButtonsPanel ActionPanel_ = null;
	private static JFrame OSWindow_ = null;
	private static JScrollPane TerritoryScrollPane_ = null;
	private static JPanel SuperPanel_ = null;
	// END DATA MEMBERS
	
	/**
	 * The main running method.
	 * @param args No options available.
	 */
	public static void main(String[] args) {
		// Create the main window frame
		OSWindow_ = new JFrame();
		OSWindow_.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		OSWindow_.setLocation(0,0);
		OSWindow_.setSize(640, 480);
		OSWindow_.setTitle("Territories Admin Menu");
		
		// create the highest container panel
		SuperPanel_ = new JPanel(new BorderLayout());
		OSWindow_.add(SuperPanel_);
		
		// create and insert action panel
		ActionPanel_ = new ActionButtonsPanel(OSWindow_, new ActionPanelListener()); 
		SuperPanel_.add(ActionPanel_, BorderLayout.SOUTH);
		
		// Create the list jscrollpane
		TerritoryScrollPane_ = new JScrollPane();
		SuperPanel_.add(TerritoryScrollPane_, BorderLayout.CENTER);
		
		// Create the territories panel
		TerritoriesPanel_ = new TerritoriesPanel();
		TerritoryScrollPane_.setViewportView(TerritoriesPanel_);
		
		// Should be last thing to do, to ensure no screen changes are visible
		OSWindow_.setVisible(true);
	} // END main
	
	/**
	 * The listener class that will react to any action buttons being pressed.
	 */
	private static class ActionPanelListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			OSWindow_.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			try {
				if (e.getActionCommand().equals("SAVE")) {
					//Update map 
					for (int i = 0; i < TerritoriesMap_.GetAmountOfTerritories(); i++) {
							TerritoriesMap_.SetTerritoriesTeam(TerritoriesMap_.GetTerritory(i),
							TerritoriesMap_.GetTeam(TerritoriesPanel_.ChoiceForIndex(i)));
					}
					// Serialize map
					TerritoriesMap_.Serialize(ActionPanel_.GetDataPath());
				}
				if (e.getActionCommand().equals("LOAD")) {
					TerritoriesMap_ = 
							org.urbangaming.territories.core.TerritoriesMap.Deserialize(ActionPanel_.GetDataPath());
					TerritoriesPanel_.UpdateOptionsChoices(TerritoriesMap_);
					OSWindow_.validate();
				}
				if (e.getActionCommand().equals("DRAW")) {
					if (TerritoriesMap_ != null) {
						//Update map 
						for (int i = 0; i < TerritoriesMap_.GetAmountOfTerritories(); i++) {
								TerritoriesMap_.SetTerritoriesTeam(TerritoriesMap_.GetTerritory(i),
								TerritoriesMap_.GetTeam(TerritoriesPanel_.ChoiceForIndex(i)));
						}
						
						// Draw
						TerritoriesMap_.Draw("BaseMap.png", ActionPanel_.GetImagePath());
					}
				}
			} catch (Exception exception) {
				JOptionPane.showMessageDialog(OSWindow_, exception.getLocalizedMessage());
			}
			OSWindow_.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	} // END ActionPanelListener	
} // END MainDriver
