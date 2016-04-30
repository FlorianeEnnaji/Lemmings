

public class WorldPixel extends EnvironmentObject{
	
	private int m_id;
	private boolean m_diggable;
	private boolean m_empty;
	private boolean m_climbable;
	private boolean m_entry;
	private boolean m_exit;
	
	private enum Type{
		dig, empty, climb, entry, exit, ground;
	}
	
	public WorldPixel(String value, int id){
		m_id = id;
		m_diggable = false;
		m_empty = false;
		m_climbable = false;
		m_entry = false;
		m_exit = false;
		Type type = Type.valueOf(value);
		
		switch (type){
			case dig : 
				m_diggable = true;
				break;
			case empty:
				m_empty = true;
				break;
			case climb : 
				m_climbable = true;
				break;
			case entry : 
				m_entry = true;
				break;
			case exit : 
				m_exit = true;
				break;
			default : 
				break;
		}
	}
	
	public int getID() {
		return m_id;
	}

	public boolean isDiggable() {
		return m_diggable;
	}


	public boolean isClimbable() {
		return m_climbable;
	}

	public boolean isEmpty() {
		return m_empty;
	}
	public void setEmpty(){
		this.m_climbable = false;
		this.m_diggable = false;
		this.m_empty = true;
	}

	public boolean isExit() {
		return m_exit;
	}

	public boolean isEntry() {
		return m_entry;
	}	
}
