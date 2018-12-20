package edu.gwu.cs6461.project1.cpu.fetch;

import edu.gwu.cs6461.project1.cpu.Instruction;

public interface Fetch {

    /**
     * Read instruction from memory pointed by pc.
     * Split it into different sections.
     * Calculate PC new value.
     *
     * @param pc
     * @return
     */
    Instruction fetch();
}
