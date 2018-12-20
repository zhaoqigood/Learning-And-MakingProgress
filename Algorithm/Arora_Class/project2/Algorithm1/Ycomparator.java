/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package closestpointpair1;
import java.util.*;
import java.io.*;
/**
 *
 * @author zhaoq
 */
public class Ycomparator implements Comparator {
    public int compare(Object o1,Object o2){
        Point point1 = (Point)o1;
        Point point2 = (Point)o2;
        if(point1.getY() > point2.getY()){
            return 1;
        }
        else if(point1.getY() < point2.getY()){
            return -1;
        }
        else{
            return 0;
        }
    }
}
