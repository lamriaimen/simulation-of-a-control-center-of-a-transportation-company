package rentable;

public class RentableState extends State{

	protected RentableState(Rentable rentable) {
		super(rentable);
	}

	@Override
	void rent() {
		this.rentable.setState(new RentedState(this.rentable));
	}

	@Override
	void giveBack() throws Exception {
		throw new Exception("Already in a station");
	}

	@Override
	void fix() throws Exception {
		throw new Exception("Nothing to fix");
	}

	@Override
	public String toString() {
		return "Rentable";
	}
	
}
