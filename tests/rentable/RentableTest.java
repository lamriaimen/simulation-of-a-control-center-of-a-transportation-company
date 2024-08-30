package rentable;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import rentable.*;

public abstract class RentableTest {

    protected Rentable rentable;  // This will be initialized in subclasses
    protected Rentable rentableBroken;
	protected abstract Rentable createRentable();


    @Before
    public  void init() throws Exception{
		this.rentable = this.createRentable();
        this.rentableBroken = this.createRentable();

        for (int i = 0; i < 10; i++) {
              
                rentableBroken.rent();
                rentableBroken.giveBack();


            }
          
        }
	

    @Test
    public void testTheUnicityOfIds() {
        assertNotEquals(rentable.getId(), rentableBroken.getId());
    }

   
    @Test
public void testRenting()  {

    try {
        rentable.rent();
         assertEquals(RentedState.class, rentable.getState().getClass());
    rentable.giveBack();
    } catch (Exception e) {
        
        fail("Unexpected exception: " + e.getMessage());
    }
   
}

@Test
public void testRentingInRentedState()  {
    try {
        rentable.rent();
        rentable.rent();
    } catch (Exception e) {
        assertEquals("Already rented", e.getMessage());
    }
}


@Test
public void testRentingInStolenState()  {
    try {
   
        Rentable rentableStolen = this.createRentable();
        rentableStolen.setState( new StolenState(rentableStolen));
        rentableStolen.rent();


    } catch (Exception e) {
        assertEquals("Stolen :(", e.getMessage());
    }
}

@Test
public void testRentingInBrokenState()  {
    try {
        rentableBroken.rent();
        rentableBroken.giveBack();
        rentableBroken.rent();
    } catch (Exception e) {
        assertEquals("Broken bikes cannot be rented", e.getMessage());
    }
}



@Test
public void testGivingBackRentedState(){
    assertEquals(0, rentable.getRentedCount());
    try {
        rentable.rent();
        rentable.giveBack();

    
    } catch (Exception e) {
        fail("Unexpected exception: " + e.getMessage());
    }
    assertEquals(1, rentable.getRentedCount());
    assertEquals(RentableState.class, rentable.getState().getClass());


}

@Test  
public void testGivingBackBrokenState()  {
    assertEquals(10, rentableBroken.getRentedCount());

    try {
        rentableBroken.rent();
        rentableBroken.giveBack();

    } catch (Exception e) {
        fail("Unexpected exception: " + e.getMessage());
    }
    

    assertEquals(11, rentableBroken.getRentedCount());
    assertEquals(BrokenState.class, rentableBroken.getState().getClass());

}

@Test  
public void testGivingBackStolenState()  {
    assertEquals(0, rentable.getRentedCount());
    try {
        Rentable rentableStolen = this.createRentable();
        rentableStolen.setState( new StolenState(rentableStolen));
        rentableStolen.giveBack();

    } catch (Exception e) {
        assertEquals("Stolen :(", e.getMessage());
    }
}




@Test
public void testFixingBrokenState()  {
     assertEquals(10, rentableBroken.getRentedCount());

    try {
        rentableBroken.rent();
        rentableBroken.giveBack();

    } catch (Exception e) {
        fail("Unexpected exception: " + e.getMessage());
    }
     assertEquals(BrokenState.class, rentableBroken.getState().getClass());


    try {
        rentableBroken.fix();
    } catch (Exception e) {
        fail("Unexpected exception: " + e.getMessage());
    }
            assertEquals(RentableState.class, rentableBroken.getState().getClass());

}

@Test
public void testFixingRentableState()  {
    assertEquals(0, rentable.getRentedCount());
        assertEquals(RentableState.class, rentable.getState().getClass());

    try {

        rentable.fix();
    } catch (Exception e) {
        assertEquals("Nothing to fix", e.getMessage());
    }

}

@Test
public void testFixingRentedState()  {
    assertEquals(0, rentable.getRentedCount());
        assertEquals(RentableState.class, rentable.getState().getClass());

    try {
        rentable.rent();
        rentable.fix();
    } catch (Exception e) {
        assertEquals("Give the bike back before", e.getMessage());
    }

}


}