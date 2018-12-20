package edu.gwu.cs6461.project1.cpu.execute;
import edu.gwu.cs6461.project1.cpu.Instruction;
import edu.gwu.cs6461.project1.cpu.Registers;
import edu.gwu.cs6461.project1.cpu.RegistersImpl;
import edu.gwu.cs6461.project1.cpu.CacheHandler;
public class SimpleVectorAddImpl extends ExecuteBase {
    public short execute(Instruction instruction){
        CacheHandler cachehandle = CacheHandler.getInstance();
        Registers registers = RegistersImpl.getInstance();
        for(int i =0;i < instruction.getValA();i++) {
            registers.setMAR((short) (instruction.getValM()+i)); // set the value of MAR as EA (effective address)
            cachehandle.dCacheRead(); // set the value of MBR with the value corresponding to the location EA in memory
            short operand1 = registers.getMBR();
            registers.setMAR((short)(instruction.getValM1()+i));
            cachehandle.dCacheRead(); // set the value of MBR with the value corresponding to the location EA in memory
            short operand2 = registers.getMBR();
            short valE = (short) (operand1 + operand2);
            registers.setMAR((short) (instruction.getValM()+i)); // set the value of MAR as EA (effective address)
            registers.setMBR(valE);
            cachehandle.dCacheWrite(); // set the value of MBR with the value corresponding to the location EA in memory
        }
        return 0;
    }

}
