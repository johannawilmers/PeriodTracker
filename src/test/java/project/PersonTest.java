package project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PersonTest {

    private Hotel hotel2; 

    Hotel hotel = new Hotel("Scandic Lerkendal", 10);
    Person person = new Person("name", LocalDate.now(), hotel);

    private Hotel hotel3;

    @BeforeEach
    public void setup() {
    hotel = new Hotel("Scandic Lerkendal", 10);
    person = new Person("name", LocalDate.now(), hotel);

    }
    

    @Test
    // Checks the constructor
    public void testConstructor() {
        LocalDate dateInfected = LocalDate.now();
        Person person = new Person("name", dateInfected, hotel);
        assertEquals("name", person.getName());
        assertEquals(dateInfected, person.getDateInfected());
        assertEquals("Scandic Lerkendal", hotel.getHotelName());

    }

    @Test
    // Checks if a person is given a valid name
    public void testValidName() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            person.setName("3384628");
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            person.setName("-+='@¨");
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            person.setName("");
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            person.setName(null);
        });
    }

    @Test
    // check if person has a hotel
    public void testPersonAtHotel() {
        hotel.addPerson(person);
        assertEquals(hotel, person.getHotel());
    }

    @Test
    // Check if person can get more than one hotel at the same time
    public void testDuplicateHotels() {
        hotel2 = new Hotel("City Living", 10); 
        hotel3 = new Hotel("Scandic Solsiden", 10); 
        hotel2.addPerson(person);
        assertEquals(person, hotel2.getPerson(person.getName())); 
        hotel3.addPerson(person);
        Assertions.assertThrows(IllegalStateException.class, () -> {
            hotel3.addPerson(person);
        });
    }
}
