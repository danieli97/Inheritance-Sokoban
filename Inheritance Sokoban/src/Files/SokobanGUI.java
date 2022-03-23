package Files;

import Mods.*;
import java.awt.Color;
import java.lang.*;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
// import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SokobanGUI extends JFrame implements ActionListener {
	
	private static final String LEFT = "LEFT";
	private static final String RIGHT = "RIGHT";
	private static final String UP = "UP";
	private static final String DOWN = "DOWN";
	private static final String RELOAD = "RELOAD";
	private static final String LOAD = "LOAD";
	private static final String EXIT = "EXIT";
	
	private String filename;
	private Map<Location, JLabel> levelMap;
	private Board board;
	
	/*
	 * ================================================================
	 *  Going to need to make a lists of things related to all objects 
	 * ================================================================
	 */
	
	private List<? extends Modification> objects;
	
	public Map<String, ImageIcon> imageIcons;	
	
	public String yourPath;
	
	public SokobanGUI(String path) throws IOException {
		
		super("Sokoban");
		this.board = new Board("level00.txt");
		this.yourPath = path;
		this.filename = "";
		this.levelMap = new HashMap<>();
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
		// loop through all the objects and draw them all using generalized drawAny
		/*
		this.drawWalls();
		this.drawStorage();
		this.drawBoxes();
		this.drawPlayer();
		*/
	}
	
	// sets all the image icons based on their names
	private final Map<String, ImageIcon> setImageIcons() throws IOException {
		Map<String, ImageIcon> theIcons = new HashMap<>();
		
		FileGetter fg = new FileGetter();
		
		Set<String> modsList = fg.listFilesUsingDirectoryStream(this.yourPath + "Mods");
		for (String currentMod : modsList) {
			String modName = currentMod.substring(0, currentMod.length()-5); // gets rid of ".java"
			if (!modName.equals("Modification")) {
				ImageIcon img = createImageIcon(modName + ".png", modName);
				theIcons.put(modName, img);
			}
		}
		return theIcons;
	}
	
	private final ImageIcon createImageIcon(String file, String description) {
		java.net.URL imgURL = getClass().getResource(this.yourPath + "Icons\\" + file);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			System.err.println("Couldn't find file: " + this.yourPath + "Icons\\" + file);
			return null;
		}
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
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Location loc = new Location(x, y);
				JLabel b = makeLabel("");
				p.add(b);
				this.levelMap.put(loc, b);
			}
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
		for (int i = 0; i < this.objects.size(); i++) {
			Location loc = this.objects.get(i).getLoc();
			JLabel b = this.levelMap.get(loc);
			String theName = objects.get(i).getClass().getSimpleName();
			if (theName.equals("Box") || theName.equals("Storage")) {
				// check if box on storage
				ImageIcon img = this.imageIcons.get(theName);
				b.setIcon(img);
			} else {
				ImageIcon img = this.imageIcons.get(theName);
				b.setIcon(img);
			}
			
			// be sure that this works for a box on a storage
			// change this part to work not only for storage & boxes but for any "Floor type" object & boxes (also players?)
			/*
			if (the_name.equals("Box")) {
				if (this.board.hasStorage(loc)) {
					b.setIcon(this.boxAndStorageIcon);
				}
				else {
					b.setIcon(this.boxIcon);
				}
			}
			else if (the_name.equals("Storage")){
				if (this.board.hasBox(loc)) {
					b.setIcon(this.boxAndStorageIcon);
				}
				else {
					b.setIcon(this.storageIcon);
				}
			}
			else {
				b.setIcon(this.wallIcon);	// somehow access the icon for the specific object type
			}
			*/
		}
	}
	
	/*
	private void drawWalls() {
		List<Wall> walls = this.board.getWalls();
		for (Wall w : walls) {
			Location loc = w.getLoc();
			JLabel b = this.levelMap.get(loc);
			b.setIcon(this.wallIcon);
		}
	}
	
	private void drawStorage() {
		List<Storage> storage = this.board.getStorage();
		for (Storage s : storage) {
			Location loc = s.getLoc();
			JLabel b = this.levelMap.get(loc);
			if (this.board.hasBox(loc)) {
				b.setIcon(this.boxAndStorageIcon);
			}
			else {
				b.setIcon(this.storageIcon);
			}
			
		}
	}
	
	private void drawBoxes() {
		List<Box> boxes = this.board.getBoxes();
		for (Box box : boxes) {
			Location loc = box.getLoc();
			JLabel b = this.levelMap.get(loc);
			if (this.board.hasStorage(loc)) {
				b.setIcon(this.boxAndStorageIcon);
			}
			else {
				b.setIcon(this.boxIcon);
			}
			
		}
	}
	
	private void drawPlayer() {
		Location loc = this.board.getPlayer().location();
		JLabel b = this.levelMap.get(loc);
		b.setIcon(this.playerIcon);
	}
	*/
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String cmd = e.getActionCommand();

		switch (cmd){
			case RELOAD:
				if (this.filename.isEmpty()) {	// maybe dont need this if default will pass null
					this.board = new Board(null);
					this.initLevel();
				}
				else {
					this.board = new Board(this.filename);
					this.initLevel();
				}
				break;
			case LOAD:
				Path path = FileSystems.getDefault().getPath("src", "sokoban");		// will probably need to change this line
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

		// update the locs
		// this.board.updateLocs();
		// redraw
		this.drawAll();
	}
	
	public static void main(String[] args) throws IOException {
		
		String yourPath = "C:\\Users\\brian\\OneDrive\\Desktop\\UNI 1\\Year 4\\W2022\\CISC 499\\Inheritance-Sokoban\\Inheritance Sokoban\\src\\";

		SokobanGUI gui = new SokobanGUI(yourPath);
		gui.setVisible(true);
		
	}

}