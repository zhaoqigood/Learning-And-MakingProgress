package edu.gwu.cs6461.project1.cpu.register_update;

import edu.gwu.cs6461.project1.cpu.Instruction;
import edu.gwu.cs6461.project1.cpu.InstructionType;
import edu.gwu.cs6461.project1.cpu.Registers;
import edu.gwu.cs6461.project1.cpu.RegistersImpl;
import edu.gwu.cs6461.project1.cpu.memory_update.MemoryUpdate;
import edu.gwu.cs6461.project1.cpu.Intermediate_Register4;
import edu.gwu.cs6461.project1.cpu.Intermediate_Register5;
public class RegisterUpdateImpl implements RegisterUpdate{

    static RegisterUpdateImpl instance=null;

    private RegisterUpdateImpl() {
        initialize();
    }

    static public RegisterUpdateImpl getInstance(){
        if(instance == null){
            instance = new RegisterUpdateImpl();
        }
        return instance;
    }

    void initialize() {

    }
    @Override
    public short updateRegister(Intermediate_Register4 instruction) {
        Intermediate_Register5 intermediate_register5 = Intermediate_Register5.get_instance();
        short value;
        Registers registers = RegistersImpl.getInstance();
        switch(instruction.getOpcode()) {
            case InstructionType.LDR:
                registers.setGPR(instruction.getR(),instruction.getValM());
                break;
            case InstructionType.LDA:
                registers.setGPR(instruction.getR(),instruction.getEA());
                break;
            case InstructionType.LDX:
                registers.setX(instruction.getIx(),instruction.getValM());
                break;
            case InstructionType.JSR:
                registers.setGPR((short)3, instruction.getValP());
                break;
            case InstructionType.RFS:
                registers.setGPR((short)0, instruction.getImmed());
                break;
            case InstructionType.SOB:
                registers.setGPR(instruction.getR(), instruction.getValA());
                break;
            case InstructionType.AMR:
            case InstructionType.SMR:
            case InstructionType.AIR:
            case InstructionType.SIR:
                registers.setGPR(instruction.getR(),(short) instruction.getValE());
                break;
            case InstructionType.MLT:
                int valE = instruction.getValE();
                short valEhigh = (short) (valE>>>16);// get the high order bits of the result of multiplication
                short valElow =  (short) valE; // get the low order bits of the result of multiplication
                registers.setGPR(instruction.getRx(),valEhigh); // set the GPR[rx] with the high order bits
                registers.setGPR((short)(instruction.getRx()+1),valElow); // set the GPR[rx+1] with the low order bits
                break;
            case InstructionType.DVD:
                int valE1 = instruction.getValE();
                short valE1high = (short)(valE1 >>> 16);// get the quotient
                short valE1low  = (short)valE1; // get the remainder
                registers.setGPR(instruction.getRx(),valE1high); // GPR[rx] = quotient
                registers.setGPR((short)(instruction.getRx()+1),valE1low);// GPR[rx+1] = remainder
                break;
            case InstructionType.AND:
            case InstructionType.ORR:
            case InstructionType.NOT:
                registers.setGPR(instruction.getRx(),(short) instruction.getValE());// GPR[rx] = valE
                break;
            case InstructionType.SRC:
            case InstructionType.RRC:
                registers.setGPR(instruction.getR(),(short)instruction.getValE());
                break;
            case InstructionType.IN:
                registers.setGPR(instruction.getR(),instruction.getInput());
                break;
            case InstructionType.CHK:
                registers.setGPR(instruction.getR(),(short)instruction.getStatus());
                break;
            case InstructionType.MOV:
                registers.setX(instruction.getIx(),instruction.getValA());
                break;
            case InstructionType.FADD:
            case InstructionType.FSUB:
                registers.setFR(instruction.getR(),(short) instruction.getValE());
                break;
            case InstructionType.CNVRT:
                short F = instruction.getValA();
                if(F == 0){
                    registers.setGPR(instruction.getR(),instruction.getValM());
                }
                else if(F == 1){
                    registers.setFR(0,instruction.getValM());
                }
                break;
            case InstructionType.LDFR:
                registers.setFR(0,instruction.getValM());
                registers.setFR(1,instruction.getValM1());
                break;
            default:
                break;

        }
        return 0;
    }
}
