package edu.gwu.cs6461.project1.cpu.execute;

import edu.gwu.cs6461.project1.cpu.Instruction;

public interface Execute {

    /**
     * Execute instruction and save result to valE and set CC
     * @param instruction
     * @return
     */
    short execute(Instruction instruction);

}
