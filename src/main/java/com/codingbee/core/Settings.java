package com.codingbee.core;

import java.util.HashMap;

public class Settings {
    private HashMap<String, String> noArgumentInstructionSet;
    private HashMap<String, String> argumentInstructionSet;
    public Settings(){
        setup();
    }
    public HashMap<String, String> getArgumentInstructionSet(){
        return argumentInstructionSet;
    }
    public HashMap<String, String> getNoArgumentInstructionSet(){
        return noArgumentInstructionSet;
    }


    private void setup(){
        argumentInstructionSet = new HashMap<>();
        argumentInstructionSet.put("lda", "04");
        argumentInstructionSet.put("ldb", "05");
        argumentInstructionSet.put("ldia", "06");
        argumentInstructionSet.put("ldib", "07");
        argumentInstructionSet.put("sta", "08");
        argumentInstructionSet.put("stb", "09");
        argumentInstructionSet.put("adda", "10");
        argumentInstructionSet.put("addb", "11");
        argumentInstructionSet.put("addia", "12");
        argumentInstructionSet.put("addib", "13");
        argumentInstructionSet.put("suba", "14");
        argumentInstructionSet.put("subb", "15");
        argumentInstructionSet.put("subia", "16");
        argumentInstructionSet.put("subib", "17");
        argumentInstructionSet.put("anda", "20");
        argumentInstructionSet.put("andb", "21");
        argumentInstructionSet.put("andia", "22");
        argumentInstructionSet.put("andib", "23");
        argumentInstructionSet.put("ora", "24");
        argumentInstructionSet.put("orb", "25");
        argumentInstructionSet.put("oria", "26");
        argumentInstructionSet.put("orib", "27");
        argumentInstructionSet.put("xora", "28");
        argumentInstructionSet.put("xorb", "29");
        argumentInstructionSet.put("xoria", "2A");
        argumentInstructionSet.put("xorib", "2B");
        argumentInstructionSet.put("nota", "2C");
        argumentInstructionSet.put("notb", "2E");
        argumentInstructionSet.put("shla", "2F");
        argumentInstructionSet.put("shlb", "30");
        argumentInstructionSet.put("shra", "31");
        argumentInstructionSet.put("shrb", "32");
        argumentInstructionSet.put("rola", "33");
        argumentInstructionSet.put("rolb", "34");
        argumentInstructionSet.put("rora", "35");
        argumentInstructionSet.put("jmp", "40");
        argumentInstructionSet.put("je", "44");
        argumentInstructionSet.put("jne", "48");

        noArgumentInstructionSet = new HashMap<>();
        noArgumentInstructionSet.put("prnt", "01");
        noArgumentInstructionSet.put("nop", "0A");
        noArgumentInstructionSet.put("nopa", "0A");
        noArgumentInstructionSet.put("nopb", "0A");
    }
}
