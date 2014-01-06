package org.urbangaming.territories.client;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import org.urbangaming.territories.core.TerritoriesMap;
import org.urbangaming.territories.core.Territory;

/**
 * This class is the encapsulation of the information shown on the territories tab.
 * @author Andrew Lopreiato
 * @version 1.0.1 12/30/2013
 */
public class TerritoriesTab extends JPanel {
	// DATA MEMBERS
	private JPanel TerritoryBar_;
	private TerritoryEditor TerritoryEditor_;
	private Territory CurrentTerritory_;
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
		
		this.setLayout(new BorderLayout());
		
		TerritoryBar_ = new JPanel();
		JComboBox<Territory> currentTerritory = new JComboBox<Territory>();
		for (int i = 0; i < tMap.GetAmountOfTerritories(); i++) {
			currentTerritory.addItem(tMap.GetTerritory(i));
		}
		currentTerritory.addItemListener(new PolygonSelectionListener());
		TerritoryBar_.add(currentTerritory);
		
		JButton saveButton = new JButton("Save Data");
		saveButton.addActionListener(new SavePolygonListener());
		TerritoryBar_.add(saveButton);
		
		TerritoryEditor_ = new TerritoryEditor(tMap.GetBaseImage(),
				listenerOwner.GetDefaultMouseListener(),
				listenerOwner.GetHoverMouseListener());
		
		add(TerritoryEditor_, BorderLayout.CENTER);
		add(TerritoryBar_, BorderLayout.NORTH);
		
	}
	
	private class PolygonSelectionListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent ie) {
			CurrentTerritory_ = (Territory)ie.getItem();
			TerritoryEditor_.SetPolygon(CurrentTerritory_.Region);
		}
	}
	
	private class SavePolygonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			CurrentTerritory_.Region = TerritoryEditor_.GetPolygon();
		}
	}
}
