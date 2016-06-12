package fr.utbm.vi51.lemmings.learning;
import java.util.ArrayList;
import java.util.List;

import fr.utbm.vi51.lemmings.model.PerceivableObject;
import fr.utbm.vi51.lemmings.utils.ActionEnum;

/**
 * @author antonin.waltz@utbm.fr, floriane.ennaji@utbm.fr, lucille.gomez@utbm.fr, romain.thibaud@utbm.fr
 * @brief Storing all the perceptions and rewards an agent makes while moving
 * Built in the learning phase and read in the playing phase.
 */
public class QTable {
	
	/**
	 * All the perceptions the agent went through
	 */
	private ArrayList<List<PerceivableObject>> stateList;
	/**
	 * All the perceptions the rewards the agent got while performing action in their perceptions
	 */
	private ArrayList<float[]> coefList;
	
	/**
	 * @brief Default Constructor
	 */
	public QTable(){
		this.stateList=new ArrayList<>();
		this.coefList=new ArrayList<>();
	}
	
	/**
	 * @brief Constructor of a QTable with a given list of states and associated lists of coefficients 
	 * 
	 * @param stateList (ArrayList<List<PerceivableObject>>) a list of different states
	 * @param coefList (ArrayList<float[]>) all list of coefficients associated the list of states
	 */
	public QTable(ArrayList<List<PerceivableObject>> stateList, ArrayList<float[]> coefList){
		this.stateList = stateList;
		this.coefList = coefList;
		System.out.println(stateList.get(0).get(0).getX() + " -- " + stateList.get(0).get(0).getY());
	}
	
	/**
	 * @brief Add a state to the current QTable
	 * 
	 * @param s (List<PerceivableObject>) a state
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
	 * @brief Check if a state already exists on the QTable
	 * 
	 * @param s (List<PerceivableObject>) a state
	 * @return true if the list of states contains the state in parameter, false otherwise 
	 */
	public boolean StateAlreadyVisit(List<PerceivableObject> s){
		return this.stateList.contains(s);
		
	}
	
	/**
	 * @brief Test if a state belongs to the list of states of the QTable
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
	 * @brief Get the list of coefficients of a state
	 * 
	 * @param s (List<PerceivableObject>) a state
	 * @return the list of coefficients for the state (float[])
	 */
	public float[] getCoef(List<PerceivableObject> s){
		if (StateAlreadyVisit(s)){
			return coefList.get(this.stateList.indexOf(s));
		}
		return null;
	}
	
	/**
	 * @brief Return the list of coefficients corresponding to a state if it exists in the list of state of QTable
	 * @param s (List<PerceivableObject>) the current state
	 * @return the list of coefficients corresponding to the current state (float[])
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
	 * @brief Update the list of coefficients of a state
	 * 
	 * @param s (List<PerceivableObject>) a state
	 * @param a (ActionEnum) the action the agent performed
	 * @param reward (float) the reward gaven by the environment for this action
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
	 * @brief Getter
	 * @return stateList (ArrayList<List<PerceivableObject>>) the list of states
	 */
	public ArrayList<List<PerceivableObject>> getStateList() {
		return this.stateList;
	}
	
	/**
	 * @brief Getter
	 * @return coefList (ArrayList<float[]>) all lists of coefficients
	 */
	public ArrayList<float[]> getCoefList() {
		return this.coefList;
	}
}
