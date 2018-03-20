package sim;

import java.util.Random;

public class Coordinate {

    private int x;
    private int y;

    public Coordinate(){
    }

    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void RandomCoordinate(int xyBounds, int lowerBound){
        Random r = new Random();
        setY(r.nextInt(xyBounds - lowerBound) + lowerBound) ;
        setX(r.nextInt(xyBounds - lowerBound) + lowerBound);
    }

    public double DistanceTo(Coordinate c){

        double xDist = Math.pow(this.X() - c.X(), 2);
        double yDist = Math.pow(this.Y() - c.Y(), 2);
        return xDist + yDist;
    }

    public int X() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int Y() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }



}
