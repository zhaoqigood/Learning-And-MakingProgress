package edu.gwu.cs6461.project1.simulator

import edu.gwu.cs6461.project1.cpu.Instruction
import edu.gwu.cs6461.project1.cpu.InstructionType
import edu.gwu.cs6461.project1.cpu.Registers
import edu.gwu.cs6461.project1.cpu.RegistersImpl
import edu.gwu.cs6461.project1.memory.Memory
import edu.gwu.cs6461.project1.memory.MemoryImpl

class SimulatorTest extends GroovyTestCase {
    void testLDR() {
        Simulator simulator = new Simulator()
        Memory memory = MemoryImpl.getInstance()
        Registers registers = RegistersImpl.getInstance()

        simulator.initialize()
        Instruction temp = new Instruction()
        temp.opcode = InstructionType.LDR
        temp.r = 3
        temp.ix = 1
        temp.address = 5
        temp.i = 0
        registers.setX((short)1, (short)0x100)
        registers.setPC((short)0x10)
        memory.setMemory((short)0x10, (short)(generateInstruction(temp)))
        memory.setMemory((short)0x105, (short)0x5A)
        simulator.runSingleStep()
        assertEquals((short)0x5A, registers.getGPR((Short)3))
        assertEquals((short)0x105, registers.getMAR())
        assertEquals((short)0x5A, registers.getMBR())
        assertEquals(generateInstruction(temp), registers.getIR())

        temp.i = 1
        registers.setX((short)1, (short)0x100)
        registers.setPC((short)0x10)
        memory.setMemory((short)0x10, (short)(generateInstruction(temp)))
        memory.setMemory((short)0x105, (short)0x110)
        memory.setMemory((short)0x110, (short)0x5C)

        simulator.runSingleStep()
        assertEquals((short)0x5C, registers.getGPR((Short)3))
        assertEquals((short)0x110, registers.getMAR())
        assertEquals((short)0x5C, registers.getMBR())
        assertEquals(generateInstruction(temp), registers.getIR())

        temp.i = 1
        temp.ix = 0
        registers.setX((short)temp.i, (short)0x100)
        registers.setPC((short)0x10)
        memory.setMemory((short)0x10, (short)(generateInstruction(temp)))
        memory.setMemory((short)0x5, (short)0x110)
        memory.setMemory((short)0x110, (short)0x5C)

        simulator.runSingleStep()
        assertEquals((short)0x5C, registers.getGPR((Short)3))
        assertEquals((short)0x110, registers.getMAR())
        assertEquals((short)0x5C, registers.getMBR())
        assertEquals(generateInstruction(temp), registers.getIR())
    }

    void testSTR() {
        Simulator simulator = new Simulator()
        Memory memory = MemoryImpl.getInstance()
        Registers registers = RegistersImpl.getInstance()

        simulator.initialize()
        Instruction temp = new Instruction()
        temp.opcode = InstructionType.STR
        temp.r = 3
        temp.ix = 1
        temp.address = 5
        temp.i = 0
        registers.setX((short)1, (short)0x100)
        registers.setGPR(temp.r, (short)0x5A)
        registers.setPC((short)0x10)
        memory.setMemory((short)0x10, (short)(generateInstruction(temp)))
        simulator.runSingleStep()
        assertEquals((short)0x5A, memory.getMemory((short)0x105))
        assertEquals((short)0x105, registers.getMAR())
        assertEquals((short)0x5A, registers.getMBR())
        assertEquals(generateInstruction(temp), registers.getIR())

        temp.i = 1
        registers.setX((short)1, (short)0x100)
        registers.setPC((short)0x10)
        memory.setMemory((short)0x10, (short)(generateInstruction(temp)))
        memory.setMemory((short)0x105, (short)0x110)
        registers.setGPR((short)3, (short)0x5C)

        simulator.runSingleStep()
        assertEquals((short)0x5C, registers.getGPR((Short)3))
        assertEquals((short)0x110, registers.getMAR())
        assertEquals((short)0x5C, registers.getMBR())
        assertEquals(generateInstruction(temp), registers.getIR())
    }

    void testLDA() {
        Simulator simulator = new Simulator()
        Memory memory = MemoryImpl.getInstance()
        Registers registers = RegistersImpl.getInstance()

        simulator.initialize()
        Instruction temp = new Instruction()
        temp.opcode = InstructionType.LDA
        temp.r = 3
        temp.ix = 1
        temp.address = 0x15
        temp.i = 0
        registers.setX((short)1, (short)0x100)
        registers.setPC((short)0x10)
        memory.setMemory((short)0x10, (generateInstruction(temp)))
        simulator.runSingleStep()
        assertEquals((short)0x115, registers.getGPR((Short)3))
        assertEquals(generateInstruction(temp), registers.getIR())

        temp.i = 1
        registers.setX((short)1, (short)0x100)
        registers.setPC((short)0x10)
        memory.setMemory((short)0x10, (generateInstruction(temp)))
        memory.setMemory((short)0x115, (short)0x120)

        simulator.runSingleStep()
        assertEquals((short)0x120, registers.getGPR((Short)3))
        assertEquals(generateInstruction(temp), registers.getIR())
    }

    void testSTX() {
        Simulator simulator = new Simulator()
        Memory memory = MemoryImpl.getInstance()
        Registers registers = RegistersImpl.getInstance()

        simulator.initialize()
        Instruction temp = new Instruction()
        temp.opcode = InstructionType.STX
        temp.ix = 1
        temp.address = 0x15
        temp.i = 0
        registers.setPC((short)0x10)
        registers.setX(temp.ix, (short)0x30)
        memory.setMemory((short)0x10, (short)(generateInstruction(temp)))

        simulator.runSingleStep()
        assertEquals((short)0x30, memory.getMemory((short)0x45))
        assertEquals((short)0x45, registers.getMAR())
        assertEquals((short)0x30, registers.getMBR())
        assertEquals(generateInstruction(temp), registers.getIR())

        temp.i = 1
        registers.setPC((short)0x10)
        registers.setX(temp.ix, (short)0x30)
        memory.setMemory((short)0x10, (short)(generateInstruction(temp)))
        memory.setMemory((short)0x45, (short)0x20)

        simulator.runSingleStep()
        assertEquals((short)0x30, memory.getMemory((short)0x20))
        assertEquals((short)0x20, registers.getMAR())
        assertEquals((short)0x30, registers.getMBR())
        assertEquals(generateInstruction(temp), registers.getIR())
    }

    void testLDX() {
        Simulator simulator = new Simulator()
        Memory memory = MemoryImpl.getInstance()
        Registers registers = RegistersImpl.getInstance()

        simulator.initialize()
        Instruction temp = new Instruction()
        temp.opcode = InstructionType.LDX
        temp.ix = 1
        temp.address = 0x15
        temp.i = 0
        registers.setPC((short)0x10)
        registers.setX(temp.ix, (short)0x20)
        memory.setMemory((short)0x10, (short)(generateInstruction(temp)))
        memory.setMemory((short)0x35, (short)0x5B)
        simulator.runSingleStep()
        assertEquals((short)0x5B, registers.getX(temp.ix))
        assertEquals((short)0x35, registers.getMAR())
        assertEquals((short)0x5B, registers.getMBR())
        assertEquals(generateInstruction(temp), registers.getIR())

        temp.i = 1
        registers.setPC((short)0x10)
        registers.setX(temp.ix, (short)0x20)
        memory.setMemory((short)0x10, (short)(generateInstruction(temp)))
        memory.setMemory((short)0x35, (short)0x20)
        memory.setMemory((short)0x20, (short)0x5E)
        simulator.runSingleStep()
        assertEquals((short)0x5E, registers.getX(temp.ix))
        assertEquals((short)0x20, registers.getMAR())
        assertEquals((short)0x5E, registers.getMBR())
        assertEquals(generateInstruction(temp), registers.getIR())
    }

    Short generateInstruction(Instruction instruction){
        Short insWord = instruction.address
        insWord |= instruction.i << 5
        insWord |= instruction.ix << 6
        insWord |= instruction.r << 8
        insWord |= instruction.opcode << 10

        System.out.printf("Instruction: 0x%x\n", insWord)
        System.out.printf("icode: 0x%x\n", (short)(insWord >>> 10))
        return insWord
    }
}
