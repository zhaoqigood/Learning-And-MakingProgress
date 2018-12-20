package edu.gwu.cs6461.project1.cpu.decode;

import edu.gwu.cs6461.project1.cpu.Instruction;
import edu.gwu.cs6461.project1.cpu.InstructionType;
import edu.gwu.cs6461.project1.cpu.Registers;
import edu.gwu.cs6461.project1.cpu.RegistersImpl;
import edu.gwu.cs6461.project1.memory.Memory;
import edu.gwu.cs6461.project1.memory.MemoryImpl;
import edu.gwu.cs6461.project1.gui.Window;
import edu.gwu.cs6461.project1.cpu.Intermediate_Register2;
import edu.gwu.cs6461.project1.cpu.Intermediate_Register1;
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
    public short decode(Intermediate_Register1 instruction) {
        Intermediate_Register2 intermediate_register2 = Intermediate_Register2.get_instance();
        Window window = Window.getInstance();
            Memory memory = MemoryImpl.getInstance();

        switch(instruction.getOpcode()){
            case InstructionType.LDR:
            case InstructionType.LDA:
            case InstructionType.JMA:
            case InstructionType.JSR:
                intermediate_register2.setValB(registers.getX(instruction.getIx()));
                intermediate_register2.setEA(intermediate_register2.getValB(),instruction.getAddress(),instruction.getI());
                if(intermediate_register2.getEA() < 6){
                    registers.setMFR((short) 1,0);
                }
                else if(intermediate_register2.getEA() > 4095){
                    registers.setMFR((short) 1, 3);
                    return 0;
                }
                break;
            case InstructionType.LDX:
            case InstructionType.STX:
                intermediate_register2.setValB(registers.getX(instruction.getIx()));
                intermediate_register2.setEA((short) 0,instruction.getAddress(),instruction.getI());
                if(intermediate_register2.getEA() < 6){
                    registers.setMFR((short) 1,0);
                }
                else if(intermediate_register2.getEA() > 4095){
                    registers.setMFR((short) 1, 3);
                    return 0;
                }
                break;
            case InstructionType.STR:
                intermediate_register2.setValA(registers.getGPR(instruction.getR()));
                intermediate_register2.setValB(registers.getX(instruction.getIx()));
                intermediate_register2.setEA(intermediate_register2.getValB(),instruction.getAddress(),instruction.getI());
                if(intermediate_register2.getEA() < 6){
                    registers.setMFR((short) 1,0);
                }
                else if(intermediate_register2.getEA() > 4095){
                    registers.setMFR((short) 1, 3);
                    return 0;
                }
                break;
            case InstructionType.JGE:
                intermediate_register2.setValA(registers.getGPR(instruction.getR()));
                intermediate_register2.setValB(registers.getX(instruction.getIx()));
                intermediate_register2.setEA(intermediate_register2.getValB(),instruction.getAddress(),instruction.getI());
                if(intermediate_register2.getEA() < 6){
                    registers.setMFR((short) 1,0);
                }
                else if(intermediate_register2.getEA() > 4095){
                    registers.setMFR((short) 1, 3);
                    return 0;
                }
                intermediate_register2.setOperand1((short)(intermediate_register2.getValA()+1));
                intermediate_register2.setOperand2((short) 32767);
                break;
            case InstructionType.JZ:
            case InstructionType.JNE:
                intermediate_register2.setValA(registers.getGPR(instruction.getR()));
                intermediate_register2.setValB(registers.getX(instruction.getIx()));
                intermediate_register2.setEA(intermediate_register2.getValB(),instruction.getAddress(),instruction.getI());
                if(intermediate_register2.getEA() < 6){
                    registers.setMFR((short) 1,0);
                }
                else if(intermediate_register2.getEA() > 4095){
                    registers.setMFR((short) 1, 3);
                    return 0;
                }
                intermediate_register2.setOperand1(intermediate_register2.getValA());
                intermediate_register2.setOperand2((short) 0);
                break;
            case InstructionType.JCC:
                intermediate_register2.setValA(registers.getCC(instruction.getR()));
                intermediate_register2.setValB(registers.getX(instruction.getIx()));
                intermediate_register2.setEA(intermediate_register2.getValB(),instruction.getAddress(),instruction.getI());
                if(intermediate_register2.getEA() < 6){
                    registers.setMFR((short) 1,0);
                }
                else if(intermediate_register2.getEA() > 4095){
                    registers.setMFR((short) 1, 3);
                    return 0;
                }
                intermediate_register2.setOperand1(intermediate_register2.getValA());
                intermediate_register2.setOperand2((short) 1);
                break;
            case InstructionType.RFS:
                intermediate_register2.setValA(registers.getGPR((short) 3));
                break;
            case InstructionType.SOB:
                intermediate_register2.setValA((short) (registers.getGPR(instruction.getR())-1));
                intermediate_register2.setValB(registers.getX(instruction.getIx()));
                intermediate_register2.setEA(intermediate_register2.getValB(),instruction.getAddress(),instruction.getI());
                if(intermediate_register2.getEA() < 6){
                    registers.setMFR((short) 1,0);
                }
                else if(intermediate_register2.getEA() > 4095){
                    registers.setMFR((short) 1, 3);
                    return 0;
                }
                intermediate_register2.setOperand1(intermediate_register2.getValA());
                intermediate_register2.setOperand2((short) 32767);
                break;
            case InstructionType.AMR:
            case InstructionType.SMR:
                intermediate_register2.setValA(registers.getGPR(instruction.getR()));
                intermediate_register2.setValB(registers.getX(instruction.getIx()));
                intermediate_register2.setEA(intermediate_register2.getValB(),instruction.getAddress(),instruction.getI());
                if(intermediate_register2.getEA() < 6){
                    registers.setMFR((short) 1,0);
                }
                else if(intermediate_register2.getEA() > 4095){
                    registers.setMFR((short) 1, 3);
                    return 0;
                }
                else {
                    intermediate_register2.setValM(memory.getMemory(intermediate_register2.getEA()));
                    intermediate_register2.setOperand2(intermediate_register2.getValM());
                }
                intermediate_register2.setOperand1(intermediate_register2.getValA());
                break;
            case InstructionType.AIR:
            case InstructionType.SIR:
                intermediate_register2.setValA(registers.getGPR(instruction.getR()));
                intermediate_register2.setOperand1(intermediate_register2.getValA());
                intermediate_register2.setOperand2(instruction.getImmed());
                break;
            case InstructionType.MLT:
            case InstructionType.DVD:
            case InstructionType.TRR:
            case InstructionType.AND:
            case InstructionType.ORR:
                intermediate_register2.setValA(registers.getGPR(instruction.getRx()));
                intermediate_register2.setValB(registers.getGPR(instruction.getRy()));
                intermediate_register2.setOperand1(intermediate_register2.getValA());
                intermediate_register2.setOperand2(intermediate_register2.getValB());
                break;
            case InstructionType.NOT:
                intermediate_register2.setValA(registers.getGPR(instruction.getRx()));
                intermediate_register2.setOperand1(intermediate_register2.getValA());
                break;
            case InstructionType.SRC:
            case InstructionType.RRC:
                intermediate_register2.setValA(registers.getGPR(instruction.getR()));
                intermediate_register2.setOperand1(intermediate_register2.getValA());
                break;
            case InstructionType.IN:
                String s = window.getInput();
                int length = s.length();
                if(length>0) {
                    char firstElement = s.charAt(0);
                    intermediate_register2.setInput((short) firstElement);
                    s = s.substring(1,length);
                }
                else{
                    char firstElement = '\0';
                    intermediate_register2.setInput((short)firstElement);
                }
                window.setInput(s);
                break;
            case InstructionType.OUT:
                short x = registers.getGPR(instruction.getR());
                intermediate_register2.setOutput(x);
                char y = (char)x;
                String out = window.getOutput();
                out = out + y;
                window.setOutput(out);
                break;
            case InstructionType.CHK:
                String in = window.getInput();
                in = "" + in;
                if(in.equals("")){
                    intermediate_register2.setStatus(0);
                }
                else{
                    intermediate_register2.setStatus(1);
                }
                break;
            case InstructionType.MOV:
                intermediate_register2.setValA(registers.getGPR(instruction.getR()));
                break;
            case InstructionType.FADD:
            case InstructionType.FSUB:
                intermediate_register2.setValA(registers.getFR(instruction.getR()));
                intermediate_register2.setValB(registers.getX(instruction.getIx()));
                intermediate_register2.setEA(intermediate_register2.getValB(),instruction.getAddress(),instruction.getI());
                intermediate_register2.setValM(memory.getMemory(intermediate_register2.getEA()));
                intermediate_register2.setOperand1(intermediate_register2.getValA());
                intermediate_register2.setOperand2(intermediate_register2.getValM());
                break;
            case InstructionType.CNVRT:
                intermediate_register2.setValA(registers.getGPR(instruction.getR()));
                intermediate_register2.setValB(registers.getX(instruction.getIx()));
                intermediate_register2.setEA(intermediate_register2.getValB(),instruction.getAddress(),instruction.getI());
                break;
            case InstructionType.LDFR:
                intermediate_register2.setValB(registers.getX(instruction.getIx()));
                intermediate_register2.setEA(intermediate_register2.getValB(),instruction.getAddress(),instruction.getI());
                break;
            case InstructionType.STFR:
                intermediate_register2.setValB(registers.getX(instruction.getIx()));
                intermediate_register2.setEA(intermediate_register2.getValB(),instruction.getAddress(),instruction.getI());
                intermediate_register2.setValA(registers.getFR(0));
                intermediate_register2.setValB(registers.getFR(1));
                break;
            case InstructionType.VADD:
            case InstructionType.VSUB:
                intermediate_register2.setValA(registers.getFR(instruction.getR()));
                intermediate_register2.setValB(registers.getX(instruction.getIx()));
                intermediate_register2.setEA(intermediate_register2.getValB(),instruction.getAddress(),instruction.getI());
                intermediate_register2.setValM(memory.getMemory(intermediate_register2.getEA()));
                intermediate_register2.setValM1(memory.getMemory((short)(intermediate_register2.getEA()+1)));
                break;
            default :
                break;
        }

        return 0;
        }
}
