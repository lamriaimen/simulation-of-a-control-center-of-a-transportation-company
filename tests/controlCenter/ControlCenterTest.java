package controlCenter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.List;

import controlCenter.ControlCenter;  // Importing the ControlCenter class
import rentable.Bike;
import rentable.EBike;
import rentable.Rentable;
import station.Station;  // Importing the Station class
import org.junit.Before;
import org.junit.Test;

public class ControlCenterTest {

    private ControlCenter controlCenter;

    @Before
    public void setUp() {
        controlCenter = ControlCenter.getTheInstance();
    }

    @Test
    public void testSingleton() {
        ControlCenter instance1 = ControlCenter.getTheInstance();
        ControlCenter instance2 = ControlCenter.getTheInstance();

        assertSame(instance1, instance2);
    }

    @Test
    public void testCreateStations() {
        controlCenter.createStations(3, 6);
        assertFalse(controlCenter.getStations().isEmpty());
    }

    @Test
    public void testAddStation() {
        Station station = new Station(15);
        controlCenter.addStation(station);
        assertTrue(controlCenter.getStations().contains(station));
    }

    @Test
    public void testRemoveStationByID() {
        Station station = new Station(12);
        controlCenter.addStation(station);
        int initialSize = controlCenter.getStations().size();
        controlCenter.removeStationByID(station.getId());
        assertEquals(initialSize - 1, controlCenter.getStations().size());
    }

     @Test
    public void testCreateBikes() {
        ControlCenter controlCenter = ControlCenter.getTheInstance();

        List<Rentable> bikes = controlCenter.createBikes(5);

        assertEquals(3, bikes.size());

        assertTrue(bikes.get(0) instanceof Bike);
        assertTrue(bikes.get(1) instanceof Bike);
        assertTrue(bikes.get(2) instanceof Bike);
       
    }
    
    
}
