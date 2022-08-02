package Models;

public class Location {
    private double x;
    private double y;

    public Location(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double diffrece2Loc(Location location){
        return Math.sqrt( Math.pow(  (this.x - location.x)  , 2) + Math.pow(  (this.y - location.y)  , 2) );
    }

    @Override
    public String toString() {
        return "x : " + x  +  " , y : " + y;
    }
}
