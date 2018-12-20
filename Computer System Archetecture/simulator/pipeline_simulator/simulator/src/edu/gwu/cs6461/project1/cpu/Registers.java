package edu.gwu.cs6461.project1.cpu;

public interface Registers {
    /**
     •	4 General Purpose Registers (GPRs) – each 16 bits in length
     •	3 Index Registers – 16 bits in length
     •	16-bit words
     •	Memory of 2048 words, expandable to 4096 words
     •	Word addressable

     The CPU has other registers:
     Mnemonic	Size	    Name
     PC	        12 bits	    Program Counter: address of next instruction to be executed.
     Note that 212 = 4096.
     CC	        4 bits	    Condition Code: set when arithmetic/logical operations are executed;
     it has four 1-bit elements: overflow, underflow, division by zero,
     equal-or-not. They may be referenced as cc(0), cc(1), cc(2), cc(3).
     Or by the names OVERFLOW, UNDERFLOW, DIVZERO, EQUALORNOT
     IR	        16 bits	    Instruction Register: holds the instruction to be executed
     MAR	    16 bits	    Memory Address Register: holds the address of the word to be
     fetched from memory
     MBR	    16 bits	    Memory Buffer Register: holds the word just fetched from or the
     word to be /last stored into memory
     MSR	    16 bits	    Machine Status Register: certain bits record the status of the
     health of the machine
     MFR	    4 bits	    Machine Fault Register: contains the ID code if a machine fault
     after it occurs
     X1…X3	    16 bits	    Index Register: contains a base address that supports base register
     addressing of memory.
     CR          16 bits    clock register
     */
    void initialize();

    short getGPR(short index);

    short setGPR(short index, short value);

    short getX(short index);

    short setX(short index, short value );

    short getIR();

    short setIR(short value);

    short getPC(); //12bits

    short setPC(short value); //12bits
    short PCadder();

    void setCC(short value,int index);
    void setCC(short value);

    short getCC(int index);

    short getMAR();

    short setMAR(short value);

    short getMBR();

    short setMBR(short value);

    short getMSR();

    short setMSR(short value);

    void setMFR(short value, int index);
    void setMFR(short value);
    short getMFR(int index);
    short getMFR();
    // Operation buffer register
    void setStop(boolean stop);
    boolean getStop();
    void setFR(int index, short value);
    short getFR(int index);
    short getCR();
    void setCR(short CR);
}
