package rentable;

import java.util.HashSet;

public class Bike extends Rentable{
	HashSet<Equipment> Equipments = new HashSet<Equipment>();

	protected void addEquipment(Equipment e) {
		this.Equipments.add(e);
	}
	@Override
	public String toString() {
		String res = super.toString() + " Bike";
		if(!Equipments.isEmpty()) {
			res += " (";
			for (Equipment equipment : Equipments) {
				res += equipment + "";
			}
			res += ")";
		}
		return res;
	}
}
