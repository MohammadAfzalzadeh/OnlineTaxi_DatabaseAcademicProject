import Models.*;
import Models.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private Connection connection;

    public Database() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/db_databaseprgp2g9","Mohammad","Admin_123");

        }catch (SQLException se){
            se.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public List<SavedLocation> getAllSavedLocationOfPassenger(Passenger p){
        ArrayList<SavedLocation> sls = new ArrayList<>();
        String qry = "select ID, LocId, NameOfLoc, ST_x (Loc)  , ST_y (Loc) , Address from savedloc where ID = ? ;";
        ArrayList<Object> para = new ArrayList<>();
        para.add(p.getId());
        try {
            ResultSet rs = runQueryWhitOutput(qry , para);
            while (rs.next()){
                sls.add(new SavedLocation(rs.getInt(1), rs.getInt(2), rs.getString(3),
                        rs.getInt(4)  , rs.getInt(5)  ,rs.getString(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sls;
    }

    private boolean isDiscountCodeVaild(int id , int discountCode ){
        String qry ="select count(*) from discount where Code = ? and ID = ?;";
        ArrayList<Object> paras = new ArrayList<>();
        paras.add(discountCode);
        paras.add(id);

        boolean b = false;

        try {
            ResultSet rs = runQueryWhitOutput(qry , paras);
            if (rs.next())
                b = rs.getInt(1) >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return b;

    }

    public long addRequestTrip(Trip trip) {

        if ( trip.getDiscountCode() != null && ! isDiscountCodeVaild(trip.getPassenger().getId() , trip.getDiscountCode())){
            throw new IllegalArgumentException("کد تخفیف معتبر نیست ");
        }

        String qry = "insert into trip (Status , PassId, Amount, PaymentMethod, DiscountCode, StartLoc, EndLoc, StartSavedLoc, EndSavedLoc) " +
                              " values (? , ? , ? , ? , ? , ST_GeomFromText( ? ,0) , ST_GeomFromText( ? ,0) , ? , ?  );";

        ArrayList<Object> paras = new ArrayList<>();
        paras.add(0);
        paras.add(trip.getPassenger().getId());
        paras.add(trip.getAmount());
        paras.add(trip.getPayment().toString());
        paras.add(trip.getDiscountCode());
        paras.add(trip.getStartLoc() != null ? "POINT(" + trip.getStartLoc().getX() +" " + trip.getStartLoc().getX() +")" : null );
        paras.add(trip.getEndLoc() != null ? "POINT(" + trip.getEndLoc().getX() +" " + trip.getEndLoc().getX() +")" : null );
        paras.add(trip.getStartSavedLocation() != null ? trip.getStartSavedLocation().getLocId() : null);
        paras.add(trip.getEndSavedLocation() != null ? trip.getEndSavedLocation().getLocId() : null);

        String qry2 = "select TripId " +
                "from trip where PassId = ? and DriverId is null;";
        ArrayList<Object> para2  = new ArrayList<>();
        para2.add(trip.getPassenger().getId());

        try {
            runQuery(qry , paras);
            ResultSet rs = runQueryWhitOutput(qry2 , para2);
            if (rs.next())
                return rs.getLong(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return -1;
    }

    public Driver DriverAcceptedTrip(long tripId ){
        Driver d = new Driver(-1 );

        String qry = "select D.ID , p.Phone , p.FullName , P.Gender , D.Car_PNum1 ,  D.Car_PChr , D.Car_PNum2 , D.Car_CNum , D.Car_Model , D.Car_Color " +
                "from trip t left join  driver d on t.DriverId = d.ID  left join person P on d.ID = P.ID " +
                "where TripId = ?";
        ArrayList<Object> para = new ArrayList<>();
        para.add(tripId);

        try {
            ResultSet rs = runQueryWhitOutput(qry , para);
            if (rs.next())
                if (rs.getInt(1) != 0) {
                    d =new Driver( rs.getInt(1) ,
                            rs.getString(2) ,
                            rs.getString(3) ,
                            rs.getString(4).equals("Male") ? 0 : 1 ,
                            rs.getInt(5) ,
                            rs.getString(6).charAt(0) ,
                            rs.getInt(7) ,
                            rs.getInt(8) ,
                            rs.getString(9) ,
                            rs.getString(10)
                            );

                }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return d;
    }

    public int isUserValid(String phone , String pass)  {

        String qry = "select id from person " +
                "where Phone = ? and Pass = ?;";

        try {
            PreparedStatement ps = connection.prepareStatement(qry);
            ps.setString(1 , phone);
            ps.setString(2 , pass);
            ResultSet rs = ps.executeQuery();
        if (rs.next())
            return rs.getInt("id");
        else
            return -1;
        } catch (SQLException e) {
            return  -1;
        }
    }

    public UserType findTypeOfUser(int id){
        UserType resUserType = null;
        String qry = "(select count(id) AS ctn from passenger where id = ?) " +
                " union all " +
                " (select  count(id) AS ctn  from  driver where id = ?)";

        try {
            PreparedStatement ps = connection.prepareStatement(qry);
            ps.setInt(1 , id);
            ps.setInt(2 , id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                if (rs.getInt(1) == 1)
                    resUserType = UserType.Passenger;
                if (rs.next() && rs.getInt(1) == 1)
                    if (resUserType == UserType.Passenger)
                        resUserType = UserType.Both;
                    else
                        resUserType = UserType.Driver;
            }
        } catch (SQLException e) {
            return  null;
        }
        return  resUserType;
    }

    public void addPassengers(Passenger passenger) {
        try {
            if ( isPhoneNumberValid(passenger.getPhone()))
                throw new IllegalArgumentException("شماره تلفن تکراری است !");
            passenger.setId(  addUser(passenger) );
            String  qry = "insert into passenger"
                    + " VALUES ( ? , ? );";
            ArrayList<Object> paras = new ArrayList<>();
            paras.add(passenger.getId());
            paras.add((passenger.getIdentify_id() == 0 ? null : passenger.getIdentify_id()));
            runQuery(qry , paras);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addDriver(Driver driver){
        try {
            driver.setId( addUser(driver) );
            isValidNationalNumber(driver.getNationalNumber());
            isValidPelak(driver.getCar_Pelak());
            isValidCertiNum(driver.getCertificate_Number());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addSavedLocationForPassenger(SavedLocation sl ){
        String q = "select max(LocId)\n" +
                "from savedloc where ID = ?;";
        ArrayList<Object> para = new ArrayList<>();
        para.add(sl.getPersonId());
        try {
            ResultSet rs = runQueryWhitOutput(q ,para);
            if (rs.next())
                sl.setLocId( rs.getInt(1) + 1 );

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String qry = "INSERT INTO `savedloc` " +
                "VALUES (?, ?, ?, ST_GeomFromText( ? ,0), ?);";

        ArrayList<Object> paras = new ArrayList<>();
        paras.add(sl.getPersonId());
        paras.add(sl.getLocId());
        paras.add(sl.getNameOfLocation());
        paras.add( "POINT( " + sl.getLocation().getX() + " " + sl.getLocation().getY() + " )" );
        paras.add(sl.getAddressDetail().equals("") ? null : sl.getAddressDetail());

        try {
            runQuery(qry , paras);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void isValidCertiNum(int certificate_number) {
    }

    private void isValidPelak(String car_pelak) {
    }

    private void isValidNationalNumber(String nationalNumber) {
    }

    public int addUser(Person p
    ) throws SQLException {

        if  (isPhoneNumberValid(p.getPhone()))
            throw new IllegalArgumentException("Phone number is already exist!!");

        String qry = "insert into person"+
                " VALUES (NULL , ? , ? , ? , ?);";
        ArrayList <Object> paras = new ArrayList<>();
        paras.add(p.getPhone());
        paras.add(p.getName());
        paras.add(p.getGenderStr());
        paras.add(p.getPass());
        runQuery(qry , paras);

        qry = "select * from person where Phone = ? ;";
        paras.removeAll(paras);
        paras.add(p.getPhone());
        ResultSet rs  = runQueryWhitOutput(qry , paras);

        if (rs.next())
            p.setId(rs.getInt("id"));

        return p.getId();

    }

    private boolean isPhoneNumberValid(String Phone){
        String qry = "select * from person where Phone = ? ;";
        ArrayList paras = new ArrayList<>();
        paras.add(Phone);

        try {
            return runQueryWhitOutput(qry , paras).next();
        } catch (SQLException e) {
            System.out.println("E is" + e);
            return false;
        }
    }

    public void cancelTrip(Long tripId) {
        String qry = "delete from trip where TripId = ? ;";
        ArrayList<Object> para = new ArrayList<>();
        para.add(tripId);

        try {
            runQuery(qry , para);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int showStatusTrip(Trip t) {
        int i = -1;

        String qry = "select Status from trip where TripId = ?;";
        ArrayList<Object> para = new ArrayList<>();
        para.add(t.getTripId());

        try {
            ResultSet rs = runQueryWhitOutput(qry , para);
            if (rs.next())
                i = rs.getInt(1);

        } catch (SQLException e) {}

        return i;
    }

    public List<Trip> getAllTripDosentHaveDriver() {
        ArrayList<Trip> ts = new ArrayList<>();
        String qry = "select TripId  , Phone , FullName , Gender , Amount , PaymentMethod  ,\n" +
                "       ST_X(StartLoc)  , ST_Y(StartLoc)  , ST_X(EndLoc)  , ST_Y(EndLoc) ,\n" +
                "       ST_X(sl1.Loc) ,ST_Y(sl1.Loc) , sl1.NameOfLoc , sl1.Address , \n" +
                "       ST_X(sl2.Loc) ,ST_Y(sl2.Loc) , sl2.NameOfLoc , sl2.Address ,\n" +
                "       d.Percent , d.Max\n" +
                "from trip join passenger ps on trip.PassId = ps.ID  join person p on p.ID = ps.ID\n" +
                "left join savedloc sl1 on sl1.LocId = trip.StartSavedLoc and sl1.ID = ps.ID \n" +
                "left join savedloc sl2 on sl2.LocId = trip.endSavedLoc and sl2.ID = ps.ID \n" +
                "left join discount d on d.Code = trip.DiscountCode\n" +
                "where Status = ? ;";
        ArrayList<Object> para = new ArrayList<>();
        para.add(0);
        try {
            ResultSet rs = runQueryWhitOutput(qry , para);
            while (rs.next()){
                ts.add(new Trip(
                        rs.getLong(1) ,
                        new Passenger(rs.getString(2) , rs.getString(3), rs.getString(4).equals("Male") ? 0:1  , null , -1) ,
                        rs.getLong(5) ,
                        rs.getString(6) ,
                        new Location( rs.getInt(7) , rs.getInt(8) ) ,
                        new Location( rs.getInt(9) , rs.getInt(10) ) ,
                        new Location( rs.getInt(11) , rs.getInt(12) ) ,
                        rs.getString(13) ,
                        rs.getString(14) ,
                        new Location( rs.getInt(15) , rs.getInt(16) ) ,
                        rs.getString(17) ,
                        rs.getString(18) ,
                        rs.getDouble(19) ,
                        rs.getInt(20)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ts;
    }

    public void reservingTrip(Long tripId , int driverId) {
        String qry = "update trip \n" +
                "set DriverId = ? , Status = 1 \n" +
                "where TripId = ?;";
        ArrayList<Object> paras = new ArrayList<>();
        paras.add(driverId);
        paras.add(tripId);

        try {
            runQuery(qry , paras);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void finishTrip(Long tripId) {
        String qry = "update trip \n" +
                "set  Status = 2 , EndTime = current_timestamp() \n" +
                "where TripId = ?;";
        ArrayList<Object> paras = new ArrayList<>();
        paras.add(tripId);

        try {
            runQuery(qry , paras);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Trip> getAllFinishedTripsOfPassenger(int id) {
        ArrayList<Trip> ts = new ArrayList<>();
        String qry = "select TripId  , Phone , FullName , Gender , Amount , PaymentMethod  ,\n" +
                "       ST_X(StartLoc)  , ST_Y(StartLoc)  , ST_X(EndLoc)  , ST_Y(EndLoc) ,\n" +
                "       ST_X(sl1.Loc) ,ST_Y(sl1.Loc) , sl1.NameOfLoc , sl1.Address , \n" +
                "       ST_X(sl2.Loc) ,ST_Y(sl2.Loc) , sl2.NameOfLoc , sl2.Address ,\n" +
                "       d.Percent , d.Max , dr.Car_PNum1, dr.Car_PChr ,  dr.Car_PNum2 , dr.Car_CNum , dr.Car_Model , dr.Car_Color , PassRate \n" +
                "from trip join driver dr on trip.DriverId = dr.ID  join person p on p.ID = dr.ID\n" +
                "left join savedloc sl1 on sl1.LocId = trip.StartSavedLoc and sl1.ID = ? \n" +
                "left join savedloc sl2 on sl2.LocId = trip.endSavedLoc and sl2.ID = ? \n" +
                "left join discount d on d.Code = trip.DiscountCode\n" +
                "where Status = 2 and trip.PassId = ? ;";
        ArrayList<Object> para = new ArrayList<>();
        para.add(id);
        para.add(id);
        para.add(id);
        try {
            ResultSet rs = runQueryWhitOutput(qry , para);
            while (rs.next()){
                ts.add(new Trip(
                        rs.getLong(1) ,
                        new Driver( -1 , rs.getString(2) , rs.getString(3), rs.getString(4).equals("Male") ? 0:1  , rs.getInt(21 ) , rs.getString(22).charAt(0) ,rs.getInt(23 ) , rs.getInt(24 ) , rs.getString(25) , rs.getString(26)) ,
                        rs.getLong(5) ,
                        rs.getString(6) ,
                        new Location( rs.getInt(7) , rs.getInt(8) ) ,
                        new Location( rs.getInt(9) , rs.getInt(10) ) ,
                        new Location( rs.getInt(11) , rs.getInt(12) ) ,
                        rs.getString(13) ,
                        rs.getString(14) ,
                        new Location( rs.getInt(15) , rs.getInt(16) ) ,
                        rs.getString(17) ,
                        rs.getString(18) ,
                        rs.getDouble(19) ,
                        rs.getInt(20) ,
                        rs.getInt(27)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ts;
    }

    public void addPassRate(long tripId, int rate) {
        String qry = "UPDATE `trip` SET  `PassRate` = ? WHERE `trip`.`TripId` = ? ;";
        ArrayList<Object> paras = new ArrayList<>();
        paras.add(rate);
        paras.add(tripId);

        try {
            runQuery(qry , paras);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addDriverRate(long tripId, int rate) {
        String qry = "UPDATE `trip` SET  DriverRate = ? WHERE `trip`.`TripId` = ? ;";
        ArrayList<Object> paras = new ArrayList<>();
        paras.add(rate);
        paras.add(tripId);

        try {
            runQuery(qry , paras);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Trip> getAllFinishedTripsOfDriver(int id) {
        ArrayList<Trip> ts = new ArrayList<>();
        String qry = "select TripId  , Phone , FullName , Gender , Amount , PaymentMethod  ,\n" +
                "       ST_X(StartLoc)  , ST_Y(StartLoc)  , ST_X(EndLoc)  , ST_Y(EndLoc) ,\n" +
                "       ST_X(sl1.Loc) ,ST_Y(sl1.Loc) , sl1.NameOfLoc , sl1.Address , \n" +
                "       ST_X(sl2.Loc) ,ST_Y(sl2.Loc) , sl2.NameOfLoc , sl2.Address ,\n" +
                "       d.Percent , d.Max , DriverRate  " +
                "from trip join passenger ps on trip.PassId = ps.ID  join person p on p.ID = ps.ID\n" +
                "left join savedloc sl1 on sl1.LocId = trip.StartSavedLoc and sl1.ID = ps.ID \n" +
                "left join savedloc sl2 on sl2.LocId = trip.endSavedLoc and sl2.ID = ps.ID \n" +
                "left join discount d on d.Code = trip.DiscountCode\n" +
                "where Status = 2 and DriverId = ? ;";
        ArrayList<Object> para = new ArrayList<>();
        para.add(id);
        try {
            ResultSet rs = runQueryWhitOutput(qry , para);
            while (rs.next()){
                ts.add(new Trip(
                        rs.getLong(1) ,
                        new Passenger(rs.getString(2) , rs.getString(3), rs.getString(4).equals("Male") ? 0:1  , null , -1) ,
                        rs.getLong(5) ,
                        rs.getString(6) ,
                        new Location( rs.getInt(7) , rs.getInt(8) ) ,
                        new Location( rs.getInt(9) , rs.getInt(10) ) ,
                        new Location( rs.getInt(11) , rs.getInt(12) ) ,
                        rs.getString(13) ,
                        rs.getString(14) ,
                        new Location( rs.getInt(15) , rs.getInt(16) ) ,
                        rs.getString(17) ,
                        rs.getString(18) ,
                        rs.getDouble(19) ,
                        rs.getInt(20) ,
                        rs.getInt(21)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ts;
    }

    private ResultSet runQueryWhitOutput(String qry , ArrayList<Object> parameters) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(qry);
        int i = 1;
        for (Object para : parameters) {
            ps.setObject(i , para);
            i++;
        }
        return ps.executeQuery();
    }

    private void runQuery(String qry , ArrayList<Object> parameters) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(qry);
        int i = 1;
        for (Object para : parameters) {
            ps.setObject(i , para);
            i++;
        }
        ps.execute();
    }
}

/*

INSERT INTO `discount` (`ID`, `Code`, `Percent`, `Max`) VALUES ('', '', '', NULL)
 */