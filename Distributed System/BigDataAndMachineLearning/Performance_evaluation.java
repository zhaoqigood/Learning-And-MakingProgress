/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author zhaoq
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
public class Performance_evaluation {
    public static void main(String[] args) throws UnknownHostException,IOException {
        long max_latency = Integer.MIN_VALUE;
        long min_latency = Integer.MAX_VALUE;
        long average_latency = 0;
        long throughput = 0;
        String host = "localhost";
        int portnum;
        if(args[0].equals("TCP_Server")){
            long sum = 0;
            for(int i = 0; i < 10000; i++ ){
            portnum = 10000;
            Socket socket;
            try{ 
               socket = new Socket(host, portnum);
            }
            catch(UnknownHostException e){
              throw new UnknownHostException();
            }
            catch(IOException e){
              throw new IOException();
            }
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
          // store the input message to a string variable
            String operation = "SET";
            String key = Integer.toString(i);
            String value = "hello";
            out.println(operation);
            out.println(key);
            out.println(value);
            long startTime = System.nanoTime();
            String output = in.readLine();
            long latency = System.nanoTime() - startTime;
            socket.close();
            sum = sum + latency;
            if(latency > max_latency){
                max_latency = latency;
            }
            if(latency < min_latency){
                min_latency = latency;
            }
        // Your code here!
        }
            average_latency = sum/10000;
            throughput = 1000000000/min_latency;
        }
        else if(args[0].equals("UDP_Server")){
			int count = 0;
            long sum = 0;
            portnum = 20000;
            for(int i = 0; i < 10000; i++){
            DatagramSocket socket = new DatagramSocket();
			socket.setSoTimeout(500);
            String operation = "SET";
            String key = Integer.toString(i);
            String value = "hello";
            InetAddress IP = InetAddress.getByName(host);
			String output = operation;
            if(args.length >= 4){
                key = args[3];
				output = output + "\n" + key;
            }
            if(args.length >= 5){
                value = args[4];
				output = output + "\n" + value;
            }
            DatagramPacket packet = new DatagramPacket(output.getBytes(),output.getBytes().length,IP,portnum);
            byte[] receive = new byte[1100];
            DatagramPacket rec_Packet = new DatagramPacket(receive,receive.length);
            socket.send(packet);
            long startTime = System.nanoTime();
			try{
                socket.receive(rec_Packet);
			}catch(SocketException a){
				socket.close();
				continue;
			}catch(SocketTimeoutException b){
				socket.close();
				continue;
			}
            socket.close();
            long latency = System.nanoTime() - startTime;
            sum = sum + latency;
			count++;
            if(latency > max_latency){
                max_latency = latency;
            }
            if(latency < min_latency){
                min_latency = latency;
            }
            
        }
            average_latency = sum/count;
            throughput = 1000000000/min_latency;
        }
        else{}
        System.out.println(min_latency);
        System.out.println(max_latency);
        System.out.println(average_latency);
        System.out.println(throughput);
    }
}

