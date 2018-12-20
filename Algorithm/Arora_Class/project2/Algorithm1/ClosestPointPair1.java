/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package closestpointpair1;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Arrays;
import java.util.*;
import java.lang.Math;
import java.io.*;
/**
 *
 * @author zhaoq
 */
public class ClosestPointPair1 {
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
        ArrayList<Point> leftSide = new ArrayList<Point>();// store the points in the left-side rectangle 
        ArrayList<Point> rightSide = new ArrayList<Point>();// store the points in the right-side rectangle
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
        leftSide.sort(new Ycomparator());
        rightSide.sort(new Ycomparator());
        distance3 = Double.MAX_VALUE;
        for(Point point: leftSide){
            int big = rightSide.size();
            int small = 0;
            int median1 = (big+small)/2;
            boolean exitPoint = false;
            while(big > small){
                median1 = (big+small)/2;
                if(rightSide.get(median1).getY() > (point.getY()+d)){
                    big = median1;
                }
                else if(rightSide.get(median1).getY() < (point.getY()-d)){
                    small = median1 + 1;
                }
                else {
                    exitPoint = true;
                    break;
                }
            }
            if(exitPoint){
                int m = median1;
                int n = median1;
                while(rightSide.get(m).getY() < (point.getY()-d)){
                    if(point.distanceTo(rightSide.get(m)) < distance3){
                        points3[0] = point;
                        points3[1] = rightSide.get(m);
                        distance3 = point.distanceTo(rightSide.get(m));
                    }
                    if(m == 0){
                        break;
                    }
                    m--;
                }
                while(rightSide.get(n).getY() > (point.getY()+d)){
                    if(point.distanceTo(rightSide.get(n)) < distance3){
                        points3[0] = point;
                        points3[1] = rightSide.get(n);
                        distance3 = point.distanceTo(rightSide.get(n));
                    }
                    if(n == rightSide.size()){
                        break;
                    }
                    n++;
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
    public static void main(String[] args) {
        // TODO code application logic here
        ClosestPointPair1 pair = new ClosestPointPair1();
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

