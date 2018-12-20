package edu.gwu.cs6461.project1.cpu.execute;
import edu.gwu.cs6461.project1.cpu.Registers;
import edu.gwu.cs6461.project1.cpu.RegistersImpl;
import edu.gwu.cs6461.project1.cpu.Instruction;
import edu.gwu.cs6461.project1.cpu.Intermediate_Register2;
import edu.gwu.cs6461.project1.cpu.Intermediate_Register3;
public class SimpleNotImpl extends ExecuteBase{
    @Override
    public short execute(Intermediate_Register2 instruction) {
        Intermediate_Register3 intermediate_register3 = Intermediate_Register3.get_instance();
        Registers registers = RegistersImpl.getInstance();
        short operand1 = instruction.getOperand1();
        int valE = operand1 ^ 0xFFFF; // use "exclusive or" to simulate "not operation"
        intermediate_register3.setValE(valE);
        return 0;
    }
}
