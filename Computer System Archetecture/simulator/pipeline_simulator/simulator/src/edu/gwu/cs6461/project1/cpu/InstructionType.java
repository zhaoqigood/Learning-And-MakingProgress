package edu.gwu.cs6461.project1.cpu;

public class InstructionType {
    public static final short HLT =   0;
    public static final short TRAP =  30;
    public static final short LDR =   1;
    public static final short STR =   2;
    public static final short LDA =   3;
    public static final short LDX = 33;
    public static final short STX = 34;
    public static final short JZ =   8;
    public static final short JNE =  9;
    public static final short JCC =  10;
    public static final short JMA =  11;
    public static final short JSR =  12;
    public static final short RFS =  13;
    public static final short SOB =  14;
    public static final short JGE =  15;
    public static final short AMR =  4;
    public static final short SMR =  5;
    public static final short AIR =  6;
    public static final short SIR =  7;
    public static final short MLT =  16;
    public static final short DVD =  17;
    public static final short TRR =  18;
    public static final short AND =  19;
    public static final short ORR=   20;
    public static final short NOT =  21;
    public static final short SRC =  25;
    public static final short RRC =  26;
    public static final short IN =   49;
    public static final short OUT =  50;
    public static final short CHK =  51;
    public static final short MOV = 61;
    public static final short FADD = 27;
    public static final short FSUB = 28;
    public static final short VADD = 29;
    public static final short VSUB = 60;
    public static final short CNVRT = 31;
    public static final short LDFR = 40;
    public static final short STFR = 41;
}

