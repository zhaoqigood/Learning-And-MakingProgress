
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
// This is a Key-value server
// It can response to the Set, Get, Stats request from the clients 
public class Server {
        private HashMap<String,String> hashTable = new HashMap<String,String>();
        public Server() {
                ServerSocket serverSocket = null;
                DatagramSocket udpSocket = null;
                boolean listening = true;
                try {
                        InetAddress addr = InetAddress.getLocalHost();

                        // Get IP Address
                        byte[] ipAddr = addr.getAddress();

                        // Get hostname
                        String hostname = addr.getHostAddress();
                        // System.out.println("Server IP = " + hostname);
                } catch (UnknownHostException e) {}
             

                try {
                        Selector selector = Selector.open();
                        int ports[] = {10000,20000};
                        for(int port: ports){
                            ServerSocketChannel serverChannel = ServerSocketChannel.open();
                            serverChannel.configureBlocking(false);
                            serverChannel.socket().bind(new InetSocketAddress(port));
                            serverChannel.register(selector,SelectionKey.OP_ACCEPT);
                        }
                        while(listening){
                            selector.select();
							System.out.println("50");
                            Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
                            while(selectedKeys.hasNext()){
                                SelectionKey selectedKey = selectedKeys.next();
                                if(selectedKey.isAcceptable()){
                                    SocketChannel socketChannel = ((ServerSocketChannel) selectedKey.channel()).accept();
                                    socketChannel.configureBlocking(false);
									System.out.println("57");
									System.out.println(socketChannel.socket().getPort());
                                    switch(socketChannel.socket().getPort()){
                                        case 10000:
											System.out.println("10000");
                                            serverSocket = new ServerSocket(10000);
                                            Socket socket = serverSocket.accept();
                                            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                                            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                            System.out.println("Got connection from " + socket.getInetAddress());
                                            String operation = in.readLine();
                                            String key = in.readLine();
                                            String value = in.readLine();
                                            if(operation.equals("SET")){
                                                hashTable.put(key, value);
                                                out.println("succeed!");
                                            }
                                            else if(operation.equals("GET")){
                                                String value_Output = hashTable.get(key);
                                                out.println(value_Output+"   "+"succeed!");
                                            } 
                                            else if(operation.equals("STATS")){
                                                int size = hashTable.size();
                                                out.println(size + "    "+ "succeed!");
                                            }
                                            else{
                                            out.println("error!");
                                            }
                                            in.close();
                                            out.close();
                                            socket.close();
                                            break;
                                        case 20000:
											System.out.println("20000");
                                            udpSocket = new DatagramSocket(20000);
                                            byte[] buf = new byte[1100];
                                            DatagramPacket packet = new DatagramPacket(buf, buf.length);
                                            udpSocket.receive(packet);
                                            String msg = new String(packet.getData()).trim();
                                            String[] messages = msg.split("\n");
                                            InetAddress IP = packet.getAddress();
                                            int portNo = packet.getPort();
                                            String output;
                                            if(messages.length == 3){
                                                hashTable.put(messages[1], messages[2]);
                                                output = "succeed!";
                    
      
                                            }
                                            else if(messages.length == 2){
                                                output = hashTable.get(messages[1]);
                                            }
                                            else if(messages.length == 1){
                                                int size = hashTable.size();
                                                output = Integer.toString(size);
                                            }
                                            else{
                                                output = "error!";
                                            }
                                            DatagramPacket sendPacket = new DatagramPacket(output.getBytes(),output.getBytes().length,IP,portNo);
                                            udpSocket.send(sendPacket);
                                            udpSocket.close();
                                            break;
                                            
                                    }
                                
                                }else if (selectedKey.isReadable()){
                                    
                                }
                            }
                        }
                } catch (IOException e) {
                        System.err.println("Could not listen on port: 10000 or 20000.");
                        System.exit(-1);
                }
                System.out.println("Waiting for connections on port 10000 or 20000");


        }

        public static void main(String[] args) {
                new Server();
        }

}
