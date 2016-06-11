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
		float[] tmp= new float[ActionEnum.values().length-4];
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
	 * @return -1 if the state does not belong to the QTable state list, the index of the state in the QT state list otherwise
	 */
	public int stateInQTable(List<PerceivableObject> s){
		int index = -1;
		for (List<PerceivableObject> state : this.stateList){
			boolean correspondingState = false;
			for (PerceivableObject po : state){
				boolean samePerceivableObject = false;
				correspondingState = false;
				for (PerceivableObject poToCompare : s) {
					samePerceivableObject = false;
					if (( po.getX() == poToCompare.getX() &&
						po.getY() == poToCompare.getY() &&
						po.isClimbable() == poToCompare.isClimbable() &&
						po.isDiggable() == poToCompare.isDiggable() &&
						po.isEmpty() == poToCompare.isEmpty() &&
						po.isEntry() == poToCompare.isEntry() &&
						po.isExit() == poToCompare.isExit())) {
						samePerceivableObject = true;
					}
					if (samePerceivableObject){
						break;
					}
				}
				if (!samePerceivableObject){
					break;
				}else {
					correspondingState = true;
				}
			}
			if (correspondingState) {
				index = this.stateList.indexOf(state); 
				break;
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
		if (StateAlreadyVisit(s)){
			return coefList.get(this.stateList.indexOf(s));
		}
		return null;
	}
	
	/**
	 * Return the list of coefficients corresponding to a state if it exists in the list of state of QTable
	 * @param s the current state
	 * @return the list of coefficients corresponding to the current state
	 */
	public float[] getCoefIfStateExist(List<PerceivableObject> s){
		int index = this.stateList.indexOf(s);
		
		if (index != -1){
			System.out.println("In QTable !! Index : " + index);
			return this.coefList.get(index);
		}
		System.out.println("Not in QTable");
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
