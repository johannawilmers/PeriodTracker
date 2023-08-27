package project;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HotelFileManagerTest {

    HotelFileManagerInterface filehandler = new HotelFileManager();
    HotelManager hotelManager;
    HotelManager hotelManagerAfterRead;

    @BeforeEach
    public void setup() throws FileNotFoundException {
        Hotel hotel1 = new Hotel("TestHotel1", 10);
        Person person1 = new Person("PersonONE", LocalDate.now(), hotel1);
        hotel1.addPerson(person1);

        Hotel hotel2 = new Hotel("TestHotel2", 5);
        Person person2 = new Person("PersonTWO", LocalDate.now(), hotel1);
        hotel2.addPerson(person2);

        hotelManager = new HotelManager(LocalDate.now());

        hotelManager.addHotel(hotel1);
        hotelManager.addHotel(hotel2);
    }

    // Checks if something is written to file
    @Test
    public void testWrite() {
        filehandler = new HotelFileManager();
        assertDoesNotThrow(() -> filehandler.write(hotelManager, "testFile.txt") );

        assertThrows(IllegalArgumentException.class , () -> filehandler.write(hotelManager, ""));
        assertThrows(IllegalArgumentException.class , () -> filehandler.write(hotelManager, null));

    }

    // Checks if you read the as what is written.
    @Test
    public void testRead(){
        filehandler.write(hotelManager, "testFile.txt");
        HotelManager hotelManagerRead = filehandler.getHotelManager("testFile.txt");

        assertEquals(hotelManager.getHotels().size(), hotelManagerRead.getHotels().size());
        assertEquals(hotelManager.getCurrentDate(), hotelManagerRead.getCurrentDate());

        for (int i = 0; i < hotelManagerRead.getHotels().size(); i++) {
            Hotel currentHotel = hotelManager.getHotels().get(i);
            Hotel currentHotelRead = hotelManagerRead.getHotels().get(i);
            assertEquals(currentHotel.getPersons().size(), currentHotelRead.getPersons().size());
            assertEquals(currentHotel.getMaxCapacity(), currentHotelRead.getMaxCapacity());

            for (int j = 0; j < currentHotel.getPersons().size(); j++) {
                Person currentPerson = currentHotel.getPersons().get(j);
                Person currentPersonRead = currentHotelRead.getPersons().get(j);

                assertEquals(currentPerson.getName(), currentPersonRead.getName());
                assertEquals(currentPerson.getCheckoutDate(), currentPersonRead.getCheckoutDate());
                assertEquals(currentPerson.getDateInfected(), currentPersonRead.getDateInfected());
            }
        }

    }

}
