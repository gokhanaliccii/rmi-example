package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import enums.FilterTypes;
import filter.IFilter;

public class Ui extends JFrame {

	private static final long serialVersionUID = 2054533248682233230L;

	private JMenu filterMenu = new JMenu("Image Filters");

	private ImagePanel imagePanel;

	public Ui() {

		super("Remote Image Filtering");

		imagePanel = new ImagePanel(Ui.class.getResource("unnamed.png"));

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		filterMenu.setMnemonic('I');

		JMenuItem originalMenuItem = new JMenuItem("Original");
		originalMenuItem.setMnemonic('O');

		originalMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent action) {
				imagePanel.displayOriginalImage();
			}

		});

		JMenuItem invertMenuItem = createMenuItem("Invert", 'I',
				FilterTypes.InvertFilter);
		JMenuItem sharpenMenuItem = createMenuItem("Sharpen", 'S',
				FilterTypes.SharpenFilter);
		JMenuItem blurMenuItem = createMenuItem("Blur", 'B',
				FilterTypes.BlurFilter);

		filterMenu.add(originalMenuItem);
		filterMenu.add(invertMenuItem);
		filterMenu.add(sharpenMenuItem);
		filterMenu.add(blurMenuItem);

		menuBar.add(filterMenu);
		getContentPane().add(imagePanel, BorderLayout.CENTER);

	}

	public JMenuItem createMenuItem(String menuItemName, char mnemonic,
			final FilterTypes filter) {
		JMenuItem menuItem = new JMenuItem(menuItemName);
		menuItem.setMnemonic(mnemonic);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent action) {

				try {
					imagePanel.applyFilter(filter);
					System.out.println(filter.name() + "  ok!");
				} catch (Exception e) {
					System.out.println(filter.name() + "  fail!");
				}
			}

		});
		return menuItem;
	}

	public static void main(String args[]) {

		Ui application = new Ui();
		application.setDefaultCloseOperation(EXIT_ON_CLOSE);
		application.pack();
		application.setVisible(true);
	}
}

class ImagePanel extends JPanel {

	private static final long serialVersionUID = -5832834915586615457L;
	private Image image;
	private BufferedImage displayImage;
	private BufferedImage originalImage;

	public ImagePanel(URL imageURL) {

		image = Toolkit.getDefaultToolkit().createImage(imageURL);
		MediaTracker mediaTracker = new MediaTracker(this);
		mediaTracker.addImage(image, 0);

		try {
			mediaTracker.waitForAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		originalImage = new BufferedImage(image.getWidth(null),
				image.getHeight(null), BufferedImage.TYPE_INT_RGB);
		displayImage = originalImage;
		Graphics2D graphics = displayImage.createGraphics();
		graphics.drawImage(image, null, null);

	}

	public void applyFilter(FilterTypes filter) throws Exception {

		Registry registry = LocateRegistry.getRegistry("localhost");
		IFilter ifilter = (IFilter) registry.lookup(filter.getPath());

		displayImage = ifilter.processImage(displayImage);
		repaint();
	}

	public void displayOriginalImage() {
		displayImage = new BufferedImage(image.getWidth(null),
				image.getHeight(null), BufferedImage.TYPE_INT_RGB);

		Graphics2D graphics = displayImage.createGraphics();
		graphics.drawImage(originalImage, null, null);
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D graphics = (Graphics2D) g;
		graphics.drawImage(displayImage, 0, 0, null);
	}

	public Dimension getPreferredSize() {
		return new Dimension(displayImage.getWidth(), displayImage.getHeight());
	}

	public Dimension getMinimumSize() {
		return getPreferredSize();
	}
}
