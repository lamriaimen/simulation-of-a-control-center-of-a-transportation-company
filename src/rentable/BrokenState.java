package rentable;

public class BrokenState extends State{

	protected BrokenState(Rentable rentable) {
		super(rentable);
	}

	@Override
	void rent() throws Exception {
		throw new Exception("Broken bikes cannot be rented");
	}

	@Override
	void giveBack() throws Exception {
		throw new Exception("Broken bikes cannot be rented");	
	}

	@Override
	void fix() {
		this.rentable.resetRentedCount();
		this.rentable.setState(new RentableState(this.rentable));
	}
	
	@Override
	public String toString() {
		return "Broken";
	}

}
