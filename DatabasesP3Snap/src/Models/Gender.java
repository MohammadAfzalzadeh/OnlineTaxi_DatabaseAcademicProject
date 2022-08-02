package Models;

public enum Gender {
    Female , Male;

    @Override
    public String toString() {
        return  super.toString() == "Male" ? "مرد" : "زن";
    }

    public String toStringEN() {
       return super.toString();
    }
}
