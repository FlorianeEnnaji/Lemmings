package fr.utbm.vi51.lemmings;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import fr.utbm.vi51.lemmings.agent.Lemming;
import fr.utbm.vi51.lemmings.model.Environment;
import fr.utbm.vi51.lemmings.model.PerceivableObject;
import fr.utbm.vi51.lemmings.model.LemmingBody;
import fr.utbm.vi51.lemmings.model.SpawnMapping;
import io.sarl.lang.core.Agent;

public class Game {

	public static void main(String[] args) throws IOException {
		try{
			File file = new File("./src/img/sample2.bmp");
			BufferedImage image = ImageIO.read(file);
			Environment env = new Environment(image);
			env.createLemming();
			
			/*
			 * Uncomment following to save QTable
			saveQTableInfos(env.getQTable());
			*/
			/*
			 * Uncomment following to get QTable from a previous saving
			QTable otherQT = getQTableFromFile();
			*/
			

		}
		catch (Exception e) {
			System.out.println("Wrong file path");
		}
		
			
	}
	
	/**
	 * Function that saves a QTable, in order to reuse it in another instance.
	 * 
	 * @param the QTable we want to save
	 * */
	public static void saveQTableInfos(QTable qt){
		ArrayList<List<PerceivableObject>> state = qt.getState();
		ArrayList<float[]> coef = qt.getCoef();
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
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(state);
			oos.close();
			
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
		
		ArrayList<List<PerceivableObject>> state = new ArrayList<>();
		ArrayList<float[]> coef = new ArrayList<>();
		ArrayList<String[]> stringCoef = new ArrayList<>();
		
		try {
			FileInputStream fis = new FileInputStream("state.dat");
			ObjectInputStream iis = new ObjectInputStream(fis);
			state = (ArrayList<List<PerceivableObject>>) iis.readObject();
			iis.close();

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
				System.out.print(list[i] + " ");
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


