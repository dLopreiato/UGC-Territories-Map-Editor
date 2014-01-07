package org.urbangaming.territories.client;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.urbangaming.territories.core.TerritoriesMap;
import org.urbangaming.territories.core.Territory;

/**
 * This class is the encapsulation of the information shown on the territories tab.
 * @author Andrew Lopreiato
 * @version 1.0.2 1/6/2014
 */
public class TerritoriesTab extends JPanel {
	// DATA MEMBERS
	private JPanel TerritoryBar_;
	private TerritoryEditor TerritoryEditor_;
	private JTextField TerritoryName_;
	private Territory CurrentTerritory_;
	private TerritoriesMap TerritoriesMap_;
	private static final long serialVersionUID = 1L;
	// END DATA MEMBERS
	
	public TerritoriesTab() {
		ReflectTerritoriesMap(null, null);
	}
	
	public void ReflectTerritoriesMap(TerritoriesMap tMap, TabbedDriver listenerOwner) {
		this.setVisible(false);
		this.removeAll();
		if (tMap == null || listenerOwner == null)
			return; // if its null, don't add anything
		try {
			tMap.GetBaseImage();
		} catch (NullPointerException npe) {
			return;
		}
		TerritoriesMap_ = tMap;
		if (CurrentTerritory_ == null) {
			try {
				CurrentTerritory_ = tMap.GetTerritory(0);
			} catch (Exception e) {
				CurrentTerritory_ = new Territory("New Territory");
			}
		}
		
		this.setLayout(new BorderLayout());
		
		TerritoryBar_ = new JPanel();
		JComboBox<Territory> currentTerritory = new JComboBox<Territory>();
		for (int i = 0; i < tMap.GetAmountOfTerritories(); i++) {
			currentTerritory.addItem(tMap.GetTerritory(i));
		}
		currentTerritory.addItemListener(new PolygonSelectionListener());
		currentTerritory.setSelectedItem(CurrentTerritory_);
		TerritoryBar_.add(currentTerritory);
		
		TerritoryName_ = new JTextField(10);
		TerritoryBar_.add(TerritoryName_);
		
		JButton saveButton = new JButton("Save Data");
		// Listeners must be added in this order
		saveButton.addActionListener(listenerOwner.GetSyncInterfaceListener());
		saveButton.addActionListener(new SavePolygonListener());
		TerritoryBar_.add(saveButton);
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(listenerOwner.GetSyncInterfaceListener());
		deleteButton.addActionListener(new DeleteListener());
		TerritoryBar_.add(deleteButton);
		
		JButton addButton = new JButton("Add New");
		addButton.addActionListener(new AddListener());
		TerritoryBar_.add(addButton);
		
		TerritoryEditor_ = new TerritoryEditor(tMap.GetBaseImage(),
				listenerOwner.GetDefaultMouseListener(),
				listenerOwner.GetHoverMouseListener());
		
		SyncToCurrentTerritory();
		
		add(TerritoryEditor_, BorderLayout.CENTER);
		add(TerritoryBar_, BorderLayout.NORTH);
	}
	
	private class PolygonSelectionListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent ie) {
			CurrentTerritory_ = (Territory)ie.getItem();
			SyncToCurrentTerritory();
		}
	}
	
	private class SavePolygonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			if (!TerritoriesMap_.TerritoryExists(CurrentTerritory_)) {
				TerritoriesMap_.AddTerritory(CurrentTerritory_, TerritoriesMap_.GetTeam(0));
			}
			CurrentTerritory_.Region = TerritoryEditor_.GetPolygon();
			CurrentTerritory_.Name = TerritoryName_.getText();
		}
	}
	
	private class DeleteListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			TerritoriesMap_.RemoveTerritory(CurrentTerritory_);
			if (TerritoriesMap_.GetAmountOfTerritories() > 0) {
				CurrentTerritory_ = TerritoriesMap_.GetTerritory(0);
			} else {
				CurrentTerritory_ = new Territory("New Territory");
			}
			SyncToCurrentTerritory();
		}
	}
	
	private class AddListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			CurrentTerritory_ = new Territory("New Territory");
			SyncToCurrentTerritory();
		}
	}
	
	private void SyncToCurrentTerritory() {
		TerritoryEditor_.SetPolygon(CurrentTerritory_.Region);
		TerritoryName_.setText(CurrentTerritory_.Name);
	}
}
