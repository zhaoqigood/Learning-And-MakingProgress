/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.util.Collections;
import java.util.Arrays;
import java.util.List;
import java.util.RandomAccess;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.lang.Character;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Set;
import java.io.PrintWriter;
/**
 *
 * @author zhaoq
 */


/* Overview of this algorithm
First, iterate this CSV file,
for each row,
1: discard the row that contain ?
2: change the character value to numerical value. Meanwhile, record the summation of each column
   for normalizing the value.
3: store the label 1 data in one list, store the label 2 data in another list.
4: normalize the datasets in list one, and in list two.
5. random split the list one into 10 lists, list two into 10 lists, and combine them to form 
   stratified 10-fold cross validation data set.
*/
public class SVM_Preprocessor {
    private String[] attributes; 
    private String label;
    private boolean[] isNumerical;// this array is used to judge whether an attribute is numerical or string
    private ArrayList<Instance> label1_Set; // store the instances whose label is 1
    private ArrayList<Instance> label2_Set; // store the instances whose label is 2
    private double[] sum_attributes; // this variable stores the summation of the data belonging to each attribute. 
    private int numOfInstances = 0; // this is the size of the dataset
    private double[] split_point; // store the split_point corresponding to the most information gain for each feature.
    SVM_Preprocessor(String fileName, HashMap[] translator){
        label1_Set = new ArrayList<Instance>();
        label2_Set = new ArrayList<Instance>();
        String line = "";
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))){
            // 1. extract the information about the attributes and label
            line = br.readLine();
            line = line.replace("\"", "");
            String[] strings = line.split(",");
            label = strings[strings.length-1];
            attributes = new String[strings.length-1];
            for(int i = 0; i<strings.length-1;i++){
                attributes[i] = strings[i];
            }
            // 2. extract the information about whether the attribute is numerical value
            isNumerical = new boolean[strings.length];
            if((line = br.readLine())!= null){
                line = line.replace("\"", "");
                strings = line.split(",");
                for(int i = 0; i< strings.length;i++){
                if(!is_Numerical(strings[i])){
                    isNumerical[i] = false;
                }
                else{
                    isNumerical[i] = true;
                }
            }
            }
            // 3. extract some information about the length of the translator and sum_attributes
            sum_attributes = new double[strings.length-1];
            // 4. iterate each each row/ each instance in the dataset
            while(line != null){
                // create one instance with one row
                line = line.replace("\"", "");
                strings = line.split(",");
                Instance instance = new Instance();
                if(instance.transform(strings,translator,isNumerical)){
                    double[] input_Values = instance.getVectors();
                    for(int j = 0; j<sum_attributes.length;j++){
                        sum_attributes[j] = sum_attributes[j] + input_Values[j];
                    }
                    numOfInstances++ ;
                    if(instance.getLabel() == -1.0){
                        label1_Set.add(instance);
                    }
                    else{
                        label2_Set.add(instance);
                    }
                    
                }
                line = br.readLine();
            }
            
        } catch(IOException e){
             e.printStackTrace();   
        }
        // 5. normalize the data sets
        double[] meanOfAttributes = new double[sum_attributes.length];
        for(int i = 0; i<sum_attributes.length;i++){
            meanOfAttributes[i] = sum_attributes[i]/numOfInstances;
        }
        double[] sumOfSquareError = new double[sum_attributes.length];
        double[] standardDeviation = new double[sum_attributes.length];
        // calculate the sumOfSquareError 
        for(Instance a: label1_Set){
            double[] input_Vector = a.getVectors();
            for(int j = 0; j<input_Vector.length;j++){
                sumOfSquareError[j] = sumOfSquareError[j] + (input_Vector[j]-meanOfAttributes[j])*(input_Vector[j]-meanOfAttributes[j]);
            }           
        }
        for(Instance a: label2_Set){
            double[] input_Vector = a.getVectors();
            for(int j = 0; j<input_Vector.length;j++){
                sumOfSquareError[j] = sumOfSquareError[j] + (input_Vector[j]-meanOfAttributes[j])*(input_Vector[j]-meanOfAttributes[j]);
            }           
        }
        for(int i = 0; i<sumOfSquareError.length; i++){
            standardDeviation[i] = Math.sqrt(sumOfSquareError[i]/numOfInstances);
        }
        // iterate each instance and normalize each attribute
        for(Instance a: label1_Set){
            double[] input_Vector = a.getVectors();
            for(int j = 0; j<input_Vector.length;j++){
                 input_Vector[j] = (input_Vector[j] - meanOfAttributes[j])/standardDeviation[j];
            }           
        }
        for(Instance a: label2_Set){
            double[] input_Vector = a.getVectors();
            for(int j = 0; j<input_Vector.length;j++){
                input_Vector[j] = (input_Vector[j] - meanOfAttributes[j])/standardDeviation[j];
            }           
        }
        
    }
    public boolean is_Numerical(String input){
        for(char a: input.toCharArray()){
            if(!Character.isDigit(a)){
                return false;
            }
        }
        return true;
    }
    // create two files, store the shuffled datasets of each label in two files 
    public void shuffle(){
        Collections.shuffle(label1_Set);
        Collections.shuffle(label2_Set);
    }
    // delete one feature, thus change this data structure a lot
    public void delete_feature(int index){
        int length = attributes.length;
        String[] attributes_change = new String[length-1];
        boolean[] isNumerical1 = new boolean[length-1];
        double[] sum_attributes1 = new double[length-1]; 
        for(int i = 0; i<length-1; i++){
            if(i>=index){
                attributes_change[i] = attributes[i+1];
                isNumerical1[i] = isNumerical[i+1];
                sum_attributes1[i] = sum_attributes[i+1];
            }
            else{
                attributes_change[i] = attributes[i];
                isNumerical1[i] = isNumerical[i];
                sum_attributes1[i] = sum_attributes[i];
            }
        }
        attributes = attributes_change;
        isNumerical = isNumerical;
        sum_attributes = sum_attributes1;
        for(Instance a: label1_Set){
            a.deleteFeature(index);
        }
        for(Instance a: label2_Set){
            a.deleteFeature(index);
        }
        
    }
    // split the dataset into n-fold cross Validation sets.
    public ArrayList[] crossValiDataSet(int fold_number){
        ArrayList[] dataSets = new ArrayList[fold_number];
        int label1_Size = label1_Set.size();
        int label2_Size = label2_Set.size();
        int step1 = label1_Size/fold_number;
        int step2 = label2_Size/fold_number;
        for(int i = 0; i < fold_number; i++){
            dataSets[i] = new ArrayList<Instance>();
            int from_Index1 = i*step1;
            int end_Index1 = (i+1)*step1;
            if(i == fold_number-1){
                end_Index1 = label1_Size;
            }
            dataSets[i].addAll(label1_Set.subList(from_Index1, end_Index1));
            int from_Index2 = i*step2;
            int end_Index2 = (i+1)*step2;
            if(i == fold_number-1){
                end_Index2 = label2_Size;
            }
            dataSets[i].addAll(label2_Set.subList(from_Index2, end_Index2));
            Collections.shuffle(dataSets[i]);
        }
        return dataSets;
        
    }
    public ArrayList<Instance> getDataSet(){
        ArrayList<Instance> list = new ArrayList<Instance>();
        list.addAll(label1_Set);
        list.addAll(label2_Set);
        return list;
    }
    // get an array that stores most information gain of each feature. 
    /* 1. iterarte the whole dataset, calculate the min value and max value for each feature
       2. for each feature: divide the interval between min and max value into many parts.
                            calculate the information gain for each part
                            store the max information gain in an array
    */
    public double[] information_Gain(int divided_parts){
        split_point = new double[attributes.length];
        double[] informationGain = new double[attributes.length];
        double[] min = new double[attributes.length];
        double[] max = new double[attributes.length];
        for(int j = 0; j< min.length;j++){
            min[j] = Double.MAX_VALUE;
            max[j] = Double.MIN_VALUE;
            informationGain[j] = Double.MIN_VALUE;
        }
        
        for(Instance instance:label1_Set){
            double[] value = instance.getVectors();
            for(int i = 0; i<value.length;i++){
                if(value[i] < min[i]){
                    min[i] = value[i];
                }
                if(value[i] > max[i]){
                    max[i] = value[i];
                }
            }            
        }
        for(Instance instance:label2_Set){
            double[] value = instance.getVectors();
            for(int i = 0; i<value.length;i++){
                if(value[i] < min[i]){
                    min[i] = value[i];
                }
                if(value[i] > max[i]){
                    max[i] = value[i];
                }
            }
        }
        double negative_fraction = (double)label1_Set.size()/(double)(label1_Set.size()+label2_Set.size());
        double positive_fraction = (double)label2_Set.size()/(double)(label1_Set.size()+label2_Set.size());
        double initial_Entropy = (-1.0)*positive_fraction*(Math.log(positive_fraction)/Math.log(2.0)) - negative_fraction*(Math.log(negative_fraction)/Math.log(2.0));
        for(int j = 0; j< attributes.length;j++){
            double step = (max[j] - min[j])/(double)divided_parts;
            for(int k = 0; k < divided_parts;k++){
                double split = min[j]+ ((double)k)*step;
                int smaller_positive = 0;
                int smaller_negative = 0;
                int bigger_positive = 0;
                int bigger_negative = 0;
                for(Instance instance: label1_Set){
                    double[] values = instance.getVectors();
                    double label = instance.getLabel();
                    if(values[j] <= split){
                        if(label == -1.0){
                            smaller_negative++;
                        }
                        else{
                            smaller_positive++;
                        }
                    }
                    else{
                        if(label == -1.0){
                            bigger_negative++;
                        }
                        else{
                            bigger_positive++;
                        }
                    }
                }
                for(Instance instance: label2_Set){
                    double[] values = instance.getVectors();
                    double label = instance.getLabel();
                    if(values[j] <= split){
                        if(label == -1.0){
                            smaller_negative++;
                        }
                        else{
                            smaller_positive++;
                        }
                    }
                    else{
                        if(label == -1.0){
                            bigger_negative++;
                        }
                        else{
                            bigger_positive++;
                        }
                    }
                }
                // calculate the entropy and information gain
                double sma_posi_fraction = (double)smaller_positive/(double)(smaller_positive+smaller_negative);
                double sma_neg_fraction = (double)smaller_negative/(double)(smaller_positive+smaller_negative);
                double big_posi_fraction = (double)bigger_positive/(double)(bigger_positive+bigger_negative);
                double big_neg_fraction = (double)bigger_negative/(double)(bigger_positive+bigger_negative);
                double smaller_Entropy = (-1.0)*sma_posi_fraction*(Math.log(sma_posi_fraction)/Math.log(2.0)) - sma_neg_fraction*(Math.log(sma_neg_fraction)/Math.log(2.0));
                double bigger_Entropy = (-1.0)*big_posi_fraction*(Math.log(big_posi_fraction)/Math.log(2.0)) - big_neg_fraction*(Math.log(big_neg_fraction)/Math.log(2.0));
                double smaller_fraction = (double)(smaller_negative+smaller_positive)/(double)(smaller_negative+smaller_positive+bigger_negative+bigger_positive);
                double bigger_fraction = (double)(bigger_negative+bigger_positive)/(double)(smaller_negative+smaller_positive+bigger_negative+bigger_positive);
                double gain = initial_Entropy - smaller_fraction*smaller_Entropy - bigger_fraction*bigger_Entropy;
                if(gain > informationGain[j]){
                    informationGain[j] = gain;
                    split_point[j] = split;
                }
            }
            
        }
        return informationGain;
    }
    
}

