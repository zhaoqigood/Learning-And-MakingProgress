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
public class ServerThread {
    private HashMap<String,String> hashTable = new HashMap<String,String>();
    public void begin(){
        TCP_Thread TCP_process = new TCP_Thread("tcp",hashTable);
        UDP_Thread UDP_process = new UDP_Thread("udp",hashTable);
        Thread tcp = new Thread(TCP_process);
        Thread udp = new Thread(UDP_process);
        tcp.start();
        udp.start();
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ServerThread server = new ServerThread();
        server.begin();
    }
    
}
