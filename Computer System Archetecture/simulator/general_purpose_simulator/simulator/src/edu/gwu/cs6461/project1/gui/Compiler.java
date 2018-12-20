package edu.gwu.cs6461.project1.gui;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import edu.gwu.cs6461.project1.cpu.Registers;
import edu.gwu.cs6461.project1.cpu.RegistersImpl;
/**
 *
 * @author zhaoq
 */
import java.util.HashMap;
public class Compiler {
    private HashMap<String,Integer> map = new HashMap<>();
    // insert the instruction indentifiers to a hash table
    private Registers registers = RegistersImpl.getInstance();
    Compiler(){
        map.put("HLT",0);
        map.put("TRAP",30);
        map.put("LDR",1);
        map.put("STR",2);
        map.put("LDA",3);
        map.put("LDX",33);
        map.put("STX",34);
        map.put("JZ",8);
        map.put("JNE",9);
        map.put("JCC",10);
        map.put("JMA",11);
        map.put("JSR",12);
        map.put("RFS",13);
        map.put("SOB",14);
        map.put("JGE",15);
        map.put("AMR",4);
        map.put("SMR",5);
        map.put("AIR",6);
        map.put("SIR",7);
        map.put("MLT",16);
        map.put("DVD",17);
        map.put("TRR",18);
        map.put("AND",19);
        map.put("ORR",20);
        map.put("NOT",21);
        map.put("SRC",25);
        map.put("RRC",26);
        map.put("IN",49);
        map.put("OUT",50);
        map.put("CHK",51);
        map.put("FADD",27);
        map.put("FSUB",28);
        map.put("VADD",29);
        map.put("VSUB",60);
        map.put("CNVRT",31);
        map.put("LDFR",40);
        map.put("STFR",41);
        map.put("MOV",61);
    }
    // compile the instructions
    public String compile(String instruction){
        String binaryRepresentation=""; //store the return value
        //split the instructions to single-line instruction
        instruction = instruction.replace("\n", ""); //eliminate the "\n" in the instruction lines
        String[] instruction_lines = instruction.split(";");
        //compile each instruction and conbine the result
        for(String instruction_line: instruction_lines){
            // split the single-line instruction to multiple components
            String[] instruction_components = instruction_line.split(",");
            //extract the opcode and operand from the first component
            String[] firstComponent = instruction_components[0].split(" ");
            String[] firstPart = new String[2];
            int j = 0;
            for(String element:firstComponent){
                if(!element.equals("")){
                    firstPart[j++] = element;
                }
            }
            //select the coresponding methods to compile the single-line instruction
            int opCode = -1;
            if(map.containsKey(firstPart[0])) {
                opCode = map.get(firstPart[0]);
            }
            switch(opCode){
                //the number of various structures of instructions is 10
                case 0:
                    String binaryRe0 = "0000000000000000";
                    binaryRepresentation = binaryRepresentation + binaryRe0 + "\n";
                    break;
                case 60:
                case 27:
                case 28:
                case 29:
                case 40:
                case 41:
                case 31:
                case 1:
                case 2:
                case 3:
                case 8:
                case 9:
                case 10:
                case 14:
                case 15:
                case 4:
                case 5:
                    String binaryRe1;//store the binary representation of single-line instruction
                    String element11 = Integer.toBinaryString(opCode);;//binary representation of first element of the instruction
                    String element12 = Integer.toBinaryString(Integer.parseInt(firstPart[1]));
                    // eliminate all the " " in the string
                    instruction_components[1] = instruction_components[1].replaceAll("\\s", "");
                    String element13 = Integer.toBinaryString(Integer.parseInt(instruction_components[1]));
                    instruction_components[2] = instruction_components[2].replaceAll("\\s", "");
                    instruction_components[2] = instruction_components[2].replace("[", "");
                    String element14 = Integer.toBinaryString(Integer.parseInt(instruction_components[2]));
                    String element15 = "0";
                    if(instruction_components.length == 4){
                        element15 = "1";
                    }
                    // complement 0 to make each element's length equal regualted length
                    while(element11.length()<6){
                        element11 = "0" + element11;
                    }
                    while(element12.length()<2){
                        element12 = "0" + element12;
                    }
                    while(element13.length()<2){
                        element13 = "0" + element13;
                    }
                    while(element14.length()<5){
                        element14 = "0" + element14;
                    }
                    // combine the element to one line
                    binaryRe1 = element11 + element12 + element13 + element15 + element14;
                    binaryRepresentation = binaryRepresentation + binaryRe1 + "\n";
                    break;
                case 33:
                case 34:
                case 11:
                case 12:
                    String binaryRe2;//store the binary representation of single-line instruction
                    String element21 = Integer.toBinaryString(opCode);;//binary representation of first element of the instruction
                    String element22 = "00";
                    String element23 = Integer.toBinaryString(Integer.parseInt(firstPart[1]));
                    // eliminate all the " " in the string
                    instruction_components[1] = instruction_components[1].replaceAll("\\s", "");
                    instruction_components[1] = instruction_components[1].replace("[", "");
                    String element24 = Integer.toBinaryString(Integer.parseInt(instruction_components[1]));
                    String element25 = "0";
                    if(instruction_components.length == 3){
                        element25 = "1";
                    }
                    // complement 0 to make each element's length equal regualted length
                    while(element21.length()<6){
                        element21 = "0" + element21;
                    }
                    while(element22.length()<2){
                        element22 = "0" + element22;
                    }
                    while(element23.length()<2){
                        element23 = "0" + element23;
                    }
                    while(element24.length()<5){
                        element24 = "0" + element24;
                    }
                    // combine the element to one line
                    binaryRe2 = element21 + element22 + element23 + element25 + element24;
                    binaryRepresentation = binaryRepresentation + binaryRe2 + "\n";
                    break;
                case 13:
                    String binaryRe3;//store the binary representation of single-line instruction
                    String element31 = Integer.toBinaryString(opCode);;//binary representation of first element of the instruction
                    String element32 = "00000";
                    String element33 = Integer.toBinaryString(Integer.parseInt(firstPart[1]));
                    // complement 0 to make each element's length equal regualted length
                    while(element31.length()<6){
                        element31 = "0" + element31;
                    }
                    while(element33.length()<5){
                        element33 = "0" + element33;
                    }
                    binaryRe3 = element31 + element32 + element33;
                    binaryRepresentation = binaryRepresentation + binaryRe3 + "\n";
                    break;
                case 6:
                case 7:
                    String binaryRe4;//store the binary representation of single-line instruction
                    String element41 = Integer.toBinaryString(opCode);;//binary representation of first element of the instruction
                    String element42 = Integer.toBinaryString(Integer.parseInt(firstPart[1]));// binary representation of r
                    String element43 = "000";
                    instruction_components[1] = instruction_components[1].replaceAll("\\s", "");
                    String element44 = Integer.toBinaryString(Integer.parseInt(instruction_components[1])); // binary represnetation of Immed
                    // complement 0 to make each element's length equal regualted length
                    while(element41.length()<6){
                        element41 = "0" + element41;
                    }
                    while(element42.length()<2){
                        element42 = "0" + element42;
                    }
                    while(element44.length()<5){
                        element44 = "0" + element44;
                    }
                    binaryRe4 = element41 + element42 + element43 + element44;
                    binaryRepresentation = binaryRepresentation + binaryRe4 + "\n";
                    break;
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                    String binaryRe5;//store the binary representation of single-line instruction
                    String element51 = Integer.toBinaryString(opCode);;//binary representation of opCode
                    String element52 = Integer.toBinaryString(Integer.parseInt(firstPart[1]));// binary representation of rx
                    instruction_components[1] = instruction_components[1].replaceAll("\\s", "");
                    String element53 = Integer.toBinaryString(Integer.parseInt(instruction_components[1])); // binary represnetation of ry
                    String element54 = "000000"; // set the useless part as "000000"
                    // complement 0 to make each element's length equal regualted length
                    while(element51.length()<6){
                        element51 = "0" + element51;
                    }
                    while(element52.length()<2){
                        element52 = "0" + element52;
                    }
                    while(element53.length()<2){
                        element53 = "0" + element53;
                    }
                    binaryRe5 = element51 + element52 + element53 + element54;
                    binaryRepresentation = binaryRepresentation + binaryRe5 + "\n";
                    break;
                case 21:
                    String binaryRe6;//store the binary representation of single-line instruction
                    String element61 = Integer.toBinaryString(opCode);;//binary representation of opCode
                    String element62 = Integer.toBinaryString(Integer.parseInt(firstPart[1]));// binary representation of rx
                    String element63 = "00000000";
                    while(element61.length()<6){
                        element61 = "0" + element61;
                    }
                    while(element62.length()<2){
                        element62 = "0" + element62;
                    }
                    binaryRe6 = element61 + element62 + element63;
                    binaryRepresentation = binaryRepresentation + binaryRe6 + "\n";
                    break;
                case 25:
                case 26:
                    String binaryRe7;//store the binary representation of single-line instruction
                    String element71 = Integer.toBinaryString(opCode);;//binary representation of opCode
                    String element72 = Integer.toBinaryString(Integer.parseInt(firstPart[1]));// binary representation of r
                    instruction_components[1] = instruction_components[1].replaceAll("\\s", "");
                    String element76 = Integer.toBinaryString(Integer.parseInt(instruction_components[1])); // binary represnetation of count
                    instruction_components[2] = instruction_components[2].replaceAll("\\s", "");
                    String element74 = Integer.toBinaryString(Integer.parseInt(instruction_components[2])); // binary represnetation of L/R
                    instruction_components[3] = instruction_components[3].replaceAll("\\s", "");
                    String element73 = Integer.toBinaryString(Integer.parseInt(instruction_components[3])); // binary represnetation of A/L
                    String element75 = "00";
                    // complement 0 to make each element's length equal regualted length
                    while(element71.length()<6){
                        element71 = "0" + element71;
                    }
                    while(element72.length()<2){
                        element72 = "0" + element72;
                    }
                    while(element76.length()<4){
                        element76 = "0" + element76;
                    }
                    binaryRe7 = element71 + element72 + element73 + element74 + element75 + element76;
                    binaryRepresentation = binaryRepresentation + binaryRe7 + "\n";
                    break;
                case 49:
                case 50:
                case 51:
                    String binaryRe8;//store the binary representation of single-line instruction
                    String element81 = Integer.toBinaryString(opCode);;//binary representation of opCode
                    String element82 = Integer.toBinaryString(Integer.parseInt(firstPart[1]));// binary representation of r
                    String element83 = "000";
                    instruction_components[1] = instruction_components[1].replaceAll("\\s", "");
                    String element84 = Integer.toBinaryString(Integer.parseInt(instruction_components[1])); // binary represnetation of devid
                    while(element81.length()<6){
                        element81 = "0" + element81;
                    }
                    while(element82.length()<2){
                        element82 = "0" + element82;
                    }
                    while(element84.length()<5){
                        element84 = "0" + element84;
                    }
                    binaryRe8 = element81 + element82 + element83 + element84;
                    binaryRepresentation = binaryRepresentation + binaryRe8 + "\n";
                    break;
                case 61:
                    String binaryRe9;//store the binary representation of single-line instruction
                    String element91 = Integer.toBinaryString(opCode);;//binary representation of opCode
                    String element92 = Integer.toBinaryString(Integer.parseInt(firstPart[1]));// binary representation of r
                    instruction_components[1] = instruction_components[1].replaceAll("\\s", "");
                    String element93 = Integer.toBinaryString(Integer.parseInt(instruction_components[1])); // binary represnetation of x
                    String element94 = "000000"; // use 0 to complement this instruction
                    while(element91.length()<6){
                        element91 = "0" + element91;
                    }
                    while(element92.length()<2){
                        element92 = "0" + element92;
                    }
                    while(element93.length()<2){
                        element93 = "0" + element93;
                    }
                    binaryRe9 = element91 + element92 + element93 + element94;
                    binaryRepresentation = binaryRepresentation + binaryRe9 + "\n";
                    break;
                case 30:
                    String binaryRe10;
                    String element101 = Integer.toBinaryString(opCode);
                    if(Integer.parseInt(firstPart[1]) > 15){
                        binaryRepresentation = "Illegal TRAP code \n" + instruction_line;
                        return binaryRepresentation;
                    }
                    String element102 = Integer.toBinaryString(Integer.parseInt(firstPart[1]));
                    while(element101.length() < 6){
                        element101 = "0" + element101;
                    }
                    while(element102.length() < 4){
                        element102 = "0" + element102;
                    }
                    binaryRe10 = element101 + "000000" + element102;
                    binaryRepresentation = binaryRepresentation + binaryRe10 + "\n";
                    break;
                default:
                    // throw illegal instrction exception
                    binaryRepresentation = "Illegal Operation Code \n" + instruction_line;
                    return binaryRepresentation;
            }
        }
        return binaryRepresentation;

    }
}


