/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import edu.princeton.cs.algs4.Picture;
import java.awt.Color;
import edu.princeton.cs.algs4.IndexMinPQ;
import java.lang.Math;
import java.lang.IllegalArgumentException;
/**
 *
 * @author zhaoq
 */
public class SeamCarver {
    private Picture picture;
    public SeamCarver(Picture picture){
        if(picture == null){
            throw new java.lang.IllegalArgumentException();
        }
        this.picture = new Picture(picture);
    }
    public Picture picture(){
		int width = picture.width();
		int height = picture.height();
		Picture picture1 = new Picture(width,height);
		for(int i=0;i<height;i++){
			for(int j=0;j<width;j++){ 
				Color color = picture.get(j,i);
				picture1.set(j,i,color);
			}
		}
        return picture1;
    }
    public int width(){
        return picture.width();
    }
    public int height(){
        return picture.height();
    }
    public double energy(int x, int y){
        int width = picture.width();
        int height = picture.height();
        // Exception
        if(x<0 || y<0 || x>(width-1) || y>(height-1)){
            throw new java.lang.IllegalArgumentException();
        }
        // corner case
        if(x == 0 || x == (width-1) || y == 0 || y == (height-1)){
            return 1000;
        }
        // calcute the energy of pixel( column x, row y );
        Color former_x = picture.get(x-1,y);
        Color latter_x = picture.get(x+1,y);
        Color former_y = picture.get(x,y-1);
        Color latter_y = picture.get(x,y+1);
        int Rx = former_x.getRed()-latter_x.getRed();
        int Gx = former_x.getGreen() - latter_x.getGreen();
        int Bx = former_x.getBlue() - latter_x.getBlue();
        int Ry = former_y.getRed()-latter_y.getRed();
        int Gy = former_y.getGreen() - latter_y.getGreen();
        int By = former_y.getBlue() - latter_y.getBlue();
        double gradient_x = Rx*Rx+Gx*Gx+Bx*Bx;
        double gradient_y = Ry*Ry+Gy*Gy+By*By;
        double Energy = Math.sqrt(gradient_x+gradient_y);
        return Energy;
    }
    public int[] findHorizontalSeam(){
        int width = picture.width();
        int height = picture.height();
        int[] edgeTo = new int[width*height]; // store the path
        double[] distanceTo = new double[width*height]; // store the distance form source point to the given point
        IndexMinPQ<Double> minPQ = new IndexMinPQ<Double>(width*height); // index min PQ in the Dijkstra Algorithm
        for(int j=0;j<height;j++){
            for(int i=0;i<width;i++){
                distanceTo[j*width+i] = Double.MAX_VALUE;
                edgeTo[j*width+i] = j*width+i;
            }
        }
        for(int i=0;i<height;i++){
            distanceTo[i*width] = 1000;
            minPQ.insert(i*width,distanceTo[i*width]);
        }
        // Diskstra Algorithm: energy is the weight for each pixel
        while(!minPQ.isEmpty()){
            int pixel = minPQ.delMin();
            int x = pixel%width;
            int y = pixel/width;
            if(x == (width-1)){
                int[] path = new int[width];
                int current = x;
                int currentPoint = pixel;
                path[current] = currentPoint/width;
                while(current != 0){
                    currentPoint = edgeTo[currentPoint];
                    current = currentPoint%width;
                    path[current] = currentPoint/width;
                }
                return path;
            }
            if(x+1<width){
                double Energy1 = energy(x+1,y);
                if(distanceTo[pixel+1] > distanceTo[pixel]+Energy1){
                    distanceTo[pixel+1] = distanceTo[pixel]+ Energy1;
                    edgeTo[pixel+1] = pixel;
                    if(minPQ.contains(pixel+1)){
                        minPQ.decreaseKey(pixel+1,distanceTo[pixel+1]);
                    }
                    else{
                        minPQ.insert(pixel+1,distanceTo[pixel+1]);
                    }
                }
            }
            if(y-1>=0 && x+1<width){
                double Energy2 = energy(x+1,y-1);
                if(distanceTo[pixel+1-width] > distanceTo[pixel]+Energy2){
                    distanceTo[pixel-width+1] = distanceTo[pixel]+Energy2;
                    edgeTo[pixel-width+1] = pixel;
                    if(minPQ.contains(pixel-width+1)){
                        minPQ.decreaseKey(pixel-width+1,distanceTo[pixel-width+1]);
                    }
                    else{
                        minPQ.insert(pixel-width+1,distanceTo[pixel-width+1]);
                    }
                }
            }
            if(y+1<height && x+1<width){
                double Energy3 = energy(x+1,y+1);
                if(distanceTo[pixel+width+1] > distanceTo[pixel]+Energy3){
                    distanceTo[pixel+width+1] = distanceTo[pixel]+Energy3;
                    edgeTo[pixel+width+1] = pixel;
                    if(minPQ.contains(pixel+width+1)){
                        minPQ.decreaseKey(pixel+width+1,distanceTo[pixel+width+1]);
                    }
                    else{
                        minPQ.insert(pixel+width+1,distanceTo[pixel+width+1]);
                    }
                }
            }
        }
        return null;        
        
    }
    public int[] findVerticalSeam(){
        int width = picture.width();
        int height = picture.height();
        int[] edgeTo = new int[width*height]; // store the path
        double[] distanceTo = new double[width*height]; // store the distance form source point to the given point
        IndexMinPQ<Double> minPQ = new IndexMinPQ<Double>(width*height); // index min PQ in the Dijkstra Algorithm
        for(int j=0;j<height;j++){
            for(int i=0;i<width;i++){
                distanceTo[j*width+i] = Double.MAX_VALUE;
                edgeTo[j*width+i] = j*width+i;
            }
        }
        for(int i=0;i<width;i++){
            distanceTo[i] = 1000;
            minPQ.insert(i,distanceTo[i]);
        }
        // Diskstra Algorithm: energy is the weight for each pixel
        while(!minPQ.isEmpty()){
            int pixel = minPQ.delMin();
            int x = pixel%width;
            int y = pixel/width;

            if(y == (height-1)){
                int[] path = new int[height];
                int current = y;
                int currentPoint = pixel;
                path[current] = currentPoint%width;
                while(current != 0){
                    currentPoint = edgeTo[currentPoint];
                    current = currentPoint/width;
                    path[current] = currentPoint%width;
                }
                return path;
            }
            if(y+1<height){
                double Energy1 = energy(x,y+1);
                if(distanceTo[pixel+width] > distanceTo[pixel]+Energy1){
                    distanceTo[pixel+width] = distanceTo[pixel]+ Energy1;
                    edgeTo[pixel+width] = pixel;
                    if(minPQ.contains(pixel+width)){
                        minPQ.decreaseKey(pixel+width,distanceTo[pixel+width]);
                    }
                    else{
                        minPQ.insert(pixel+width,distanceTo[pixel+width]);
                    }
                }
            }
            if(x-1>=0 && y+1<height){
                double Energy2 = energy(x-1,y+1);
                if(distanceTo[pixel+width-1] > distanceTo[pixel]+Energy2){
                    distanceTo[pixel+width-1] = distanceTo[pixel]+Energy2;
                    edgeTo[pixel+width-1] = pixel;
                    if(minPQ.contains(pixel+width-1)){
                        minPQ.decreaseKey(pixel+width-1,distanceTo[pixel+width-1]);
                    }
                    else{
                        minPQ.insert(pixel+width-1,distanceTo[pixel+width-1]);
                    }
                }
            }
            if(x+1<width && y+1<height){
                double Energy3 = energy(x+1,y+1);
                if(distanceTo[pixel+width+1] > distanceTo[pixel]+Energy3){
                    distanceTo[pixel+width+1] = distanceTo[pixel]+Energy3;
                    edgeTo[pixel+width+1] = pixel;
                    if(minPQ.contains(pixel+width+1)){
                        minPQ.decreaseKey(pixel+width+1,distanceTo[pixel+width+1]);
                    }
                    else{
                        minPQ.insert(pixel+width+1,distanceTo[pixel+width+1]);
                    }
                }
            }
        }
        return null;        
    }
    public void removeHorizontalSeam(int[] seam){
        if(seam == null){
            throw new java.lang.IllegalArgumentException();
        }
        int length = seam.length;
        if(length != picture.width()){
            throw new java.lang.IllegalArgumentException();
        }
        for(int i=0; i < length-1;i++){
            if((seam[i]-seam[i+1])> 1 || (seam[i]-seam[i+1])<-1){
                throw new java.lang.IllegalArgumentException();
            }
        }
        Picture currentPicture = new Picture(picture.width(),picture.height()-1);
        int width = picture.width();
        int height = picture.height();
        int i1 = 0;
        int j1 = 0;
        for(int j = 0 ; j< width; j++){
            i1 = 0;
            for(int i = 0 ; i< height; i++){
                if(seam[j] == i){
                    continue;
                }
                Color color = picture.get(j,i);
                currentPicture.set(j1,i1,color);
                i1++;
            }
            j1++;
        }
        picture = currentPicture;
    }
    public void removeVerticalSeam(int[] seam){
        if(seam == null){
            throw new java.lang.IllegalArgumentException();
        }
        int length = seam.length;
        if(length != picture.height()){
            throw new java.lang.IllegalArgumentException();
        }
        for(int i=0; i < length-1;i++){
            if((seam[i]-seam[i+1])> 1 || (seam[i]-seam[i+1])<-1){
                throw new java.lang.IllegalArgumentException();
            }
        }
        Picture currentPicture = new Picture(picture.width()-1,picture.height());
        int width = picture.width();
        int height = picture.height();
        int i1 = 0;
        int j1 = 0;
        for(int i = 0 ; i< height; i++){
            j1 = 0;
            for(int j = 0 ; j< width; j++){
                if(seam[i] == j){
                    continue;
                }
                Color color = picture.get(j,i);
                currentPicture.set(j1,i1,color);
                j1++;
            }
            i1++;
        }
        picture = currentPicture;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Picture picture = new Picture(args[0]);
        SeamCarver seamCarver = new SeamCarver(picture);
        int width = picture.width();
		int resize = width/2;
		for(int i=0;i<resize;i++){
			int[] seam = seamCarver.findVerticalSeam();
			seamCarver.removeVerticalSeam(seam);
		}
		seamCarver.picture().show();
        // TODO codice application logic here
    }
    
}
