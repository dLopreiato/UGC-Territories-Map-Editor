package org.urbangaming.territories.client;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.urbangaming.territories.core.Team;
import org.urbangaming.territories.core.TerritoriesMap;
import org.urbangaming.territories.core.Territory;

/**
 * This class is the encapsulation of the information shown on the ownership tab.
 * @author Andrew Lopreiato
 * @version 1.0 12/25/2013
 */
public class OwnershipTab extends JScrollPane{
	
	// DATA MEMBERS
	private JPanel ScrollingPane_;
	private ArrayList<JComboBox<Team>> ChoiceList_;
	private static final long serialVersionUID = 1L;
	// END DATA MEMBERS
	
	OwnershipTab () {
		ReflectTerritoriesMap(null, null);
	}
	
	public void ReflectTerritoriesMap(TerritoriesMap map, TabbedDriver listenerOwner) {
		this.setVisible(false);
		if (map == null || listenerOwner == null)
			return; // if its null, don't add anything
		
		ScrollingPane_ = new JPanel(); 
		ScrollingPane_.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 2;
		ChoiceList_ = new ArrayList<JComboBox<Team>>(map.GetAmountOfTerritories());
		// for each territory in the territory manifest
		for (int i = 0; i < map.GetAmountOfTerritories(); i++) {
			gbc.gridy = i;
			Territory relevantTerritory = map.GetTerritory(i);
			
			// add the territory name to the panel
			ScrollingPane_.add(new JLabel(relevantTerritory.Name + "  "), gbc);
			
			// Create a new set of choices
			JComboBox<Team> territoryChoice = new JComboBox<Team>();
			// for each team in the team manifest
			for (int j = 0; j < map.GetAmountOfTeams(); j++) {
				// add the possible teams as choices
				territoryChoice.addItem(map.GetTeam(j));
			}
			// set the owner
			territoryChoice.setSelectedItem(map.GetTerritoryOwner(relevantTerritory));
			
			// add the listener
			territoryChoice.addItemListener(listenerOwner.GetOwnershipListener(relevantTerritory));
			
			// add the choices to the list
			ChoiceList_.add(territoryChoice);
			// add the choice to the panel
			ScrollingPane_.add(territoryChoice, gbc);
		}
		
		this.setViewportView(ScrollingPane_);
	} // END UpdateOptionsChoices

}
