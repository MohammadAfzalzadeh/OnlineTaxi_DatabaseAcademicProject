package Models;

public abstract class Person {
    protected int id;
    protected String Phone;
    protected Gender gender;
    protected String name;
    protected String Pass;

    public Person(String phone, int gender, String name, String Pass) {
        Phone = phone;
        this.gender = gender == 1 ? Gender.Female : Gender.Male ;
        this.name = name;
        this.Pass = Pass;
    }

    public Person() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getPhone() {
        return Phone;
    }

    public Gender getGender() {
        return gender;
    }
    public String getGenderStr() {
        return gender.toStringEN();
    }

    public String getName() {
        return name;
    }

    public String getPass() {
        return Pass;
    }
}
