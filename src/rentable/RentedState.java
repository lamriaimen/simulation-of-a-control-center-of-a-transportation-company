package rentable;

public class RentedState extends State {

	protected RentedState(Rentable rentable) {
		super(rentable);
	}

	@Override
	void rent() throws Exception {
		throw new Exception("Already rented");
	}

	/**
	 * Give the rentable back to the station 
	 * If the rented count exceeds 10, then the bike breaks and needs to be fixed 
	 * else goes back to rentable state
	 */
	@Override
	void giveBack() {
		this.rentable.incrementRentedCount();
		if(this.rentable.rentedCount>10) {
			this.rentable.setState(new BrokenState(this.rentable));
		}else {			
			this.rentable.setState(new RentableState(this.rentable));
		}
	}

	@Override
	void fix() throws Exception {
		throw new Exception("Give the bike back before");
	}

	@Override
	public String toString() {
		return "Rented";
	}

}
