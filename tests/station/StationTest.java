package station;

import org.junit.*;

import java.util.List;

import rentable.Bike;

import static org.junit.Assert.*;



public class StationTest {

    private Station station1;
    private Station station2;
    private Bike bike1;
    private Bike bike2;
    private Bike bike3;
    private List<Bike> bikeslist;

    @Before
    public void setUp() throws Exception {
        // Initialize instances before each test method
        station1 = new Station(10);
        station2 = new Station(20);
        bike1 = new Bike();
        bike2 = new Bike();
        bike3 = new Bike();
        bikeslist = List.of(bike1, bike2, bike3);
    }

    @Test
    public void testStationInitiation() {
        Assert.assertEquals(10, station1.getCapacity());
        Assert.assertEquals(20, station2.getCapacity());
        Assert.assertNotEquals(station1.getId(), station2.getId());
    }

    @Test
    public void testAddRentable() {
        
            int initialSize = station1.getRentablesNumber();
            try {
                bike1.rent();
            } catch (Exception e) {
                
               fail("Should not have thrown any exception");
            }
            try {
                station1.addRentable(bike1);
            } catch (Exception e) {
                
                assertEquals("Already in a station", e.getMessage());
            }

            Assert.assertEquals(initialSize + 1, station1.getRentablesNumber());
            
            
      
    }
    

    @Test
    public void testAddRentables() {
        int actualSize = station1.getRentablesNumber();
        station1.addRentables(bikeslist);
        Assert.assertEquals(actualSize + 3, station1.getRentablesNumber());
    }

    @Test
    public void testRemoveRentableByID() {
        station1.addRentables(bikeslist);
        int actualSize = station1.getRentablesNumber();
        station1.removeRentableByID(bike1.getId());
        Assert.assertEquals(actualSize - 1, station1.getRentablesNumber());
    }

    
}
