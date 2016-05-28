package fr.utbm.vi51.lemmings;
import java.util.ArrayList;
import java.util.List;

import fr.utbm.vi51.lemmings.model.PerceivableObject;
import fr.utbm.vi51.lemmings.utils.enums.ActionEnum;

public class QTable {
	
	private ArrayList<List<PerceivableObject>> state;
	private ArrayList<float[]> coef;
	
	public QTable(){
		this.state=new ArrayList<>();
		this.coef=new ArrayList<>();
	}
	
	public QTable(ArrayList<List<PerceivableObject>> state, ArrayList<float[]> coef){
		this.state = state;
		this.coef = coef;
	}
	
	public void AddState(List<PerceivableObject> s){
		float[] tmp= new float[ActionEnum.values().length-3];
		for (int i = 0; i < tmp.length; i++){
			tmp[i] = -5;
		}
		this.state.add(s);
		this.coef.add(tmp);		
	}
	
	public boolean StateAlreadyVisit(List<PerceivableObject> s){
		return this.state.contains(s);
	}
	
	public float[] getCoef(List<PerceivableObject> s){
		if (this.StateAlreadyVisit(s)){
			return this.coef.get(this.state.indexOf(s));
		}else{
			return null;
		}
	}

	public void UpdateCoef(List<PerceivableObject> s, ActionEnum a, int reward){
		if (s != null) {
			if (!this.StateAlreadyVisit(s)){
				this.AddState(s);
			}
			float[] tmp=this.coef.get(this.state.indexOf(s));
			tmp[a.ordinal()]=(tmp[a.ordinal()]+ reward)/2;
			this.coef.set(this.state.indexOf(s), tmp);
		}
	}
	
	public ArrayList<List<PerceivableObject>> getState() {
		return this.state;
	}
	
	public ArrayList<float[]> getCoef() {
		return this.coef;
	}
}
