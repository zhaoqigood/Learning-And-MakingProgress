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
    }

    @Override
    public void initialize() {
        registers.initialize();
        //Todo: initialize the five components.
    }

    @Override
    public void run() {
        registers.setStop(false);
        while(!registers.getStop()){
            registers.setMFR((short) 0,0);
            registers.setMFR((short) 0,3);
            singleStepRun();
            System.out.println("1");
        }
    }

    @Override
    public void singleStepRun() {

            Instruction instruction = fetch.fetch();
            if(instruction.getOpcode() == InstructionType.TRAP){
                memory.setMemory((short) 2,instruction.getValP());
                registers.setPC((short) (memory.getMemory((short)0)+instruction.getTrapCode()));
                return;
            }
            decode.decode(instruction);
            // handle the memory exception
            if(registers.getMFR(0)==1){
                Window window = Window.getInstance();
                window.setOutput("Illegal Memory Address to Reserved Locations");
                memory.setMemory((short) 4,instruction.getValP());
                registers.setPC(memory.getMemory((short) 1));
                return;
            }
            else if(registers.getMFR(3)==1){
                Window window = Window.getInstance();
                window.setOutput("Illegal Memory Address beyond 2048");
                memory.setMemory((short) 4,instruction.getValP());
                registers.setPC(memory.getMemory((short) 1));
                return;
            }
            execute = ExecuteFactory.createExecutor(instruction);
            if(execute!=null) {
                execute.execute(instruction);
            }
            memoryUpdate.updateMemory(instruction);
            // check the memory exception and handle it
            registerUpdate.updateRegister(instruction);
            PCUpdate.pcUpdate(instruction);
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
