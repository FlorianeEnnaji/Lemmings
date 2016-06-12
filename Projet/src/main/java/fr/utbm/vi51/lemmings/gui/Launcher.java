package fr.utbm.vi51.lemmings.gui;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fr.utbm.vi51.lemmings.agent.Lemming;
import fr.utbm.vi51.lemmings.model.Body;
import fr.utbm.vi51.lemmings.model.Environment;
import fr.utbm.vi51.lemmings.model.LemmingBody;
import fr.utbm.vi51.lemmings.utils.MoveDirection;

/**
 * @author antonin.waltz@utbm.fr, floriane.ennaji@utbm.fr, lucille.gomez@utbm.fr, romain.thibaud@utbm.fr
 * @brief Launcher which link the GUI with the Game
 */
public class Launcher extends JFrame implements Runnable {

	private static final long serialVersionUID = -1254993287812657782L;
	private Environment environment = null;
	
	/**
	 * The representation of the environment
	 */
	private GUI level;

	/**
	*@brief The launcher make the link between the GUI and the Game
	*@param env (Environment) the environment of the game
	*@see JFrame
	*Constructor of the class
	*/
	public Launcher(Environment env) {
		super("VI51 Project - Lemmings");

		setGUI();

		launch(env);
		
		new Thread(level).start();
	}
	
	/**
	*@brief Setting the environment and start the thread
	*@param env (Environment) the environment of the game
	*@return void
	*/
	private void launch(Environment env) {
		this.setEnvironment(env);
		new Thread(this).start();
	}
	
	/**
	*@brief Setting the GUI
	*@return void
	*Setting the GUI with size, layout, etc.
	*/
	private void setGUI() {

		level = new GUI(this);
		level.setSize(600, 600);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(605,620);
		setResizable(false);



		setLayout(new BorderLayout());
		add(level, BorderLayout.CENTER);


		setLocationRelativeTo(null);
		setVisible(true);
		level.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {}

			@Override
			public void keyReleased(KeyEvent arg0) {}

			@Override
			public void keyPressed(KeyEvent key) {

			}
		});

	}
	
	/**
	 * @brief Creation of the lemmings game and temporization between each step
	 */
	@Override
	public void run() {
		environment.createLemmingGame();
		while (true) {
			try {		
				Thread.sleep(1000);		
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

}
