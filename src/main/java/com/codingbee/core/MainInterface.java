package com.codingbee.core;

import com.codingbee.exceptions.InternalErrorException;

import java.io.File;
import java.io.IOException;

public class MainInterface {
    public static void main(String[] args) throws IOException{
        if (args == null){
            throw new IllegalArgumentException("Arguments are required: inputPath outputPath + optional" +
                    " ROMStartingAddress RAMStartingAddress");
        }
        if (args.length < 2){
            throw new IllegalArgumentException("Not enough arguments, required: inputPath outputPath" +
                    " + optional:  ROMStartingAddress RAMStartingAddress");
        }
        if (args.length > 4){
            throw new IllegalArgumentException("Too many arguments, required: inputPath outputPath + optional:" +
                    " ROMStartingAddress RAMStartingAddress");
        }
        if (args.length == 3){
            throw new IllegalArgumentException("Two or four arguments required, required arguments: inputPath" +
                    " outputPath + optional: ROMStartingAddress RAMStartingAddress");
        }
        if (args[0] == null || args[1] == null){
            throw new IllegalArgumentException("Illegal argument: the paths can not be null");
        }

        String inputPath = args[0];
        String outputPath = args[1];

        int address1 = Integer.parseInt("0000", 16);
        int address2 = Integer.parseInt("3E80", 16);

        if (args.length == 4){
            if (args[2] == null || args[3] == null){
                throw new IllegalArgumentException("Illegal argument: the addresses can not be null");
            }
            address1 = Integer.parseInt(args[2], 16);
            address2 = Integer.parseInt(args[3], 16);
        }

        File translatedFile = new File(inputPath);
        String inputCode;
        if (translatedFile.isDirectory()) inputCode = FileManager.loadProject(inputPath);
        else inputCode = FileManager.loadFileAsString(inputPath);
        String assemblyCode = Compiler.process(inputCode);

        if (assemblyCode == null) {
            throw new InternalErrorException("The compiler failed to translate the code" +
                    " to assembly and responded with a null");
        }

        String outputValues = Assembler.process(assemblyCode, address1, address2);

        FileManager.writeAsmAsBinary(outputValues, outputPath);
    }
}