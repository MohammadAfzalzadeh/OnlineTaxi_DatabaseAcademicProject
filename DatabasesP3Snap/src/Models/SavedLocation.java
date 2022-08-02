package Models;

//import javax.xml.stream.Location;

public class SavedLocation {
    private int PersonId;
    private int LocId; // -1 is wrong value for it
    private String NameOfLocation;
    private Location lc;
    private String AddressDetail;

    public SavedLocation(int personId, String nameOfLocation, Location lc, String addressDetail) {
        PersonId = personId;
        NameOfLocation = nameOfLocation;
        this.lc = lc;
        AddressDetail = addressDetail;
    }

    public SavedLocation(int personId, int locId , String nameOfLocation, int x , int y, String addressDetail) {
        this(personId , nameOfLocation , new Location(x , y) , addressDetail);
        setLocId(locId);
    }

    public void setLocId(int locId) {
        LocId = locId;
    }

    public int getPersonId() {
        return PersonId;
    }

    public int getLocId() {
        return LocId;
    }

    public String getNameOfLocation() {
        return NameOfLocation;
    }

    public String getAddressDetail() {
        return AddressDetail;
    }

    public Location getLocation() {
        return lc;
    }

    @Override
    public String toString() {
        return " کد مکان : " + LocId + "\n" +
                ", نام ذخیره شده مکان : '" + NameOfLocation + '\'' + "\n" +
                ", x : " + lc.getX()  +  " , y : " + lc.getY() + "\n" +
                ", جزئیات آدرس : '" + AddressDetail + '\'' ;
    }
}
