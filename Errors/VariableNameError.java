package Errors;
import Utils.*;

public class VariableNameError{
    public VariableNameError(String variable){
        System.out.println(ConsoleColors.RED + "Variable Name Error: var " + ConsoleColors.RED_BOLD + variable + ConsoleColors.RED + " not found" + ConsoleColors.RESET);
    }
}