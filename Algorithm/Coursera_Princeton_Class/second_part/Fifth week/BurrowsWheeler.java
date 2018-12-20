/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import java.util.Arrays;
/**
 *
 * @author zhaoq
 */
public class BurrowsWheeler {
    public static void transform(){
        String s = BinaryStdIn.readString();
        CircularSuffixArray array = new  CircularSuffixArray(s);
        int length = s.length();
        int first = 0;
        char[] lastColumn = new char[length];
        for(int i =0; i< length; i++){
            if(array.index(i) == 0){
                first = i;
            }
            int x = array.index(i);
            if(x>0){
                lastColumn[i] = s.charAt(array.index(i)-1);
            }
            else{
                lastColumn[i] = s.charAt(length-1);
            }          
        }
        BinaryStdOut.write(first);
                  
        for(int i = 0; i<length; i++){
            BinaryStdOut.write(lastColumn[i]);
        }
		BinaryStdOut.close();
    }
    public static void inverseTransform(){
        int R = 256;
        int first = BinaryStdIn.readInt();
        String s = BinaryStdIn.readString();
        int length = s.length();
        char[] lastColumn = new char[length];
        char[] firstColumn = new char[length];
        int[] count = new int[R+1];
        
        for(int i = 0; i<length; i++){
            lastColumn[i] = s.charAt(i);
            count[lastColumn[i]+1]++;
        }
        for(int i = 1; i < R; i++){
            count[i] = count[i] + count[i-1];
        }
        int[] next = new int[length];
        for(int j = 0;j< length; j++){
            firstColumn[count[lastColumn[j]]] = lastColumn[j]; 
            next[count[lastColumn[j]]++] = j; 
        }
        int current = first;
        for(int n = 0; n<length; n++){
            BinaryStdOut.write(firstColumn[current]);
            current = next[current];
        }
		BinaryStdOut.close();
          
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        if(args[0].equals("-")) transform();
        if(args[0].equals("+")) inverseTransform();
    }
    
}
