package edu.gwu.cs6461.project1.cpu.fetch;

import edu.gwu.cs6461.project1.cpu.Instruction;
import edu.gwu.cs6461.project1.cpu.InstructionType;
import edu.gwu.cs6461.project1.cpu.Registers;
import edu.gwu.cs6461.project1.cpu.RegistersImpl;
import edu.gwu.cs6461.project1.memory.Memory;
import edu.gwu.cs6461.project1.memory.MemoryImpl;
import edu.gwu.cs6461.project1.cache.Cache;
import edu.gwu.cs6461.project1.cache.DCache;
import edu.gwu.cs6461.project1.cache.ICache;
import edu.gwu.cs6461.project1.memory.MemoryImpl;
import edu.gwu.cs6461.project1.cpu.CacheHandler;
public class FetchImpl implements Fetch {
    private Registers registers = RegistersImpl.getInstance();
    static FetchImpl _instance;

    private FetchImpl() {
        initialize();
    }

    static public FetchImpl getInstance(){
        if(_instance == null){
            _instance = new FetchImpl();
        }
        return _instance;
    }

    void initialize() {

    }

    @Override
    public Instruction fetch() {

        Registers registers= RegistersImpl.getInstance();
        Cache iCache = ICache.getInstance();
        CacheHandler cacheHandle = CacheHandler.getInstance();
        Instruction instruction = new Instruction();
        registers.setMAR(registers.getPC()); // set the MAR with the value of PC
        cacheHandle.iCacheRead(); // use the handler to copy the value from cache to MBR
        registers.setIR(registers.getMBR()); // set the IR with the instructions stored in MBR
        short code = registers.getIR();
        instruction.setInstruct_code(code);// get the instructions from the IR
        registers.setX((short)0, (short)0);
        splitInstruction(instruction);// split the instructions and get the value required by the next step
        short valP= (short) (registers.getPC() + 1);
        if(instruction.getOpcode() == InstructionType.HLT){
            valP = registers.getPC();
        }
        instruction.setValP(valP);
        registers.setPC(valP);
        return instruction;
    }

    private void splitInstruction(Instruction instruction){
        short sourceInstr=instruction.getInstruct_code();
        short opcode;
        String maskStr;
        int maskInt;
        opcode=(short)((sourceInstr>>10) & 0x3f);
        registers.setMFR((short) 0,2);
        switch(opcode){
            case InstructionType.LDR:
            case InstructionType.STR:
            case InstructionType.LDA:
            case InstructionType.LDX:
            case InstructionType.STX:
            case InstructionType.JZ:
            case InstructionType.JNE:
            case InstructionType.JCC:
            case InstructionType.JMA:
            case InstructionType.JSR:
            case InstructionType.SOB:
            case InstructionType.JGE:
            case InstructionType.AMR:
            case InstructionType.SMR://instruction be made up with R,Ix,I,Address
            case InstructionType.MOV:
            case InstructionType.FADD:
            case InstructionType.FSUB:
            case InstructionType.VADD:
            case InstructionType.VSUB:
            case InstructionType.CNVRT:
            case InstructionType.LDFR:
            case InstructionType.STFR:
                maskStr="0000001100000000";
                maskInt=Integer.parseUnsignedInt(maskStr,2);
                instruction.setR((short)((sourceInstr&maskInt)>>8));
                maskStr="0000000011000000";
                maskInt=Integer.parseUnsignedInt(maskStr,2);
                instruction.setIx((short)((sourceInstr&maskInt)>>6));
                maskStr="0000000000100000";
                maskInt=Integer.parseUnsignedInt(maskStr,2);
                instruction.setI((short)((sourceInstr&maskInt)>>5));
                maskStr="0000000000011111";
                maskInt=Integer.parseUnsignedInt(maskStr,2);
                instruction.setAddress((short)(sourceInstr&maskInt));
                break;
            case InstructionType.RFS://instruction only need immediate operand
                maskStr="0000000000011111";
                maskInt=Integer.parseUnsignedInt(maskStr,2);
                instruction.setImmed((short)(sourceInstr&maskInt));
                break;
            case InstructionType.AIR:
            case InstructionType.SIR://instruction be made up with R,Ix,I,Immediate
                maskStr="0000001100000000";
                maskInt=Integer.parseUnsignedInt(maskStr,2);
                instruction.setR((short)((sourceInstr&maskInt)>>8));
                maskStr="0000000011000000";
                maskInt=Integer.parseUnsignedInt(maskStr,2);
                instruction.setIx((short)((sourceInstr&maskInt)>>6));
                maskStr="0000000000100000";
                maskInt=Integer.parseUnsignedInt(maskStr,2);
                instruction.setI((short)((sourceInstr&maskInt)>>5));
                maskStr="0000000000011111";
                maskInt=Integer.parseUnsignedInt(maskStr,2);
                instruction.setImmed((short)(sourceInstr&maskInt));
                break;
            case InstructionType.MLT:
            case InstructionType.DVD:
            case InstructionType.TRR:
            case InstructionType.AND:
            case InstructionType.ORR://instruction be made up with Rx,Ry
                maskStr="0000001100000000";
                maskInt=Integer.parseUnsignedInt(maskStr,2);
                instruction.setRx((short)((sourceInstr&maskInt)>>8));
                maskStr="0000000011000000";
                maskInt=Integer.parseUnsignedInt(maskStr,2);
                instruction.setRy((short)((sourceInstr&maskInt)>>6));
                break;
            case InstructionType.NOT://instruction only need Rx
                maskStr="0000001100000000";
                maskInt=Integer.parseUnsignedInt(maskStr,2);
                instruction.setRx((short)((sourceInstr&maskInt)>>8));
                break;
            case InstructionType.SRC:
            case InstructionType.RRC://instruction be made up with R,AL,LR,Count
                maskStr="0000001100000000";
                maskInt=Integer.parseUnsignedInt(maskStr,2);
                instruction.setR((short)((sourceInstr&maskInt)>>8));
                maskStr="0000000010000000";
                maskInt=Integer.parseUnsignedInt(maskStr,2);
                instruction.setAL((short)((sourceInstr&maskInt)>>7));
                maskStr="0000000001000000";
                maskInt=Integer.parseUnsignedInt(maskStr,2);
                instruction.setLR((short)((sourceInstr&maskInt)>>6));
                maskStr="0000000000001111";
                maskInt=Integer.parseUnsignedInt(maskStr,2);
                instruction.setCount((short)(sourceInstr&maskInt));
                break;
            case InstructionType.IN:
            case InstructionType.OUT:
            case InstructionType.CHK://instruction be made up with R,DevID
                maskStr="0000001100000000";
                maskInt=Integer.parseUnsignedInt(maskStr,2);
                instruction.setR((short)((sourceInstr&maskInt)>>8));
                maskStr="0000000000011111";
                maskInt=Integer.parseUnsignedInt(maskStr,2);
                instruction.setDevID((short)(sourceInstr&maskInt));
                break;
            case InstructionType.HLT:
                break;
            case InstructionType.TRAP:
                maskStr="0000000000001111";
                maskInt=Integer.parseUnsignedInt(maskStr,2);
                instruction.setTrapCode((short)(sourceInstr&maskInt));
                break;
            default :
                // throw illegal instruction exception
                break;
        }

        //instruction.addValP;
        instruction.setOpcode(opcode);
    }
}