package edu.gwu.cs6461.project1.cpu.execute

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import org.junit.Test;

import edu.gwu.cs6461.project1.cpu.Instruction

import static org.junit.Assert.assertThat


class ExecuteFactoryTest {
    void testCreateExecutor() {
    }

    @Test
    public void testConcatenate() {
        Instruction instruction = new Instruction();
        instruction.setOpcode((short)1);
        Execute execute = ExecuteFactory.createExecutor(instruction);

        short result = execute.execute(instruction);

        assertEquals(0, result);
    }
}
