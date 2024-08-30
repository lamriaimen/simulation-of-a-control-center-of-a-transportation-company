package rentable;

public abstract class State {
	protected Rentable rentable;
	
	protected State(Rentable rentable) {
		this.rentable = rentable;
	}
	
	abstract void rent() throws Exception;
	abstract void giveBack() throws Exception;
	abstract void fix() throws Exception;
	
}
