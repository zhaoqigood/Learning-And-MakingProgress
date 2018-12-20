/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kservers;
import java.lang.Math;
/**
 *
 * @author zhaoq
 */
public class Kservers {

    /**
     * @param args the command line arguments
     */
    
    public int abs(int input){
        if(input < 0){
            return -input;
        }
        else{
            return input;
        }
    }
    public static void main(String[] args) {
        // TODO code application logic here
        // initialize the K servers and N clients
        int N = 10000000;
        for(int k =10;k<1000;k*=2){
        int[] w = new int[N];
        int[] servers = new int[k];
        int[] sums = new int[N];
        int sum = 0;
        for(int i = 0;i<k;i++){
            servers[i] = 0;
        }
        for(int i = 0;i<N;i++){
            w[i] = (int)(Math.random()*100);
            sum = sum + w[i];
            sums[i] =sum;
        }
        boolean moveHappen = true;
        long startTime = System.nanoTime();
        while(moveHappen){
            moveHappen = false;
            for(int i = k-1; i>=0 ;i--){
                while(true){
                   int previous_Position;
                   if(i == 0){
                       previous_Position = 0;
                   }
                   else{
                       previous_Position = (servers[i-1]+servers[i])/2;
                   }
                   int latter_Position;
                   if(i == k-1){
                       latter_Position = N-1;
                   }
                   else{
                       latter_Position = (servers[i]+servers[i+1])/2;
                   }
                   int former_sum = sums[servers[i]]-sums[previous_Position];
                   int latter_sum = sums[latter_Position]-sums[servers[i]];
                   if(former_sum < latter_sum){
                       servers[i] = servers[i]+1;
                       moveHappen = true;
                   }
                   else{
                       break;
                   }
                }
            }
        }
        long time = System.nanoTime() - startTime;
        System.out.println(time);
        int total_Traffic =0;
        Kservers kservers = new Kservers();
        for(int i =0;i<k;i++){
            int former_position; 
            if(i == 0){
                former_position = 0;
            }
            else{
                former_position = (servers[i-1]+servers[i])/2+1;
            }
           
            int latter_position;
            if(i == k-1){
                latter_position = N-1;
            }
            else{
                latter_position = (servers[i]+servers[i+1])/2;
            }
            for(int j = former_position;j<=latter_position;j++){
                total_Traffic = total_Traffic + w[j]*kservers.abs(j-servers[i]);
            }
        }
        for(int n = 0;n<10;n++){
            int total_Traffic1 = 0;
            for(int j =0;j<k;j++){
                servers[j] = (int)(Math.random()*N);
            }
            for(int i =0;i<k;i++){
                int former_position; 
                if(i == 0){
                    former_position = 0;
                }
                else{
                    former_position = (servers[i-1]+servers[i])/2+1;
                }
           
                int latter_position;
                if(i == k-1){
                    latter_position = N-1;
                }
                else{
                    latter_position = (servers[i]+servers[i+1])/2;
                }
                for(int j = former_position;j<=latter_position;j++){
                    total_Traffic1 = total_Traffic1 + w[j]*kservers.abs(j-servers[i]);
                }
            }
        }
        }    
        
    }
    
}
