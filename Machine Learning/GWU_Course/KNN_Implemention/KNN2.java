/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.lang.Math;
import java.util.Comparator;
import java.util.Iterator;
import java.io.PrintWriter;
import java.io.File;
/**
 *
 * @author zhaoq
 */
public class KNN2 {
      ArrayList trainSet = new ArrayList();
      String[] featureName;
      double[][] trainSet_Scale;
      int[] label;
      int k;
      int[] labels;
      double[] average;
      KNN2(int k, String fileName,int mark[]){
        this.k = k;
        labels = new int[mark.length];
        for(int i = 0;i<mark.length;i++){
            labels[i] = mark[i];
        }
        String csvFile = fileName;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        try {

            br = new BufferedReader(new FileReader(csvFile));
            if((line = br.readLine()) != null){
                featureName = line.split(cvsSplitBy);
            }
            
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] instance = line.split(cvsSplitBy);
                Example example = new Example(instance); 
                trainSet.add(example);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    class Example{
		int label1;
        double[] features;
        Example(String[] input){
            features = new double[input.length];
			label1 = Integer.parseInt(input[0]);
            for(int i = 1; i<input.length; i++){
                features[i-1] = Double.parseDouble(input[i]);
            }
        }
    }
    class keyPoint implements Comparable<keyPoint>{
        double distance;
        int index;
        keyPoint(double distance, int index){
        this.distance = distance;
        this.index = index;
        }
        public int compareTo(keyPoint that){
            if(this.distance > that.distance){
                return 1;
            }
            else if(that.distance > this.distance){
               return -1;
            }
            else{
                return 0;
            }
        }
    }
    public void average_Scale(){
        int train_number = trainSet.size();
        // the number of the (feature and label)
        int feature_number = featureName.length -1;
        label = new int[train_number];
        trainSet_Scale = new double[train_number][feature_number];
        // declare an array to store the maxvaule and min value for each feature
        double[][] max_min = new double[feature_number][2];
        average = new double[feature_number];
        for(int i = 0; i < feature_number; i++){
            max_min[i][0] = Double.MAX_VALUE;
            max_min[i][1] = Double.MIN_VALUE;
        }
        for(Object x : trainSet){
            Example y = (Example)x;
            for(int j = 0; j< feature_number;j++){
                if(y.features[j]<max_min[j][0]){
                    max_min[j][0] = y.features[j];
                }
                if(y.features[j] > max_min[j][1]){
                    max_min[j][1] = y.features[j];
                }
            }
        }
		for(int j = 0; j<feature_number;j++){
		    average[j] = (max_min[j][0]+max_min[j][1])/2;
        }
        for(int i = 0;i< train_number;i++){
            Example y = (Example)trainSet.get(i);
            for(int j = 0; j< feature_number;j++){
				if( average[j] != 0){
                    trainSet_Scale[i][j] = y.features[j]/average[j];
                }
            }
            label[i] = y.label1;
        }
    }
    public int estimate(double[] input){
		PriorityQueue<keyPoint> PQ = new PriorityQueue<keyPoint>(k);
        int train_number = label.length;
        int feature_number = featureName.length -1;
        if(input.length != feature_number){
            throw new IllegalArgumentException();    
        }
        for(int i=0;i<feature_number;i++){
			if(average[i] != 0){
                input[i] = input[i]/average[i];
		    }
        }
        for(int i = 0; i< train_number;i++){
            double distance = 0;
            for(int j = 0; j< feature_number; j++){
                double difference = trainSet_Scale[i][j] - input[j];
                distance = distance + difference*difference;
            }
            distance = Math.sqrt(distance);
            // 1/distance for the PQ 
            keyPoint point = new keyPoint(1/distance,i);
            if(PQ.size() < k){
                PQ.add(point);
            }
            else{
                if(point.distance > PQ.peek().distance){
                    keyPoint o = PQ.peek();
                    PQ.remove(o);
                    PQ.add(point);
                }
            }
        }
        Iterator value = PQ.iterator();
        int[] count = new int[labels.length];
        while(value.hasNext()){
            keyPoint key = (keyPoint)value.next();
            int index1 = key.index;
            for(int j = 0;j< count.length;j++){
                if(label[index1] == labels[j]){
                    count[j]++;
                    break;
                }
            }
        }
        int max = 0;
        int max_index = 0;
        for(int j = 0;j<count.length;j++){
            if(count[j] > max){
                max = count[j];
                max_index = j;
            }
        }
        return labels[max_index];
    }
    /**
     * @param args the command line arguments
     */ 
    public static void main(String[] args) throws FileNotFoundException{
		long start = System.currentTimeMillis();
        int k = 10;
        String fileName= "C:\\Study materials\\Third semester\\machine learning2\\week 2\\digit_train.csv";
        int[] mark = {0,1,2,3,4,5,6,7,8,9};
		KNN2 classifier = new KNN2(k,fileName,mark);
        classifier.average_Scale();
		String[] test1;
        double[] test = new double[784];
		int[][] output = new int[mark.length][mark.length];
		String csvFile = "C:\\Study materials\\Third semester\\machine learning2\\week 2\\digit_validation.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
		PrintWriter pw = new PrintWriter(new File("confusion_matrix2.csv"));
		StringBuilder sb = new StringBuilder();
		int index = 1;
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while((line = br.readLine()) != null){
               test1  = line.split(cvsSplitBy);
			   for(int i = 0;i<784;i++){
			       test[i] = Double.parseDouble(test1[i+1]);
		       }
		       int predict = classifier.estimate(test);
			   int actual = Integer.parseInt(test1[0]);
			   output[predict][actual]++;
			   index++;
			   if(index % 100 == 0){
				   System.out.println(index);
               }
            }
			
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
		for(int i = 0; i< output.length;i++){
            for(int j = 0; j<output.length; j++){
                String x = Integer.toString(output[i][j]);
				sb.append(x);
				sb.append(',');
			
			}
			sb.append('\n');
		} 
        pw.write(sb.toString());
		pw.close();
		float elapsedTimeMin = (System.currentTimeMillis() - start)/(60*1000F);
		System.out.println(elapsedTimeMin);
    }
    
}
