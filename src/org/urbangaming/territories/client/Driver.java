package org.urbangaming.territories.client;
import org.urbangaming.territories.core.Team;
import org.urbangaming.territories.core.TerritoriesMap;
import org.urbangaming.territories.core.Territory;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * This is the class that should be used by the territories admin. This class is the entry point for users wanting to
 * use a graphical interface. A map file must already be in existence for this driver to work.
 * @author Andrew Lopreiato
 * @version 1.0 11/24/13
 */
public class Driver {
	// DATA MEMBERS
	private static TerritoriesMap TerritoriesMap = null;
	private static TerritoriesPanel TerritoriesPanel = null;
	private static ActionButtonsPanel ActionPanel = null;
	private static Frame OSWindow = null;
	// END DATA MEMBERS
	
	/**
	 * The main running method.
	 * @param args No options available.
	 */
	public static void main(String[] args) {
		// Create the main window frame
		OSWindow = new Frame();
		OSWindow.addWindowListener(new ExitWindowListener());
		OSWindow.setLocation(0,0);
		OSWindow.setSize(640, 480);
		OSWindow.setTitle("Territories Admin Menu");
		
		// create the highest container panel
		Panel superPanel = new Panel(new BorderLayout());
		OSWindow.add(superPanel);
		
		// create and insert action panel
		ActionPanel = new ActionButtonsPanel(OSWindow, new ActionPanelListener()); 
		superPanel.add(ActionPanel, BorderLayout.SOUTH);
		
		// Create the List scroll pane
		ScrollPane territoryScrollPane = new ScrollPane();
		superPanel.add(territoryScrollPane, BorderLayout.CENTER);
		
		// Create the List panel
		TerritoriesPanel = new TerritoriesPanel();
		territoryScrollPane.add(TerritoriesPanel);
		
		// Should be last thing to do, to ensure no screen changes are visible
		OSWindow.setVisible(true);
	} // END main
	
	/**
	 * The listener class used to properly exit out of the main window frame. 
	 */
	private static class ExitWindowListener extends WindowAdapter {
		public void windowClosing(WindowEvent ev) {
			ev.getComponent().setVisible(false);
			System.exit(0);
		}
	} // END ExitWindowListener
	
	/**
	 * The listener class that will react to any action buttons being pressed.
	 */
	private static class ActionPanelListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("SAVE")) {
				//Update map
				int amountOfTerritories = TerritoriesMap.getAmountOfTerritories(); 
				for (int i = 0; i < amountOfTerritories; i++) {
					Territory relevantTerritory = TerritoriesMap.getTerritory(i);
					DetachTerritory(TerritoriesMap.getTeams(), relevantTerritory);
					AttachTerritory(TerritoriesMap.getTeams().get(TerritoriesPanel.ChoiceForIndex(i)),
							relevantTerritory);
				}
				try {
					TerritoriesMap.Serialize(ActionPanel.GetDataDirectory());
				} catch (Exception ex) {
					System.out.println(ex.getLocalizedMessage());
				}
				
			}
			if (e.getActionCommand().equals("LOAD")) {
				try {
					TerritoriesMap = 
							org.urbangaming.territories.core.TerritoriesMap.Deserialize(ActionPanel.GetDataDirectory());
					TerritoriesPanel.UpdateOptionsChoices(TerritoriesMap);
					OSWindow.validate();
				} catch (Exception ex) {
					System.out.println(ex.getLocalizedMessage());
				}
			}
			if (e.getActionCommand().equals("DRAW")) {
				if (TerritoriesMap != null) {
					//Reflect the onscreen changes with the TerritoriesMap
					int amountOfTerritories = TerritoriesMap.getAmountOfTerritories(); 
					for (int i = 0; i < amountOfTerritories; i++) {
						Territory relevantTerritory = TerritoriesMap.getTerritory(i);
						DetachTerritory(TerritoriesMap.getTeams(), relevantTerritory);
						AttachTerritory(TerritoriesMap.getTeams().get(TerritoriesPanel.ChoiceForIndex(i)),
								relevantTerritory);
					}
					
					// Draw
					try {
						TerritoriesMap.Draw("BaseMap.png", ActionPanel.GetImageDirectory());
					} catch (Exception ex) {
						System.out.println(ex.getLocalizedMessage());
					}
				}
			}
		}
	} // END ActionPanelListener
	
	/**
	 * Detaches a territory from its owning team.
	 * @param teamsList The territories team list.
	 * @param territory The relevant territory.
	 */
	private static void DetachTerritory(ArrayList<Team> teamsList, Territory territory) {
		for (int i = 0; i < teamsList.size(); i++) {
			if(teamsList.get(i).OwnedTerritories.contains(territory)) {
				teamsList.get(i).OwnedTerritories.remove(territory);
			}
		}
	} // END DetachTerritory
	
	/**
	 * Attaches a territory to a desired team.
	 * @param team The relevant team.
	 * @param territory The relevant territory.
	 */
	private static void AttachTerritory(Team team, Territory territory) {
		team.OwnedTerritories.add(territory);
	} // END AttachTerritory
	
} // END MainDriver
