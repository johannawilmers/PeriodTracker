package project;

public interface HotelFileManagerInterface {
    
    void write(HotelManager hm, String fileName);
    HotelManager getHotelManager(String fileName);
    
}
