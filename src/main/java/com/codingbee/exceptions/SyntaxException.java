package com.codingbee.exceptions;

public class SyntaxException extends NoSuchMethodException{
    public SyntaxException(String msg){
        super("Unsupported operation in syntax: " + msg);
    }
}
