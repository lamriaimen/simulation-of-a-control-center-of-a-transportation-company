package rentable;

public class BikeTest extends RentableTest {

    @Override

    protected Rentable createRentable() {
        return new Bike();
    }




}
