package project;

import java.time.LocalDate;

public class Person {
   
    private String name;
    private LocalDate dateInfected; 
    public final static int isolationDays = 7; 
    private LocalDate checkoutDate; 
    private Hotel hotel; 
    
    /*
    The constructor takes in name, dateInfected and hotel as arguments.
    */
    public Person(String name, LocalDate dateInfected, Hotel hotel) {
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException("You need to set a name");
        }
        if (dateInfected == null) {
            throw new IllegalArgumentException("You need to set a date");
        }
        setName(name);
        setDateInfected(dateInfected);
        setCheckoutDate(dateInfected);
        this.hotel = hotel;
        
    }
    
    /*
    The method setName() validates the persons name and sets this.name to be name if the name is valid.
    */
    public void setName(String name) {
        if (!name.matches("[a-zA-Z]+")){
            throw new IllegalArgumentException("The name can only contain letters from the alfabeth A-Z!");
        }
        else if (name.equals(null)) {
            throw new NullPointerException("You need to set a name");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Hotel getHotel() {
        return hotel;
    }
    
    public static int getIsolationDays() {
        return isolationDays;
    }

    public void setCheckoutDate(LocalDate dateInfected) {
        this.checkoutDate = dateInfected.plusDays(isolationDays);
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate; 
    }

    
    /*
    The toString() method is used for when writing to and reading from file.
    */
    @Override
    public String toString() {
        return "Person [checkoutDate=" + checkoutDate + ", dateInfected="
                + dateInfected + ", name=" + name + "]";
    }

    public void setDateInfected(LocalDate dateInfected) {        
        this.dateInfected = dateInfected;
    }

    public LocalDate getDateInfected() {
        return dateInfected;
    }

    public String getPersonAtHotel() {
        return name + " har fått plass på "+ hotel;
    } 


    /*
    The main method is just a check for us to see if the class works as we want.
    */
    public static void main(String[] args) {
        LocalDate infected1 = LocalDate.of(2022,01,15);
        Hotel solsiden = new Hotel("Scandic", 5);
        Person person1 = new Person("Johanna", infected1, solsiden);
        System.out.println(person1.name);
        System.out.println(person1.dateInfected);
        System.out.println(Person.isolationDays);

    }
       
}
