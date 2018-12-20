package edu.gwu.cs6461.project1.simulator;

import edu.gwu.cs6461.project1.cpu.CPU;
import edu.gwu.cs6461.project1.cpu.CPUImpl;
import edu.gwu.cs6461.project1.cpu.Registers;
import edu.gwu.cs6461.project1.memory.Memory;
import edu.gwu.cs6461.project1.memory.MemoryImpl;

public class Simulator{

    CPU cpu;
    Memory memory;

    public Simulator() {
        cpu = new CPUImpl();
        memory = MemoryImpl.getInstance();
    }

    /**
     * Initialize CPU and it's registers, memory.
     */
    public void initialize(){
        cpu.initialize();
        memory.initialize();
    }

    /**
     * Run from position of PC to HLT instruction.
     * Stop when exception occurs.
     */
    public void run(){
        cpu.run();
    }

    /**
     * Run instruction pointed by PC and stop.
     */
    public void runSingleStep(){
        cpu.singleStepRun();
    }

    /**
     * Return the CPU instance.
     * Refers to CPU interface for operation supported.
     */
    Memory getMemory(){
        return memory;
    }

    /**
     *
     * @return registers of the cpu
     */
    Registers getRegisters() {
        return cpu.getRegisters();
    }
}
