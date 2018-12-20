/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virtual_chat_room;
import java.util.Queue;
import java.net.*;
import java.io.*;
import javax.swing.JTextField;
import java.util.LinkedList;
/**
 *
 * @author zhaoq
 */
public class BackEnd {
    private String NickName;
    private String IP_Address1;
    private String IP_Address2;
    private static volatile LinkedList<String> server_buffer = new LinkedList<String>();
    private static volatile LinkedList<String> client_buffer = new LinkedList<String>();
    private static volatile Socket client1_socket;
    private static volatile Socket client2_socket;
    private static volatile ServerSocket server_socket;
    private static volatile DataInputStream client1_input;
    private static volatile DataInputStream client2_input;
    private static volatile DataOutputStream client1_output;
    private static volatile DataOutputStream client2_output;
    private int flag; // 0 represents the clients, 1 represent the initializer 
    private boolean connected;
    private static BackEnd instance = null;
    private BackEnd(){
        
    }
    public static BackEnd getInstance(){
        if(instance == null){
            instance = new BackEnd();
        }
        return instance;
    }
    // this is some basic functions
    // In the process of the initialization of the chatPage,
    // these functions be ran 
    
    // this function is for set connection with the other hosts
    public void startConnection(){
        // for two waiter
        if(flag == 0){
            try{
                server_socket = new ServerSocket(60000);
                System.out.println("wait to be connected");
                client1_socket = server_socket.accept();
                System.out.println("Connected!");
                client1_input = new DataInputStream(new BufferedInputStream(client1_socket.getInputStream()));
                client1_output = new DataOutputStream(client1_socket.getOutputStream());  
                connected = true;
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        // for the initializer
        else{
            try{
                System.out.println("Start to build the connection:");
                client1_socket = new Socket(IP_Address1,60000);
                System.out.println("connectedTo1!");
                client1_input = new DataInputStream(new BufferedInputStream(client1_socket.getInputStream()));
                client1_output = new DataOutputStream(client1_socket.getOutputStream());    
                client2_socket = new Socket(IP_Address2,60000);
                System.out.println("connectedTo2!");
                client2_input = new DataInputStream(new BufferedInputStream(client2_socket.getInputStream()));
                client2_output = new DataOutputStream(client2_socket.getOutputStream());
                connected = true;
            }
            catch(UnknownHostException u){
                
            }
            catch(IOException e){
                
            }
        }
    }
    // this function is for sending the nick name to other hosts and update their nick name field
    public void sendNickName(JTextField name1,JTextField name2,JTextField name3){
        if(connected){
            if(flag == 0){
                try{
                    String sendMessage = "name" + NickName;
                    client1_output.writeUTF(sendMessage);
                    String names = null;
                    while(names == null){
                        String line1 = client1_input.readUTF();
                        if(line1.substring(0,5).equals("names")){
                            names = line1.substring(5,line1.length());
                        }
                    }
                    String[] NickNames = names.split(",");
                    name1.setText(NickNames[0]);
                    name2.setText(NickNames[1]);
                    name3.setText(NickNames[2]);
                }
                catch(IOException e){
                    
                }
            }
            else{
                String[] NickNames = new String[3];
                NickNames[1] = NickName;
                try{
                    while(NickNames[0] == null){
                        String line1 = client1_input.readUTF();
                        if(line1.substring(0,4).equals("name")){
                            NickNames[0] = line1.substring(4,line1.length());
                        }
                    }
                    while(NickNames[2] == null){
                        String line2 = client2_input.readUTF();
                        if(line2.substring(0,4).equals("name")){
                            NickNames[2] = line2.substring(4,line2.length());
                        }
                    }
                    String names = "names" + NickNames[0];
                    for(int i = 1;i<3;i++){
                        names = names + "," + NickNames[i]; 
                    }
                    client1_output.writeUTF(names);
                    client2_output.writeUTF(names);
                    }
                catch(IOException e){                 
                }
                name1.setText(NickNames[0]);
                name2.setText(NickNames[1]);
                name3.setText(NickNames[2]);
            }
        }
    }
    // this function is for adding message to the buffer collecting the message and 
    // distributing the message
    public void addTo_ServerBuffer(){
        Threads thread1 = new Threads(client1_input,server_buffer,client1_output,client1_socket);
        thread1.start();
        Threads thread2 = new Threads(client2_input,server_buffer,client2_output,client2_socket);
        thread2.start();
    }
    // this function is for distributing the message
    public void message_Distribute(JTextField name1, JTextField name3){
        Threads thread3 = new Threads(client1_output,client2_output,server_buffer,client_buffer,name1,name3); 
        thread3.start();
    }
    // this function is for adding the message to the client buffer.
    public void addTo_ClientBuffer(JTextField name1,JTextField name3){
        Threads thread4 = new Threads(client1_input,client_buffer,client1_socket,name1,name3);
        thread4.start();
    }
    // this function is for reading message from the client buffer and update the text area
    // this function is for sending message to other hosts
    public void update_TextArea(javax.swing.JTextArea textArea){
        Threads thread5 = new Threads(textArea,client_buffer);
        thread5.start();
    }
    public void connectedStatus(JTextField name1,JTextField name2,JTextField name3){
        if(flag == 1){
            Threads thread6 = new Threads(client1_socket,client2_socket,client1_output,client2_output,name1,name3);
            thread6.start();
        }
        else{
            Thread thread7 = new Threads(client1_socket,name1,name2,name3);
            thread7.start();
        }
    }
    public void sendMessage(String message,JTextField name1, JTextField name2, JTextField name3){
        if(flag == 0){
            try{
                client1_output.writeUTF(message);
            }
            catch(IOException e){
                name1.setText("");
                name2.setText("");
                name3.setText("");
            }
        }
        else{
            System.out.println("send the message 0");
            server_buffer.add(message);
        }
    }
    // this function is for quiting the chat room
    public void quit(){
        String message = "over";
        if(flag == 0){
            try{
                client1_output.writeUTF(message);
            }
            catch(IOException e){
                
            }
            System.exit(0);
        }
        else{
            try{
                client1_output.writeUTF(message);
            }
            catch(IOException e){
                
            }
            try{
                client2_output.writeUTF(message);
            }
            catch(IOException e){
            }
            System.exit(0);
        }
        
    }
    // ******************************************************************
    // the following function is not the basic functions. But they are still very useful
    public void setNickName(String name){
        NickName = name;
    }
    public void setIPAddress1(String IP){
        IP_Address1 = IP;
    }
    public void setIPAddress2(String IP){
        IP_Address2 = IP;
    }
    public String getNickName(){
        return NickName;
    }
    public String getIPAddress1(){
        return IP_Address1;
    }
    public String getIPAddress2(){
        return IP_Address2;
    }
    public void setFlag(int flag){
        this.flag = flag;
    }
    public int getFlag(){
        return flag;
    }
    // this function is for checking the connection status of these users
    public void startRun(JTextField name1,JTextField name2,JTextField name3,javax.swing.JTextArea textArea){
        startConnection();
        sendNickName(name1,name2,name3);
        if(flag == 1){
            addTo_ServerBuffer();
            message_Distribute(name1,name3);
        }
        if(flag == 0){
            addTo_ClientBuffer(name1,name3);
        }
        update_TextArea(textArea);
        connectedStatus(name1,name2,name3);
    }
    
}
