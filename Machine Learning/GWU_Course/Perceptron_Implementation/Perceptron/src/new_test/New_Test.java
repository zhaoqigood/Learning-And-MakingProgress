/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package new_test;
import weka.core.converters.ArffLoader.ArffReader;
import weka.core.Instances;
import weka.core.Instance;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.lang.Math;
import java.util.ArrayList;
/**
 *
 *
 * @author zhaoq
 */
public class New_Test {
    public void split(ArrayList<Instance> initialSet, ArrayList<Instance> trainSet, ArrayList<Instance> testSet, double split_rate){
        int instance_Number = initialSet.size();
        int test_Number = (int)((double)instance_Number * split_rate);
        Hashtable<Integer,Integer> randomNumber = new Hashtable<Integer,Integer>();
        int count = test_Number;
        while(count>0){
            int number = (int)(Math.random()*instance_Number);
            if(!randomNumber.containsKey(number)){
                randomNumber.put(number,1);
                count--;
            }
            else{
                continue;
            }
        }
        for(int i = 0;i<instance_Number;i++){
            Instance instance = initialSet.get(i);
            if(!randomNumber.containsKey(i)){
                trainSet.add(instance);
            }
            else{
                testSet.add(instance);
            }
        }
        
        
    }
    public double logicFunction(double x){
        return 1.0/(1.0+Math.exp(-x));
    }
    public double sign(double x){
        if(x < 0){
            return -1;
        }
        else if(x > 0){
            return 1;
        }
        else{
            return 0;
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)throws FileNotFoundException, IOException{
        // TODO code application logic here
        // preprocess the initial dataSet
        New_Test train_Test = new New_Test(); 
        BufferedReader reader = new BufferedReader(new FileReader("C:\\Study materials\\Third semester\\machine learning2\\week 3\\dataset_8_liver-disorders.arff")); 
        ArffReader arff = new ArffReader(reader);
        Instances data = arff.getData();
        data.setClassIndex(6);
        int t = data.numInstances();
        ArrayList<Instance> processed_DataSet = new ArrayList<Instance>();
        for(int i = 0; i<t;i++){
            Instance inst = data.get(i);
            processed_DataSet.add(inst);
        }
        // store the sum of all the training results of each split
        double[] w_sum = new double[8];
        // store the average of all the training results of each split
        double[] w_average = new double[8];
        double[] average_accuracies = new double[50];
        for(int i = 0; i<50; i++){
            // split the dataset to training dataset and testing dataset
            ArrayList<Instance> trainSet = new ArrayList<Instance>();
            ArrayList<Instance> testSet = new ArrayList<Instance>();
            train_Test.split(processed_DataSet, trainSet, testSet, 0.1);
            double[] w = {1,1,1,1,1,1,1,1};
            int trainNumber = trainSet.size();
            // start trainning for one split
            int error_count = 1;
            // denote the number of updates
            int update_count = 0;
            int print_count = 0;  // record the times of print 
            while(error_count >0){
                error_count = 0;
                if(update_count > 10000000){
                    break;
                }
                for(int j = 0; j<trainNumber;j++){
                    Instance train_Instance = trainSet.get(j);
                    double y_estimate = w[0];
                    double y_actual = train_Instance.value(6);
                    double z_estimate;
                    double z_actual;
                    double feature_seven = 0;
                    for(int k = 0;k<6;k++){
                        y_estimate = y_estimate + train_Instance.value(k)*w[k+1];
                        feature_seven = feature_seven + train_Instance.value(k)*train_Instance.value(k);
                    }
                    y_estimate = y_estimate + w[7]*feature_seven;
                    // in the class attribute, 1 correspond to -1, 2 correspond to 1
                    if(y_actual < 0.5){
                        z_actual = -1;
                    }
                    else if (y_actual >0.5){
                        z_actual = 1;
                    }
                    else{
                        z_actual = 0;
                    }
                    z_estimate = train_Test.sign(y_estimate);
                    // update w
                    if(z_actual != z_estimate){
                        error_count++;
                        update_count++;
                        // update w[0] first, w = w + learning rate*(target value - actual value) * x;
                        w[0] = w[0] + 10*(z_actual - z_estimate)*1;
                        for(int m = 1;m < 7;m++){
                            w[m] = w[m] + 10*(z_actual - z_estimate)*train_Instance.value(m-1);
                        }
                        w[7] = w[7] + 10*(z_actual - z_estimate)*feature_seven;
                    }
                    
                
            }
                   if(i == 20 && (update_count/100000 == print_count)){
                        print_count++;
                        double w_module = 0;
                        for(int n = 0;n<8;n++){
                            w_module = w_module + w[n]*w[n];
                        }
                        w_module = Math.sqrt(w_module);
                        double margin = 100000*(1/w_module);
                        double error_rate = (double)(error_count)/(double)(trainNumber);
                    }
            }
            for(int j = 0;j<8;j++){
                w_sum[j] = w_sum[j] + w[j];
            }
            // denote the number of correctly estimate
            int correct_count = 0;
            int test_number = testSet.size();
            // calculate the accuracy of specific trainset with the testSet
            for(int j = 0;j<test_number;j++){
                Instance test_Instance = testSet.get(j);
                double y_estimate = w[0];
                double feature_seven = 0;
                for(int k = 0;k<6;k++){
                    y_estimate = y_estimate + w[k+1] * test_Instance.value(k);
                    feature_seven = feature_seven + test_Instance.value(k)*test_Instance.value(k);
                }
                y_estimate = y_estimate + w[7]*feature_seven;
                double z_estimate = train_Test.sign(y_estimate);
                double y_actual = test_Instance.value(6);
                if(y_actual < 0.5 && z_estimate == -1){
                    correct_count++;
                }
                else if(y_actual > 0.5 && z_estimate == 1){
                    correct_count++;
                }
            }
            average_accuracies[i] = ((double)correct_count)/((double)test_number);
        }
        for(int i = 0;i<8;i++){
            w_average[i] = w_sum[i]/50;
        }
        ArrayList<Instance> trainSet = new ArrayList<Instance>();
        ArrayList<Instance> testSet = new ArrayList<Instance>();
        train_Test.split(processed_DataSet, trainSet, testSet, 0.1);
        int test_number = testSet.size();
        int correct_count = 0;
            // calculate the accuracy of specific trainset with the testSet
        for(int j = 0;j<test_number;j++){
            Instance test_Instance = testSet.get(j);
            double y_estimate = w_average[0];
            double feature_seven = 0;
            for(int k = 0;k<6;k++){
                y_estimate = y_estimate + w_average[k+1] * test_Instance.value(k);
                feature_seven = feature_seven + test_Instance.value(k)*test_Instance.value(k);
            }
            y_estimate = y_estimate + w_average[7]*feature_seven;
            double z_estimate = train_Test.sign(y_estimate);
            double y_actual = test_Instance.value(6);
            if(y_actual < 0.5 && z_estimate == -1){
                    correct_count++;
                }
            else if(y_actual > 0.5 && z_estimate == 1){
                    correct_count++;
                }
        }
        double final_accuracy = ((double)correct_count)/((double)test_number);
        System.out.println(String.format("%.2f",final_accuracy));
    
    }
   
    
}