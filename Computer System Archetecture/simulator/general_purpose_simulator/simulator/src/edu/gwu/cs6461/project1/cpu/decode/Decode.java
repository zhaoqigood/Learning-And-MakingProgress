package edu.gwu.cs6461.project1.cpu.decode;

import edu.gwu.cs6461.project1.cpu.Instruction;

public interface Decode {
    /**
     * Decode instruction. Fetch operand from memory to valM.
     *
     * @param instruction
     * @return 0: succeed, 1: memory failure, 2: invalid instruction.
     */
    short decode(Instruction instruction);
}
