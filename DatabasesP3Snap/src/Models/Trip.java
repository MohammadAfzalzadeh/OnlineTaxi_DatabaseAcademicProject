package Models;

import java.sql.Time;
import java.time.LocalTime;

public class Trip {
    private Long tripId;
    private Passenger passenger;
    private Driver driver;
    private long amount;
    private long amountFinaly;
    private PaymentMethod payment;
    private Integer discountCode;

    private Location startLoc;
    private SavedLocation startSavedLocation;
    private Location endLoc;
    private SavedLocation endSavedLocation;

    private Integer driverRate = 0;
    private Integer passengerRate = 0;

    private Time startTime;
    private Time endTime;


    public Trip(Long tripId, Passenger passenger, Driver driver, long amount, PaymentMethod payment, Integer discountCode, Location startLoc, SavedLocation startSavedLocation, Location endLoc, SavedLocation endSavedLocation, Integer driverRate, Integer passengerRate , Time startTime , Time endTime) {
        this.tripId = tripId;
        this.passenger = passenger;
        this.driver = driver;
        this.amount = amount;
        this.payment = payment;
        this.discountCode = discountCode;
        this.startLoc = startLoc;
        this.startSavedLocation = startSavedLocation;
        this.endLoc = endLoc;
        this.endSavedLocation = endSavedLocation;
        this.driverRate = driverRate;
        this.passengerRate = passengerRate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Trip(Passenger passenger, PaymentMethod payment ,  Location startLoc, Location endLoc, SavedLocation startSavedLocation, SavedLocation endSavedLocation,  Integer discountCode ) {
        this (null , passenger , null , 0 , payment , discountCode , startLoc , startSavedLocation , endLoc , endSavedLocation , null , null  , null , null);

        if (startSavedLocation == null && endSavedLocation == null)
            setAmount(startLoc , endLoc);
        else if (startSavedLocation == null && endLoc == null)
            setAmount(startLoc , endSavedLocation.getLocation());
        else if (startLoc == null && endSavedLocation == null)
            setAmount(startSavedLocation.getLocation() , endLoc);
        else if (startLoc == null && endLoc == null)
            setAmount(startSavedLocation.getLocation() , endSavedLocation.getLocation());
        else
            throw new IllegalArgumentException();

    }

    public Trip(long trpId , Passenger p , long amount , String payment  , Location startLoc , Location endLoc ,
               Location startSavedLocation , String name_sS , String dis_sS ,
               Location endSavedLocation , String name_eS , String dis_eS ,
                Double percentDicount , Integer maxDis
    ){
        this(trpId , p , null ,amount ,payment.equals("نقدی") ? PaymentMethod.BY_MONEY : PaymentMethod.BY_CREDIT , -1 , startLoc ,new SavedLocation(-1  , name_eS , startSavedLocation , dis_sS)  , endLoc ,
                new SavedLocation(-1  , name_eS , endSavedLocation , dis_eS) ,null ,null , null ,null);

        setAmountFinaly(amount , percentDicount , maxDis);
    }

    public Trip(long trpId , Driver d , long amount , String payment  , Location startLoc , Location endLoc ,
                Location startSavedLocation , String name_sS , String dis_sS ,
                Location endSavedLocation , String name_eS , String dis_eS ,
                Double percentDicount , Integer maxDis , Integer PassRate){
        this (trpId , new Passenger(null , null , 1 , null , -1) ,  amount , payment  , startLoc ,  endLoc ,
                 startSavedLocation ,  name_sS ,  dis_sS ,
                 endSavedLocation ,  name_eS ,  dis_eS ,
                 percentDicount ,  maxDis);
        driver = d;

        passengerRate = PassRate;
    }

    public Trip(long trpId , Passenger p , long amount , String payment  , Location startLoc , Location endLoc ,
                Location startSavedLocation , String name_sS , String dis_sS ,
                Location endSavedLocation , String name_eS , String dis_eS ,
                Double percentDicount , Integer maxDis , Integer DriverRate){
        this (trpId , p ,  amount , payment  , startLoc ,  endLoc ,
                 startSavedLocation ,  name_sS ,  dis_sS ,
                 endSavedLocation ,  name_eS ,  dis_eS ,
                 percentDicount ,  maxDis);


        driverRate = DriverRate;
    }

    //setter method
    private void setAmount(Location src , Location dst){
        amount = (long) (src.diffrece2Loc(dst) * 100);
    }

    private void setAmountFinaly(long amount , Double percent , Integer max) {
        if (percent == 0 || percent == null)
            this.amountFinaly = amount;
        else
            this.amountFinaly = (long) Math.max( (amount *  (1 - percent)) , amount - max);
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public void setPaymentMethod(PaymentMethod payment) {
        this.payment = payment;
    }

    public void setDiscountCode(Integer discountCode) {
        this.discountCode = discountCode;
    }

    public void setDriverRate(Integer driverRate) {
        this.driverRate = driverRate;
    }

    public void setPassengerRate(Integer passengerRate) {
        this.passengerRate = passengerRate;
    }


    //getter method
    public Long getTripId() {
        return tripId;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public Driver getDriver() {
        return driver;
    }

    public long getAmount() {
        return amount;
    }

    public PaymentMethod getPayment() {
        return payment;
    }

    public Integer getDiscountCode() {
        return discountCode;
    }

    public Location getStartLoc() {
        return startLoc;
    }

    public SavedLocation getStartSavedLocation() {
        return startSavedLocation;
    }

    public Location getEndLoc() {
        return endLoc;
    }

    public SavedLocation getEndSavedLocation() {
        return endSavedLocation;
    }

    public Integer getDriverRate() {
        return driverRate;
    }

    public Integer getPassengerRate() {
        return passengerRate;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
               String res = " مسافر : " + passenger + "\n" +
                " مبلغ نهایی : " + amountFinaly + "\n" +
                " روش پرداخت :" + payment + "\n" ;

        if (startLoc.getX() == 0 && startLoc.getY() == 0)
            res += startSavedLocation.getLocation().toString() + "\t" +
                    "جزئیات آدرس : " + startSavedLocation.getAddressDetail() + "\n";
        else
            res += startLoc.toString() +"\n";

        if (endLoc.getX() == 0 && endLoc.getY() == 0)
            res += endSavedLocation.getLocation().toString() + "\t" +
                    "جزئیات آدرس : " + endSavedLocation.getAddressDetail() + "\n";
        else
            res += endLoc.toString() +"\n";


        res += ( driverRate == null || driverRate == 0 ?  "نظری برای این سفر ثبت نشده است" :
                ("امتیاز داده شده شما از 1 تا 10 : " + driverRate));



        return res;
    }

    public String toStringFromPassengers() {
        String res = " راننده : " + driver + "\n" +
                " مبلغ نهایی : " + amountFinaly + "\n" +
                " روش پرداخت :" + payment + "\n" ;

        if (startLoc.getX() == 0 && startLoc.getY() == 0)
            res += startSavedLocation.getLocation().toString() + "\t" +
                    "جزئیات آدرس : " + startSavedLocation.getAddressDetail() + "\n";
        else
            res += startLoc.toString() +"\n";

        if (endLoc.getX() == 0 && endLoc.getY() == 0)
            res += endSavedLocation.getLocation().toString() + "\t" +
                    "جزئیات آدرس : " + endSavedLocation.getAddressDetail() + "\n";
        else
            res += endLoc.toString() +"\n";

        res += (passengerRate == 0 ?  "نظری برای این سفر ثبت نشده است" :
                ("امتیاز داده شده شما از 1 تا 10 : " + passengerRate));


        return res;
    }
}
