package edu.gwu.cs6461.project1.cpu.execute;
import edu.gwu.cs6461.project1.cpu.Registers;
import edu.gwu.cs6461.project1.cpu.RegistersImpl;
import edu.gwu.cs6461.project1.cpu.Instruction;
public class SimpleRotateImpl extends ExecuteBase{
    @Override
    public short execute(Instruction instruction) {
        Registers registers = RegistersImpl.getInstance();
        short operand1 = instruction.getOperand1();
        short AL = instruction.getAL(); // arithmatic or logical shift
        short LR = instruction.getLR(); // left or right shift
        short count = instruction.getCount();
        int valE = 0;
        if(AL == 0){
            throw new IllegalArgumentException(); // only logical rotation is permitted
        }
        else {
            if (LR == 0) {
                int valE1 = (operand1 & 0xFFFF) >>> count; // get the right part of the new value
                int valE2 =  operand1 << (16-count); // get the left part of the new value
                valE = valE1 | valE2 ; // combine the left part and right part
            }
            else{
                int valE1 = operand1 << count; // get the left part of the new value
                int valE2 = (operand1 & 0xFFFF) >>> (16-count); // get the right part of the new value
                valE = valE1 | valE2;
            }
        }
        instruction.setValE(valE);
        return 0;
    }
}

