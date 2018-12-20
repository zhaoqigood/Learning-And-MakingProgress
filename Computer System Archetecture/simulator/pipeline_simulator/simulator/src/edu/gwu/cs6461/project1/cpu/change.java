package edu.gwu.cs6461.project1.cpu;
import java.lang.Math;
public class change {
    public float shortToFloat(short input){
        String bitMask1 = "1000000000000000";
        String bitMask2 = "0111111100000000";
        String bitMask3 = "0000000011111111";
        int mask1 = Integer.parseUnsignedInt(bitMask1,2);
        int mask2 = Integer.parseUnsignedInt(bitMask2, 2);
        int mask3 = Integer.parseUnsignedInt(bitMask3, 2);
        int S = (mask1 & input) >>15;
        int medium = ((mask2 & input)<<17);
        int exponent = medium >>25;
        double mantissa = (double)(mask3 & input)/(double)256;
        float output = (float) (mantissa*(Math.pow(2,(double)exponent)));
        if(S == 1){
            output = -output;
        }
        return output;
    }
    public short floatToShort(float input){
        if(input == 0){
            return 0;
        }
        float x = input;
        if(x<0){
            x = -x; // get the absolute value of input
        }
        int exponent = 0;
        // get the 0.1xxxx format
        if(x>=1){
            while(x>=1){
                x = x/2;
                exponent++;
            }
        }
        else{
            while((x*2)<1){
                x = x*2;
                exponent--;
            }
        }
        int mantissa = (int) (x * 256);
        String string1,string2,string3;
        string1 = "0";
        if(input <0){
            string1 = "1";
        }
        string2 = Integer.toBinaryString(exponent);
        if(string2.length() < 7){
            string2 = "0" + string2;
        }
        else{
            string2 = string2.substring(string2.length()-7,string2.length());
        }
        string3 = Integer.toBinaryString(mantissa);
        String floatRepresentation = string1+string2+string3;
        short output = (short)Integer.parseUnsignedInt(floatRepresentation,2);
        return output;
    }
    public static void main(String args[]){
        change a = new change();
        short z = a.floatToShort((float)0.125);
        float q = a.shortToFloat((short)896);
        System.out.println(q);
    }
}
