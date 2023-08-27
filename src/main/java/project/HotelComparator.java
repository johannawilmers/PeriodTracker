package project;

import java.util.Comparator;

public class HotelComparator implements Comparator<Hotel> {

    /*
    This class checks which hotel that has the most places, because we want the app to fill up the hotel
    with the fewest availaiable rooms at each time.
    This method returns:
    if the first hotel, hotel o1, has the least amount of rooms, the method returns a negative number,
    then we want to fill up the first hotel o1.
    if it returns 0, they will have the same amount of available rooms, and it doesen't matter which hotel we fill up first.
    if it returns a positive integer, hotel o2 has the least amount of places and we want to fill up hotel o2.
    */
    @Override
    public int compare(Hotel o1, Hotel o2) {
        return o1.getAvailableRooms() - o2.getAvailableRooms();
    }
    
}
