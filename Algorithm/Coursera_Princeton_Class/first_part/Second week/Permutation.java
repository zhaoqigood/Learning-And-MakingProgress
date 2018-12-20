/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import edu.princeton.cs.algs4.StdIn;
public class Permutation {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int k=Integer.parseInt(args[0]);
        RandomizedQueue<String> queue=new RandomizedQueue<String>();
        while(!StdIn.isEmpty()){
            String in=StdIn.readString();
            queue.enqueue(in);
        }
        for(int i=0;i<k;i++){
            String out=queue.dequeue();
            System.out.println(out);
        }
    }
    
}
