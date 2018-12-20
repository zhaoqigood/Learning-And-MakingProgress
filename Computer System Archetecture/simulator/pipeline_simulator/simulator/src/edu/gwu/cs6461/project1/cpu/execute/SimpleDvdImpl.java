package edu.gwu.cs6461.project1.cpu.execute;
import edu.gwu.cs6461.project1.cpu.Intermediate_Register3;
import edu.gwu.cs6461.project1.cpu.Registers;
import edu.gwu.cs6461.project1.cpu.RegistersImpl;
import edu.gwu.cs6461.project1.cpu.Intermediate_Register2;
import edu.gwu.cs6461.project1.cpu.Intermediate_Register3;
public class SimpleDvdImpl extends ExecuteBase{
    @Override
    public short execute(Intermediate_Register2 instruction) {
        Intermediate_Register3 intermediate_register3 = Intermediate_Register3.get_instance();
        Registers registers = RegistersImpl.getInstance();
        short operand1 = instruction.getOperand1();
        short operand2 = instruction.getOperand2();
        if(operand2 == 0){
            registers.setCC((short)1,2);
        }
        int valE1 = operand1 / operand2; // get the quotient
        int valE2 = operand1 % operand2; // get the remainder
        valE1 = valE1 << 16; // represent the quotient with the former half of a int value
        valE2 = valE2 & 0xFFFF; // represent the remainder with the latter half of a int vale
        int valE = valE1 +valE2; // conbine the quotient and remainder to one value
        intermediate_register3.setValE(valE);
        return 0;
    }
}

