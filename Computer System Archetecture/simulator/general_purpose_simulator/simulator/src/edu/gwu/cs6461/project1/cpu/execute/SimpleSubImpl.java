package edu.gwu.cs6461.project1.cpu.execute;
import edu.gwu.cs6461.project1.cpu.Registers;
import edu.gwu.cs6461.project1.cpu.RegistersImpl;
import edu.gwu.cs6461.project1.cpu.Instruction;
public class SimpleSubImpl extends ExecuteBase{
    @Override
    public short execute(Instruction instruction) {
        Registers registers = RegistersImpl.getInstance();
        short operand1 = instruction.getOperand1();
        short operand2 = instruction.getOperand2();
        int valE = operand1 - operand2;
        instruction.setValE(valE);
        if(valE < -32768){
            registers.setCC((short)1,1);
        }
        else{
            registers.setCC((short)0,1);
        }
        if(valE == 0){
            registers.setCC((short)1,3);
        }
        else{
            registers.setCC((short)0,3);
        }
        return 0;
    }
}
