package edu.gwu.cs6461.project1.cpu;
import edu.gwu.cs6461.project1.cpu.Registers;
import edu.gwu.cs6461.project1.cpu.RegistersImpl;
import edu.gwu.cs6461.project1.cache.Cache;
import edu.gwu.cs6461.project1.cache.ICache;
import edu.gwu.cs6461.project1.cache.DCache;

public class CacheHandler {
    // to access to cache, we need icache,dcache, registers
    static CacheHandler _instance = null;
    Registers registers;
    Cache iCache;
    Cache dCache;
    private CacheHandler(){
        registers = RegistersImpl.getInstance();
        iCache = ICache.getInstance();
        dCache = DCache.getInstance();
    }
    static public CacheHandler getInstance(){
        if(_instance == null){
            _instance = new CacheHandler();
        }
        return _instance;
    }
    // there must be some value stored in the MAR before using this method
    public void iCacheRead(){
        short word = iCache.getValue(registers.getMAR()); // get the instruction locating in the corresponding position
        registers.setMBR(word);// set MBR
    }
    public void dCacheRead(){
        short word = dCache.getValue(registers.getMAR());// get the data locating in the corresponding position
        registers.setMBR(word); // set MBR
    }
    // there must be some value stored in the MAR and MBR before using this method
    public void iCacheWrite(){
        short address = registers.getMAR();
        short word = registers.getMBR();
        iCache.setValue(address,word);
    }

    public void dCacheWrite(){
        short address = registers.getMAR();
        short word = registers.getMBR();
        dCache.setValue(address,word);
    }
}
