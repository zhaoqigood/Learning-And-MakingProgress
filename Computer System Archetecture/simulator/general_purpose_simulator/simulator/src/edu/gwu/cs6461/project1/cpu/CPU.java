package edu.gwu.cs6461.project1.cpu;


/**
 Reserved Locations:
 Memory Address 		Usage
 0			Reserved for the Trap instruction for Part III.
 1			Reserved for a machine fault (see below).
 2			Store PC for Trap
 3			Not Used
 4			Store PC for Machine Fault
 5			Not Used

 */

/**
 * Machine Fault (Part III):
 An erroneous condition in the machine will cause a machine fault. The machine traps to memory
 address 1, which contains the address of a routine to handle machine faults. Your simulator must
 check for faults.

 The possible machine faults that are predefined are:

 ID	Fault
 0	Illegal Memory Address to Reserved Locations
 1	Illegal TRAP code
 2	Illegal Operation Code
 3	Illegal Memory Address beyond 2048 (memory installed)

 When a Trap instruction or a machine fault occurs, the processor saves the current PC and MSR
 contents to the locations specified above, then fetches the address from Location 0 (Trap) or
 1 (Machine Fault) into the PC which becomes the next instruction to be executed. This address
 will be the first instruction of a routine which handles the trap or machine fault.
 */

/**
 OpCode8	Instruction	Description
 000	HLT	Stops the machine.
 036	TRAP code	Traps to memory address 0, which contains the address of a table in memory.
        Stores the PC+1 in memory location 2. The table can have a maximum of 16 entries
        representing 16 routines for user-specified instructions stored elsewhere in memory. Trap
        code contains an index into the table, e.g. it takes values 0 – 15. When a TRAP instruction
        is executed, it goes to the routine whose address is in memory location 0, executes those
        instructions, and returns to the instruction stored in memory location 2. The PC+1 of the
        TRAP instruction is stored in memory location 2.
 *
 */
public interface CPU {

    void initialize();

    void run();

    void singleStepRun();

    Registers getRegisters();

    void setRegisters(Registers registers);
}
