package edu.gwu.cs6461.project1.cpu.execute;

import edu.gwu.cs6461.project1.cpu.Instruction;
import edu.gwu.cs6461.project1.cpu.Intermediate_Register2;

public class ExecuteBase implements Execute{
    @Override
    public short execute(Intermediate_Register2 instruction) {
        return 0;
    }
}
