/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package closestpointpair;
import java.util.Arrays;
import java.util.*;
import java.lang.Math;
import java.io.*;
/**
 *
 * @author zhaoq
 */
public class ClosestPointPair {
    // initialize the size of the key board
    private double x = 1;
    private double y = 1;
    /**
     * @param args the command line arguments
     */
    public Point[] findClosestPair(Point[] points){
        Arrays.sort(points);  // sort the array,such that the array can be split into many parts
        int hi = points.length-1; // get the length of the array
        return findClosestPair(points,hi,0);
    }
    private Point[] findClosestPair(Point[] points,int hi, int low){
        Point[] points1 = new Point[2];// declare the first array that store the closest pair of points in the left side
        Point[] points2 = new Point[2];// declare the second array that store the closest pair of points in the right side
        Point[] points3 = new Point[2];// declare the third array that store the closest pair of points between the left side and the right side
        if(low == hi){
            points1[0] = points[low];
            return points1;
        }
        if(hi-low == 1){
            points1[0] = points[low];
            points1[1] = points[hi];
            return points1;
        }
        int median = (hi+low)/2;
        points1 = findClosestPair(points,median,low);
        points2 = findClosestPair(points,hi,median+1);
        double distance1,distance2,distance3; // store the value of the closest pair of points in left side, right side and left-right sides respectively
        // if there is only one point returned
        if(points1[1] == null){
            distance1 = Double.MAX_VALUE;
        }
        else{
            distance1 = points1[0].distanceTo(points1[1]);
        }
        if(points2[1] == null){
            distance2 = Double.MAX_VALUE;
        }
        else{
            distance2 = points2[0].distanceTo(points2[1]);
        }
        double d = distance1 > distance2? distance2:distance1;//choose the smaller distance to split the squares
        LinkedList<Point> leftSide = new LinkedList<Point>();// store the points in the left-side rectangle 
        LinkedList<Point> rightSide = new LinkedList<Point>();// store the points in the right-side rectangle
        int i = median;
        // find the points in the left side rectangle and insert them to the leftSide
        while(points[i].getX()>(points[median].getX()-d)){
            leftSide.add(points[i]);
            if(i ==low){
                break;
            }
            i--;
        }
        int j = median +1;
        // find the points in the right side rectangle and insert  them to the rightside
        while(points[j].getX()<(points[median].getX()+d)){
            rightSide.add(points[j]);
            if(j == hi){
                break;
            }
            j++;
        }
        // split the rectangle to many squares whose sides' length is d
        int numOfSquares = (int)(y/d)+1;
        LinkedList<Point>[] squares = new LinkedList[numOfSquares];
        // store the points in the corresponding square
        for(Point point: rightSide){
            int index = (int)(point.getY()/d);
            squares[index] = new LinkedList<Point>();
            squares[index].add(point);
        }
        // iterate the leftside to find the closest pair of points between the leftside and rightside
        distance3 = Double.MAX_VALUE;
        int mediumDistance;// it is used in the loop to represent the distance
        for(Point point: leftSide){
            int index1 = (int)(point.getY()/d);
            //for each point in the left side, just account for the adjacent three squares in the right side 
            if(index1>0){
                if(squares[index1-1]!=null){
                for(Point point1:squares[index1-1]){
                    if(point.distanceTo(point1)<distance3){
                        points3[0] = point;
                        points3[1] = point1;
                        distance3 = point.distanceTo(point1);
                    }
                }
                }
            }
            if(squares[index1]!=null){
            for(Point point2:squares[index1]){
                if(point.distanceTo(point2)<distance3){
                    points3[0] = point;
                    points3[1] = point2;
                    distance3 = point.distanceTo(point2);
                }
            }
            }
            if(index1<numOfSquares-1){
                if(squares[index1+1]!=null){
                for(Point point3:squares[index1+1]){
                    if(point.distanceTo(point3)<distance3){
                        points3[0] = point;
                        points3[1] = point3;
                        distance3 = point.distanceTo(point3);
                    }
                }
                }
            }
        }
        if(distance3 < d){
            return points3;
        }
        else{
            if(distance1 < distance2){
                return points1;
            }
            else{
                return points2;
            }
        }
    }
    /**
     *
     * @param args
     */
    public static void main(String[] args){
        // TODO code application logic here
        ClosestPointPair pair = new ClosestPointPair();
        for(int length=2;length<10000000;length=length*2){
            Point[] points = new Point[length];
            for(int i =0;i<length;i++){
                double x = Math.random();
                double y = Math.random();
                points[i] = new Point(x,y);
            }
        long startTime = System.nanoTime();
        Point[] pointPair = new Point[2];
        pointPair = pair.findClosestPair(points);
        long endTime = System.nanoTime();
        double distance = pointPair[0].distanceTo(pointPair[1]);
        System.out.print(distance);
        System.out.print("  ");
        System.out.println(endTime-startTime);
        }
    }
    
}
