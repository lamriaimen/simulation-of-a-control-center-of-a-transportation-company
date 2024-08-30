package rentable;

public abstract class Rentable {
	protected static int nextID = 0; 
	protected int id;
	protected int timeBroken = 0;
//	a été loué rentedCount fois sans avoir été réparé
	protected int rentedCount = 0;
	private State state = new RentableState(this);
	

	protected Rentable() {
        this.id = nextID;
        nextID++; // Increment the nextID for the next Rentable object
    }
	public void rent() throws Exception {
		this.state.rent();
	}
	public void giveBack() throws Exception {
		this.state.giveBack();
	}
	public void fix() throws Exception {
		this.state.fix();
	}
	
	public int getTimeBroken() {
		return this.timeBroken;
	}
	
	public void incrementTimeBroken() {
		this.timeBroken++;
	}
	
	public void resetTimeBroken() {
		this.timeBroken = 0;
	}
	
	protected void incrementRentedCount() {
		this.rentedCount++;
	}
	
	protected void resetRentedCount() {
		this.rentedCount = 0;
	}
	
	public void setState(State s) {
		this.state = s;
	}
	
	public int getId() {
		return this.id;
	}
	
	@Override
	public String toString() {
		return "Rentable " + id + " [State:" + state + "]";
	}

	public State getState() {
		return this.state;
	}

	public int getRentedCount() {
		return this.rentedCount;
	}
}
