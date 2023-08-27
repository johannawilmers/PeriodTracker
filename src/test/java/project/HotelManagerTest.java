package project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Assertions;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class HotelManagerTest {

    HotelManager hotelManager = new HotelManager(LocalDate.now());
    HotelManager hotelManager1 = new HotelManager(LocalDate.now());
    Hotel hotel = new Hotel("Scandic Lerkendal", 5);
    Hotel hotel2 = new Hotel("Scandic Solsiden", 5);
    Hotel hotel3 = new Hotel("City Living", 5);

    @BeforeEach
    public void setup(){
        hotelManager.addHotel(hotel);
        hotelManager.addHotel(hotel2);
        hotelManager.addHotel(hotel3);
        
        hotel = new Hotel("Scandic Lerkendal", 10);
    }
  
    
    // Checks the constructor, and that you can't get the hotel on index 3 (non-existing) 
    @Test
    public void testConstructor() {
        assertEquals(LocalDate.now(), hotelManager.getCurrentDate());
        assertEquals("Scandic Lerkendal", hotelManager.getHotels().get(0).getHotelName());
        assertEquals("Scandic Solsiden", hotelManager.getHotels().get(1).getHotelName());
        assertEquals("City Living", hotelManager.getHotels().get(2).getHotelName());
        assertThrows(IndexOutOfBoundsException.class, () -> {
            hotelManager.getHotels().get(3); 
        });

    }

    // Checks if only addHotel can add a hotel
    @Test
    public void testAddHotel() {
        hotelManager.getHotels().add(hotel);
        assertFalse(hotelManager.getHotels().contains(hotel));
    }


    /*Checks if a you can add a person to a hotel, and that we get a IllegalStateException 
    when we try to add a person to a full hotel
    */
    
    @Test
    public void testGetAvailableHotels() {
        hotel = new Hotel("Scandic Lerkendal", 1);
        hotel2 = new Hotel("Scandic Solsiden", 1);
        hotel3 = new Hotel("City Living", 1);
        Person p1 = new Person("pen", LocalDate.now(), hotelManager.getCurrentHotel());
        Person p2 = new Person("pto", LocalDate.now(), hotelManager.getCurrentHotel());
        Person p3 = new Person("ptre", LocalDate.now(), hotelManager.getCurrentHotel());
        Person p4 = new Person("pfire", LocalDate.now(), hotelManager.getCurrentHotel());
        hotel.addPerson(p1);
        hotel2.addPerson(p2);
        hotel3.addPerson(p3);
        assertThrows(IllegalStateException.class, () -> {
            hotel.addPerson(p4);
        });

    }

    // Checks that the hotel added in the list of hotels is the current hotel
    @Test
    public void testgetCurrentHotel() {
        hotel = new Hotel("Scandic Lerkendal", 1);
        hotelManager1.addHotel(hotel);
        assertEquals(hotel, this.hotelManager1.getCurrentHotel());

    }

    /*
    Checks if the method increaseDays() works; the method takes in days as an argument and
    increases the current date with the same number of days as the method takes in.
    */
    @Test
    public void testIncreaseDays() {
        LocalDate currentDate = LocalDate.now();
        hotelManager.increaseDays(1);
        assertEquals(hotelManager.getCurrentDate(), currentDate.plusDays(1));

    }

    // Checks if dateInfected is a valid date. it is valid if it is before the current date.
    @Test
    public void testValidateAndSetPersonAtHotel() {
        LocalDate currentDate = LocalDate.now();
        LocalDate dateInfected = currentDate.plusDays(10);
        Person person9 = new Person("Turid", dateInfected, hotelManager.getCurrentHotel());
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            hotelManager.setPersonAtHotel(person9);
        });
        
    }

  
}
