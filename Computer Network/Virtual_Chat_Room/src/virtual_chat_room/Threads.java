/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virtual_chat_room;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.LinkedList;
import javax.swing.JTextField;
/**
 *
 * @author 
 */
public class Threads extends Thread{
    private DataInputStream in;
    private LinkedList<String> buffer;
    private DataOutputStream out1;
    private DataOutputStream out2;
    private LinkedList<String> client_buffer;
    private javax.swing.JTextArea textArea;
    private Socket socket;
    private Socket socket2;
    private ServerSocket server;
    private int flag;
    private JTextField name1;
    private JTextField name3;
    private JTextField name2;
    Threads(DataInputStream in, LinkedList<String> buffer,DataOutputStream out,Socket socket){
        this.in = in;
        this.buffer = buffer;
        this.out1 = out;
        this.socket = socket;
        flag = 0;
    }
    Threads(DataInputStream in, LinkedList<String> buffer,Socket socket,JTextField name1, JTextField name3){
        this.in = in;
        this.client_buffer = buffer;
        this.socket = socket;
        this.name1 = name1;
        this.name3 = name3;
        flag = 3;
    }
    Threads(DataOutputStream out1, DataOutputStream out2,LinkedList<String> buffer,LinkedList<String> client_buffer,JTextField name1, JTextField name3){
        this.out1 = out1;
        this.out2 = out2;
        this.buffer = buffer;
        this.client_buffer = client_buffer;
        this.name1 = name1;
        this.name3 = name3;
        flag = 1;
    }
    Threads(javax.swing.JTextArea textArea, LinkedList<String> client_buffer){
        this.textArea = textArea;
        this.client_buffer = client_buffer;
        flag = 2;
    }
    Threads(Socket socket1,Socket socket2,DataOutputStream out1, DataOutputStream out2,JTextField name1, JTextField name3){
        socket = socket1;
        this.socket2 = socket2;
        this.out1 = out1;
        this.out2 = out2;
        this.name1 = name1;
        this.name3 = name3;
        flag = 4;
    }
    Threads(Socket socket1,JTextField name1, JTextField name2,JTextField name3){
        socket = socket1;
        this.name1 = name1;
        this.name2 = name2;
        this.name3 = name3;
        flag = 5;
    }
    public void run(){
        if(flag == 0){
            String line = "";
                System.out.println("Server_Buffer Start to receive information from others...");
                while(!line.equals("over")){
                    try{
                        line = in.readUTF();
                        if(line != null && line.length() > 0){
                            if(!line.equals("over")){
                                buffer.add(line);
                            }
                        }
                        Thread.sleep(1000);
                    }
                    catch(InterruptedException i){
                    }
                    catch(IOException e){
                    }    
                }
                try{
                    in.close();
                    out1.close();
                    socket.close();
                }
                catch(IOException e){
                    
                }
        }
        else if(flag == 1){
            String line = "";
            System.out.println("Initializer start to distribute the information...");
            while(true){
                try{
                    line = buffer.poll();
                    if(line != null && line.length()> 0){
                        try{
                            out1.writeUTF(line);
                        }
                        catch(IOException e){
                            name1.setText("");
                            try{
                                out2.writeUTF("lost1");
                            }
                            catch(IOException m){
                                
                            }
                        }
                        try{
                            out2.writeUTF(line);
                        }
                        catch(IOException e){
                            name3.setText("");
                            try{
                                out1.writeUTF("lost3");
                            }
                            catch(IOException m){
                                
                            }
                        }
                        client_buffer.add(line);
                        System.out.println("send message finished");
                    }
                    Thread.sleep(1000);
                    }
                catch(InterruptedException i){
                        
                }
                }
        }
        else if(flag == 2){
            System.out.println("The text area start to be udpated...");
            String line = "";
            while(true){
                try{
                    line = client_buffer.poll();
                    if(line != null && line.length()> 0){
                        line = "\n" + line;
                        textArea.append(line);
                    }
                    Thread.sleep(1000);
                }
                catch(InterruptedException e){
                    
                }
            }
        }
        else if(flag == 3){
            System.out.println("Waiter start to receive information...");
            String line = "";
            while(!line.equals("over")){
                try{
                    line = in.readUTF();
                    if(line.substring(0,4).equals("lost")){
                        int mark = Integer.parseInt(line.substring(4,5));
                        if(mark == 1){
                            name1.setText("");
                        }
                        else if(mark == 3){
                            name3.setText("");
                        }
                        continue;
                    }
                    if(line != null && line.length() > 0){
                        client_buffer.add(line);
                    }
                    Thread.sleep(1000);
                    }
                catch(InterruptedException i){
                }
                catch(IOException e){
                }    
            }
                
            System.exit(0);
        }
        else if(flag == 4){
            boolean judge1 = true;
            boolean judge2 = true;
            while((!socket.isClosed()) || (!socket2.isClosed())){
                try{
                    if(socket.isClosed() && judge1){
                        if(!socket2.isClosed()){
                            name1.setText("");
                            out2.writeUTF("lost1");
                        }
                        judge1 = false;
                    }
                    if(socket2.isClosed()&& judge2){
                        if(!socket.isClosed()){
                            name3.setText("");
                            out1.writeUTF("lost3");
                        }
                        judge2 = false;
                    }
                    Thread.sleep(1000);
                }
                catch(InterruptedException i){
                    
                }
                catch(IOException e){
                    
                }
            }
            name1.setText("");
            name3.setText("");
        }
        else if(flag == 5){
            while(!socket.isClosed()){
                try{
                    Thread.sleep(1000);
                }
                catch(InterruptedException e){
                    
                }
            }
            System.exit(0);   
        }
        
    }
}
