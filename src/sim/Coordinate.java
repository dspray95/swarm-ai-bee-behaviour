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

    /**
     * Euclidean distance, balanced to not perform square root operation
     * Euclidean between A and B = sqrt((Ax - Bx)^2 + (Ay - By)*2)
     * Returned distance = (Ax - Bx)^2 + (Ay - By)*2
     * @param c
     * @return
     */
    public double DistanceTo(Coordinate c){
        double xDist = Math.pow(this.X() - c.X(), 2);
        double yDist = Math.pow(this.Y() - c.Y(), 2);
        return xDist + yDist;
    }

    /**
     * Two radii intersect if the square of the difference in the radii is greater than the distance between the two
     * center positions like so:
     *  (r1 - r2)^2 > (x1 - x2)^2 + (y1 - y2)^2
     * @param target
     * @param targetRadius
     * @param thisRadius
     * @return
     */
    public boolean CircleIntersects(Coordinate target, double targetRadius, double thisRadius){
        double distance = Math.pow(this.x - target.X(), 2d) + Math.pow(this.y - target.Y(), 2d);
        double radii = Math.pow(targetRadius - thisRadius, 2d);
        return radii > distance;
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

    public void setXY(int x, int y){
        this.x = x;
        this.y = y;
    }

}
