package Errors;

import Utils.*;

public class SyntaxError{
    public SyntaxError(String errorType){
        System.out.println(ConsoleColors.RED + "Syntax Error in: " + ConsoleColors.RED_BOLD + errorType + ConsoleColors.RESET);
    }
}