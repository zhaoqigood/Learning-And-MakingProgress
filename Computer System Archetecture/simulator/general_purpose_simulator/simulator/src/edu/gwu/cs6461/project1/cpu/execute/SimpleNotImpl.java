package edu.gwu.cs6461.project1.cpu.execute;
import edu.gwu.cs6461.project1.cpu.Registers;
import edu.gwu.cs6461.project1.cpu.RegistersImpl;
import edu.gwu.cs6461.project1.cpu.Instruction;
public class SimpleNotImpl extends ExecuteBase{
    @Override
    public short execute(Instruction instruction) {
        Registers registers = RegistersImpl.getInstance();
        short operand1 = instruction.getOperand1();
        int valE = operand1 ^ 0xFFFF; // use "exclusive or" to simulate "not operation"
        instruction.setValE(valE);
        return 0;
    }
}
