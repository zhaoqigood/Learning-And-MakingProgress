/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;
public class RandomizedQueue<Item> implements Iterable<Item>{
    private Item[] a=(Item[]) new Object[1];
    private int N=0;
    public RandomizedQueue(){
    }
    private void resize(int max){
        Item[] temp=(Item[]) new Object[max];
        for(int i=0;i<N;i++){
            temp[i]=a[i];
        }
        a=temp;
    }
    public boolean isEmpty(){
        return N==0;
    }
    public int size(){
        return N;
    }
    public void enqueue(Item item){
        if(item==null){
            throw new IllegalArgumentException();
        }
        if(N==a.length) resize(2*a.length);
        a[N++]=item;
    }
    public Item dequeue(){
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        int random=StdRandom.uniform(0,N);
        Item medium=a[random];
        a[random]=a[N-1];
        a[N-1]=medium;
        Item item=a[--N];
        a[N]=null;
        if(N>0&&N==a.length/4) resize(a.length/2);
        return item;
    }
    public Item sample(){
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        int random=StdRandom.uniform(0,N);
        Item item=a[random];
        return item;
    }
    public Iterator<Item> iterator(){
        return new listIterator<Item>();
    }
    private class listIterator<Item> implements Iterator<Item>{
        private int i=N;
        private Item[] b=(Item[])a;
        public boolean hasNext(){ return i>0;}
        public Item next(){
            if(i==0){
                throw new NoSuchElementException();
            }
            int random=StdRandom.uniform(0,i);
            Item medium=b[random];
            b[random]=b[--i];
            b[i]=null;
            return medium;
        }
        public void remove(){
            throw new UnsupportedOperationException();
        }
    }
    public static void main(String[] args){
        
    }
}
