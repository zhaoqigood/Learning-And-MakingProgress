/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package closestpointpair;
import java.lang.Math;
/**
 *
 * @author zhaoq
 */
public class Point implements Comparable<Point> {
    private double x;
    private double y;
    Point(double x,double y){
        this.x = x;
        this.y = y;
    }
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public double distanceTo(Point that){
        double distance = Math.sqrt((this.x - that.getX())*(this.x - that.getX()) + (this.y - that.getY())*(this.y - that.getY()));
        return distance;
    }
    @Override
    public int compareTo(Point that) {
        if(this.x > that.getX()) { return 1;}
        if(this.x < that.getX()) { return -1;}
        else { return 0;}   
    }
    
}
