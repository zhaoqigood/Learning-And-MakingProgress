package edu.gwu.cs6461.project1.cpu.execute;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import edu.gwu.cs6461.project1.cpu.Instruction;

import edu.gwu.cs6461.project1.cpu.InstructionType;
import edu.gwu.cs6461.project1.cpu.Intermediate_Register2;

public class ExecuteFactory {

    private interface Creator {
        Execute create();
    }

    private static final Map<Short, Creator> factoryMap =
            Collections.unmodifiableMap(new HashMap<Short,Creator>() {{
                put(InstructionType.JZ, new Creator() {
                            public Execute create() { return new SimpleSubImpl(); }});

                put(InstructionType.JNE, new Creator() {
                            public Execute create() { return new SimpleSubImpl(); }});

                put(InstructionType.JCC, new Creator() {
                            public Execute create() { return new SimpleSubImpl(); }});

                put(InstructionType.SOB, new Creator() {
                            public Execute create() { return new SimpleAddImpl(); }});

                put(InstructionType.JGE, new Creator() {
                            public Execute create() { return new SimpleAddImpl(); }});

                put(InstructionType.AMR, new Creator() {
                             public Execute create() { return new SimpleAddImpl(); }});

                put(InstructionType.SMR, new Creator() {
                             public Execute create() { return new SimpleSubImpl(); }});

                put(InstructionType.AIR, new Creator() {
                             public Execute create() { return new SimpleAddImpl(); }});

                put(InstructionType.SIR, new Creator() {
                             public Execute create() { return new SimpleSubImpl(); }});

                put(InstructionType.MLT, new Creator() {
                             public Execute create() { return new SimpleMulImpl(); }});

                put(InstructionType.DVD, new Creator() {
                             public Execute create() { return new SimpleDvdImpl(); }});

                put(InstructionType.TRR, new Creator() {
                             public Execute create() { return new SimpleSubImpl(); }});

                put(InstructionType.AND, new Creator() {
                             public Execute create() { return new SimpleAndImpl(); }});

                put(InstructionType.ORR, new Creator() {
                             public Execute create() { return new SimpleOrImpl(); }});

                put(InstructionType.NOT, new Creator() {
                             public Execute create() { return new SimpleNotImpl(); }});

                put(InstructionType.SRC, new Creator() {
                             public Execute create() { return new SimpleShiftImpl(); }});

                put(InstructionType.RRC, new Creator() {
                             public Execute create() { return new SimpleRotateImpl(); }});

                put(InstructionType.FADD, new Creator() {
                             public Execute create() { return new SimpleFloatAddImpl(); }});

                put(InstructionType.FSUB, new Creator() {
                             public Execute create() { return new SimpleFloatSubImpl(); }});

                put(InstructionType.VADD, new Creator() {
                             public Execute create() { return new SimpleVectorAddImpl(); }});

                put(InstructionType.VSUB, new Creator() {
                             public Execute create() { return new SimpleVectorSubImpl(); }});

            }});

    public static Execute createExecutor(Intermediate_Register2 instruction) {
        Creator factory = factoryMap.get(instruction.getOpcode());
        if (factory == null) {
            return null;
        }
        return factory.create();
    }
}



