package edu.gwu.cs6461.project1.cpu.execute;
import edu.gwu.cs6461.project1.cpu.Registers;
import edu.gwu.cs6461.project1.cpu.RegistersImpl;
import edu.gwu.cs6461.project1.cpu.Instruction;
public class SimpleShiftImpl extends ExecuteBase{
    @Override
    public short execute(Instruction instruction) {
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
        instruction.setValE(valE);
        return 0;
    }
}
