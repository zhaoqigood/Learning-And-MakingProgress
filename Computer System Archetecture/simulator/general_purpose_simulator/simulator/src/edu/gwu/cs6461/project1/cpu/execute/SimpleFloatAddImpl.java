package edu.gwu.cs6461.project1.cpu.execute;
import edu.gwu.cs6461.project1.cpu.Instruction;
import edu.gwu.cs6461.project1.cpu.Registers;
import edu.gwu.cs6461.project1.cpu.RegistersImpl;
import edu.gwu.cs6461.project1.cpu.change;
public class SimpleFloatAddImpl extends ExecuteBase {
    @Override
    public short execute(Instruction instruction) {
        Registers registers = RegistersImpl.getInstance();
        change short_float = new change();
        float operand1 = short_float.shortToFloat(instruction.getOperand1());
        float operand2 = short_float.shortToFloat(instruction.getOperand2());
        float result = operand1 + operand2;
        short valE = short_float.floatToShort(result);
        instruction.setValE(valE);
        return 0;
    }
}
