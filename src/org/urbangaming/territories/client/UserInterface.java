package org.urbangaming.territories.client;
import java.awt.event.InputEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;
import org.urbangaming.territories.core.TerritoriesMap;

/**
 * This class provides the graphical user interface for the territories map client.
 * @author Andrew Lopreiato
 * @version 1.0 12/4/2013
 */
public class UserInterface extends JFrame {

	// DATA MEMBERS
	private JScrollPane TerritoriesPanelScrollPane_;
	private TerritoriesPanel TerritoriesPanel_;
	private JMenuBar MenuBar_;
	private static final long serialVersionUID = 1L;
	// END DATA MEMBERS

	public UserInterface() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Territories Map Creator");
		setLocation(0, 0);
		setSize(640, 480);
		
		addContentComponents();
		
		addMenuBar();
	}
	
	private void addContentComponents() {
		// initialize all the content driven components
		TerritoriesPanelScrollPane_ = new JScrollPane();
		TerritoriesPanel_ = new TerritoriesPanel();
		
		TerritoriesPanelScrollPane_.setViewportView(TerritoriesPanel_);
		
		// and finally add everything
		add(TerritoriesPanelScrollPane_);
	}
	
	private void addMenuBar() {
		// initialize all the menu bar buttons
		MenuBar_ = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu helpMenu = new JMenu("Help");
		JMenuItem openOption = new JMenuItem("Open Map...");
		JMenuItem saveMapOption = new JMenuItem("Save Map");
		JMenuItem saveMapAsOption = new JMenuItem("Save Map As...");
		JMenuItem setBaseImageOption = new JMenuItem("Set Base Image...");
		JMenuItem drawMapOption = new JMenuItem("Draw Map");
		JMenuItem drawMapAsOption = new JMenuItem("Draw Map As...");
		JMenuItem aboutOption = new JMenuItem("About");
		
		// set the file menu options
		fileMenu.add(openOption);
		fileMenu.addSeparator();
		fileMenu.add(saveMapOption);
		fileMenu.add(saveMapAsOption);
		fileMenu.addSeparator();
		fileMenu.add(setBaseImageOption);
		fileMenu.add(drawMapOption);
		fileMenu.add(drawMapAsOption);
		
		// set the help menu options
		helpMenu.add(aboutOption);
		
		// set the option listeners
		openOption.addActionListener(new EntryPoint.OpenListener());
		saveMapOption.addActionListener(new EntryPoint.SaveListener());
		saveMapAsOption.addActionListener(new EntryPoint.SaveAsListener());
		setBaseImageOption.addActionListener(new EntryPoint.SetBaseListener());
		drawMapOption.addActionListener(new EntryPoint.DrawListener());
		drawMapAsOption.addActionListener(new EntryPoint.DrawAsListener());
		aboutOption.addActionListener(new EntryPoint.AboutListener());
		
		// set all the keyboard shortcuts
		openOption.setAccelerator(KeyStroke.getKeyStroke('O', InputEvent.CTRL_DOWN_MASK));
		saveMapOption.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK));
		saveMapAsOption.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.SHIFT_DOWN_MASK));
		drawMapOption.setAccelerator(KeyStroke.getKeyStroke('D', InputEvent.CTRL_DOWN_MASK));
		drawMapAsOption.setAccelerator(KeyStroke.getKeyStroke('D', InputEvent.SHIFT_DOWN_MASK));
		
		// add all the menus
		MenuBar_.add(fileMenu);
		MenuBar_.add(helpMenu);
		
		// and finally set it as the menu bar
		this.setJMenuBar(MenuBar_);
	}
	
	public void UpdateTerritoriesPanel(TerritoriesMap tMap) {
		TerritoriesPanel_.UpdateOptionsChoices(tMap);
	}
	
	public int GetTerritoryChoice(int index) {
		return TerritoriesPanel_.ChoiceForIndex(index);
	}
}
