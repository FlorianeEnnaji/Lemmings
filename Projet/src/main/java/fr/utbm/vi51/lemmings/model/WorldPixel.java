package fr.utbm.vi51.lemmings.model;

/**
 * WorldPixel represent a pixel of the world
 */
public class WorldPixel implements EnvironmentObject{
	
	private int idPixel;
	private boolean diggablePixel;
	private boolean emptyPixel;
	private boolean climbablePixel;
	private boolean entryPixel;
	private boolean exitPixel;
	
	private enum Type{
		dig, empty, climb, entry, exit, ground;
	}
	
	/**
	 * Constructor
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
	 * @return the id of the current pixel
	 */
	public int getID() {
		return this.idPixel;
	}

	/**
	 * @return true if the current pixel is diggable, false otherwise
	 */
	public boolean isDiggable() {
		return this.diggablePixel;
	}

	/**
	 * @return true if the current pixel is climbable, false otherwise
	 */
	public boolean isClimbable() {
		return this.climbablePixel;
	}

	/**
	 * @return true if the current pixel is empty, false otherwise
	 */
	public boolean isEmpty() {
		return this.emptyPixel;
	}
	
	/**
	 * Set an empty pixel
	 */
	public void setEmpty(){
		this.climbablePixel = false;
		this.diggablePixel = false;
		this.emptyPixel = true;
	}

	/**
	 * @return true if the current pixel is the exit, false otherwise
	 */
	public boolean isExit() {
		return this.exitPixel;
	}

	/**
	 * @return true if the current pixel is the entry, false otherwise
	 */
	public boolean isEntry() {
		return this.entryPixel;
	}	
}
