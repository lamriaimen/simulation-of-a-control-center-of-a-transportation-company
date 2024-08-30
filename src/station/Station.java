package station;

import java.util.ArrayList;
import java.util.List;

import rentable.Rentable;

public class Station {
	
	
	private static int nextID=0;
	private int id;
	private final int capacity;
	private List<Rentable> rentables = new ArrayList<Rentable>();
	private int timeFullOrEmpty = 0;
	
	
	
	public Station(int capacity  ) {
		nextID++;
		this.id=nextID;
		this.capacity=capacity;
	}
	
	public int getCapacity() {
		
		return this.capacity;
		
	} 

	public int getEmptyEmplacements() {
		
		return this.capacity-this.getRentablesNumber();
		
	}
	
	public int getRentablesNumber() {
		
		return this.rentables.size();
		
	}
	
	public boolean isEmpty() {
		return this.rentables.size() == 0;
	}
	
	public boolean isFull() {
		return this.getEmptyEmplacements() == 0;
	}
	
	public int getTimeFullOrEmpty() {
		return this.timeFullOrEmpty;
	}
	
	public void incrementTimeFullOrEmpty() {
		this.timeFullOrEmpty++;
	}
	
	public void resetTimeFullOrEmpty() {
		this.timeFullOrEmpty = 0;
	}
	
	public void addRentables(List<? extends Rentable> rentables) {
		if (rentables.size() > this.getEmptyEmplacements()) {
			throw new IllegalArgumentException("Not enough space in the station");
			
		}
		if (rentables.size() == 0 || rentables == null) {
			throw new IllegalArgumentException("No rentables to add");
			
		}
	
		this.rentables.addAll(rentables);
		
	}

	/**
	 * 
	 * @param rentable added to the station, if possible
	 * @throws Exception : Full station, null given, or the Rentable state doesn't match with this method call
	 */
	public void addRentable(Rentable rentable) throws Exception {
		if ( this.getEmptyEmplacements() == 0) {
			throw new IllegalArgumentException("Not enough space in the station");
			
		}
		if (rentable == null) {
			throw new IllegalArgumentException("No rentable to add");
			
		}
		rentable.giveBack();
		this.rentables.add(rentable);
		
	}
	
	/**
	 * 
	 * When a @param rentable is redistributed from the control center
	 * @throws Exception
	 */
	public void replaceRentable(Rentable rentable) throws Exception {
		if ( this.getEmptyEmplacements() == 0) {
			throw new IllegalArgumentException("Not enough space in the station");
			
		}
		if (rentable == null) {
			throw new IllegalArgumentException("No rentable to add");
			
		}
		this.rentables.add(rentable);
		
	}

	public Rentable removeRentable(Rentable rentable) throws Exception {
		if (rentable == null) {
			throw new IllegalArgumentException("No rentable to remove");
			
		}
		if (this.rentables.contains(rentable) == false) {
			throw new IllegalArgumentException("Rentable not in the station");
			
		}
		rentable.rent();
		this.rentables.remove(rentable);
		return rentable;
	}
	
	public Rentable removeRentableByID(int id) {
		Rentable r = null;
		for (Rentable rentable : rentables) {
			if(rentable.getId() == id) {
				r = rentable;
				break;
			}
		}
		if (r == null) {
			throw new IllegalArgumentException("Rentable with id "+id+" not in the station");
			
		}
		
		this.rentables.remove(r);
		return r;
	}

	public int getId() {
		
		return this.id;
		
	}



	public List<Rentable> getRentables() {
		
		return this.rentables;
		
	}
	
	public List<Rentable> removeAllRentables() {
		List<Rentable> r = new ArrayList<Rentable>();
		r.addAll(rentables);
		this.rentables.clear();
		return r;
		
	}

	@Override
	public String toString() {
		return "Station " + id +" (" +this.rentables.size()+ "/" + capacity + ")";
	}


	
	
	
	
	

	
}
