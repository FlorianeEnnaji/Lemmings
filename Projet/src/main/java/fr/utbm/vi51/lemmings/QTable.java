package fr.utbm.vi51.lemmings;
import java.util.ArrayList;
import java.util.List;

import fr.utbm.vi51.lemmings.model.PerceivableObject;
import fr.utbm.vi51.lemmings.utils.enums.ActionEnum;

/**
 * Define the QTable
 *
 */
public class QTable {
	
	private ArrayList<List<PerceivableObject>> stateList;
	private ArrayList<float[]> coefList;
	
	/**
	 * Default Constructor
	 */
	public QTable(){
		this.stateList=new ArrayList<>();
		this.coefList=new ArrayList<>();
	}
	
	/**
	 * Constructor of a QTable with a given list of states and associated lists of coefficients 
	 * 
	 * @param stateList a list of different states
	 * @param coefList all list of coefficients associated the list of states
	 */
	public QTable(ArrayList<List<PerceivableObject>> stateList, ArrayList<float[]> coefList){
		this.stateList = stateList;
		this.coefList = coefList;
		System.out.println(stateList.get(0).get(0).getX() + " -- " + stateList.get(0).get(0).getY());
	}
	
	/**
	 * Add a state to the current QTable
	 * 
	 * @param s a state
	 */
	public void AddState(List<PerceivableObject> s){
		float[] tmp= new float[ActionEnum.values().length-3];
		for (int i = 0; i < tmp.length; i++){
			tmp[i] = -5;
		}
		this.stateList.add(s);
		this.coefList.add(tmp);		
	}
	
	/**
	 * Check if a state already exists on the QTable
	 * 
	 * @param s a state
	 * @return true if the list of states contains the state in parameter, false otherwise 
	 */
	public boolean StateAlreadyVisit(List<PerceivableObject> s){
		return this.stateList.contains(s);
		
	}
	
	/**
	 * Test if a state belongs to the list of states of the QTable
	 * @param s the current state
	 * @return true if the state belongs to the states, false otherwise
	 */
	public int stateInQTable(List<PerceivableObject> s){
		boolean same = true;
		int index = -1;
		for (List<PerceivableObject> list : this.stateList){
			for (PerceivableObject o : list){
				for (PerceivableObject os : s) {
					PerceivableObject compare = os;
					if (!(o.getX() == compare.getX() &&
						o.getY() == compare.getY() &&
						o.isClimbable() == compare.isClimbable() &&
						o.isDiggable() == compare.isDiggable() &&
						o.isEmpty() == compare.isEmpty() &&
						o.isEntry() == compare.isEntry() &&
						o.isExit() == compare.isExit())) {
						/*System.out.print(o.getX() == compare.getX());
						System.out.println(o.getY() == compare.getY());
						System.out.println(compare.getX() + " -- " + compare.getY());*/
						same = false;
						break;
					} else {
						index = list.indexOf(o);
					}
				}
			}
			if (same) {
				return index;
			} else {
				same = true;
			}
			
		}
		return index;
	}
		
	/**
	 * Get the list of coefficients of a state
	 * 
	 * @param s a state
	 * @return the list of coefficients for the state
	 */
	public float[] getCoef(List<PerceivableObject> s){
		if (this.StateAlreadyVisit(s)){
			return this.coefList.get(this.stateList.indexOf(s));
		}
		return null;
	}
	
	/**
	 * Return the list of coefficients corresponding to a state if it exists in the list of state of QTable
	 * @param s the current state
	 * @return the list of coefficients corresponding to the current state
	 */
	public float[] getCoefIfStateExist(List<PerceivableObject> s){
		int index = this.stateInQTable(s);
		System.out.println("In QTable ? " + index);
		if (index != -1){
			return this.coefList.get(index);
		}
		return null;
	}

	/**
	 * Update the list of coefficients of a state
	 * 
	 * @param s a state
	 * @param a
	 * @param reward
	 */
	public void UpdateCoef(List<PerceivableObject> s, ActionEnum a, int reward){
		if (s != null) {
			if (!this.StateAlreadyVisit(s)){
				this.AddState(s);
			}
			System.out.println(reward);
			float[] tmp=this.coefList.get(this.stateList.indexOf(s));
			tmp[a.ordinal()]=(tmp[a.ordinal()]+ reward)/2;
			this.coefList.set(this.stateList.indexOf(s), tmp);
			System.out.println(this.coefList.get(this.stateList.indexOf(s)));
		}
	}
	
	/**
	 * @return the list of states
	 */
	public ArrayList<List<PerceivableObject>> getStateList() {
		return this.stateList;
	}
	
	/**
	 * @return all lists of coefficients
	 */
	public ArrayList<float[]> getCoefList() {
		return this.coefList;
	}
}
