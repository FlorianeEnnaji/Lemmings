# Lemmings
VI51 Project - UTBM student project

Artificial Intelligence for lemmings based on Q-Learning, multi-agent approach.

Authors :
ENNAJI Floriane, 
GOMEZ Lucille, 
THIBAUD Romain, 
WALTZ Antonin 

##Resources needed
* Eclipse SARL (v0.3.1): http://www.sarl.io/download/index.html (last visit 12/06/2016)
* Janus platform : http://www.janusproject.io/ (last visit (12/06/2016)
  
##Before running
* Import the Janus library with the external jar downloaded (http://www.janusproject.io/) and remove the Sarl default library
  
##Execution
There're two ways to execute the application :

####Learning mode
It will create a QTable that will be used in the auto-playing mode.

To enable it, go to `Game.java` and ensure that `main` function looks like following :

			/*
			 * Comment following if you want to play
			 */
			
			
			launchLearning(worlds, 3);


			/*
			 * Uncomment following if you want to play
			 */
			
			/*Random rand = new Random();
			int worldNb = rand.nextInt(worlds.length);
			
			File file = new File(worlds[worldNb]);
			BufferedImage image = ImageIO.read(file);
			env = new Environment(image);
			QTable qt = getQTableFromFile();
			env.setQTable(qt);
			
			new Launcher(env);*/

####Auto-playing mode
It consists in letting Lemming use the QTable to live in the world. To enable it, ensure that `main` function looks like following :

			/*
			 * Comment following if you want to play
			 */
			
			
			/*launchLearning(worlds, 3);*/


			/*
			 * Uncomment following if you want to play
			 */
			
			Random rand = new Random();
			int worldNb = rand.nextInt(worlds.length);
			
			File file = new File(worlds[worldNb]);
			BufferedImage image = ImageIO.read(file);
			env = new Environment(image);
			QTable qt = getQTableFromFile();
			env.setQTable(qt);
			
			new Launcher(env);
