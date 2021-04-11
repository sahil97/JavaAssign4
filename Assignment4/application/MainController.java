package application;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class MainController{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private RadioButton airlineRadioBtn;

    @FXML
    private ToggleGroup viewCategory;

    @FXML
    private RadioButton airlineNumberRadioBtn;

    @FXML
    private RadioButton depAirportRadioBtn;

    @FXML
    private RadioButton arrAirportRadioBtn;

    @FXML
    private Button searchBtn;

    @FXML
    private ChoiceBox<String> airlineChoiceBox;

    @FXML
    private ChoiceBox<String> airlineNumChoiceBox;

    @FXML
    private ChoiceBox<String> depAirportChoiceBox;

    @FXML
    private ChoiceBox<String> arrAirportChoiceBox;

    @FXML
    private Button closeBtn;

    @FXML
    private ListView<String> flightsListView;
    
    // Arraylist to hold the airlines fetched from file
    private static ArrayList<Airline> airlines = new ArrayList<Airline>();

    @FXML
    void initialize() {
       
    	// Disabling all choiceboxes by default
        airlineChoiceBox.setDisable(true);
		airlineNumChoiceBox.setDisable(true);
		depAirportChoiceBox.setDisable(true);
		arrAirportChoiceBox.setDisable(true);
		
		// Loading data from file
		ArrayList<Airline> airlines = getDataFromFile();
		
		// Finding unique values for different attributes
		Set<String> airlineNames = new HashSet<String>();
		Set<String> airlineNumbers = new HashSet<String>();
		Set<String> depAirports = new HashSet<String>();
		Set<String> arrAirports = new HashSet<String>();
		
		airlines.forEach(a -> {
			airlineNames.add(a.getAirlineName());
			airlineNumbers.add(a.getAirlineNumber());
			depAirports.add(a.getDepartureAirport());
			arrAirports.add(a.getArrivalAirport());
		});
		
		
		// Adding values in choiceboxes
		airlineNames.forEach(a -> airlineChoiceBox.getItems().add(a));
		airlineNumbers.forEach(a -> airlineNumChoiceBox.getItems().add(a));
		depAirports.forEach(a -> depAirportChoiceBox.getItems().add(a));
		arrAirports.forEach(a -> arrAirportChoiceBox.getItems().add(a));
		
		// Adding default header in the listView
		String headerString = String.format("%-11s\t%-13s\t%-17s\t%-15s", "Flight Name", "Flight Number", "Departure Airport", "Arrival Airport");
		flightsListView.getItems().add(headerString);
    }
    
    @FXML
    public void viewCategoryAction(ActionEvent action)
    {
    	// Getting id of selected radio button
    	RadioButton selectedRadioButton = (RadioButton) viewCategory.getSelectedToggle();
    	String selectionId = selectedRadioButton.getId();
    	
    	// Disabling all at first
		airlineChoiceBox.setDisable(true);
		airlineNumChoiceBox.setDisable(true);
		depAirportChoiceBox.setDisable(true);
		arrAirportChoiceBox.setDisable(true);
    	
		// Only enabling the required choiceBox
    	switch(selectionId) {
    	case "airlineRadioBtn":
    		airlineChoiceBox.setDisable(false);
    		break;
    	case "airlineNumberRadioBtn":
    		airlineNumChoiceBox.setDisable(false);
    		break;
    	case "depAirportRadioBtn":
    		depAirportChoiceBox.setDisable(false);
    		break;
    	case "arrAirportRadioBtn":
    		arrAirportChoiceBox.setDisable(false);
    		break;
    	}
    }
    
    public static ArrayList<Airline> getDataFromFile() {
		
    	
    	// Reading from file
		Path file = Paths.get("flight.txt");
		String s = "";
		
		try {
			InputStream input = new BufferedInputStream(Files.newInputStream(file));
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			s = reader.readLine();
			
			while(s != null) {
				
				String[] airline = s.split(",");
				// Creating airline object
				Airline tempAirline = new Airline(airline[0], airline[1], airline[2], airline[3]);
				// Adding into arraylist
				airlines.add(tempAirline);
				
				s = reader.readLine();
			}
			reader.close();	
		} catch (Exception e) {
			System.out.println("Message: " + e);
		}
		return airlines;
	}
    
    @FXML
    void searchAirlines(ActionEvent event) {
    	// Getting Id of selected radio button
    	RadioButton selectedRadioButton = (RadioButton) viewCategory.getSelectedToggle();
    	String selectionId = selectedRadioButton.getId();
    	
    	// Clearing the list and adding the default header
    	flightsListView.getItems().clear();
		String headerString = String.format("%-11s\t%-13s\t%-17s\t%-15s", "Flight Name", "Flight Number", "Departure Airport", "Arrival Airport");
		flightsListView.getItems().add(headerString);
		
    	// Filtering the arrayList on the basis of
		// value selected in the required choiceBox
		// then displaying the arrayList in the listView
		switch(selectionId) {
    	case "airlineRadioBtn":
    		
    		String airlineChoice = airlineChoiceBox.getSelectionModel().getSelectedItem().toString();
    		
    		airlines.forEach(a -> {
    			if(a.getAirlineName().equals(airlineChoice)) {
    				
    				String outString = String.format("%-11s\t%-13s\t%-17s\t%-15s", a.getAirlineName(), a.getAirlineNumber(), a.getDepartureAirport(), a.getArrivalAirport());
    				flightsListView.getItems().add(outString);
    			}
    		});
    		break;
    	case "airlineNumberRadioBtn":
    		String airlineNumChoice = airlineNumChoiceBox.getSelectionModel().getSelectedItem().toString();
    		
    		airlines.forEach(a -> {
    			if(a.getAirlineNumber().equals(airlineNumChoice)) {
    				
    				String outString = String.format("%-11s\t%-13s\t%-17s\t%-15s", a.getAirlineName(), a.getAirlineNumber(), a.getDepartureAirport(), a.getArrivalAirport());
    				flightsListView.getItems().add(outString);
    			}
    		});
    		
    		break;
    	case "depAirportRadioBtn":
    		String depAirportChoice = depAirportChoiceBox.getSelectionModel().getSelectedItem().toString();
    		
    		airlines.forEach(a -> {
    			if(a.getDepartureAirport().equals(depAirportChoice)) {
    				
    				String outString = String.format("%-11s\t%-13s\t%-17s\t%-15s", a.getAirlineName(), a.getAirlineNumber(), a.getDepartureAirport(), a.getArrivalAirport());
    				flightsListView.getItems().add(outString);
    			}
    		});
    		
    		
    		break;
    	case "arrAirportRadioBtn":
    		String arrAirportChoice = arrAirportChoiceBox.getSelectionModel().getSelectedItem().toString();
    		
    		airlines.forEach(a -> {
    			if(a.getArrivalAirport().equals(arrAirportChoice)) {
    				
    				String outString = String.format("%-11s\t%-13s\t%-17s\t%-15s", a.getAirlineName(), a.getAirlineNumber(), a.getDepartureAirport(), a.getArrivalAirport());
    				flightsListView.getItems().add(outString);
    			}
    		});
    		
    		break;
    	}
    }

    @FXML
    void closeWindow(ActionEvent event) {
    	// Closing the window
    	Platform.exit();
    }
    
}
