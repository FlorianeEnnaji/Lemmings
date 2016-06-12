package fr.utbm.vi51.lemmings.model;

/**
 * @author antonin.waltz@utbm.fr, floriane.ennaji@utbm.fr, lucille.gomez@utbm.fr, romain.thibaud@utbm.fr
 * @brief A pixel of a world
 */
public class WorldPixel implements EnvironmentObject{
	
	private int idPixel;
	private boolean diggablePixel;
	private boolean emptyPixel;
	private boolean climbablePixel;
	private boolean entryPixel;
	private boolean exitPixel;
	
	/**Type of the pixel*/
	private enum Type{
		dig, empty, climb, entry, exit, ground;
	}
	
	/**
	 * @brief Constructor
	 * @param value the Type of the pixel
	 * @param id the identifier of the pixel
	 */
	public WorldPixel(String value, int id){
		this.idPixel = id;
		this.diggablePixel = false;
		this.emptyPixel = false;
		this.climbablePixel = true;
		this.entryPixel = false;
		this.exitPixel = false;
		Type type = Type.valueOf(value);
		
		switch (type){
			case dig : 
				this.diggablePixel = true;
				this.climbablePixel = false;
				break;
			case empty:
				this.emptyPixel = true;
				this.climbablePixel = false;
				break;
			case entry : 
				this.entryPixel = true;
				this.climbablePixel = false;
				this.emptyPixel = true;
				break;
			case exit : 
				this.exitPixel = true;
				this.climbablePixel = false;
				break;
			default : 
				break;
		}
	}
	
	/**
	 * @brief Getter
	 * @return the id of the current pixel
	 */
	public int getID() {
		return this.idPixel;
	}

	/**
	 * @brief Getter
	 * @return true if the current pixel is diggable, false otherwise
	 */
	public boolean isDiggable() {
		return this.diggablePixel;
	}

	/**
	 * @brief Getter
	 * @return true if the current pixel is climbable, false otherwise
	 */
	public boolean isClimbable() {
		return this.climbablePixel;
	}

	/**
	 * @brief Getter
	 * @return true if the current pixel is empty, false otherwise
	 */
	public boolean isEmpty() {
		return this.emptyPixel;
	}
	
	/**
	 * @brief Sets a pixel empty
	 */
	public void setEmpty(){
		this.climbablePixel = false;
		this.diggablePixel = false;
		this.emptyPixel = true;
	}

	/**
	 * @brief Getter
	 * @return true if the current pixel is the exit, false otherwise
	 */
	public boolean isExit() {
		return this.exitPixel;
	}

	/**
	 * @brief Getter
	 * @return true if the current pixel is the entry, false otherwise
	 */
	public boolean isEntry() {
		return this.entryPixel;
	}	
}
