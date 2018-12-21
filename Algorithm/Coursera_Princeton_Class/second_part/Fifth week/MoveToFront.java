/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
/**
 *
 * @author zhaoq
 */
public class MoveToFront {
    private static class Node{
        char val;
        Node former;
        Node next;
    }
    public static void encode(){
        // create the 256 alphabet
        int R = 256;
        Node root = new Node();
        root.val = (char)0;
        Node current = root;
        for(int i=1; i<R ; i++){
            Node letter = new Node();
            letter.val = (char)i;
            current.next = letter;
            letter.former = current;
            current = letter;
			
        }
        while(!BinaryStdIn.isEmpty()){
            char c = BinaryStdIn.readChar();
            current = root;
            int order = 0;
            while(current.val != c){
                order++;
                current = current.next;
            }
            BinaryStdOut.write(order,8);
			if(current.former != null){
                current.former.next = current.next;
			    if(current.next!=null){
                    current.next.former = current.former;
                }
				current.next = root;
                root.former = current;
                current.former = null;
                root = current;
			}
        }
        BinaryStdOut.close();
    }
    public static void decode(){
        int R = 256;
        Node root = new Node();
        root.val = (char)0;
        Node current = root;
        for(int i=1; i<R ; i++){
            Node letter = new Node();
            letter.val = (char)i;
            current.next = letter;
            letter.former = current;
            current = letter;
        }
        while(!BinaryStdIn.isEmpty()){
            int order = BinaryStdIn.readChar();
            current = root;
            while(order>0){
                current = current.next;
                order--;
            }
            char c = current.val;
            BinaryStdOut.write(c);
            if(current.former != null){
                current.former.next = current.next;
			    if(current.next!=null){
                    current.next.former = current.former;
                }
				current.next = root;
                root.former = current;
                current.former = null;
                root = current;
			}
        }
        BinaryStdOut.close();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        if(args[0].equals("-")) encode();
        if(args[0].equals("+")) decode();
    }
    
}