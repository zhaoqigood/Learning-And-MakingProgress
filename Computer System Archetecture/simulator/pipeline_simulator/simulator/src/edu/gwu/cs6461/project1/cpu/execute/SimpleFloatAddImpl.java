package edu.gwu.cs6461.project1.cpu.execute;
import edu.gwu.cs6461.project1.cpu.Instruction;
import edu.gwu.cs6461.project1.cpu.Registers;
import edu.gwu.cs6461.project1.cpu.RegistersImpl;
import edu.gwu.cs6461.project1.cpu.change;
import edu.gwu.cs6461.project1.cpu.Intermediate_Register2;
import edu.gwu.cs6461.project1.cpu.Intermediate_Register3;
public class SimpleFloatAddImpl extends ExecuteBase {
    @Override
    public short execute(Intermediate_Register2 instruction) {
        Intermediate_Register3 intermediate_register3 = Intermediate_Register3.get_instance();
        Registers registers = RegistersImpl.getInstance();
        change short_float = new change();
        float operand1 = short_float.shortToFloat(instruction.getOperand1());
        float operand2 = short_float.shortToFloat(instruction.getOperand2());
        float result = operand1 + operand2;
        short valE = short_float.floatToShort(result);
        intermediate_register3.setValE(valE);
        return 0;
    }
}
