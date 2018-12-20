import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Arrays;
import java.util.Collections;
import java.lang.Math;
public class boxStacking{
    private HashMap<Double,Double> base_Height_Map; // store the base area-height pair
    private HashMap<Double,Integer> base_Type_Map; // store the base area-type pair
    private Double[] stack_types; // store the types that are in the stack
    private HashMap<Double,Integer> type_order_Array; // store the order of the base_type in Array
    private int order = 0; // the order of the final base in Array
    private double tallestStack = 0;
    public boxStacking(double[][] types){
        int types_length = types.length;
        double[][] bases = new double[types_length][3];
        // build the array of bases
        for(int i = 0; i<types_length; i++){
            bases[i][0] = types[i][1]*types[i][2];
            bases[i][1] = types[i][0]*types[i][2];
            bases[i][2] = types[i][0]*types[i][1];
        }
        base_Height_Map = new HashMap<Double,Double>();
        base_Type_Map = new HashMap<Double,Integer>();
        stack_types = new Double[3*types.length];
        type_order_Array = new HashMap<Double,Integer>();
        /* iterate the bases,
        /*if the base doesn't exist in base_Height_Map, put it and its height in this hashmap,
        /*then put it and its types in the base_Type_Map, and put the base in the stack_types,
        /*and put the order and this base in the type_order_Array;
        /* if the base exist in base_Height_Map, compare the height, and store the higher one
          following steps are to update the values of hashmap and array,they are almost the same as the former one
         */
        for(int i = 0; i < types_length;i++){
            for(int j=0; j<3; j++){
                if(!base_Height_Map.containsKey(bases[i][j])){
                    base_Height_Map.put(bases[i][j],types[i][j]);
                    base_Type_Map.put(bases[i][j],i);
                    stack_types[order] = bases[i][j];
                    type_order_Array.put(bases[i][j],order);
                    tallestStack += types[i][j];
                    order++;
                }
                else{
                    double value = base_Height_Map.get(bases[i][j]);
                    int base_order = type_order_Array.get(bases[i][j]);
                    if(value < types[i][j]){
                        base_Height_Map.put(bases[i][j],types[i][j]);
                        base_Type_Map.put(bases[i][j],i);
                        stack_types[base_order] = bases[i][j];
                        tallestStack = tallestStack + types[i][j] - value;
                    }
                }
            }
        }
    }
    public double height(){
        return tallestStack;
    }
    public ArrayList stackBoxes(){
        Arrays.sort(stack_types, Collections.reverseOrder());
        int length = stack_types.length;
        ArrayList<Integer> boxes = new ArrayList<Integer>();
        for(int i = 0; i < length; i++){
            if(stack_types[i] != 0){
                boxes.add(base_Type_Map.get(stack_types[i]));
            }
            else{
                break;
            }
        }
        return boxes;
    }
    public static void main(String args[]){
        // for each input size i, ouput its result and running time
        System.out.println("Box Types          tallest height          running time");
        for(int i =100; i< 2000000; i=i*2){
            // initialize the boxes
            double[][] box_types = new double[i][3];
            for(int j =0; j < i ; j++){
                // build an random array representing the input n-types boxes
                for(int n =0; n<3;n++){
                    box_types[j][n] = Math.random()*100;
                }
            }
            long startTime = System.nanoTime();
            boxStacking stack = new boxStacking(box_types);
            double tallestHeight = stack.height();
            long runTime = System.nanoTime() - startTime;
            System.out.printf("%d                 %.2f                  %d",i,tallestHeight,runTime);
            System.out.println();
        }
    }
}
