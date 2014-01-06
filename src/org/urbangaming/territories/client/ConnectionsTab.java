package org.urbangaming.territories.client;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.urbangaming.territories.core.ConnectionLine;
import org.urbangaming.territories.core.TerritoriesMap;

/**
 * The methods and data needed to run a panel that edits connections.
 * @author Andrew Lopreiato
 * @version 1.0 1/5/2014
 */
public class ConnectionsTab extends JPanel {
	// DATA MEMBERS
	private JPanel ConnectionBar_;
	private JLabel IterateLabel_;
	private ConnectionEditor ConnectionEditor_;
	private TerritoriesMap TerritoriesMap_;
	private Integer ConnectionIterateNumber_;
	private static final long serialVersionUID = 1L;
	// END DATA MEMBERS
	
	public ConnectionsTab() {
		ReflectTerritoriesMap(null, null);
	}
	
	public void ReflectTerritoriesMap(TerritoriesMap tMap, TabbedDriver listenerOwner) {
		this.setVisible(false);
		this.removeAll();
		if (tMap == null || listenerOwner == null)
			return; // if its null, don't add anything
		TerritoriesMap_ = tMap;
		ConnectionIterateNumber_ = 0;
		
		this.setLayout(new BorderLayout());
		
		ConnectionBar_ = new JPanel();
		
		JButton previousButton = new JButton("<-");
		previousButton.addActionListener(new IterateConnectionListener(true));
		ConnectionBar_.add(previousButton);
		
		IterateLabel_ = new JLabel("1");
		ConnectionBar_.add(IterateLabel_);
		
		JButton nextButton = new JButton("->");
		nextButton.addActionListener(new IterateConnectionListener(false));
		ConnectionBar_.add(nextButton);
		
		JCheckBox toggleWrapping = new JCheckBox("Wraps");
		toggleWrapping.addItemListener(new WrappingListener());
		ConnectionBar_.add(toggleWrapping);
		
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new SaveListener());
		ConnectionBar_.add(saveButton);
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new DeleteListener());
		ConnectionBar_.add(deleteButton);
		
		JButton createButton = new JButton("New Connection");
		createButton.addActionListener(new AddListener());
		ConnectionBar_.add(createButton);
		
		ConnectionEditor_ = new ConnectionEditor(tMap.GetBaseImage(),
				listenerOwner.GetDefaultMouseListener(),
				listenerOwner.GetHoverMouseListener());
		
		add(ConnectionEditor_, BorderLayout.CENTER);
		ConnectionEditor_.SetConnection(tMap.GetConnectionLine(0));
		add(ConnectionBar_, BorderLayout.NORTH);
		
	}
	
	private class IterateConnectionListener implements ActionListener {
		private boolean Previous_;
		public IterateConnectionListener(boolean forPreviousButton) {
			Previous_ = forPreviousButton;
		}
		@Override
		public void actionPerformed(ActionEvent ae) {
			if (Previous_ && ConnectionIterateNumber_ > 0) {
				ConnectionIterateNumber_--;
			}
			if (!Previous_ && ConnectionIterateNumber_ < (TerritoriesMap_.GetAmountOfConnectionLines() - 1)) {
				ConnectionIterateNumber_++;
			}
			RefreshIterateNumber();
		}
	}
	
	private class SaveListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			ConnectionLine relevantLine = TerritoriesMap_.GetConnectionLine(ConnectionIterateNumber_);
			ConnectionLine newLine = ConnectionEditor_.GetConnection();
			relevantLine.X1 = newLine.X1;
			relevantLine.X2 = newLine.X2;
			relevantLine.Y1 = newLine.Y1;
			relevantLine.Y2 = newLine.Y2;
			relevantLine.Wrapping = newLine.Wrapping;
		}
	}
	
	private class DeleteListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			TerritoriesMap_.RemoveConnectionLine(TerritoriesMap_.GetConnectionLine(ConnectionIterateNumber_));
			if (ConnectionIterateNumber_ > 0) {
				ConnectionIterateNumber_--;
			}
			RefreshIterateNumber();
		}
	}
	
	private class AddListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			TerritoriesMap_.AddConnectionLine(new ConnectionLine(
					0, 0,
					TerritoriesMap_.GetBaseImage().getWidth() / 2, TerritoriesMap_.GetBaseImage().getHeight() / 2));
			ConnectionIterateNumber_ = TerritoriesMap_.GetAmountOfConnectionLines() - 1;
			RefreshIterateNumber();
		}
	}
	
	private class WrappingListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent ie) {
			ConnectionEditor_.ToggleWrapping();
		}
	}
	
	private void RefreshIterateNumber() {
		Integer displayInt = ConnectionIterateNumber_ + 1;
		IterateLabel_.setText(displayInt.toString());
		ConnectionEditor_.SetConnection(TerritoriesMap_.GetConnectionLine(ConnectionIterateNumber_));
		repaint();
	}
}
