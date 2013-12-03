package org.urbangaming.territories.client;
import org.urbangaming.territories.core.TerritoriesMap;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Isolates all of the data and behavior that would be seen in the owner list portion of the main user interface. The
 * owner list is the series of names with choices next to them to allow a user to edit the territory's owner.
 * @author Andrew Lopreiato
 * @version 1.2 12/1/2013
 */
public class TerritoriesPanel extends JPanel {
	
	// DATA MEMBERS
	private ArrayList<JComboBox<String>> ChoiceList_ = null;
	private static final long serialVersionUID = 3L;
	// END DATA MEMBERS

	/**
	 * Constructs an empty territories panel.
	 */
	TerritoriesPanel() {
		UpdateOptionsChoices(null);
	} // END TerritoriesPanel
	
	/**
	 * Changes the viewed list of territories to a give territories map.
	 * @param map The desired territories map.
	 */
	public void UpdateOptionsChoices(TerritoriesMap map) {
		this.setVisible(false);
		this.removeAll();
		GridLayout customGrid = new GridLayout();
		customGrid.setColumns(2);
		this.setLayout(customGrid);
		if (map == null)
			return; // if its null, don't add anything
		ChoiceList_ = new ArrayList<JComboBox<String>>(map.GetAmountOfTerritories());
		customGrid.setRows(map.GetAmountOfTerritories());
		// for each territory in the territory manifest
		for (int i = 0; i < map.GetAmountOfTerritories(); i++) {
			// add the territory name to the panel
			this.add(new JLabel(map.GetTerritory(i).Name));
			
			// Create a new set of choices
			JComboBox<String> territoryChoice = new JComboBox<String>();
			// for each team in the team manifest
			for (int j = 0; j < map.GetAmountOfTeams(); j++) {
				// add the possible teams as choices
				territoryChoice.addItem(map.GetTeam(j).Name);
				
				// if this territory is owned by relevant team
				if (map.GetTerritoryOwner(map.GetTerritory(i)).equals(map.GetTeam(j))) {
					// show this team as the selected team
					territoryChoice.setSelectedIndex(j);
				}
			}
			
			// add the choices to the list
			ChoiceList_.add(territoryChoice);
			// add the choice to the panel
			this.add(territoryChoice);
		}
		
		this.setVisible(true);
	} // END UpdateOptionsChoices
	
	/**
	 * Returns the current choice for a territory given by index.
	 * @param index The relevant territory.
	 * @return It's given owner.
	 */
	public int ChoiceForIndex(int index) {
		return ChoiceList_.get(index).getSelectedIndex();
	} // END ChoiceForIndex
	
}
