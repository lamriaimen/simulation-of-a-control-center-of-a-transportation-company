package controlCenter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import rentable.Rentable;
import rentable.RentedState;
import rentable.StolenState;
import rentable.Bike;
import rentable.EBike;
import station.Station;

public class ControlCenter {
	/**
	 * list of all the stations that contain Rentables that are currently rentable, or broken
	 */
	private List<Station> stations = new ArrayList<Station>();

	private ControlCenter() {
	}

	/**
	 * Singleton ControlCenter
	 */
	private static ControlCenter UNIQUE_INSTANCE = null;

	/**
	 * @return the unique instance of ControlCenter
	 */
	public static ControlCenter getTheInstance() {
		if (UNIQUE_INSTANCE == null) {
			UNIQUE_INSTANCE = new ControlCenter();
		}
		return UNIQUE_INSTANCE;
	}

	/**
	 * Creates a random number of stations, between @param min and @param max  
	 * with a random capacity between 5 and 15
	 * and fill them with (capacity-2) Rentables
	 */
	public void createStations(int min, int max) {
		for (int i = 0; i < (Math.random() * (max-min) + min); i++) {
			Station station = new Station((int) (Math.random() * 5 + 5));
			station.addRentables(this.createBikes(station.getCapacity()));
			this.addStation(station);
		}
	}

	/**
	 * Adds the @param station to the list of stations
	 */
	public void addStation(Station station) {
		stations.add(station);
	}


	/**
	 * Removes the station with ID @param idStation from the list of stations
	 */
	public void removeStationByID(int idStation) {
		Station stationToRemove = null;
		for (Station station : stations) {
			if (station.getId() == idStation) {
				stationToRemove = station;
				break;
			}
		}
		if (stationToRemove == null) {
			throw new IllegalArgumentException("Station with ID " + idStation + " not found.");
		}
		stations.remove(stationToRemove);
	}
	
	/**
	 * @return the list of stations
	 */
	public List<Station> getStations() {
		return stations;
	}
	
	/**
	 * @return the station with ID @param idStation from the list of stations
	 */
	public Station getStationByID(int idStation) {
		Station station = null;
		for (Station s : stations) {
			if (s.getId() == idStation) {
				station = s;
				break;
			}
		}
		if (station == null) {
			throw new IllegalArgumentException("Station with ID " + idStation + " not found.");
		}
		return station;
	}

	/**
	 * Displays the toString of each station from the list of the sations
	 */
	public void showStations() {
		System.out.println("  "+this.stations.size() + " stations dans le centre de contrôle : ");
		for (Station station : stations) {
			System.out.println("   "+station.toString());
		}
	}

	/**
	 * Creates a list of rentable Bikes
	 * The number of created bikes is the given capacity of the station ( @param numberOfBikes ) minus 2 
	 * @return a Rentable list of created Bikes 
	 */
	public List<Rentable> createBikes(int numberOfBikes) {
		List<Rentable> rentables = new ArrayList<Rentable>();
		
		for (int i = 0; i < numberOfBikes-2; i++) {
			Rentable rentable;
			if(i<numberOfBikes-3) {				
				rentable = new Bike();
			}else {
				rentable = new EBike();
			}
			rentables.add(rentable);
		}
		return rentables;
	}
	
	public void checkForRedistribution(){
        for (Station s : this.stations){
            if (s.isEmpty() || s.isFull()){
                s.incrementTimeFullOrEmpty();
            }
            else {
                s.resetTimeFullOrEmpty();            
            }
            if (s.getTimeFullOrEmpty() > 2){
            	System.out.println("Voilà maintenant 3 tours qu'une même station est pleine/vide sans changement, redistribution de tous les vélos...");
                List<Rentable> getAllRentables = new ArrayList<Rentable>();
            	for (Station station : this.stations) {
            		getAllRentables.addAll(station.removeAllRentables());
            		station.resetTimeFullOrEmpty();
				}
            	int stationId = 0;
                while (getAllRentables.size()>0){
                    try{
                        this.stations.get(stationId).replaceRentable(getAllRentables.remove(0));
                    }catch (Exception e){}
                    stationId = (stationId + 1) % this.stations.size();
                }

            }
        }
    }
	
	public void checkForBrokenBikesToFix(){
        for (Station s : this.stations){
        	for (Rentable r : s.getRentables()) {
        		if(r.getState().toString().equals("Broken")) {
        			System.out.println("Rentable "+r.getId()+" cassé :(");
        			r.incrementTimeBroken();
        		}else {
        			r.resetTimeBroken();
        		}
        		if (r.getTimeBroken() > 2){
                	System.out.println("Voilà maintenant 3 tours qu'un même rentable est cassé, il est temps de le réparer pour le remettre en service :)");
                	try {
						r.fix();
						System.out.println("Rentable réparé : "+r.toString());
						r.resetTimeBroken();
					} catch (Exception e) {}
                }
			}
            
        }
    }
	
	/**
	 * checks for broken bikes to fix, for any bike redistribution amongst all the stations, 
	 * and displays the current state of all of the control center's stations.
	 */
	public void update() {
		this.checkForBrokenBikesToFix();
		this.checkForRedistribution();
		this.showStations();
	}
	/**
	 * Simulation 
	 */
	
	public static void main(String[] args) {
		System.out.println("=== Création du centre de contrôle ===\n");
		ControlCenter controlCenter = ControlCenter.getTheInstance();
		System.out.println("~ Création de la liste des rentables qui ne sont pas dans les stations (c.à.d. rented ou stolen)");
		List<Rentable> rentables = new ArrayList<Rentable>();
		System.out.println("~ Création de 3 à 6 stations dans le centre");
		System.out.println("  ==> Capacité des stations aléatoire (entre 5 et 10 places)");
		System.out.println("  ==> Remplissage de ces stations avec (capacité-2) bike/e-bike");
		controlCenter.createStations(3,6);
		System.out.println("\n=========== Début de la simulation ===========");
		int nbBoucles = 60;
		System.out.println("=== "+nbBoucles+" boucles de location/retour de vélos ===");
		for (int i = 0; i < nbBoucles; i++) {			
			
			System.out.println("\n========= TOUR "+(i+1)+" =========\n");
			
			int rentedNumber = (int) (Math.random()*10+2);
			System.out.println("~ " +rentedNumber+" vélos vont être loués dans des stations aléatoires :");
			for (int j = 0; j < rentedNumber; j++) {
				Station s = controlCenter.getStationByID((int)(Math.random()*controlCenter.stations.size())+1);
				int randomId = (int) (Math.random()*s.getRentablesNumber());
				if(s.getRentablesNumber()>0) {
					for (Rentable r : s.getRentables()) {
						if (randomId == 0) {	
							try{
								Rentable temp = s.removeRentable(r);
								rentables.add(temp);
								System.out.println("  Rentable "+r.getId()+" a quitté Station No."+s.getId());
							} catch(Exception e){
								System.out.println("  Rentable "+r.getId()+" n'a pas pu être retiré de Station "+s.getId()+" : "+e.getMessage());
							}
							break;
						}
						randomId --;
					}
				}else {
					System.out.println("Station "+s.getId()+" vide");
				}
			}
			
			
			int takenBackNumber = (int) (Math.random()*10+2);
			System.out.println("\n~ "+takenBackNumber+" vélos vont être rendus dans des stations aléatoires :");
			for (int j = 0; j < takenBackNumber; j++) {
				if(rentables.size()==0) {
					System.out.println("  Tous les Rentables sont actuellement dans les stations...");
					break;
				}
				Station s = controlCenter.getStationByID((int)(Math.random()*controlCenter.stations.size())+1);
				int randomId = ((int) (Math.random()*rentables.size()));
//				for (Station s : controlCenter.stations) {
//				if(s.getEmptyEmplacements()>0) {
				Rentable r = rentables.remove(randomId);
				try {
					s.addRentable(r);
                    System.out.println("  Station No."+s.getId()+" a reçu Rentable "+r.getId());
                } catch (Exception e) {
                	rentables.add(r);
                    System.out.println("  Rentable "+r.getId()+" n'a pas pu être rendu à Station "+s.getId()+" : "+e.getMessage());
                }
//				}
//				}
				
			}
			
			System.out.println("\n~ Update de l'état du centre de contrôle :");
			controlCenter.update();
			
			System.out.println("  "+rentables.size() + " Rentables actuellement hors stations :");
			for (Rentable rentable : rentables) {
				System.out.println("   "+rentable.toString());
			}
			// UN VOLEUR DE VELO ESSAYE DE VOLER DANS UNE STATION ALEATOIRE TOUS LES 10 TOURS
			if(i>0 && i%10==0) {
				Station randomStation = controlCenter.getStationByID((int)(Math.random()*controlCenter.stations.size())+1);
				System.out.println("\n/!\\ Un voleur rode autour de la station "+randomStation.getId()+" ... /!\\");
				if(randomStation.getRentablesNumber()>0) {
					System.out.println("La station n'est pas vide, le voleur essaye de voler le premier vélo qu'il voit!");
					Rentable stolenRentable = randomStation.getRentables().get(0);
					try{
						randomStation.removeRentable(stolenRentable);
						rentables.add(stolenRentable);
						stolenRentable.setState(new StolenState(stolenRentable));
						System.out.println("/!\\ un rentable a été volé : "+stolenRentable.toString());
					} catch(Exception e){
						System.out.println("Rentable "+stolenRentable.getId()+" n'a pas pu être retiré de Station "+randomStation.getId()+" : "+e.getMessage());
					}
				}else {
					System.out.println("La station est vide, le voleur n'a rien pu voler.");
				}
				
			}

		}
	}
}
