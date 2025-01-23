package com.codingbee.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    /**
     * Loads all project files with ".bpp" extension and formats them into one String
     * @param dirPath path to project directory with all files, can be searched recursively
     * @return content of all files with ".bpp" extension read as plain text in one String
     * @throws IOException if the path is not valid, can not be read or does not contain ".bpp" files
     */
    public static String loadProject(String dirPath) throws IOException {
        StringBuilder content = new StringBuilder();
        File[] bppFiles = listFilesRecursively(dirPath).stream()
                .filter(file -> file.isFile() && file.getName().endsWith("bpp"))
                .toArray(File[]::new);
        for (File bppFile : bppFiles) {
            content.append(bppFile.getAbsoluteFile()).append("\n");
            content.append(loadFileAsString(bppFile.getAbsolutePath()));
            content.append("FILE_END_").append(bppFile.getAbsoluteFile()).append("\n");
        }
        return content.toString();
    }

    /**
     * Recursively searches directory and all directories inside and returns all non-directory files.
     * @param directoryPath path of the directory to search
     * @return all files that are not a directory in the directories and recursively all the directories inside
     */
    private static List<File> listFilesRecursively(String directoryPath) {
        List<File> fileList = new ArrayList<>();
        File directory = new File(directoryPath);

        if (!directory.exists() || !directory.isDirectory()){
            throw new IllegalArgumentException("The directory does not exist or is not a valid directory");
        }
        File[] files = directory.listFiles();
        if (files == null){
            throw new IllegalArgumentException("The directory does not contain any files");
        }
        for (File file : files) {
            if (file.isDirectory()) {
                fileList.addAll(listFilesRecursively(file.getAbsolutePath()));
            } else {
                fileList.add(file);
            }
        }
        return fileList;
    }


    /**
     * Loads the text file at given path as one String object
     * @param filePath path of the given file
     * @return the content of the file, when read as a text file, in one String object
     * @throws IOException when file can not be found or read, or when access id denied
     */
    public static String loadFileAsString(String filePath) throws IOException {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath))).replace("\t", "")
                    .replace("\r\n", "\n");/*Loads information, removes tabs and
                    normalizes new lines*/
        } catch (IOException e) {
            throw new IOException("Could not read the file " + filePath + ": " + e.getLocalizedMessage());
        }
    }

    /**
     * Writes assembly instructions to the specified file in binary format
     * @param asmString assembly instructions as one String
     * @param filePath path to the given file
     * @throws IOException if the file can not be created, accessed or written to
     */
    public static void writeAsmAsBinary(String asmString, String filePath) throws IOException {
        try (FileOutputStream outStream = new FileOutputStream(filePath)) {
            byte[] byteData = asmStringToByteArray(asmString);
            outStream.write(byteData);
        } catch (IOException e) {
            throw new IOException("The binary could not be written to " + filePath + ": " + e.getLocalizedMessage());
        }
    }

    /**
     * Translates the assembly instructions in String format to byte values
     * @param asmString the assembly instructions
     * @return byte array with the translated instructions. The length is equal to half
     * of the characters after removing spaces and new lines
     */
    private static byte[] asmStringToByteArray(String asmString) {
        //Removing characters which should not be translated
        asmString = asmString.replaceAll("\\s", "");
        asmString = asmString.replaceAll("\t", "");
        byte[] data = new byte[asmString.length() / 2];

        for (int i = 0; i < asmString.length(); i += 2) {
            data[i / 2] = (byte) ((Character.digit(asmString.charAt(i), 16) << 4)
                    + Character.digit(asmString.charAt(i + 1), 16));
        }
        return data;
    }
}