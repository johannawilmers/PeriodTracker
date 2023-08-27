package project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HotelManager {
    private static String[] hotelStrings = {"Scandic Lerkendal", "Scandic Solsiden", "City Living"};
    public static final List<String> HOTEL_LIST = new ArrayList<String>(Arrays.asList(hotelStrings));
    private List<Hotel> hotels = new ArrayList<Hotel>();
    private LocalDate currentDate;
    private HotelFileManagerInterface HotelFileManager = new HotelFileManager();
    private String fileName;
    private HotelComparator hotelComparator = new HotelComparator();

    /* 
    The constructor only takes in a date as an argument, we don't think it is necessary to 
    take in a Hotel for example beacause we have methods like addHotel()
    Here we set the current date, therefore we don't have a method SetCurrentDate()
    */ 
    public HotelManager(LocalDate date) {
        this.currentDate = date;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    // This method adds a hotel to the list of all hotels.
    public void addHotel(Hotel hotel) {
        hotels.add(hotel);
    }
    
    public LocalDate getCurrentDate() {
        return currentDate;
    }

    /*
    This method goes through the list with all the hotels and finds the hotelName that matches the one that belongs 
    to the current hotel. If no hotelname matches the hotels name the method will throw noSuchElementException().
    */
    public Hotel getHotel(String hotelName) {
        return hotels.stream().filter(hotel -> hotel.getHotelName().equals(hotelName)).findFirst().orElseThrow();
    }

    public List<Hotel> getHotels() {
        return new ArrayList<>(hotels);
    }

    /*
    This method has a for-loop that loops through each hotel in the list all hotels, 
    and then if the hotel has more available rooms than zero, the hotel gets added to a new list we have 
    called availableHotels (a list of all available hotels). 
    If there is no available hotels, the method will throw a new IllegalStateException("No avaliable hotels"), 
    else the method will return the list of available hotels.
    */
    public ArrayList<Hotel> getAvailableHotels() {
        ArrayList<Hotel> availableHotels = new ArrayList<>();
        for (Hotel hotel : hotels) {
            if(hotel.getAvailableRooms() > 0){
                availableHotels.add(hotel);
            }
            else  {
                throw new IllegalStateException("No avaliable hotels");
            }
        }
        return availableHotels;
    }

        /*
        The method getCurrentHotel() sorts the list with available hotels so the hotel with the 
        least amount of available rooms gets first in the list.
        Then there is a for loop that loops through each hotel in the list of all hotels and returns 
        the hotel with the fewest available rooms (as long as it is more than zero available rooms)
        */

    public Hotel getCurrentHotel() {
        hotels.sort(hotelComparator);
        for (Hotel hotel : hotels) {
            if(hotel.getAvailableRooms() > 0){
                return hotel;
            }
            
        }
        return null;
    }
        

    public int getAvailableRooms() {
        return this.getCurrentHotel().getAvailableRooms();
        
    }

    /*
    The method increaseDays() changes the current date to a new day 
    (only Admin can change the date to see all the persons in every hotel at each time)
    The method has a for loop that loops through hotels and then the method dateChanged() 
    is called and this method removes persons from the hotel if they are done with isolation.
    */
    public void increaseDays(int days) {
        this.currentDate = currentDate.plusDays(days);
        for (Hotel hotel : getHotels()) {
            hotel.dateChanged(currentDate);
        }
        this.save();
    }

    public void setPersonAtHotel(Person person) {
        ValidateAndSetPersonAtHotel(person, getCurrentHotel());
    }

    private void ValidateAndSetPersonAtHotel(Person person, Hotel currentHotel) {
        if(!person.getDateInfected().plusDays(Person.isolationDays).isAfter(currentDate)){
            throw new IllegalArgumentException("Checkoutdate can't be before the current date!"); 
        }
        if (person.getDateInfected().isAfter(currentDate)) {
            throw new IllegalArgumentException("You can't choose a future date");
        }

        currentHotel.addPerson(person);
        this.save();
    }

    /**
     * 
     * @param personName
     * @return The person if person exists in the hotel based on personName, else null
     */
    public Person getPersonAtHotel(String personName) {
        for (Hotel hotel : hotels) {
            Person person = hotel.getPerson(personName);
            if (person != null) {
                return person;
            }
        }
        return null;
    }

    private void save()
    {
        if (this.fileName == null) {
            return;
        }
        if (this.fileName == "") {
            return;
        }
        HotelFileManager.write(this,fileName);
    }

    /*
    The main method is just a check for us to see if the class works as we want.
    */
    public static void main(String[] args) {
        HotelManager hm = new HotelManager(LocalDate.now());
        Person person = new Person("Johanna", LocalDate.now(), hm.getCurrentHotel());
        System.out.println(person);
    }
   

}


