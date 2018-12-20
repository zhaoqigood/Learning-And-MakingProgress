package edu.gwu.cs6461.project1.cpu.decode;

import edu.gwu.cs6461.project1.cpu.Instruction;
import edu.gwu.cs6461.project1.cpu.InstructionType;
import edu.gwu.cs6461.project1.cpu.Registers;
import edu.gwu.cs6461.project1.cpu.RegistersImpl;
import edu.gwu.cs6461.project1.memory.Memory;
import edu.gwu.cs6461.project1.memory.MemoryImpl;
import edu.gwu.cs6461.project1.gui.Window;
import edu.gwu.cs6461.project1.cpu.change;
public class DecodeImpl implements Decode {

    static DecodeImpl _instance = null;
    Registers registers;
    private DecodeImpl() {
        registers = RegistersImpl.getInstance();
        initialize();
    }
    static public DecodeImpl getInstance(){
        if(_instance == null){
            _instance = new DecodeImpl();
        }
        return _instance;
    }

    void initialize() {
    }

    @Override
    public short decode(Instruction instruction) {
        Window window = Window.getInstance();
            Memory memory = MemoryImpl.getInstance();

        switch(instruction.getOpcode()){
            case InstructionType.LDR:
            case InstructionType.LDA:
            case InstructionType.JMA:
            case InstructionType.JSR:
                instruction.setValB(registers.getX(instruction.getIx()));
                instruction.setEA(instruction.getValB(),instruction.getAddress(),instruction.getI());
                if(instruction.getEA() < 6){
                    registers.setMFR((short) 1,0);
                }
                else if(instruction.getEA() > 4095){
                    registers.setMFR((short) 1, 3);
                    return 0;
                }
                break;
            case InstructionType.LDX:
            case InstructionType.STX:
                instruction.setValB(registers.getX(instruction.getIx()));
                instruction.setEA((short) 0,instruction.getAddress(),instruction.getI());
                if(instruction.getEA() < 6){
                    registers.setMFR((short) 1,0);
                }
                else if(instruction.getEA() > 4095){
                    registers.setMFR((short) 1, 3);
                    return 0;
                }
                break;
            case InstructionType.STR:
                instruction.setValA(registers.getGPR(instruction.getR()));
                instruction.setValB(registers.getX(instruction.getIx()));
                instruction.setEA(instruction.getValB(),instruction.getAddress(),instruction.getI());
                if(instruction.getEA() < 6){
                    registers.setMFR((short) 1,0);
                }
                else if(instruction.getEA() > 4095){
                    registers.setMFR((short) 1, 3);
                    return 0;
                }
                break;
            case InstructionType.JGE:
                instruction.setValA(registers.getGPR(instruction.getR()));
                instruction.setValB(registers.getX(instruction.getIx()));
                instruction.setEA(instruction.getValB(),instruction.getAddress(),instruction.getI());
                if(instruction.getEA() < 6){
                    registers.setMFR((short) 1,0);
                }
                else if(instruction.getEA() > 4095){
                    registers.setMFR((short) 1, 3);
                    return 0;
                }
                instruction.setOperand1((short)(instruction.getValA()+1));
                instruction.setOperand2((short) 32767);
                break;
            case InstructionType.JZ:
            case InstructionType.JNE:
                instruction.setValA(registers.getGPR(instruction.getR()));
                instruction.setValB(registers.getX(instruction.getIx()));
                instruction.setEA(instruction.getValB(),instruction.getAddress(),instruction.getI());
                if(instruction.getEA() < 6){
                    registers.setMFR((short) 1,0);
                }
                else if(instruction.getEA() > 4095){
                    registers.setMFR((short) 1, 3);
                    return 0;
                }
                instruction.setOperand1(instruction.getValA());
                instruction.setOperand2((short) 0);
                break;
            case InstructionType.JCC:
                instruction.setValA(registers.getCC(instruction.getR()));
                instruction.setValB(registers.getX(instruction.getIx()));
                instruction.setEA(instruction.getValB(),instruction.getAddress(),instruction.getI());
                if(instruction.getEA() < 6){
                    registers.setMFR((short) 1,0);
                }
                else if(instruction.getEA() > 4095){
                    registers.setMFR((short) 1, 3);
                    return 0;
                }
                instruction.setOperand1(instruction.getValA());
                instruction.setOperand2((short) 1);
                break;
            case InstructionType.RFS:
                instruction.setValA(registers.getGPR((short) 3));
                break;
            case InstructionType.SOB:
                instruction.setValA((short) (registers.getGPR(instruction.getR())-1));
                instruction.setValB(registers.getX(instruction.getIx()));
                instruction.setEA(instruction.getValB(),instruction.getAddress(),instruction.getI());
                if(instruction.getEA() < 6){
                    registers.setMFR((short) 1,0);
                }
                else if(instruction.getEA() > 4095){
                    registers.setMFR((short) 1, 3);
                    return 0;
                }
                instruction.setOperand1(instruction.getValA());
                instruction.setOperand2((short) 32767);
                break;
            case InstructionType.AMR:
            case InstructionType.SMR:
                instruction.setValA(registers.getGPR(instruction.getR()));
                instruction.setValB(registers.getX(instruction.getIx()));
                instruction.setEA(instruction.getValB(),instruction.getAddress(),instruction.getI());
                if(instruction.getEA() < 6){
                    registers.setMFR((short) 1,0);
                }
                else if(instruction.getEA() > 4095){
                    registers.setMFR((short) 1, 3);
                    return 0;
                }
                else {
                    instruction.setValM(memory.getMemory(instruction.getEA()));
                    instruction.setOperand2(instruction.getValM());
                }
                instruction.setOperand1(instruction.getValA());
                break;
            case InstructionType.AIR:
            case InstructionType.SIR:
                instruction.setValA(registers.getGPR(instruction.getR()));
                instruction.setOperand1(instruction.getValA());
                instruction.setOperand2(instruction.getImmed());
                break;
            case InstructionType.MLT:
            case InstructionType.DVD:
            case InstructionType.TRR:
            case InstructionType.AND:
            case InstructionType.ORR:
                instruction.setValA(registers.getGPR(instruction.getRx()));
                instruction.setValB(registers.getGPR(instruction.getRy()));
                instruction.setOperand1(instruction.getValA());
                instruction.setOperand2(instruction.getValB());
                break;
            case InstructionType.NOT:
                instruction.setValA(registers.getGPR(instruction.getRx()));
                instruction.setOperand1(instruction.getValA());
                break;
            case InstructionType.SRC:
            case InstructionType.RRC:
                instruction.setValA(registers.getGPR(instruction.getR()));
                instruction.setOperand1(instruction.getValA());
                break;
            case InstructionType.IN:
                String s = window.getInput();
                int length = s.length();
                if(length>0) {
                    char firstElement = s.charAt(0);
                    instruction.setInput((short) firstElement);
                    s = s.substring(1,length);
                }
                else{
                    char firstElement = '\0';
                    instruction.setInput((short)firstElement);
                }
                window.setInput(s);
                break;
            case InstructionType.OUT:
                short x = registers.getGPR(instruction.getR());
                instruction.setOutput(x);
                char y = (char)x;
                String out = window.getOutput();
                out = out + y;
                window.setOutput(out);
                break;
            case InstructionType.CHK:
                String in = window.getInput();
                in = "" + in;
                if(in.equals("")){
                    instruction.setStatus(0);
                }
                else{
                    instruction.setStatus(1);
                }
                break;
            case InstructionType.MOV:
                instruction.setValA(registers.getGPR(instruction.getR()));
                break;
            case InstructionType.FADD:
            case InstructionType.FSUB:
                instruction.setValA(registers.getFR(instruction.getR()));
                instruction.setValB(registers.getX(instruction.getIx()));
                instruction.setEA(instruction.getValB(),instruction.getAddress(),instruction.getI());
                instruction.setValM(memory.getMemory(instruction.getEA()));
                instruction.setOperand1(instruction.getValA());
                instruction.setOperand2(instruction.getValM());
                break;
            case InstructionType.CNVRT:
                instruction.setValA(registers.getGPR(instruction.getR()));
                instruction.setValB(registers.getX(instruction.getIx()));
                instruction.setEA(instruction.getValB(),instruction.getAddress(),instruction.getI());
                break;
            case InstructionType.LDFR:
                instruction.setValB(registers.getX(instruction.getIx()));
                instruction.setEA(instruction.getValB(),instruction.getAddress(),instruction.getI());
                break;
            case InstructionType.STFR:
                instruction.setValB(registers.getX(instruction.getIx()));
                instruction.setEA(instruction.getValB(),instruction.getAddress(),instruction.getI());
                instruction.setValA(registers.getFR(0));
                instruction.setValB(registers.getFR(1));
                break;
            case InstructionType.VADD:
            case InstructionType.VSUB:
                short length_binary = registers.getFR(instruction.getR());
                change float_short = new change();
                short length_value = (short)float_short.shortToFloat(length_binary);
                instruction.setValA(length_value);
                instruction.setValB(registers.getX(instruction.getIx()));
                instruction.setEA(instruction.getValB(),instruction.getAddress(),instruction.getI());
                instruction.setValM(memory.getMemory(instruction.getEA()));
                instruction.setValM1(memory.getMemory((short)(instruction.getEA()+1)));
                break;
            default :
                break;
        }

        return 0;
        }
}
