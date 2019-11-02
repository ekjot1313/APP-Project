package pattern;

import java.util.ArrayList;

public class Observable {

	ArrayList observers = new ArrayList<>();
	
	public void attach(Observer o) {
		observers.add(o);
	}
	
	public void detach(Observer o) {
		observers.remove(o);
	}
	
	public void notify(Observable obj) {
		for(int i=0;i<observers.size();i++) {
			((Observer) observers.get(i)).update(obj);
		}
	}
}
