/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Iterator;
import java.util.NoSuchElementException;
public class Deque<Item> implements Iterable<Item>{
    private Node first;
    private Node last;
    private int size;
    private class Node{
        Item item;
        Node formerNode;
        Node backNode;
    }
    public Deque(){
        first=null;
        last=null;
        size=0;
    }
    public boolean isEmpty(){
        return first==null;
    }
    public int size(){
        return size;
    }
    public void addFirst(Item item){
        if(item==null){
            throw new IllegalArgumentException();
        }
        if(isEmpty()){        
            first=new Node();
            first.item=item;
            first.backNode=null;
            first.formerNode=null;
            last=first;
        }
        else{
			Node oldfirst=first;
			first=new Node();
            first.item=item;
            first.backNode=oldfirst;
            first.formerNode=null;
            oldfirst.formerNode=first;
        }
		size++;
    }
    public void addLast(Item item){
        if(item==null){
            throw new IllegalArgumentException();
        }
        Node oldlast=last;
        last=new Node();
        last.item=item;
        if(isEmpty()){
            last.formerNode=null;
            last.backNode=null;
            first=last;
        }
        else{
            oldlast.backNode=last;
            last.formerNode=oldlast;
            last.backNode=null;
        }
		size++;
    }
    public Item removeFirst(){
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        Item item=first.item;
        first=first.backNode;
        if(isEmpty()){
            last=null;
        }
		size--;
        return item;
    }
    public Item removeLast(){
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        Item item=last.item;
        if(first.backNode==null){
            first=null;
            last=null;
        }
        else{
            last=last.formerNode;
            last.backNode=null;
        }
		size--;
        return item;
    }
    public Iterator<Item> iterator(){
        return new listIterator();
    }
    private class listIterator implements Iterator<Item>{
        private Node current=first;
        public boolean hasNext(){
            return current!=null;
        }
        public void remove(){
            throw new UnsupportedOperationException();
        }
        public Item next(){
            if(current==null){
                throw new NoSuchElementException();
            }
            Item item=current.item;
            current=current.backNode;
            return item;
        }
    }
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
