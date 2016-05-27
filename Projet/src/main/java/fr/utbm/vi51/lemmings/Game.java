package fr.utbm.vi51.lemmings;
import java.awt.Point;
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

import fr.utbm.vi51.lemmings.model.Environment;
import fr.utbm.vi51.lemmings.model.LemmingBody;
import fr.utbm.vi51.lemmings.model.PerceivableObject;
import fr.utbm.vi51.lemmings.utils.enums.ActionEnum;
import fr.utbm.vi51.lemmings.utils.enums.MoveDirection;

public class Game {

	public static void main(String[] args) throws IOException {
		try{
			File file = new File("./src/img/sample2.bmp");
			BufferedImage image = ImageIO.read(file);
			Environment env = new Environment(image);
			env.createLemming();

			ArrayList<List<PerceivableObject>> state = new ArrayList<>();
			ArrayList<float[]> coef = new ArrayList<>();
			ArrayList<String[]> stringCoef = new ArrayList<>();
			
			List<PerceivableObject> model = new ArrayList<>();
			model.add(new PerceivableObject(new Point(1,2), false,true,false,false,false));
			model.add(new PerceivableObject(new Point(2,2), false,true,false,false,false));
			model.add(new PerceivableObject(new Point(3,2), false,true,false,false,false));
			state.add(model);
			System.out.println("Before serialization");
			for (List<PerceivableObject> list : state) {
				for (PerceivableObject obj : list){
					System.out.println(obj.getX() + " " + obj.getY());
				}
			}
			float[] tab = {(float) 1.1, (float) 2.2,(float)  3.3};
			coef.add(tab);
			tab[0] = (float) 4.4;
			coef.add(tab);
			for (float[] list : coef) {
				String[] table = new String[list.length];
				for (int i = 0; i < list.length; i++){
					table[i] = Float.toString(list[i]);
				}
				stringCoef.add(table);
			}
			for (String[] list : stringCoef) {
				for (int i = 0; i < list.length; i++){
					System.out.println(list[i]);
				}
			}

			ArrayList<List<PerceivableObject>> newState = new ArrayList<>();
			ArrayList<String[]> newCoef = new ArrayList<>();

			try {
				FileOutputStream fos = new FileOutputStream("state.dat");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(state);

				FileInputStream fis = new FileInputStream("state.dat");
				ObjectInputStream iis = new ObjectInputStream(fis);
				newState = (ArrayList<List<PerceivableObject>>) iis.readObject();
				
				FileOutputStream fos1 = new FileOutputStream("coef.dat");
				ObjectOutputStream oos1 = new ObjectOutputStream(fos1);
				oos1.writeObject(stringCoef);

				FileInputStream fis1 = new FileInputStream("coef.dat");
				ObjectInputStream iis1 = new ObjectInputStream(fis1);
				System.out.println("hello lol");
				System.out.println(stringCoef);
				System.out.println(((ArrayList<String[]>) iis1.readObject()).get(0)[0]);
				newCoef = (ArrayList<String[]>) iis1.readObject();
				System.out.println(newCoef.get(0)[0]);

			} catch (Exception e) {

			}

			System.out.println("After serialization");
			for (List<PerceivableObject> list : newState) {
				for (PerceivableObject obj : list){
					System.out.println(obj.getX() + " " + obj.getY());
				}
			}
			System.out.println(newCoef);
			for (String[] list : newCoef) {
				for (int i = 0; i < list.length; i++){
					System.out.println(list[i]);
				}
			}
		}
		catch (Exception e) {
			System.out.println("Wrong file path");
		}
		
			
	}
	
	

}
