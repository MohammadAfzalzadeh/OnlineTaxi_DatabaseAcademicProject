package Models;

import java.sql.Date;
import java.util.Objects;

public class Driver extends Person {

    private String nationalNumber;
    private String fatherName;
    private String addres;
    private Date birthDay;
    private String imgAddr;

    // car attr
    private  String car_Pelak;
    private  String car_Model;
    private  String car_Color;
    private Date car_EndInsurance;

    // Certificate Attr
    private int certificate_Number;
    private Date certificate_IssueDate;
    private Date certificate_ValidityDuration;

    public Driver(String phone, int gender, String name, String Pass,
                  String nationalNumber, String fatherName, String addres, Date birthDay, String imgAddr,
                  int car_pelak_Num1, char car_pelak_Char , int car_pelak_Num2 , int car_pelak_CNum ,
                  String car_Model, String car_Color, Date car_EndInsurance,
                  int certificate_Number, Date certificate_IssueDate, Date certificate_ValidityDuration) {

        super(phone, gender, name, Pass);
        this.nationalNumber = nationalNumber;
        this.fatherName = fatherName;
        this.addres = addres;
        this.birthDay = birthDay;
        this.imgAddr = imgAddr;
        setCar_Pelak(car_pelak_Num1 , car_pelak_Num2 , car_pelak_Char , car_pelak_CNum);
        this.car_Model = car_Model;
        this.car_Color = car_Color;
        this.car_EndInsurance = car_EndInsurance;
        this.certificate_Number = certificate_Number;
        this.certificate_IssueDate = certificate_IssueDate;
        this.certificate_ValidityDuration = certificate_ValidityDuration;
    }

    public Driver(int id , String phone , String name ,  int gender, int car_pelak_Num1, char car_pelak_Char , int car_pelak_Num2 , int car_pelak_CNum, String car_Model, String car_Color) {
        this (phone , gender , name , null ,
                null , null , null , null , null ,
                car_pelak_Num1 , car_pelak_Char , car_pelak_Num2 , car_pelak_CNum ,
                car_Model , car_Color , null ,
                -1 , null , null);
        setId(id);
    }

    public Driver(int id) {
        setId(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return this.id == driver.id;
    }


    private void setCar_Pelak(int num1 , int num2 , char ch , int cityNum) {
        this.car_Pelak =  num1+ " " + ch + " " + num2 + " " + cityNum;
    }

    public String getNationalNumber() {
        return nationalNumber;
    }

    public String getFatherName() {
        return fatherName;
    }

    public String getAddres() {
        return addres;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public String getImgAddr() {
        return imgAddr;
    }

    public String getCar_Pelak() {
        return car_Pelak;
    }

    public String getCar_Model() {
        return car_Model;
    }

    public String getCar_Color() {
        return car_Color;
    }

    public Date getCar_EndInsurance() {
        return car_EndInsurance;
    }

    public int getCertificate_Number() {
        return certificate_Number;
    }

    public Date getCertificate_IssueDate() {
        return certificate_IssueDate;
    }

    public Date getCertificate_ValidityDuration() {
        return certificate_ValidityDuration;
    }

    @Override
    public String toString() {
        return " نام : '" + name + '\'' + "\n" +
                " شماره تماس : '" + Phone + '\'' + "\n" +
                " جنسیت : " + gender.toString() + "\n" +
                " نوع ماشین : '" + car_Model + '\'' + "\n" +
                "رنگ ماشین :'" + car_Color + '\'' + "\n" +
                " پلاک : '" + car_Pelak + '\'' ;
    }
}
