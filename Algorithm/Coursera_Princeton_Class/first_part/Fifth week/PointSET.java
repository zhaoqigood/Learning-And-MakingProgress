/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.TreeSet;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Queue;
/**
 *
 * @author zhaoq
 */
public class PointSET {
    private TreeSet<Point2D> pointSet;
    public PointSET(){
        pointSet=new TreeSet<Point2D>();
    }
    public boolean isEmpty(){
        return pointSet.isEmpty();
    }
    public int size(){
        return pointSet.size();
    }
    public void insert(Point2D p){
		if(p==null){
			throw new IllegalArgumentException();
		}
        pointSet.add(p);
    }
    public boolean contains(Point2D p){
		if(p==null){
			throw new IllegalArgumentException();
		}
        return pointSet.contains(p);
    }
    public void draw(){
        for(Point2D point: pointSet){
            point.draw();
        }
    }
    public Iterable<Point2D> range(RectHV rect){
		if(rect==null){
			throw new IllegalArgumentException();
		}	
	    Queue<Point2D> pointRange=new Queue<Point2D>();
        for(Point2D point: pointSet){
            if(rect.contains(point)){
                pointRange.enqueue(point);
            }
        }
        return pointRange;
    }
    public Point2D nearest(Point2D p){
		if(p==null){
			throw new IllegalArgumentException();
		}
        double minDistance=Double.MAX_VALUE;
        Point2D nearPoint=null;
        for(Point2D point: pointSet){
            double distance=p.distanceTo(point);
            if(distance<minDistance){
                minDistance=distance;
                nearPoint=point;
            }
        }
        return nearPoint;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
