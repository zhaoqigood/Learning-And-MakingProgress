/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
/**
 *
 * @author zhaoq
 */

/* The the overview of the structure:
   This SVM implement the SMO algorithm that Platt developed.
   
   The purpose:
       Receive a trainset, train the SVM and reserve a set of optimazed parameters
       Recieve a validationSet, validate the SVM.

   methods:
       1. constructor( train set) . Given a trainset, construct a SVM
       2. examine(Instance). Given a Instance, determine whether the corresponding parameter are suitable for optimizing.
       3. OneStep(parameter1,parameter2). Given two parameters, optimize the SVM and update other values.
       4. kernel(x1,x2). Given two instance, return the k(x1,x2).
       5. validate(validation set). Given a validation dataSet, evaluate the model.
   
   Global variable:
       1.  if SVM is linear, there is a W(parameter vector).
           else there is support vectors and parameters.( store the value of kernel corresponding to the non-zero parameters.)    
*/
public class SVM {
    double[] w;
    ArrayList<Instance> supportVectors;
    ArrayList<Integer> nonZeroAlpha;  
    double[] alpha;
    double C; // this is the slack variable cofficient
    double tolerance; // this is used to get an approximate optimal solution
    ArrayList<Instance> trainSet; // this variable is used to store train data
    double[] errorCache; // this is used to store the difference between SVM output and real label for each instance
    double eps; // this is used to determine whether a optimize step is taken or not
    double b; // this is the threshold
    int flag; // this is used to specify what kind of the kernel that this SVM use. 1(linear kernel). other value( other kernel)
    SVM(ArrayList<Instance> trainSet, double C , double tolerance, double eps, int flag){
        // initialize the global variable
        w = new double[trainSet.get(0).getVectors().length];
        this.trainSet = trainSet;
        supportVectors = new ArrayList<Instance>();
        nonZeroAlpha = new ArrayList<Integer>();
        // initialize the alpha with 0
        alpha = new double[trainSet.size()];
        b = 0;
        // initialize errorCahce, with the given alpha[]
        errorCache = new double[trainSet.size()];
        for(int i = 0; i < errorCache.length;i++){
            errorCache[i] = 0 - trainSet.get(i).getLabel();
        }
        this.C = C;
        this.tolerance = tolerance;
        this.eps = eps;
        this.flag = flag;
        // start to train the model
        int numOfOptimized1 = 1;// this is used to control outer loop
        while(numOfOptimized1>0){
            numOfOptimized1 = 0;
            for(int i = 0; i<alpha.length;i++){
                numOfOptimized1 = numOfOptimized1 + examine(i);
            }
            if(numOfOptimized1 == 0){
                break;
            }
            int numOfOptimized2 = 1; // this is used to control inner loop
            while(numOfOptimized2 > 0 ){
                numOfOptimized2 = 0;
                for(int i = 0; i< alpha.length;i++){
                    if(alpha[i]>0 && alpha[i]<C){
                        numOfOptimized2 = numOfOptimized2 + examine(i);
                    }    
                }
            }
            
        }
        
    }
    public int examine(int index2){
        double[] x2 = trainSet.get(index2).getVectors();
        double y2 = trainSet.get(index2).getLabel();
        double alpha2 = alpha[index2];
        double error2 = errorCache[index2];
        double judge = y2*error2;
        // if this instance violate the KKT conditions, then execute the optimazation condition
        
        if((judge < (-tolerance)&& alpha2 < C) || (judge>tolerance && alpha2 > 0)){
            int number = 0;
            for(int i = 0; i< alpha.length;i++){
                if(alpha[i] > 0 && alpha[i] < C){
                    number++;
                }
            }
            // make a choice first
            if(number > 1){
                double max_difference = Double.MIN_VALUE;
                int index1 = 0;
                for(int i = 0; i<errorCache.length; i++){
                    double difference = abs(errorCache[i],error2);
                    if(difference > max_difference){
                        max_difference = difference;
                        index1 = i;
                    }
                }
                if(index1 != index2 && oneStep(index1,index2)){
                    return 1;
                }
            }
            // make a choice second if the first one doesn't work
            int start_Index = (int)(Math.random())*(alpha.length);
            for(int k = 0; k < alpha.length;k++){
                int index1 = (start_Index + k)%(alpha.length);
                if(alpha[index1] != 0 && alpha[index1] != C && oneStep(index1,index2)){
                    return 1;
                }
            }
            start_Index = (int)(Math.random())*(alpha.length);
            for(int k = 0; k < alpha.length;k++){
                int index1 = (start_Index + k)%(alpha.length);
                if(oneStep(index1,index2)){
                    return 1;
                }
            }
        }
        return 0;
    }
    // this method is used for calculating the absolute values of the difference
    public double abs(double x1, double x2){
        double t = x1 - x2;
        if(t<0){
            return -t;
        }
        else{
            return t;
        }
    }
    public boolean oneStep(int index1, int index2){
        if(index1 == index2){
            return false;
        }
        double alpha1 = alpha[index1];
        double y1 = trainSet.get(index1).getLabel();
        double error1 = errorCache[index1];
        double y2 = trainSet.get(index2).getLabel();
        double alpha2 = alpha[index2];
        double error2 = errorCache[index2];
        double s = y1*y2;
        double b_old = b;
        // compute L and H
        double L,H,alpha1_new,alpha2_new;
        if(y1 != y2){
            L = Math.max(0, alpha2-alpha1);
            H = Math.min(C, C+alpha2-alpha1);
        }
        else{
            L = Math.max(0, alpha2+alpha1-C);
            H = Math.min(C, alpha2+alpha1);
        }
        if(L == H){
            return false;
        }
        // start to optimize the objective function
        double k11 = kernel(trainSet.get(index1),trainSet.get(index1));
        double k12 = kernel(trainSet.get(index1),trainSet.get(index2));
        double k22 = kernel(trainSet.get(index2),trainSet.get(index2));
        double eta = k11 + k22 - 2*k12;
        // in this condition, the normal optimiza can be made
        if(eta > 0){
            alpha2_new = alpha2 + y2*(error1 - error2)/eta;
            if(alpha2_new < L){
                alpha2_new = L;
            }
            else if(alpha2_new > H){
                alpha2_new = H;
            }
        }
        // in this condition, we need to compare the value of objective function with respect to L and H
        else{
            double f1 = y1*(error1+b)-alpha1*k11-s*alpha2*k12;
            double f2 = y2*(error2+b)-s*alpha1*k12-alpha2*k22;
            double L1 = alpha1 + s*(alpha2-L);
            double H1 = alpha1 + s*(alpha2-H);
            double obj_L = L1*f1 + L*f2 + 0.5*L1*L1*k11+0.5*L*L*k22+s*L*L1*k12;
            double obj_H = H1*f1 + H*f2 + 0.5*H1*H1*k11+0.5*H*H*k22+s*H*H1*k12;
            if(obj_L < obj_H - eps){
                alpha2_new = L;
            }else if(obj_L > obj_H + eps){
                alpha2_new = H;
            }else{
                alpha2_new = alpha2;
            }
        }
        if(abs(alpha2_new, alpha2) < eps*(alpha2_new + alpha2 + eps)){
            return false;
        }
        alpha1_new = alpha1 + s*(alpha2 - alpha2_new);
        if(alpha1_new > 0 && alpha1_new < C){
            b = b + error1+y1*(alpha1_new - alpha1)*k11 + y2*(alpha2_new - alpha2)*k12;
        }
        else if(alpha2_new > 0 && alpha2_new < C){
            b = b + error2+y1*(alpha1_new - alpha1)*k12 + y2*(alpha2_new - alpha2)*k22;
        }
        else{
            double b1 = error1+y1*(alpha1_new - alpha1)*k11 + y2*(alpha2_new - alpha2)*k12+b;
            double b2 = error2+y1*(alpha1_new - alpha1)*k12 + y2*(alpha2_new - alpha2)*k22+b;
            b = (b1+b2)/2.0;
        }
        // update the alpha vector
        alpha[index1] = alpha1_new;
        alpha[index2] = alpha2_new;
        // update weight vector w
        if(flag == 1){
            double[] x1 = trainSet.get(index1).getVectors();
            double[] x2 = trainSet.get(index2).getVectors();
            double coffi1 = y1*(alpha1_new - alpha1);
            double coffi2 = y2*(alpha2_new - alpha2);
            for(int k= 0; k<w.length;k++){
                w[k] = w[k] + coffi1*x1[k] + coffi2*x2[k];
            }
            for(int i = 0; i< errorCache.length;i++){
                double ui = -b;
                double yi = trainSet.get(i).getLabel();
                double[] x = trainSet.get(i).getVectors();
                for(int j = 0; j< w.length; j++){
                    ui = ui + w[j]*x[j];
                }
                errorCache[i] = ui - yi;
            }
            return true;
        }
        //update the error cache
        else{
            nonZeroAlpha.clear();
            for(int i = 0; i< alpha.length;i++){
                // choose the support vectors.
                if(alpha[i] != 0){
                    nonZeroAlpha.add(i);
                }
            }
            // update the error cache
            for(int i = 0; i< errorCache.length;i++){
                double error_old = errorCache[i];
                Instance xi = trainSet.get(i);
                errorCache[i] = error_old + b_old - b +(alpha1_new - alpha1)*y1*kernel(trainSet.get(index1),xi) + (alpha2_new - alpha2)*y2*kernel(trainSet.get(index2),xi);
            }
            return true;
            } 
    }
    public double kernel(Instance x1, Instance x2){
        double[] x1_vectors = x1.getVectors();
        double[] x2_vectors = x2.getVectors();
        // this condition correspond to linear kernel
        if(flag == 1){
            double sum = 0;
            for(int i = 0; i<x1_vectors.length;i++){
                sum = sum + x1_vectors[i]*x2_vectors[i]; 
            }
            return sum;
        }
        // this condition correspond to RBF kernel. the standard deviation = 10 
        else if(flag == 2){
           double sum = 0;
           for(int i = 0; i<x1_vectors.length;i++){
               double dif = x1_vectors[i]-x2_vectors[i];
               sum = sum + dif*dif;
           }
           double k12 = Math.exp(-sum/10);
           return k12;
        }
        // this condition correspond to polynomial kernel. The degree = 2, c = 1;
        else if(flag == 3){
            double sum = 1.0;
            for(int i = 0; i<x1_vectors.length;i++){
                sum = sum + x1_vectors[i]*x2_vectors[i];
            }
            double k12 = sum * sum;
            return k12;
        }
        else{
            return 0;
        }
    }
    public double predict(Instance x){
        if(flag == 1){
            double fx = -b;
            double[] x_vectors = x.getVectors();
            for(int i = 0; i<x_vectors.length;i++){
                fx = fx + x_vectors[i]*w[i];
            }
            return fx;
        }
        else{
            double fx = -b;
            for(Integer i: nonZeroAlpha){
                double yi = trainSet.get(i).getLabel();
                fx = fx + yi*alpha[i]*kernel(trainSet.get(i),x);
            }
            return fx;
        }
    }
    // this method preprocess data and return recall, precision,accuracy, f1_score, variance.
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
    public double[] getSupportVectors(){
        return alpha;
    }
    public double[] getW(){
        return w;
    }
    public double getB(){
        return b;
    }
}
