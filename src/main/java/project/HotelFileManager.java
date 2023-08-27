package project;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Scanner;

public class HotelFileManager implements HotelFileManagerInterface {

/*
The method write() takes in a HotelManager hotelManager as an argument.
Here we have chosen a try catch so that the method tries to write to file, and if it doesen't work there will be caught an exception.
In try: We make a new PrintWriter called writer and place the file in the correct folders.
On top of the file we want to print the current date (this date can only change if admin wants to).
We have two for-loop in this method. The first for-loop iterates through the list of all hotels. 
For each hotel we print the name of the hotel and their maxcapacity with ":" in between.
The other for-loop iterates through every person in the list of all persons in that hotel, and the method 
prints the persons name and the date they got infected with ";" in between.
*/
    public void write(HotelManager hotelManager, String fileName) {
      if (fileName == null) {
        throw new IllegalArgumentException("Filename can't be null");
      }
      if (fileName.isBlank()) {
        throw new IllegalArgumentException("Filename can't be empty");
      }
      hotelManager.setFileName(fileName);
        try {
            PrintWriter writer = new PrintWriter("src/main/resources/project/" + fileName);
            writer.println(hotelManager.getCurrentDate());
            for (Hotel hotel : hotelManager.getHotels()) {
                writer.println(hotel.getHotelName() +":"+ hotel.getMaxCapacity());
                for (Person person : hotel.getPersons()) {
                    writer.println(person.getName() + ";" + person.getDateInfected());                     
                }
                  
            }
    
          writer.flush(); // to check that everything is written to file (if the try goes through)
          writer.close(); // to close the file
          System.out.println("Successfully wrote to 'hotelRegister.txt'"); // for reassurance, feedback to the admin that the persons was saved to hotelRegister.txt

        } catch (IOException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
        }
      }

      /*
      The method "getHotelManager" is a method to read the file 
      */

    public HotelManager getHotelManager(String fileName) {
        try {
            Scanner scanner = new Scanner(new File("src/main/resources/project/" + fileName));
            String line = scanner.nextLine();

            HotelManager hotelManager = new HotelManager(LocalDate.parse(line));
            
            Hotel currentHotel = null;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine().strip();
                String[] lineArr = line.split(";"); // this will split the list only on person and dateinfected --> person ; dateInfected

                if (lineArr.length == 1) { // This will find the line with the hotelname, because the hotel and maxcapacity will be read as a list with lenght 1 (before it is split)
                    lineArr = lineArr[0].split(":"); // this will split hotel and maxcapacity --> hotel : maxCapacity
                    currentHotel = new Hotel(lineArr[0], Integer.parseInt(lineArr[1])); // we create a new Hotel by using the index of hotel and maxCapacity
                    hotelManager.addHotel(currentHotel); // We add the current hotel to the list of all hotels
                }
                else {
                  Person p = new Person(lineArr[0] ,LocalDate.parse(lineArr[1]), currentHotel);
                  currentHotel.addPerson(p);
                }
            }
          hotelManager.setFileName(fileName);
          return hotelManager;

        } catch (IOException e) {
          System.out.println("An error occurred."); // this will print to terminal if the try doesn't work
          e.printStackTrace();
        }
        return null; // return null if there is no one in any of the hotels.

    }
    
}
