package edu.gwu.cs6461.project1.cpu.register_update;

import edu.gwu.cs6461.project1.cpu.Instruction;

public interface RegisterUpdate {

    short updateRegister(Instruction instruction);
}
