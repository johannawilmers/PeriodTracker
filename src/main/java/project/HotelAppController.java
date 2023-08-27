package project;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


public class HotelAppController implements Initializable {

    @FXML private Pane output;
    @FXML private Label today;
    @FXML private Label recievedHotelName;
    @FXML private Label checkoutDate;
    @FXML private TextField name;
    @FXML private DatePicker DateOfSymptoms;
    @FXML private Pane imagePane;
    @FXML private ListView<String> listView;
    @FXML private Pane hotelView;
    @FXML private Button nextDayButton, backDayButton;


    private Person activePerson;
    private HotelManager hotelManager;
    private Hotel hotel;
    private Boolean isAdminView = false;
 

    /* 
        Line 50- to make it possible read and write to file 
        Line 51- gets the text file that belongs to the hotel manager 
        Line 52- adminview is set to be invisible at the start 
        Line 53- sets todays text to be the current date of the hotel manager 
    */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        HotelFileManagerInterface HotelFileManager = new HotelFileManager();
        hotelManager = HotelFileManager.getHotelManager("hotelRegister.txt");
        SetAdminView(false);
        today.setText("Today's date: " + hotelManager.getCurrentDate());       
    }

    /*
    The method @FXML getAvailableHotels() contains a try-catch
    */
    @FXML
    private void getAvailableHotels() throws FileNotFoundException{
        
        try {
        Hotel currentHotel = hotelManager.getCurrentHotel();

        /* 
        First we want to set a current hotel, currentHotel. Then we check if currentHotel is equal to null,
        then we want the labels in FXML "recievedHotelName" and "checkoutDate" to be sat to null, 
        and then we want an image to show that says "No available hotels nearby."
        */
        if (Objects.isNull(currentHotel)) {
            recievedHotelName.setText(null);
            checkoutDate.setText(null);
            setImage("NoAvaliableHotels.png");
            SetAdminView(false);
            return;
        }

        /*
        The method getPersonAtHotel() is a method that returns person, else null if person is equal to 0.
        if activePerson is null, it means that the person is not in a hotel, so we need to add the person.
        If activeperson is NOT null, that means that the person is already in a hotel; therefore throw new IllegalStateException("The person " + activePerson.getName() + " is already in a hotel")
        */
        activePerson = hotelManager.getPersonAtHotel(name.getText());
        if (activePerson != null) {
            throw new IllegalStateException("The person " + activePerson.getName() + " is already in a hotel");
        }

        /*
        if the person is null, it means that the person is not already in a hotel, and therefore we create a new person 
        */
        activePerson = new Person(name.getText(), DateOfSymptoms.getValue(), hotel);

        /*
        Here we set activePerson to an available hotel 
        setText() method is used to set the text to the belonging FXML id's 
        */
        hotelManager.setPersonAtHotel(activePerson);
        recievedHotelName.setText("You have recieved a room at: " + currentHotel.getHotelName());
        checkoutDate.setText("Checkoutdate: " + activePerson.getCheckoutDate().toString());
       
        /*
        Each hotel has an unique picture. Here we set each picture to its belonging hotel so that they appear when that hotel 
        is the current hotel 
        */
        
        String imageName = "";
        if (currentHotel.getHotelName().equals("Scandic Lerkendal")) {
            imageName = "ScandicLerkendal.png";
        }
        else if (currentHotel.getHotelName().equals("Scandic Solsiden")) {
            imageName = "ScandicSolsiden.png";
        }
        else if (currentHotel.getHotelName().equals("City Living")) {
            imageName = "CityLiving.png";
        }

        setImage(imageName);
        
        SetAdminView(false);
                    
        /*
        If try doesn't work, there will caught an exception "e" with an alertmessage. 
        */
        } catch (Exception e) {
            createAlert(e.getMessage());
        }

    } 
    /* 
    Creates an alert with "OBS" and the string content
    */
    private void createAlert(String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("OBS");
        alert.setContentText(content);
        alert.show();

    }

    /*
    The method setImage() takes in a string imageName as an argument.
    This is a help method that we use for reusable coding, the only thing that needs to change is the imageName, 
    so therefore we have written: FileInputStream input = new FileInputStream("src/main/resources/project/" + imageName);
    */
    private void setImage(String imageName) throws FileNotFoundException  {
        FileInputStream input = new FileInputStream("src/main/resources/project/" + imageName);
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        imageView.setTranslateX(1); 
        imageView.setTranslateY(60); 
        imageView.setImage(image); 
        imageView.setFitHeight(150.0);
        imageView.setFitWidth(200.0);
        imagePane.getChildren().clear();
        imagePane.getChildren().add(imageView);

    }


    /*
    The method SetAdminView() is a method that is called when you click on the "Admin" button in the app. The method switch from
    the the users perspective to admins perspective. In admin you can see all the persons in the hotels at each time. 
    */

    @FXML
    private void SetAdminView() {
        SetAdminView(!isAdminView);

    }

    private void SetAdminView(Boolean adminView) {

        int bool = adminView ? 1 : 0;
        

        hotelView.setDisable(!adminView);
        hotelView.setOpacity(bool);

        nextDayButton.setOpacity(bool); 
        nextDayButton.setDisable(!adminView);

        backDayButton.setOpacity(bool); 
        backDayButton.setDisable(!adminView);

        output.setDisable(adminView);
        output.setOpacity(1-bool);
        this.isAdminView = adminView;
    }

    /*
    The method updateListView updates the pages at the hotels so that it gets updated if you have changed the date
    each time the method is called, it will first clear the listview, and then add the updated information. 
    updateListView is used when you click the button for each hotel in admin view. 
    
    */
    private void updateListView(Hotel hotel) {
        System.out.println(hotel.getPersons());
        listView.getItems().clear();
        
        for (Person person : hotel.getPersons()) {
            listView.getItems().add(person.getName() + "'s checkoutdate is sat to be: " + person.getCheckoutDate().toString());
        }

    }

    
    // buttons for each hotels listview that calls on updateListView in fxml (onAction in scenebuilder)

    @FXML
    private void lerkendalButton() {
        updateListView(hotelManager.getHotel("Scandic Lerkendal"));
    }
    @FXML
    private void solsidenButton() {
        updateListView(hotelManager.getHotel("Scandic Solsiden"));
    }
    @FXML   
    private void cityLivingButton() {
        updateListView(hotelManager.getHotel("City Living"));
    } 

    private void addDay(int n) {
        hotelManager.increaseDays(n);
        today.setText("Today's date: " + hotelManager.getCurrentDate());

    }

    // nextDay() and backDay() are two functions that are used for the admin to see to the days forward or backward in time.
    @FXML
    private void nextDay() {
        addDay(1);
    }

    @FXML
    private void backDay() {
        addDay(-1);
    }
}
