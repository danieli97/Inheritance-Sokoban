package Files;

import Mods.Mod_Config;

import java.awt.Image;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SokobanGUI extends JFrame implements ActionListener {

	// Attributes

	// Static
	private static final String LEFT = "LEFT";
	private static final String RIGHT = "RIGHT";
	private static final String UP = "UP";
	private static final String DOWN = "DOWN";
	private static final String RELOAD = "RELOAD";
	private static final String LOAD = "LOAD";
	private static final String EXIT = "EXIT";
	private static String yourpath;
	// Variable
	private Map<Integer, JLabel> levelMap;		// Map coordinate to label at that coordinate
	private Map<String, ImageIcon> imageIcons;	// Map Icon name to associated image icon
	private Board board;						// current Board
	private String filename;					// name of level file
	private Map<String, Modification> clonables;

	// Constructor
	public SokobanGUI() throws IOException {
		// initialize
		super("Sokoban");
		this.filename = "level00.txt";
		this.levelMap = new HashMap<Integer, JLabel>();
		// get path
		Path path = Paths.get("src\\Files\\SokobanGUI.java");
		path = path.toAbsolutePath().getParent().getParent();
		SokobanGUI.yourpath = path.toString() + "\\";
		// setup board
		this.clonables = Mod_Config.getClonables();
		this.board = new Board(this.filename, this.clonables);
		this.imageIcons = setImageIcons();
		this.setJMenuBar(this.makeMenu());
		this.initLevel();
	}

	// Methods

	// Setup
	private final void initLevel() {
		String title = "Sokoban";
		if (!this.filename.isEmpty()) {
			title += " (" + this.filename + ")";
		}
		this.setTitle(title);
		JPanel contentPanel = new JPanel();
		contentPanel.add(makeLevelPanel());
		contentPanel.add(makeButtonPanel());
		this.setContentPane(contentPanel);
		this.pack();
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		this.drawAll();
	}

	private Set<String> listFilesUsingDirectoryStream(String dir) throws IOException {
		Set<String> fileList = new HashSet<>();
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dir))) {
			for (Path path : stream) {
				if (!Files.isDirectory(path)) {
					fileList.add(path.getFileName().toString());
				}
			}
		}
		return fileList;
	}

	private final Map<String, ImageIcon> setImageIcons() throws IOException {
		/*
		sets all the image icons based on their names
		*/
		Map<String, ImageIcon> theIcons = new HashMap<>();

		Set<String> iconsList = listFilesUsingDirectoryStream(yourpath + "Icons");
		for (String currentImg : iconsList) {
			String modName = currentImg.substring(0, currentImg.length() - 4); // gets rid of ".java"
			ImageIcon img = new ImageIcon(yourpath + "Icons\\" + currentImg, modName);
			Image image = img.getImage(); // transform it to Image so it can be scaled
			Image newimg = image.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH); // scale it to 50x50 pixels
			img = new ImageIcon(newimg); // transform it back to ImageIcon
			theIcons.put(modName, img);
		}
		return theIcons;
	}

	private final JMenuBar makeMenu() {
		JMenuBar bar = new JMenuBar();
		JMenu menu = new JMenu("Sokoban");
		bar.add(menu);

		this.addMenuItem(menu, "Reload level", RELOAD);
		this.addMenuItem(menu, "Load level", LOAD);
		menu.addSeparator();
		this.addMenuItem(menu, "Exit", EXIT);
		return bar;
	}

	private final void addMenuItem(JMenu menu, String label, String action) {
		JMenuItem item = new JMenuItem(label);
		item.setActionCommand(action);
		item.addActionListener(this);
		menu.add(item);
	}

	private static JLabel makeLabel(String s) {
		Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 32);
		JLabel b = new JLabel(s);
		b.setPreferredSize(new Dimension(50, 50));
		b.setMaximumSize(b.getSize());
		b.setFont(font);
		b.setBackground(Color.WHITE);
		return b;
	}

	private JButton makeButton(String s, String cmd) {
		Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 16);
		JButton b = new JButton(s);
		b.setPreferredSize(new Dimension(50, 50));
		b.setMaximumSize(b.getSize());
		b.setFont(font);
		b.setBackground(Color.WHITE);
		b.setActionCommand(cmd);
		b.addActionListener(this);
		return b;
	}

	private JPanel makeLevelPanel() {
		int width = this.board.getWidth();
		int height = this.board.getHeight();

		JPanel p = new JPanel();
		p.setLayout(new GridLayout(height, width, 0, 0));

		for (Integer coord : this.board.getLocs().keySet()) {
			JLabel b = makeLabel("");
			p.add(b);
			this.levelMap.put(coord, b);
		}

		return p;
	}

	private JPanel makeButtonPanel() {
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(3, 3, 0, 0));

		// row 1
		p.add(makeLabel(""));
		JButton up = makeButton("U", UP);
		p.add(up);
		p.add(makeLabel(""));

		// row 2
		JButton left = makeButton("L", LEFT);
		p.add(left);
		p.add(makeLabel(""));
		JButton right = makeButton("R", RIGHT);
		p.add(right);

		// row 3
		p.add(makeLabel(""));
		JButton down = makeButton("D", DOWN);
		p.add(down);
		p.add(makeLabel(""));

		return p;
	}

	// Functions
	private void drawAll() {
		for (Integer coord : this.board.getLocs().keySet()) { 			// for each coord on board
			this.levelMap.get(coord).setIcon(null);
			ArrayList<Location> locs = this.board.getLocs().get(coord); // get the Location objects at that coord
			for (Location loc : locs) { 								// for each Location objects
				Modification mod = this.board.getModLocs().get(loc); 	// get the mod for this Location
				JLabel b = this.levelMap.get(this.board.getWidth() * mod.getLoc().getY() + mod.getLoc().getX());
				ImageIcon img = this.imageIcons.get(mod.img);
				b.setIcon(img);
			}
		}
	}

	// Overrides
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();

		switch (cmd) {
			case RELOAD:
				if (this.filename.isEmpty()) { // maybe dont need this if default will pass null
					this.board = new Board(null, this.clonables);
					this.initLevel();
				} else {
					this.board = new Board(this.filename, this.clonables);
					this.initLevel();
				}
				break;
			case LOAD:
				final JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new File(yourpath + "\\Levels"));
				int returnVal = fc.showOpenDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					this.board = new Board(file.getName(), this.clonables);
					this.filename = file.getName();
					this.initLevel();
				}
				break;
			case EXIT:
				this.dispose();
				break;
			default:
				if (!board.getIsWon()){
					board.notifyObservers(cmd);
				}
		}

		// redraw
		if (!board.getIsWon()){
			this.drawAll();
			if (this.board.isSolved()) {
				JOptionPane.showMessageDialog(this, "You won!");
			}
		}
	}		

	// Main
	public static void main(String[] args) throws IOException {
		SokobanGUI gui = new SokobanGUI();
		gui.setVisible(true);
	}

}
