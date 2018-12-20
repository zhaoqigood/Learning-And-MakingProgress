/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.HashMap;

/**
 *
 * @author zhaoq
 */
public class Instance {
        private double[] input_vectors;
        private double label;
        // replace the string with numerical value and determine whether this string contains "?"
        Instance(double[] vectors){
            input_vectors = vectors;
        }
        Instance(){
        }
        public boolean transform(String[] strings, HashMap<String,Double>[] translator, boolean[] isNumerical){
            int length = strings.length;
            input_vectors = new double[length-1];
            for(int i = 0; i < length; i++ ){
                if(strings[i].equals("?")){
                    return false;
                }
                else if (i < length-1){
                    if(isNumerical[i]){
                        input_vectors[i] = Double.parseDouble(strings[i]);
                    }
                    else{
                        if(translator[i].containsKey(strings[i])){
                            input_vectors[i] = translator[i].get(strings[i]);
                        }
                        else{
                            return false;    
                        }
                    }
                }
            }
            if(translator[length-1].containsKey(strings[length-1])){
                label = translator[length-1].get(strings[length-1]);
            }
            else{
                return false;
            }
            return true;    
        }
        public double getLabel(){
            return label;
        }
        public void deleteFeature(int index){
            int length = input_vectors.length;
            double[] vectors = new double[length-1];
            for(int i = 0; i<length-1; i++){
                if(i>=index){
                    vectors[i] = input_vectors[i+1];
                }
                else{
                    vectors[i] = input_vectors[i];
                }
            }
            input_vectors = vectors;
        }
        public double[] getVectors(){
            return input_vectors;
        }
        public double getfeature(int index){
            return input_vectors[index];
        }
}


