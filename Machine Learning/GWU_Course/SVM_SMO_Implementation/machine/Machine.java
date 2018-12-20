/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author zhaoq
 */
public class Machine {
     public ArrayList<Instance>[] preprocess(String dataSet_file_name, int foldNumber){
        String fileName = dataSet_file_name;
        String translatorName = "C:\\Study materials\\Third semester\\machine learning2\\week9\\attributes.csv";
        HashMap[] translator;
        String line = "";
        // get the translator from the attributes file
        try(BufferedReader br = new BufferedReader(new FileReader(translatorName))){
            line = br.readLine();
            String[] strings = line.split(",");
            translator = new HashMap[strings.length];
            for(int i = 0;i < strings.length;i++){
                translator[i] = new HashMap<String,Double>();
            }
            while( (line = br.readLine())!=null){
                strings = line.split(",");
                int length = strings.length;
                for(int j = 0; j<length;j++){
                    // attribute translator
                    if(j < translator.length-1){
                        if(strings[j].length()!=0){
                            double size = (double)translator[j].size();
                            translator[j].put(strings[j], size);
                        }
                    }
                    // label translator
                    else{
                        if(strings[j].length()!=0){
                            double size = (double)translator[j].size();
                            if(size == 0){
                                translator[j].put(strings[j], -1.0);
                            }
                            else{
                                translator[j].put(strings[j], 1.0);
                            }
                        }
                    }
                }
            }
            SVM_Preprocessor preprocessor = new SVM_Preprocessor(fileName,translator);
            preprocessor.delete_feature(3);
            preprocessor.shuffle();
            ArrayList<Instance>[] dataSet = preprocessor.crossValiDataSet(foldNumber);
            return dataSet;
        } catch(IOException e){
            e.printStackTrace();
        }
        return null;
    
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
         String fileName = "C:\\Study materials\\Third semester\\machine learning2\\week9\\adult.csv";
         int foldNumber = 10;
         double C = 0.02;
         double tolerance = 0.1;
         double eps = 0.01;
         int flag = 3;
         Machine machine = new Machine();
         ArrayList<Instance>[] dataSet = machine.preprocess(fileName, foldNumber);
         System.out.println("preprocess finished!");
         ArrayList<Instance> trainSet = new ArrayList<Instance>();  
         for(int i = 0;i<dataSet.length-3;i++){
             trainSet.addAll(dataSet[i]);
         }
         int size = trainSet.size();
         boolean[] isChosen = new boolean[size];
         ArrayList<Instance> validationSet = new ArrayList<Instance>();
         validationSet.addAll(dataSet[dataSet.length-1]);
         validationSet.addAll(dataSet[dataSet.length-2]);
         validationSet.addAll(dataSet[dataSet.length-3]);
         Bagging bagging = new Bagging(trainSet,5,isChosen);
         double[] performance = bagging.validate(validationSet);
         System.out.println(performance[2]);
         System.out.println(performance[4]);
    }
    
}
