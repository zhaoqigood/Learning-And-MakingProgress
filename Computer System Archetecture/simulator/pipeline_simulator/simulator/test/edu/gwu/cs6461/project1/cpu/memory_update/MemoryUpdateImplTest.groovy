package edu.gwu.cs6461.project1.cpu.memory_update

import edu.gwu.cs6461.project1.cpu.Instruction
import edu.gwu.cs6461.project1.cpu.InstructionType
import edu.gwu.cs6461.project1.cpu.register_update.RegisterUpdate
import edu.gwu.cs6461.project1.cpu.register_update.RegisterUpdateImpl

class MemoryUpdateImplTest extends GroovyTestCase {
    void testUpdateMemory() {
        Instruction instruction = new Instruction();
        RegisterUpdate registerUpdate = RegisterUpdateImpl.getInstance();
        instruction.setOpcode(InstructionType.LDR);
        short result = registerUpdate.updateRegister(instruction);
        assertEquals(0, result);
    }
}
