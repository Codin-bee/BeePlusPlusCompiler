package com.codingbee.core;

import com.codingbee.exceptions.InternalErrorException;

import java.util.*;

public class Assembler {
    //Temporarily always true
    private static final boolean verbose = true;
    private static final Settings instructionSetting = new Settings();
    private static final HashMap<String, String> instructionsWithArguments = instructionSetting.getArgumentInstructionSet();
    private static final HashMap<String, String> noArgumentInstructions = instructionSetting.getNoArgumentInstructionSet();

    private static final HashMap<String, String> variables = new HashMap<>();
    private static int instructionNo;


    public static String process(String input, int startingAddress, int arithmeticAddress){
        instructionNo = startingAddress;

        System.out.println("The addresses of instructions start at: " + startingAddress);
        System.out.println("The addresses of variables start at: " + arithmeticAddress);
        System.out.println("\n\n");

        //Prepare for parsing
        String[] commands = tokenize(cleanUpRegex(input));

        List<String> translated = new ArrayList<>();
        for (String command : commands) {

            //Skips comments and empty lines
            if (command.startsWith(";") || command.isEmpty()) {
                continue;
            }
            //Translates and adds to the list
            String[] translatedCmd = translateCommand(command);
            translated.addAll(Arrays.asList(translatedCmd));
        }
        System.out.println("\nThe number of instructions: " + instructionNo);

        //Returns formated output
        return generateOutput(translated);
    }

    private static String[] translateCommand(String cmd){
        String[] trans = null;

        String[] commandParts = cmd.split(" ");

        switch (commandParts.length){
            case 1:
                if (verbose) System.out.println(cmd);

                trans = new String[]{noArgumentInstructions.get(commandParts[0]) + " 000000"};
                instructionNo++;
                break;
            case 2:
                if (verbose) System.out.println(cmd);

                trans = new String[]{instructionsWithArguments.get(commandParts[0]) + " "
                        + padAddress(replaceVariables(commandParts[1]))};
                instructionNo++;
                break;
            case 3:
                String[] asmb = translateCustom(commandParts);
                trans = new String[asmb.length];
                for (int i = 0; i < asmb.length; i++) {
                    System.out.println(asmb[i]);

                    String[] parts = asmb[i].split(" ");
                    if (parts.length != 2) {
                        throw new IllegalArgumentException("The line can not be translated: " + asmb[i]);
                    }
                    instructionNo++;
                    trans[i] = instructionsWithArguments.get(parts[0]) + " " + parts[1];
                }
                break;
        }


        if (trans == null) throw new InternalErrorException("The assembler could not translate the code " +
                "and returned null");
        return trans;
    }

    private static String padAddress(String address){
        return address;
    }

    private static String cleanUpRegex(String code){
        return code.replace("\t", "")
                .replace("\r\n", "\n")
                .toLowerCase(Locale.ROOT);
    }

    private static String[] tokenize(String codebase){
        return codebase.split("\n");
    }

    private static String generateOutput(List<String> translatedCode){
        StringBuilder o = new StringBuilder();
        for (int i = 0; i < translatedCode.size(); i++) {
            if (translatedCode.get(i) != null) {
                o.append(translatedCode.get(i));
                if (i != translatedCode.size()-1){
                    o.append("\n");
                }
            }
        }
        return o.toString();
    }

    private static String replaceVariables(String var){
        String address = variables.get(var);
        if (address==null) return var;
        return address;
    }

    private static String[] translateCustom(String[] commandParts){
        List<String> commandSet = new ArrayList<>();

        switch (commandParts[0]){
            case "feed":
                commandSet.add("lda " + variables.get(commandParts[1]));
                commandSet.add("adda " + commandParts[2]);
                commandSet.add("sta " + variables.get(commandParts[1]));
                break;

            case "cell":
                variables.put(commandParts[1], commandParts[2]);
                break;
            default:
                throw new IllegalArgumentException("The instruction \n" + commandParts[0] + "\n unrecognized");
        }

        String[] translatedSet = new String[commandSet.size()];
        for (int i = 0; i < commandSet.size(); i++) {
            translatedSet[i] = commandSet.get(i);
        }

        return translatedSet;
    }
}
