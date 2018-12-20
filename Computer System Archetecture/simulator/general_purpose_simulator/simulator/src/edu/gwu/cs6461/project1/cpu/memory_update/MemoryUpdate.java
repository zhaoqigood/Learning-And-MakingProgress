package edu.gwu.cs6461.project1.cpu.memory_update;

import edu.gwu.cs6461.project1.cpu.Instruction;

public interface MemoryUpdate {

    /**
     *
     * @param instruction
     * @return
     */
    short updateMemory(Instruction instruction);
}
