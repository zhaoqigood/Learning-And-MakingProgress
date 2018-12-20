/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
/**
 *
 * @author zhaoq
 */

public class Bagging {
    private double[] w;
    private double b;
    Bagging(ArrayList<Instance> trainSet,int sample_number,boolean[] isChosen){
        int size = trainSet.size();
        int attributesNumber = trainSet.get(0).getVectors().length;
        w = new double[attributesNumber];
        b = 0;
        for(int j = 0;j<sample_number;j++){
            ArrayList<Instance> training = new ArrayList<Instance>();
            for(int i = 0; i<size;i++){
                int chooseIndex = (int)(Math.random()*size);
                training.add(trainSet.get(chooseIndex));
                isChosen[chooseIndex] = true;
            }
            SVM svm = new SVM(training,0.02,0.1,0.01,1);
            double[] train_w = svm.getW();
            double train_b = svm.getB();
            for(int i = 0; i<attributesNumber;i++){
                w[i] = w[i]+train_w[i];
            }
            b = b + train_b;
            System.out.println("this is the " + j+"th loop");
        }
        for(int j = 0; j<attributesNumber;j++){
            w[j] = w[j]/sample_number;
        }
        b = b/sample_number;
    }
    public double predict(Instance instance){
        double[] x = instance.getVectors();
        double sum = -b;
        for(int i = 0; i<x.length;i++){
            sum = sum + x[i]*w[i];
        }
        return sum;
    }
    public double[] validate(ArrayList<Instance> validationSet){
        double[] performance = new double[5];
        int totalNumber = validationSet.size();
        double[] predict_Values = new double[totalNumber]; 
        // the following four variables record the number of True positive, true negative, false positive and False negativel.
        int TP = 0;
        int TN = 0;
        int FP = 0;
        int FN = 0;
        double sum = 0; // this vatiable record the summation of the whole predicted values which would be used to calculate the mean
        for(int i = 0; i< totalNumber;i++){
            double predict = predict(validationSet.get(i));
            double y = validationSet.get(i).getLabel();
            sum = sum + predict;
            predict_Values[i] = predict;
            if(predict>=0){
                if(y == 1.0){
                    TP++;
                }
                else{
                    FP++;
                }
            }
            else{
                if(y == 1.0){
                    FN++;
                }
                else{
                    TN++;
                }
            }
        }
        //calculate recall, precision,accuracy, f1_score
        performance[0] = (double)TP/(double)(TP+FN);
        performance[1] = (double)TP/(double)(TP+FP);
        performance[2] = (double)(TP+TN)/(double)(TP+TN+FP+FN);
        performance[3] = (double)(2*TP)/(double)(2*TP+FP+FN);
        double mean = sum/(double)totalNumber;
        double variance = 0;
        for(int i = 0; i< totalNumber;i++){
            double dif = predict_Values[i] - mean;
            variance = variance + dif*dif;
        }
        performance[4] = variance/totalNumber;
        return performance;
    }
}
