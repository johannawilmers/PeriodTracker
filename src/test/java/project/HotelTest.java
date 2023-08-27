package project;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class HotelTest {
    
    private Hotel hotel; 
    private Hotel hotel2; 
    private Hotel hotel3; 
    private Person person;


    @BeforeEach
    public void setup(){
        hotel = new Hotel("Scandic Lerkendal", 1);
        
    }
    
    // Checks if hotelName is a valid hotel and that maxCapacity is an integer and not a negative value
    @Test
    public void testConstructor() {
        assertEquals("Scandic Lerkendal", hotel.getHotelName());
        assertEquals(1, hotel.getMaxCapacity());
        hotel2 = new Hotel("Scandic Solsiden", 40);
        assertEquals("Scandic Solsiden", hotel2.getHotelName());
        assertEquals(40, hotel2.getMaxCapacity());

    }
    @Test
    // Check that we cant put a negative number 
    public void testSetMaxCapacityWithNegativeNumber() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            hotel.setMaxCapacity(-10);
        });
    }

    @Test
    // Checks that you can't add a person to a hotel if the hotel is full
    public void testFullHotel(){
        hotel3 = new Hotel("City Living", 0);
        Assertions.assertThrows(IllegalStateException.class, () -> {
            hotel3.addPerson(person);
        });
    }

    @Test
    // Checks that you can't add a person to a hotel when the person is already there 
    public void testDuplicatePersons() {
        hotel.setMaxCapacity(10);
        hotel.addPerson(person);
        Assertions.assertThrows(IllegalStateException.class, () -> {
            hotel.addPerson(person);
        });
       }
    
    // Checks if you can remove a person from a hotel
    @Test
    public void testRemovePerson(){
        hotel.addPerson(person);
        hotel.removePerson(person);
        Assertions.assertTrue(hotel.getPersons().isEmpty());
         
    } 
   


    @Test
    // Checks if you can remove a person from a hotel when the person is not in the hotel
    public void testRemoveNonExistingPerson() {
        Assertions.assertFalse(hotel.getPersons().contains(person));
        Assertions.assertThrows(IllegalStateException.class, () -> {
            hotel.removePerson(person);
        });
        
    }

   

    


}
