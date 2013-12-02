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
	private static TerritoriesMap TerritoriesMap = null;
	private static TerritoriesPanel TerritoriesPanel = null;
	private static ActionButtonsPanel ActionPanel = null;
	private static JFrame OSWindow = null;
	// END DATA MEMBERS
	
	/**
	 * The main running method.
	 * @param args No options available.
	 */
	public static void main(String[] args) {
		// Create the main window frame
		OSWindow = new JFrame();
		OSWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		OSWindow.setLocation(0,0);
		OSWindow.setSize(640, 480);
		OSWindow.setTitle("Territories Admin Menu");
		
		// create the highest container panel
		JPanel superPanel = new JPanel(new BorderLayout());
		OSWindow.add(superPanel);
		
		// create and insert action panel
		ActionPanel = new ActionButtonsPanel(OSWindow, new ActionPanelListener()); 
		superPanel.add(ActionPanel, BorderLayout.SOUTH);
		
		// Create the list jscrollpane
		JScrollPane territoryScrollPane = new JScrollPane();
		superPanel.add(territoryScrollPane, BorderLayout.CENTER);
		
		// Create the territories panel
		TerritoriesPanel = new TerritoriesPanel();
		territoryScrollPane.setViewportView(TerritoriesPanel);
		
		// Should be last thing to do, to ensure no screen changes are visible
		OSWindow.setVisible(true);
	} // END main
	
	/**
	 * The listener class that will react to any action buttons being pressed.
	 */
	private static class ActionPanelListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			OSWindow.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			try {
				if (e.getActionCommand().equals("SAVE")) {
					//Update map 
					for (int i = 0; i < TerritoriesMap.GetAmountOfTerritories(); i++) {
							TerritoriesMap.SetTerritoriesTeam(TerritoriesMap.GetTerritory(i),
							TerritoriesMap.GetTeam(TerritoriesPanel.ChoiceForIndex(i)));
					}
					// Serialize map
					TerritoriesMap.Serialize(ActionPanel.GetDataPath());
				}
				if (e.getActionCommand().equals("LOAD")) {
					TerritoriesMap = 
							org.urbangaming.territories.core.TerritoriesMap.Deserialize(ActionPanel.GetDataPath());
					TerritoriesPanel.UpdateOptionsChoices(TerritoriesMap);
					OSWindow.validate();
				}
				if (e.getActionCommand().equals("DRAW")) {
					if (TerritoriesMap != null) {
						//Update map 
						for (int i = 0; i < TerritoriesMap.GetAmountOfTerritories(); i++) {
								TerritoriesMap.SetTerritoriesTeam(TerritoriesMap.GetTerritory(i),
								TerritoriesMap.GetTeam(TerritoriesPanel.ChoiceForIndex(i)));
						}
						
						// Draw
						TerritoriesMap.Draw("BaseMap.png", ActionPanel.GetImagePath());
					}
				}
			} catch (Exception exception) {
				JOptionPane.showMessageDialog(OSWindow, exception.getLocalizedMessage());
			}
			OSWindow.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	} // END ActionPanelListener	
} // END MainDriver
