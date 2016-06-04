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
	
	private ArrayList<List<PerceivableObject>> state;
	private ArrayList<float[]> coef;
	
	/**
	 * Default Constructor
	 */
	public QTable(){
		this.state=new ArrayList<>();
		this.coef=new ArrayList<>();
	}
	
	/**
	 * Constructor of a QTable with a given list of state and associated coefficients 
	 * 
	 * @param state a list of different states
	 * @param coef a list of coefficients associated to those states
	 */
	public QTable(ArrayList<List<PerceivableObject>> state, ArrayList<float[]> coef){
		this.state = state;
		this.coef = coef;
	}
	
	/**
	 * Add a list of state to the current QTable
	 * 
	 * @param s a list of states
	 */
	public void AddState(List<PerceivableObject> s){
		float[] tmp= new float[ActionEnum.values().length-3];
		for (int i = 0; i < tmp.length; i++){
			tmp[i] = -5;
		}
		this.state.add(s);
		this.coef.add(tmp);		
	}
	
	/**
	 * Check if a given state already exists on the QTable
	 * 
	 * @param s a state
	 * @return true if the current state contains the list of states, false otherwise 
	 */
	public boolean StateAlreadyVisit(List<PerceivableObject> s){
		return this.state.contains(s);
	}
	
	/**
	 * Get the coefficients of given states from the QTable
	 * 
	 * @param s a list of PerceivableObject
	 * @return the coefficients on the QTable for the list of PerceivableObject given 
	 */
	public float[] getCoef(List<PerceivableObject> s){
		if (this.StateAlreadyVisit(s)){
			return this.coef.get(this.state.indexOf(s));
		}else{
			return null;
		}
	}

	/**
	 * Update coefficients of the QTable
	 * 
	 * @param s a list of states
	 * @param a
	 * @param reward
	 */
	public void UpdateCoef(List<PerceivableObject> s, ActionEnum a, int reward){
		if (s != null) {
			if (!this.StateAlreadyVisit(s)){
				this.AddState(s);
			}
			System.out.println(reward);
			float[] tmp=this.coef.get(this.state.indexOf(s));
			tmp[a.ordinal()]=(tmp[a.ordinal()]+ reward)/2;
			this.coef.set(this.state.indexOf(s), tmp);
			System.out.println(this.coef.get(this.state.indexOf(s)));
		}
	}
	
	public ArrayList<List<PerceivableObject>> getState() {
		return this.state;
	}
	
	public ArrayList<float[]> getCoef() {
		return this.coef;
	}
}
