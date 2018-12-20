package edu.gwu.cs6461.project1.cpu;

import edu.gwu.cs6461.project1.cpu.decode.Decode;
import edu.gwu.cs6461.project1.cpu.decode.DecodeImpl;
import edu.gwu.cs6461.project1.cpu.execute.Execute;
import edu.gwu.cs6461.project1.cpu.execute.ExecuteFactory;
import edu.gwu.cs6461.project1.cpu.fetch.Fetch;
import edu.gwu.cs6461.project1.cpu.fetch.FetchImpl;
import edu.gwu.cs6461.project1.cpu.memory_update.MemoryUpdate;
import edu.gwu.cs6461.project1.cpu.memory_update.MemoryUpdateImpl;
import edu.gwu.cs6461.project1.cpu.register_update.RegisterUpdate;
import edu.gwu.cs6461.project1.cpu.register_update.RegisterUpdateImpl;
import edu.gwu.cs6461.project1.memory.Memory;
import edu.gwu.cs6461.project1.memory.MemoryImpl;
import edu.gwu.cs6461.project1.cache.Cache;
import edu.gwu.cs6461.project1.cache.ICache;
import edu.gwu.cs6461.project1.cache.DCache;
import edu.gwu.cs6461.project1.cpu.pc_update.PC_Update;
import edu.gwu.cs6461.project1.cpu.pc_update.PC_UpdateImpl;
import edu.gwu.cs6461.project1.gui.Window;

public class CPUImpl implements CPU {
    Boolean isFault;
    Registers registers;
    Memory memory;
    Fetch fetch;
    Decode decode;
    Execute execute;
    MemoryUpdate memoryUpdate;
    RegisterUpdate registerUpdate;
    Cache iCache;
    Cache dCache;
    PC_Update PCUpdate;
    Intermediate_Register1   intermediate_register1;
    Intermediate_Register2   intermediate_register2;
    Intermediate_Register3   intermediate_register3;
    Intermediate_Register4   intermediate_register4;
    Intermediate_Register5   intermediate_register5;
    public CPUImpl() {
        isFault = false;
        registers = RegistersImpl.getInstance();
        memory = MemoryImpl.getInstance();
        fetch = FetchImpl.getInstance();
        decode = DecodeImpl.getInstance();
        memoryUpdate = MemoryUpdateImpl.getInstance();
        registerUpdate = RegisterUpdateImpl.getInstance();
        iCache = ICache.getInstance();
        dCache = DCache.getInstance();
        PCUpdate = PC_UpdateImpl.getInstance();
        intermediate_register1 = Intermediate_Register1.get_instance();
        intermediate_register2 = Intermediate_Register2.get_instance();
        intermediate_register3 = Intermediate_Register3.get_instance();
        intermediate_register4 = Intermediate_Register4.get_instance();
        intermediate_register5 = Intermediate_Register5.get_instance();
    }

    @Override
    public void initialize() {
        registers.initialize();
        //Todo: initialize the five components.
    }

    @Override
    public void run() {
        intermediate_register1.initialize();
        intermediate_register2.initialize();
        intermediate_register3.initialize();
        intermediate_register4.initialize();
        intermediate_register5.initialize();
        registers.setStop(false);
        while(!registers.getStop()){
            registers.setMFR((short) 0,0);
            registers.setMFR((short) 0,3);
            singleStepRun();
        }
    }

    @Override
    public void singleStepRun() {
            registers.setCR((short)(registers.getCR()+1));
            // PC update stage
            PCUpdate.pcUpdate(intermediate_register5);
            // register update stage
            intermediate_register5.transfer(intermediate_register4);
            registerUpdate.updateRegister(intermediate_register4);
            // memory update stage
            intermediate_register4.transfer(intermediate_register3);
            memoryUpdate.updateMemory(intermediate_register3);
            // execute stage
            intermediate_register3.transfer(intermediate_register2);
            execute = ExecuteFactory.createExecutor(intermediate_register2);
            if(execute!=null) {
                execute.execute(intermediate_register2);
            }
            // decode stage
            intermediate_register2.transfer(intermediate_register1);
            decode.decode(intermediate_register1);
            // handle the memory exception
            if(registers.getMFR(0)==1){
                Window window = Window.getInstance();
                window.setOutput("Illegal Memory Address to Reserved Locations");
                memory.setMemory((short) 4,intermediate_register2.getValP());
                registers.setPC(memory.getMemory((short) 1));
                return;
            }
            else if(registers.getMFR(3)==1){
                Window window = Window.getInstance();
                window.setOutput("Illegal Memory Address beyond 2048");
                memory.setMemory((short) 4,intermediate_register2.getValP());
                registers.setPC(memory.getMemory((short) 1));
                return;
            }
            // fetch stage
            Instruction instruction = fetch.fetch();
            intermediate_register1.transfer(instruction);
            if(intermediate_register1.getOpcode() == InstructionType.TRAP){
                memory.setMemory((short) 2,intermediate_register1.getValP());
                registers.setPC((short) (memory.getMemory((short)0)+intermediate_register1.getTrapCode()));
                return;
            }





    }
    @Override
    public Registers getRegisters() {
        return registers;
    }
    @Override
    public void setRegisters(Registers registers) {
        this.registers = registers;
    }


}
