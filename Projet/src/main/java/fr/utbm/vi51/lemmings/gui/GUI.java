package fr.utbm.vi51.lemmings.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import fr.utbm.vi51.lemmings.Game;
import fr.utbm.vi51.lemmings.model.Environment;
import fr.utbm.vi51.lemmings.model.LemmingBody;
import fr.utbm.vi51.lemmings.model.WorldPixel;

public class GUI extends JPanel implements Runnable{
	
	private static final long serialVersionUID = 123689456945974264L;
	
	public static final int CELL_DIM = 60;
	
	private Launcher launcher;
	private Image entry;
	private Image empty;
	private Image dig;
	private Image exit;
	private Image climb;
	private Image ground;

	public GUI(Launcher launcher) {
		super();
		this.launcher = launcher;
		
		entry = (new ImageIcon("./src/img/entry.png")).getImage();
		empty = (new ImageIcon("./src/img/empty.png")).getImage();
		dig = (new ImageIcon("./src/img/dig.png")).getImage();
		exit = (new ImageIcon("./src/img/exit.png")).getImage();
		climb = (new ImageIcon("./src/img/climb.png")).getImage();	
		ground = (new ImageIcon("./src/img/ground.png")).getImage();

		
		setFocusable(true);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(launcher != null) {
			Environment env = launcher.getEnvironment();
			WorldPixel[][] map = env.getWorld();
			if(map != null && env.getHeight() > 0 && env.getWidth() > 0) {
				for(int y = 0; y < env.getHeight() ; y++) {
					for(int x = 0; x < env.getWidth() ; x++) {
						WorldPixel pixel = map[x][y];
						Image image = null;
						if (pixel.isEntry()) {
							image = entry;
						} else if (pixel.isEmpty()) {
							image = empty;
						} else if (pixel.isDiggable()) {
							image = dig;
						} else if (pixel.isExit()){
							image = exit;
						} else if (pixel.isClimbable()){
							image = climb;
						} else {
							image = ground;
						}
						if(image != null)
							g.drawImage(image, x * CELL_DIM, y * CELL_DIM, this);

					}
				}
			}
		
			Map<UUID, LemmingBody> lemmingsBodies = env.getAgentBodies();
			
			if(!lemmingsBodies.isEmpty()) {
				for ( Entry<UUID, LemmingBody> lemming : lemmingsBodies.entrySet()){ 
					if(lemming != null) {
						//System.out.println((int)lemming.getValue().getPosition().getX());
						Image img = (new ImageIcon("./src/img/lemming.png")).getImage();	
						g.drawImage(img, (int)lemming.getValue().getPosition().getX()*CELL_DIM,(int)lemming.getValue().getPosition().getY()*CELL_DIM, this);
					}
				}
			}
		}
	}
	
	/**TODO*/
	private Image getLemmingImage(LemmingBody lemmingBody) {
		return null;
	}

	@Override
	public void run() {
		
		while(true) {
			repaint();
			try {
				Thread.currentThread().sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
}


