package edu.gwu.cs6461.project1.cpu.memory_update;

import edu.gwu.cs6461.project1.cpu.Instruction;
import edu.gwu.cs6461.project1.cpu.Intermediate_Register3;
public interface MemoryUpdate {

    /**
     *
     * @param instruction
     * @return
     */
    short updateMemory(Intermediate_Register3 instruction);
}
