package fr.utbm.vi51.lemmings;
import java.util.ArrayList;

import fr.utbm.vi51.lemmings.utils.enums.ActionEnum;

public class QTable {
	
	private ArrayList<int[]> state;
	private ArrayList<int[]> coef;
	
	public QTable(){
		this.state=new ArrayList<>();
		this.coef=new ArrayList<>();
	}
	
	public void AddState(int[] s){
		int[] tmp= new int[ActionEnum.values().length];
		this.state.add(s);
		this.coef.add(tmp);		
	}
	
	public boolean StateAlreadyVisit(int[] s){
		return this.state.contains(s);
	}
	
	public int[] getCoef(int[] s ){
		if (this.StateAlreadyVisit(s)){
			return this.coef.get(this.state.indexOf(s));
		}else{
			return null;
		}
	}

	public void UpdateCoef(int[] s, ActionEnum a, int reward){
		int[] tmp=this.coef.get(this.state.indexOf(s));
		if (!this.StateAlreadyVisit(s)){
			this.AddState(s);
		}
		tmp[a.ordinal()]=(tmp[a.ordinal()]+ reward)/2;
		this.coef.set(this.state.indexOf(s), tmp);
	}
}
