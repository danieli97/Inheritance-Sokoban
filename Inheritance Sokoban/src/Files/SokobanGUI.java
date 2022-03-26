package Files;

import java.awt.Image;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
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

	// ATTRIBUTES
	// STATIC ATTRIBUTES
	private static final String LEFT = "LEFT";
	private static final String RIGHT = "RIGHT";
	private static final String UP = "UP";
	private static final String DOWN = "DOWN";
	private static final String RELOAD = "RELOAD";
	private static final String LOAD = "LOAD";
	private static final String EXIT = "EXIT";
	private static final String YOURPATH = "C:\\Users\\brian\\OneDrive\\Desktop\\UNI 1\\Year 4\\W2022\\CISC 499\\Inheritance-Sokoban\\Inheritance Sokoban\\src\\";
	// ATTRIBUTES
	private Map<Integer, JLabel> levelMap;
	private Map<String, ImageIcon> imageIcons;
	private Board board;
	private String filename;

	public SokobanGUI() throws IOException {

		super("Sokoban");
		this.board = new Board("level00.txt");
		this.filename = "";
		this.levelMap = new HashMap<Integer, JLabel>();
		this.imageIcons = setImageIcons();

		this.setJMenuBar(this.makeMenu());

		this.initLevel();
	}

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

	// sets all the image icons based on their names
	private final Map<String, ImageIcon> setImageIcons() throws IOException {
		Map<String, ImageIcon> theIcons = new HashMap<>();

		FileGetter fg = new FileGetter();

		Set<String> iconsList = fg.listFilesUsingDirectoryStream(YOURPATH + "Icons");
		for (String currentImg : iconsList) {
			String modName = currentImg.substring(0, currentImg.length() - 4); // gets rid of ".java"
			ImageIcon img = new ImageIcon(YOURPATH + "Icons\\" + currentImg, modName);
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

	private void drawAll() {

		for (Integer coord : this.board.getLocs().keySet()) { // for each coord on board
			this.levelMap.get(coord).setIcon(null);
			ArrayList<Location> locs = this.board.getLocs().get(coord); // get the Location objects at that coord
			for (Location loc : locs) { // for each Location objects
				ArrayList<Modification> mods = this.board.getModLocs().get(loc); // get the mods at this Location
				for (Modification mod : mods) { // for each mod at this location
					JLabel b = this.levelMap.get(this.board.getWidth() * mod.getLoc().getY() + mod.getLoc().getX());
					ImageIcon img = this.imageIcons.get(mod.img);
					b.setIcon(img);
				}
			}
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String cmd = e.getActionCommand();

		switch (cmd) {
			case RELOAD:
				if (this.filename.isEmpty()) { // maybe dont need this if default will pass null
					this.board = new Board(null);
					this.initLevel();
				} else {
					this.board = new Board(this.filename);
					this.initLevel();
				}
				break;
			case LOAD:
				Path path = FileSystems.getDefault().getPath("src", "sokoban"); // will probably need to change this
																				// line
				final JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(path.toFile());
				int returnVal = fc.showOpenDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					this.board = new Board(file.getName());
					this.filename = file.getName();
					this.initLevel();
				}
				break;
			case EXIT:
				this.dispose();
				break;
			default:
				board.notifyObservers(cmd);
		}

		// redraw
		this.drawAll();
		if (this.board.isSolved()) {
			JOptionPane.showMessageDialog(this, "You won!");
		}
	}

	public static void main(String[] args) throws IOException {

		SokobanGUI gui = new SokobanGUI();
		gui.setVisible(true);

	}

}
