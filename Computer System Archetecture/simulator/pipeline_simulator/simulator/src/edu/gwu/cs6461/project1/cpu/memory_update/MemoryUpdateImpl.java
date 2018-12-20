package edu.gwu.cs6461.project1.cpu.memory_update;

import edu.gwu.cs6461.project1.cpu.*;
import edu.gwu.cs6461.project1.memory.Memory;
import edu.gwu.cs6461.project1.memory.MemoryImpl;
import edu.gwu.cs6461.project1.cpu.Intermediate_Register3;
import edu.gwu.cs6461.project1.cpu.Intermediate_Register4;
public class MemoryUpdateImpl implements MemoryUpdate{

    static MemoryUpdateImpl _instance;

    private MemoryUpdateImpl() {
        initialize();
    }

    static public MemoryUpdateImpl getInstance(){
        if(_instance == null){
            _instance = new MemoryUpdateImpl();
        }
        return _instance;
    }

    void initialize() {

    }

    @Override
    public short updateMemory(Intermediate_Register3 instruction) {
        CacheHandler memoryHandle = CacheHandler.getInstance();// declare the cacheHandler that has the same funtion with the memory handler
        Intermediate_Register4 intermediate_register4 = Intermediate_Register4.get_instance();
        Registers registers = RegistersImpl.getInstance();
        switch(instruction.getOpcode()) {
            case InstructionType.LDR:
            case InstructionType.LDX:
            case InstructionType.CNVRT:
                registers.setMAR(instruction.getEA()); // set the value of MAR as EA (effective address)
                memoryHandle.dCacheRead(); // set the value of MBR with the value corresponding to the location EA in memory
                short value1 = registers.getMBR();
                intermediate_register4.setValM(value1); // set the value of valM
                break;

            case InstructionType.STR:
                short value2 = instruction.getValA(); // get the value of valA
                registers.setMAR(instruction.getEA()); // set MAR with EA
                registers.setMBR(value2); // set MBR with valA
                memoryHandle.dCacheWrite();
                break;
            case InstructionType.STX:
                short value3 = instruction.getValB(); // get the value of valB
                registers.setMAR(instruction.getEA()); // set MAR with EA
                registers.setMBR(value3); // set MBR with valB
                memoryHandle.dCacheWrite();
                break;
            case InstructionType.LDFR:
                registers.setMAR(instruction.getEA()); // set the value of MAR as EA (effective address)
                memoryHandle.dCacheRead(); // set the value of MBR with the value corresponding to the location EA in memory
                short value4 = registers.getMBR();
                intermediate_register4.setValM(value4); // set the value of valM
                registers.setMAR((short)(instruction.getEA()+1)); // set the value of MAR as EA (effective address)
                memoryHandle.dCacheRead(); // set the value of MBR with the value corresponding to the location EA in memory
                short value5 = registers.getMBR();
                intermediate_register4.setValM1(value5); // set the value of valM
                break;
            case InstructionType.STFR:
                short value6 = instruction.getValA(); // get the value of valA
                short value7 = instruction.getValB();
                registers.setMAR(instruction.getEA()); // set MAR with EA
                registers.setMBR(value6); // set MBR with valA
                memoryHandle.dCacheWrite();
                registers.setMAR((short) (instruction.getEA()+1)); // set MAR with EA
                registers.setMBR(value7); // set MBR with valA
                memoryHandle.dCacheWrite();
                break;
            default:
                break;

        }
        return 0;
    }
}
