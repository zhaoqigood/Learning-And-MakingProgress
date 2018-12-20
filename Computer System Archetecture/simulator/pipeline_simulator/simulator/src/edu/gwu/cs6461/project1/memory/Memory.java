package edu.gwu.cs6461.project1.memory;

public interface Memory {

    /**
     * Fill the memory with all zeros.
     */
    void initialize();

    short getMemory(short address);
    void setMemory(short address, short value);


    short[] getMemory(short address, short length);
    void setMemory(short address, short[] value, short length);
}
