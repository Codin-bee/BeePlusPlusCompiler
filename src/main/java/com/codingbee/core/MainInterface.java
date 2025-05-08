package com.codingbee.core;

import java.io.File;
import java.io.IOException;

public class MainInterface {
    public static void main(String[] args) throws IOException{


        args = new String[]{"C:\\Users\\theco\\IdeaProjects\\BeePlusPlusAssembler\\src\\main\\resources\\test\\i.bpp", "C:\\Users\\theco\\IdeaProjects\\BeePlusPlusAssembler\\src\\main\\resources\\test\\o.bpp", "0000", "3E80"};


        if (args == null){
            throw new IllegalArgumentException("Arguments are required: inputPath outputPath" +
                    " ROMStartingAddress RAMStartingAddress");
        }
        if (args.length < 4){
            throw new IllegalArgumentException("Not enough arguments, required: inputPath outputPath" +
                    " ROMStartingAddress RAMStartingAddress");
        }
        if (args.length > 4){
            throw new IllegalArgumentException("Too many arguments, required: inputPath outputPath" +
                    " ROMStartingAddress RAMStartingAddress");
        }
        if (args[0] == null || args[1] == null || args[2] == null || args[3] == null){
            throw new IllegalArgumentException("Illegal argument: the paths and addresses can not be null");
        }

        String inputPath = args[0];
        String outputPath = args[1];
        int address1 = Integer.parseInt(args[2], 16);
        int address2 = Integer.parseInt(args[3], 16);

        File inputFile = new File(inputPath);
        String originalCode;
        if (inputFile.isDirectory()) originalCode = FileManager.loadProject(inputPath);
        else originalCode = FileManager.loadFileAsString(inputPath);

        String assemblyCode = Assembler.process(originalCode, address1, address2);

        FileManager.writeAsmAsBinary(assemblyCode, outputPath);
    }
}