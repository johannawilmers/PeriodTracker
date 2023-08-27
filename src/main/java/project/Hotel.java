package project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Hotel {

    private List<Person> persons = new ArrayList<Person>();
    private String hotelName; 
    private int maxCapacity;
  
    /*  
    The constructor takes in two arguments: hotelname and maxCapacity
    */
    public Hotel(String hotelName, int maxCapacity) {
        this.hotelName = hotelName;
        this.maxCapacity = maxCapacity; 
    }

    /*
    The getPerson() method takes in personName as an argument.
    The method checks if each person in the list of persons is equal to personName. 
    This method checks if a person is in the list of persons.
    If the person is not in the list the method returns null
    */
    public Person getPerson(String personName) {
        for (Person person : persons) {
            if (person.getName().equals(personName)) {
                return person;
            } 
        }
        return null;

    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public List<Person> getPersons() {
        return new ArrayList<>(persons);
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelName() {
        return hotelName;
    }

 /*
    The setMaxCapacity takes in maxCapacity as an argument.
    If maxCapacity is sat to be less than 0, the method will throw an IllegalArgumentException("Max capasity can't be a negative number!").
    If maxcapacity is valid, the method will run this.maxCapacity = maxCapacity.
    */
    public void setMaxCapacity(int maxCapacity) {
        if (maxCapacity < 0) {
            throw new IllegalArgumentException("Max capasity can't be a negative number!");
        }
        this.maxCapacity = maxCapacity;
    } 

    public int getMaxCapacity() {
        return maxCapacity;
    }

    /*
    The addPerson() method takes in a person as an argument.
    If a person already has a place in a hotel, the method will throw an IllegalStateException("This person already has a hotel").
    If the person doesen't have recieved a hotel yet, the person will be added to the list "persons".
    (persons is a list of all persons that is currently in the hotel.)
    */
    public void addPerson(Person person) {
        if(persons.contains(person)) {
            throw new IllegalStateException("This person already has a hotel");
        }
        if (maxCapacity == persons.size()) {
            throw new IllegalStateException("This hotel is full");
        }
        persons.add(person);
    }
    
    /*
    The method removePerson takes in a person as an argument.
    removePerson checks first if a person has a place in a hotel, and if the person is in the hotel it will get removed.
    If you try to remove a person that is not in the hotel, the method will throw an IllegalStateException("Can't remove this person because this person is not in this hotel")
    */
    public void removePerson(Person person) {
        if(!persons.contains(person)){
            throw new IllegalStateException("Can't remove this person because this person is not in this hotel");
        }
        persons.remove(person); 
    }

    /*
    The method getAvailableRooms takes in no arguments, and returns how many availableRooms there is in each hotel.
    the places available in a hotel depends on the size (length) of the list of persons, therefore: maxCapacity - persons.size()
    */
    public int getAvailableRooms() {
        return maxCapacity - persons.size();
    }

    /*
    The method dateChanged takes in the currentDate as an argument, and checks if the persons checkoutdate is after the currentdate.
    if the checkoutdate is after currentdate, the method calls on removePerson so the person is removed from the hotel.
    */
    public void dateChanged(LocalDate currentDate) {
        for (Person person : getPersons()) {
            if (!person.getCheckoutDate().isAfter(currentDate)){
                this.removePerson(person);
            }
        }
    }

    /*
    The toString method is used for writing to and reading from file.
    */
    @Override
    public String toString() {
        return "Hotel [ persons=" + persons + "]";
    }
    
    /*
    The main method is just a check for us to see if the class works as we want.
    */
    public static void main(String[] args) {
        Hotel scandic = new Hotel("Scandic", 5);
        LocalDate infected = LocalDate.of(2022, 05, 05);
        Person person = new Person("Johanna", infected, scandic);
        System.out.println(person.getCheckoutDate());
    }


}