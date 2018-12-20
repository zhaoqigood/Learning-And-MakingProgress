package edu.gwu.cs6461.project1.cpu.execute;
import edu.gwu.cs6461.project1.cpu.Registers;
import edu.gwu.cs6461.project1.cpu.RegistersImpl;
import edu.gwu.cs6461.project1.cpu.Instruction;
import edu.gwu.cs6461.project1.cpu.Intermediate_Register2;
import edu.gwu.cs6461.project1.cpu.Intermediate_Register3;
public class SimpleShiftImpl extends ExecuteBase{
    @Override
    public short execute(Intermediate_Register2 instruction) {
        Intermediate_Register3 intermediate_register3 = Intermediate_Register3.get_instance();
        Registers registers = RegistersImpl.getInstance();
        short operand1 = instruction.getOperand1();
        short AL = instruction.getAL(); // arithmatic or logical shift
        short LR = instruction.getLR(); // left or right shift
        short count = instruction.getCount();
        int valE;
        if(LR == 1){
                valE =  operand1 << count;
            }
        else{
            if(AL == 1) {
                valE = (operand1 & 0xFFFF) >>> count;
            }
            else{
                valE = operand1 >> count;
            }
        }
        intermediate_register3.setValE(valE);
        return 0;
    }
}
