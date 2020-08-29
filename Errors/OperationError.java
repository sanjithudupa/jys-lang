package Errors;

import Utils.*;

public class OperationError{
    public OperationError(String errorType){
        System.out.println(ConsoleColors.RED + "Operation Error: " + ConsoleColors.RED_BOLD + errorType + ConsoleColors.RESET);
    }
}