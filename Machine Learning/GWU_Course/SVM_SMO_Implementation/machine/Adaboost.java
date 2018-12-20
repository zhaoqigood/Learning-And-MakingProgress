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
/* this is an adaboost class
There are  following methods:
Constructor: Given the training Dataset and T, initialize an instance of this class.
validate:   Given the validation dataset, return the performance of this model.
predict:    Given an input instance, return the predicted value
*/
public class Adaboost {
    private stump[] stumps;
    private double[] w; // this variable represents the coefficients
	// divide_part represent the number of split points for each feature
    Adaboost(ArrayList<Instance> trainSet, int T,int divide_part){
        int size = trainSet.size();
        double[] alpha = new double[size];
        // initialize the weights of each instance
        for(int i = 0;i<size;i++){
            alpha[i] = 1.0/(double)size;
        }
        // declare a set of classifiers
        stumps = new stump[T];
        w = new double[T];
        double[] x = trainSet.get(0).getVectors();
        double[][] max_min = new double[x.length][2];
        for(int i = 0;i<max_min.length;i++){
            max_min[i][0] = Double.MIN_VALUE;
            max_min[i][1] = Double.MAX_VALUE;
        }
        for(int i = 0;i<size;i++){
            double[] vector = trainSet.get(i).getVectors();
            for(int j = 0;j<x.length;j++){
                if(vector[j] > max_min[j][0]){
                    max_min[j][0] = vector[j];
                }
                else if(vector[j]<max_min[j][1]){
                    max_min[j][1] = vector[j];
                }
            }
        }
        for(int i = 0; i<T;i++){
            stump decisionTree = new stump(trainSet,alpha,max_min,divide_part);
            double error_rate = decisionTree.getError();
            double wi = 0.5*Math.log((1.0-error_rate)/error_rate); 
            boolean[] feedback = decisionTree.getFeedBack();
            for(int j = 0;j<size;j++){
                if(feedback[j]){
                    alpha[j] = alpha[j]*Math.exp(-wi);
                }
                else{
                    alpha[j] = alpha[j]*Math.exp(wi);
                }
            }
            stumps[i] = decisionTree;
            w[i] = wi;
        }    
    }
    
    class stump{
        double splitPoint;// this variable represent the split point of the stump
        double less_direction = 1;// if direction = false, the feature(>= splitPoint) is +1, the other is -1. verse vela.
        double more_direction = 1;
        int splitFeature;
        boolean[] predict;
        double minError = Double.MAX_VALUE;
        stump(ArrayList<Instance> trainSet, double[] weights, double[][] max_min, int divide_part){
            int size = trainSet.size();
            predict = new boolean[size];
            for(int i=0; i<max_min.length;i++){
                double split = 0;
                double start = max_min[i][1];
                double step = (max_min[i][0]-max_min[i][1])/(double)divide_part;
                double outer_error = Double.MAX_VALUE;
                boolean[] outer_feedback = new boolean[size];
                double outer_lessDirection = 1;
                double outer_moreDirection = 1;
                for(int j = 0; j<divide_part;j++){
                    double divide_point = start+j*step;
                    // this is the weighted summation of the point
                    double smaller_positive = 0;
                    double smaller_negative = 0;
                    double bigger_positive = 0;
                    double bigger_negative = 0;
                    double error_rate = 0;
                    double inner_lessDirection = 1;
                    double inner_moreDirection = 1;
                    boolean[] predict_feedback = new boolean[size];
                    int[] predict_conditions = new int[size];
                    for(int k=0; k<size;k++){
                        double feature_value = trainSet.get(k).getfeature(i);
                        double y = trainSet.get(k).getLabel();
                        if(feature_value>=divide_point){
                            if(y == 1.0){
                                predict_conditions[k] = 1;
                                bigger_positive = bigger_positive + weights[k];
                            }
                            else{
                                predict_conditions[k] = 2;
                                bigger_negative = bigger_negative + weights[k];
                            }
                        }
                        else{
                            if(y == 1.0){
                                predict_conditions[k] = 3;
                                smaller_positive = smaller_positive + weights[k];
                            }  
                            else{
                                predict_conditions[k] = 4;
                                smaller_negative = smaller_negative + weights[k];
                            }
                        }
                    }
                    if(bigger_positive>=bigger_negative && smaller_positive>=smaller_negative){
                        error_rate = bigger_negative + smaller_negative;
                        inner_moreDirection = 1;
                        inner_lessDirection = -1;
                        for(int m = 0; m<size;m++){
                            if(predict_conditions[m]%2 == 1){
                                predict_feedback[m] = true;
                            }
                        }
                        
                    }else if(bigger_positive<bigger_negative && smaller_positive>=smaller_negative){
                        error_rate = bigger_positive + smaller_negative;
                        inner_moreDirection = -1;
                        inner_lessDirection = -1;
                        for(int m = 0; m<size;m++){
                            if(predict_conditions[m] == 2 || predict_conditions[m] == 3){
                                predict_feedback[m] = true;
                            }
                        }
                        
                    }else if(bigger_positive>=bigger_negative && smaller_positive<smaller_negative){
                        error_rate = bigger_negative + smaller_positive;
                        inner_moreDirection = 1;
                        inner_lessDirection = 1;
                        for(int m = 0; m<size;m++){
                            if(predict_conditions[m] == 1 || predict_conditions[m] == 4){
                                predict_feedback[m] = true;
                            }
                        }
                    }else{
                        error_rate = bigger_positive + smaller_positive;
                        inner_moreDirection = -1;
                        inner_lessDirection = 1;
                        for(int m = 0; m<size;m++){
                            if(predict_conditions[m] == 2 || predict_conditions[m] == 4){
                                predict_feedback[m] = true;
                            }
                        }
                    }
                    if(error_rate < outer_error){
                        outer_error = error_rate;
                        outer_feedback = predict_feedback;
                        split = divide_point;
                        outer_lessDirection = inner_lessDirection;
                        outer_moreDirection = inner_moreDirection;
                    }
                }
                if(outer_error < minError){
                    minError = outer_error;
                    splitPoint = split;
                    predict = outer_feedback;
                    splitFeature = i;
                    less_direction = outer_lessDirection;
                    more_direction = outer_moreDirection;
                }
            }
        }
        public double decide(Instance instance){
            double x = instance.getfeature(splitFeature);
            if(x>=splitPoint){
                return more_direction;
            }
            else{
                return (-1)*less_direction;
            }
        }
        public boolean[] getFeedBack(){
            return predict;
        }
        public double getError(){
            return minError;
        }
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
    public double predict(Instance x){
        double y = 0;
        for(int i = 0;i<stumps.length;i++){
            y = y + w[i] * stumps[i].decide(x);
        }
        return y;
        
    }
}
