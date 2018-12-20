/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.net.SocketException;
import java.net.SocketTimeoutException;
/**
 *
 * @author zhaoq
 */
public class UDP_Thread implements Runnable{
    private String threadName;
    private HashMap<String,String> hashTable;
    public UDP_Thread(String name, HashMap map){
        threadName = name;
        hashTable = map;
    }
    public void run(){
        try{
            System.out.println("Waiting for connection on port 20000:");
            while(true){ 
		        DatagramSocket  udpSocket = new DatagramSocket(20000);
                udpSocket.setSoTimeout(500);
                byte[] buf = new byte[1100];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                try{
                    udpSocket.receive(packet);
                }catch(SocketException a){
					udpSocket.close();
                    continue;
                }catch(SocketTimeoutException b){
					udpSocket.close();
                    continue;
                }
                String msg = new String(packet.getData()).trim();
                String[] messages = msg.split("\n");
                InetAddress IP = packet.getAddress();
                int portNo = packet.getPort();
				System.out.println("Received a Packet from:" +" "+ IP);
                String output;
                if(messages.length == 3 && messages[0].equals("SET")){
                    hashTable.put(messages[1], messages[2]);
                    output = "succeed!";
                    
                }
                else if(messages.length == 2 && messages[0].equals("GET")){
                    if(hashTable.containsKey(messages[1])){
                        output = hashTable.get(messages[1]);
                    }
					else{
						output = "Cann't find the key";
					}
                }
                else if(messages.length == 1 && messages[0].equals("STATS")){
                    int size = hashTable.size();
                    output = Integer.toString(size);
                }
                else{
                    output = "error!";
                }
                DatagramPacket sendPacket = new DatagramPacket(output.getBytes(),output.getBytes().length,IP,portNo);
                udpSocket.send(sendPacket);
				udpSocket.close();
                }
            }
        catch(IOException e){
            e.printStackTrace();
        }
        System.out.println("Waiting for connections on port 20000");
                                                                                    
    }
}
