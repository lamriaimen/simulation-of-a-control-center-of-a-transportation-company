package rentable;

public class EBikeTest extends RentableTest {
    
        @Override
    
        protected Rentable createRentable() {
            return new EBike();
        }
    
}
