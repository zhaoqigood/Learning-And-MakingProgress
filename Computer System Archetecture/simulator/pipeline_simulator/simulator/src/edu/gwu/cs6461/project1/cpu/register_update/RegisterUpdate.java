package edu.gwu.cs6461.project1.cpu.register_update;

import edu.gwu.cs6461.project1.cpu.Intermediate_Register4;

public interface RegisterUpdate {

    short updateRegister(Intermediate_Register4 instruction);
}
