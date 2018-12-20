package edu.gwu.cs6461.project1.cpu.execute;

import edu.gwu.cs6461.project1.cpu.Instruction;
import edu.gwu.cs6461.project1.cpu.Intermediate_Register2;
import edu.gwu.cs6461.project1.cpu.Intermediate_Register3;
import edu.gwu.cs6461.project1.cpu.Registers;
import edu.gwu.cs6461.project1.cpu.RegistersImpl;
public class SimpleAddImpl extends ExecuteBase{
    @Override
    public short execute(Intermediate_Register2 instruction) {
        Intermediate_Register3 intermediate_register3 = Intermediate_Register3.get_instance();
        Registers registers = RegistersImpl.getInstance();
        short operand1 = instruction.getOperand1();
        short operand2 = instruction.getOperand2();
        int valE = operand1 + operand2;
        intermediate_register3.setValE(valE);
        if(valE > 32767){
            registers.setCC((short)1,0);
        }
        else{
            registers.setCC((short)0,0);
        }
        return 0;
    }
}
