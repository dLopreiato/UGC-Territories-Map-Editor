package org.urbangaming.territories.client;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import org.urbangaming.territories.core.Team;
import org.urbangaming.territories.core.TerritoriesMap;

/**
 * This class encapsulates all of the information shown on the team tab.
 * @author Andrew Lopreiato
 * @version 1.1 12/27/2013
 */
public class TeamTab extends JScrollPane {
	
	// DATA MEMBERS
	private JPanel ScrollingPane_;
	private HashMap<Team, JTextField> TextMap_;
	//private HashMap<Team, Color> ColorMap_;
	private static final long serialVersionUID = 1L;
	// END DATA MEMBERS
	
	public TeamTab() {
		ReflectTerritoriesMap(null, null);
	}
	
	public void ReflectTerritoriesMap(TerritoriesMap tMap, TabbedDriver listenerOwner) {
		this.setVisible(false);
		if (tMap == null || listenerOwner == null)
			return; // if its null, don't add anything
		ScrollingPane_ = new JPanel();
		ScrollingPane_.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		TextMap_ = new HashMap<Team, JTextField>();
		
		// the first team is always unowned
		JTextField firstField = new JTextField(tMap.GetTeam(0).Name);
		firstField.setEditable(false);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 4;
		gbc.gridy = 0;
		ScrollingPane_.add(firstField, gbc);
		
		// here's the rest
		for (int i = 1; i < tMap.GetAmountOfTeams(); i++) {
			Team relevantTeam = tMap.GetTeam(i);
			gbc.gridy = i;
			gbc.gridwidth = 1;
			JTextField newField = new JTextField(relevantTeam.Name);
			JButton saveButton = new JButton("Rename");
			saveButton.addActionListener(new TeamSaveListener(relevantTeam));
			JButton colorChooserButton = new JButton("Color");
			colorChooserButton.addActionListener(listenerOwner.GetTeamColorListener(relevantTeam));
			JButton deleteButton = new JButton("Delete");
			deleteButton.addActionListener(listenerOwner.GetDeleteTeamListener(relevantTeam));
			
			ScrollingPane_.add(newField, gbc);
			TextMap_.put(relevantTeam, newField);
			ScrollingPane_.add(saveButton, gbc);
			ScrollingPane_.add(colorChooserButton, gbc);
			ScrollingPane_.add(deleteButton, gbc);
		}
		
		JButton addButton = new JButton("Create new");
		addButton.addActionListener(listenerOwner.GetAddTeamListener());
		gbc.gridy = tMap.GetAmountOfTeams() + 1;
		gbc.gridwidth = 2;
		gbc.gridx = 2;
		ScrollingPane_.add(addButton, gbc);
		
		
		this.setViewportView(ScrollingPane_);
	}
	
	private void RefreshTeamName(Team team) {
		team.Name = TextMap_.get(team).getText();
	}
	
	private class TeamSaveListener implements ActionListener {
		private Team Team_;
		public TeamSaveListener(Team team) {
			Team_ = team;
		}
		@Override
		public void actionPerformed(ActionEvent ae) {
			RefreshTeamName(Team_);
		}
	}
}
