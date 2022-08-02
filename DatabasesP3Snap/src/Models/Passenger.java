package Models;


import java.util.ArrayList;
import java.util.List;

public class Passenger extends Person{
    private int identify_id;
    private List<SavedLocation> savedLocations;

    public Passenger(String phone , String name , int gender, String Pass , int identify) {
        super(phone, gender, name, Pass);
        identify_id = identify;
        savedLocations = new ArrayList<>();
    }


    public int getIdentify_id() {
        return identify_id;
    }

    public List<SavedLocation> getSavedLocations() {
        return savedLocations;
    }

    public void setSavedLocations(List<SavedLocation> savedLocations) {
        this.savedLocations = savedLocations;
    }

    public void addSavedLocations(SavedLocation savedLocation) {
        this.savedLocations.add(savedLocation);
    }

    @Override
    public String toString() {
        return   " نام : '" + name + '\'' + "\n" +
                " تلفن : '"  + Phone + '\'' + "\n" +
                " جنسیت " + gender ;
    }
}
