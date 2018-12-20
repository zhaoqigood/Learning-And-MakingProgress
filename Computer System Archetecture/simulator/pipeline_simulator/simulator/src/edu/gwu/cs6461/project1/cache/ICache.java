package edu.gwu.cs6461.project1.cache;

import edu.gwu.cs6461.project1.memory.Memory;
import edu.gwu.cs6461.project1.memory.MemoryImpl;
import edu.gwu.cs6461.project1.cpu.Registers;
import edu.gwu.cs6461.project1.cpu.RegistersImpl;
import java.util.HashMap;
import java.util.Map;


public class ICache implements Cache {
    Memory memory;
    static ICache _instance;
    Registers registers = RegistersImpl.getInstance();
    int size;
    line head;
    line tail;
    // initialize the Cache
    private ICache(){
        memory = MemoryImpl.getInstance();
        size = 0;
        head = null;
        tail = null;
    }
    static public Cache getInstance(){
        if(_instance == null){
            _instance = new ICache();
        }
        return _instance;
    }
    private class line{
        short[] page = new short[8];
        short tag;
        line previous;
        line next;
    }
    private line getLine(short address){
        line Page = new line();
        int remainder = address % 8;
        short startAddress =(short)(address - remainder);
        Page.tag = startAddress;
        for(int i =0;i<8;i++){
            short address1 =(short)(startAddress + i);
            Page.page[i] = memory.getMemory(address1);
        }
        return Page;
    }
    private void setLine(line Page){
        short startAddress = Page.tag;
        for(int i = 0; i<8; i++){
            short address1 = (short)(startAddress + i);
            memory.setMemory(address1, Page.page[i]);
        }
    }
    private void addToCache(line page){
        if(head == null){
            head = page;
            tail = page;
            size++;
        }
        else{
            page.next = head;
            head.previous = page;
            head = page;
            size++;
            if(size>16){
                tail.previous.next = null;
                tail = tail.previous;
                size--;
            }
        }
    }
    public short getValue(short address){
        short Tag = (short)(address & 0xFFF8); // get the tag
        int offset = address & 0x7; // get the offset
        line current = head;
        boolean hit = false;
        while(current!=null){
            if(current.tag == Tag){
                hit = true;
                return current.page[offset];
            }
            current = current.next;
        }
        if(hit == false){
            line Page = getLine(address);
            addToCache(Page);
        }
        return getValue(address);
    }
    public void setValue(short address, short word){
        short Tag = (short)(address & 0xFFF8);
        int offset = address & 0x7;
        line current = head;
        boolean hit = false;
        while(current!=null){
            if(current.tag == Tag){
                hit = true;
                current.page[offset] = word;
                setLine(current);
            }
            current = current.next;
        }
        if(hit == false){
            memory.setMemory(address,word);
        }
    }
}

