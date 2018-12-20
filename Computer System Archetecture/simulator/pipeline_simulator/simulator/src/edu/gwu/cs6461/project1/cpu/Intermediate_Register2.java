package edu.gwu.cs6461.project1.cpu;

import edu.gwu.cs6461.project1.memory.Memory;
import edu.gwu.cs6461.project1.memory.MemoryImpl;

public class Intermediate_Register2 {
    static Intermediate_Register2 _instance = null;
    Memory memory = MemoryImpl.getInstance();
    /**
     * Set in fetching phase by the fetcher
     */
    short instruct_code;
    short opcode;
    short valP;
    short valC;
    // For the load/store instructions, transfer instructions, add and subtract instructions
    short ix;
    short r;
    short i;
    short address;
    short immed;
    // For the multiplication,division,and logic instructions
    short rx;
    short ry;
    // For the shift and rotate instructions (include r);
    short al;
    short lr;
    short count;
    // For the I/O instructions (include r)
    short devID;
    short input;
    short output;
    int device_Status;
    /**
     * Set in decoding phase by the decoder
     * Decoder get operand from memory and save it to valM
     */
    short valA;
    short valB;
    short EA;
    short operand1;
    short operand2;
    /**
     *  Set in executing phase by the executor
     */
    int valE;

    /**
     *  Set in memory phase by the memory_updater
     */
    short valM;

    /**
     * Set in every phase;
     */
    short ticks;    //only add;
    short state;    //0: success, 1: memory error, 2: instruction invalid
    short trapCode; // for TRAP instruction
    short valM1; // for the STFR instruction
    private Intermediate_Register2(){

    }
    public static Intermediate_Register2 get_instance(){
        if(_instance ==null){
            _instance = new Intermediate_Register2();
        }
        return _instance;
    }
    // initialize the intermediate register
    public void initialize(){
        instruct_code = 0;
        opcode = 0;
        valP = 0;
        ix = 0;
        r = 0;
        i = 0;
        address = 0;
        immed = 0;
        rx = 0;
        ry = 0;
        al = 0;
        lr = 0;
        count = 0;
        devID = 0;
        input = 0;
        output = 0;
        device_Status = 0;
        valA = 0;
        valB = 0;
        EA = 0;
        operand1 = 0;
        operand2 = 0;
        valE = 0;
        valM = 0;
        ticks = 0;
        state = 0;
        trapCode = 0;
        valM1 = 0;
    }
    // transfer the data in intermediate register1 to intermediate register2
    public void transfer(Intermediate_Register1 intermediate_register1){
        instruct_code = intermediate_register1.getInstruct_code() ;
        opcode = intermediate_register1.getOpcode();
        valP = intermediate_register1.getValP();
        ix = intermediate_register1.getIx();
        r = intermediate_register1.getR();
        i = intermediate_register1.getI();
        address = intermediate_register1.getAddress();
        immed = intermediate_register1.getImmed();
        rx = intermediate_register1.getRx();
        ry = intermediate_register1.getRy();
        al = intermediate_register1.getAL();
        lr = intermediate_register1.getLR();
        count = intermediate_register1.getCount();
        devID = intermediate_register1.getDevID();
        input = intermediate_register1.getInput();
        output = intermediate_register1.getOutput();
        device_Status = intermediate_register1.getStatus();
        valA = intermediate_register1.getValA();
        valB = intermediate_register1.getValB();
        EA = intermediate_register1.getEA();
        operand1 = intermediate_register1.getOperand1();
        operand2 = intermediate_register1.getOperand2();
        valE = intermediate_register1.getValE();
        valM = intermediate_register1.getValM();
        ticks = intermediate_register1.getTicks();
        state = intermediate_register1.getState();
        trapCode = intermediate_register1.getTrapCode();
        valM1 = intermediate_register1.getValM1();
    }
    public short getInstruct_code() {
        return instruct_code;
    }

    public void setInstruct_code(short instruct_code) {
        this.instruct_code = instruct_code;
    }

    public short getOpcode() {
        return opcode;
    }

    public void setOpcode(short opcode) {
        this.opcode = opcode;
    }

    public short getIx() {
        return ix;
    }

    public void setIx(short ix) {
        this.ix = ix;
    }

    public short getR() {
        return r;
    }

    public void setR(short r) {
        this.r = r;
    }

    public short getI() {
        return i;
    }

    public void setI(short i) {
        this.i = i;
    }

    public short getAddress() {
        return address;
    }

    public void setAddress(short address) {
        this.address = address;
    }
    public short getImmed(){ return immed;}
    public void setImmed(short value){ immed = value;}

    public short getValP() {
        return valP;
    }

    public void setValP(short valP) {
        this.valP = valP;
    }
    public short getRx() {
        return rx;
    }

    public void setRx(short rx) {
        this.rx = rx;
    }
    public short getRy() {
        return ry;
    }

    public void setRy(short ry) {
        this.ry = ry;
    }
    public short getAL() {
        return al;
    }

    public void setAL(short al) {
        this.al = al;
    }
    public short getLR() {  return lr;  }

    public void setLR(short lr) {
        this.lr = lr;
    }
    public short getCount() {
        return count;
    }

    public void setCount(short count) {
        this.count = count;
    }
    public short getDevID() {
        return devID;
    }

    public void setDevID(short devID) {
        this.devID = devID;
    }
    public void setInput(short input){ this.input = input;}
    public short getInput(){ return this.input;}
    public void setOutput(short output){ this.output = output;}
    public short getOutput() { return this.output;}
    public void setStatus(int status){ this.device_Status = status;}
    public int getStatus() { return this.device_Status;}
    public short getValM() {
        return valM;
    }

    public void setValM(short valM) {
        this.valM = valM;
    }

    public int getValE() {
        return valE;
    }

    public void setValE(int valE) {
        this.valE = valE;
    }

    public short getTicks() {
        return ticks;
    }

    public void setTicks(short ticks) {
        this.ticks = ticks;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public short getValA() {
        return valA;
    }

    public void setValA(short valA) {
        this.valA = valA;
    }

    public short getValB() {
        return valB;
    }
    public short getOperand1(){ return operand1;}
    public void setOperand1(short value){ operand1 = value;}
    public short getOperand2(){ return operand2;}
    public void setOperand2(short value){ operand2 = value;}

    public void setValB(short valB) {
        this.valB = valB;
    }
    public short getEA (){
        return EA;
    }
    public void setEA(short valB,short address,short i){
        short val = (short)(valB+address);
        if( i == 0){
            EA = val;
        }
        else {
            EA = memory.getMemory(val);
        }
    }
    public void setTrapCode(short value){
        trapCode = value;
    }
    public short getTrapCode(){
        return trapCode;
    }
    public void setValM1(short value){
        valM1 = value;
    }
    public short getValM1(){
        return valM1;
    }

}
