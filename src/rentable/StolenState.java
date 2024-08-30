package rentable;

public class StolenState extends State {

	public StolenState(Rentable rentable) {
		super(rentable);
	}

	@Override
	void rent() throws Exception {
		throw new Exception("Stolen :(");
	}

	@Override
	void giveBack() throws Exception {
		throw new Exception("Stolen :(");
	}

	@Override
	void fix() throws Exception {
		throw new Exception("Stolen :(");	
	}
	
	@Override
	public String toString() {
		return "Stolen";
	}

}
