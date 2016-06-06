package fr.utbm.vi51.lemmings;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import fr.utbm.vi51.lemmings.agent.Lemming;
import fr.utbm.vi51.lemmings.model.Environment;
import fr.utbm.vi51.lemmings.model.PerceivableObject;
import fr.utbm.vi51.lemmings.model.LemmingBody;
import fr.utbm.vi51.lemmings.model.SpawnMapping;
import io.sarl.lang.core.Agent;

/**
 * Game is the class in which we create the environment and launch the simulation
 */
public class Game {
	private static Environment env;

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		try{
			
			//String[] worlds = {"./src/img/world1.bmp","./src/img/world2.bmp","./src/img/world3.bmp","./src/img/world4.bmp","./src/img/world5.bmp"};
			String[] worlds = {"./src/img/world1.bmp"};
			/*
			 * Comment following if you want to play
			 *
			
			launchLearning(worlds, 3); */
			
			/*
			 * Uncomment following if you want to play
			 */
			
			Random rand = new Random();
			int worldNb = rand.nextInt(worlds.length);
			
			worldNb = 0;
			File file = new File(worlds[worldNb]);
			BufferedImage image = ImageIO.read(file);
			env = new Environment(image);
			QTable qt = getQTableFromFile();
			env.setQTable(qt);
			//TODO Find a way to play
			env.createLemmingGame();
			

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Function that launches a loop to test lots of moving possibilities 
	 * for the Lemming regarding a list of world
	 * @throws IOException 
	 * 
	 * @param worlds the array of paths to world images
	 * @param nbLemmings the number of lemmings that will cross each world
	 * */
	public static void launchLearning(String[] worlds, int nbLemmings) throws IOException {
		File file;
		try {
			for (String world:worlds) {
				file = new File(world);
				BufferedImage image = ImageIO.read(file);
				
				env = new Environment(image);
				for (int i = 0; i < nbLemmings; i++){
					env.createLemming();
				}
			}
			//Learning is over
			saveQTableInfos(env.getQTable());
		}		
		catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	/**
	 * Function that saves a QTable, in order to reuse it in another instance.
	 * 
	 * @param qt the QTable we want to save
	 * */
	public static void saveQTableInfos(QTable qt){
		ArrayList<List<PerceivableObject>> state = qt.getStateList();
		ArrayList<float[]> coef = qt.getCoefList();
		ArrayList<String[]> stringCoef = new ArrayList<>();
		
		for (float[] list : coef) {
			String[] table = new String[list.length];
			for (int i = 0; i < list.length; i++){
				table[i] = Float.toString(list[i]);
			}
			stringCoef.add(table);
		}
		
		try {
			FileOutputStream fos = new FileOutputStream("state.dat");
		    XMLEncoder encoder=new XMLEncoder(fos);
			encoder.writeObject(state);
			encoder.close();
			
			FileOutputStream fos1 = new FileOutputStream("coef.dat");
			ObjectOutputStream oos1 = new ObjectOutputStream(fos1);
			oos1.writeObject(stringCoef);
			oos1.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	/**
	 * @return the QTable previously saved
	 * */
	public static QTable getQTableFromFile(){
		
		ArrayList<List<PerceivableObject>> state = new ArrayList<List<PerceivableObject>>();
		ArrayList<float[]> coef = new ArrayList<float[]>();
		ArrayList<String[]> stringCoef = new ArrayList<>();
	    
		try {
			FileInputStream fis = new FileInputStream("state.dat");
			XMLDecoder decoder =new XMLDecoder(fis);
			state = (ArrayList<List<PerceivableObject>>) decoder .readObject();
			decoder .close();

			FileInputStream fis1 = new FileInputStream("coef.dat");
			ObjectInputStream iis1 = new ObjectInputStream(fis1);
			stringCoef = (ArrayList<String[]>) iis1.readObject();
			iis1.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (String[] list : stringCoef) {
			float[] table = new float[list.length];
			for (int i = 0; i < list.length; i++){
				table[i] = Float.parseFloat(list[i]);
			}
			coef.add(table);
		}
		QTable qt = new QTable(state, coef);
		
		return qt;
		
	}
	
	private static class ApplicationMapping extends SpawnMapping {

		@Override
		public Class<? extends Agent> getAgentTypeForBody(LemmingBody body) {			
				return Lemming.class;
		}
	}
}


