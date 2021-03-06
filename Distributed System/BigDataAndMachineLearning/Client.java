
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
public class Client {
    public static void main(String[] args) throws UnknownHostException,IOException {
        String host = args[0];
        int portnum = Integer.parseInt(args[1]);
        if(portnum == 10000){
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
            String operation = args[2];
            String key = "";
            String value = "";
            if(args.length >= 4){
                    key = args[3];
            }
            if(args.length >= 5){
                value = args[4];
            }
            String input = operation + "\n" + key + "\n" + value;
			out.println(input);
            String output = in.readLine();
            System.out.println(output);
        // Your code here!
        }
        else if(portnum == 20000){
            DatagramSocket socket = new DatagramSocket();
            String operation = args[2];
            String key = "";
            String value = "";
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
            socket.send(packet);
            byte[] receive = new byte[1100];
            DatagramPacket rec_Packet = new DatagramPacket(receive,receive.length);
            socket.receive(rec_Packet);
            String rec_String = new String(rec_Packet.getData());
            System.out.println(rec_String.trim());
        }
		else{
			}


    }
}
