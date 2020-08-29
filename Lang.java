import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import Errors.OperationError;
import Errors.SyntaxError;
import Utils.*;

/**
 * loop 10
 * 
 */

public class Lang {

    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public static HashMap<String, Object> variables = new HashMap<String, Object>();
    public static Lexer lexer = new Lexer();

    public static void main(final String[] args) {
        printWelcome();
        while(true){
            final String line = readLine();
            if(line.contains("stop()")){
                break;
            }else{
                processLine(line);
            }
        }
        System.out.println("Ended");
    }

    public static void printWelcome(){
        System.out.println("Welcome. Run stop() to stop");
    }

    public static String readLine(){
        String input = "";
        try {
            input = reader.readLine();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return input;
    }

    public static void processLine(String line){
        if(line.length() > 8 && line.substring(0, 3).contains("let")){
            //it is an assignment
            final int equalsIndex = line.indexOf("=");
            final String name = line.substring(4, equalsIndex-1).trim();
            final String value = line.substring(equalsIndex + 1).trim();
            set(name, value);
        }else{
            String get = get(line);
            if(get != ""){
                System.out.println(get);
            }else{
                new SyntaxError(line);
            }
        }
    }

    public static void set(final String name, final String value){
        if(!(Utils.countOccurences(value, Utils.DOUBLE_QUOTE, 0) > 2) && (Utils.isNumeric(value) || (value.charAt(0) == '\"' && value.charAt(value.length()-1) == '\"') || value == "false" || value == "true")){
            if(value.charAt(0) == '\"' && value.charAt(value.length()-1) == '\"'){
                variables.put(name, value.replace("\"", ""));
            }else if(Utils.isNumeric(value)){
                variables.put(name, Double.parseDouble(value));
            }else{
                variables.put(name, Boolean.parseBoolean(value));
            }
        }else{
            ArrayList<Token> tokens = lexer.understand(value, variables);
            if(tokens != null){
                if(tokens.get(0).type != Token.TokenType.operator){
                    if(tokens.get(0).type == Token.TokenType.string){
                        //string concatenation
                        String total = "";
                        for(Token token : tokens){
                            if(token.type == Token.TokenType.operator){
                                if(!token.getValue().toString().contains("+")){
                                    new OperationError("Illegal argument " + token.getValue().toString());
                                    return;
                                }
                            }else{
                                total += token.getValue().toString();
                            }
                        }
                        variables.put(name, total.replace("\"", ""));
                    }else if(tokens.get(0).type == Token.TokenType.number){
                        double total = Double.parseDouble(tokens.get(0).getValue().toString());
                        int curIndex = 0;

                        for(Token token : tokens){
                            // token.print();
                            if(token.type == Token.TokenType.operator && curIndex % 2 == 1){
                                try{
                                    double next = Double.parseDouble(tokens.get(curIndex+1).getValue().toString());
                                    switch(token.getValue().toString()){
                                        case "+": {
                                            total += next;
                                            break;
                                        }
                                        case "-": {
                                            total -= next;
                                            break;
                                        }
                                        case "*": {
                                            total *= next;
                                            break;
                                        }
                                        default: {
                                            total /= next;
                                            break;
                                        }
                                    }
                                }catch(NumberFormatException e){
                                    new OperationError("Found String in Numeric Expression");
                                    return;
                                }
                                
                                
                            }else if(token.type != Token.TokenType.number){
                                new OperationError("Found Non-Number in Numeric Expression");
                                return;
                            }
                            curIndex ++;
                        }
                        variables.put(name, total);
                    }
                }else{
                    new OperationError("Illegal start of e");
                    return;
                }
            }
        }
    }

    public static String get(final String line){
        for(final String name : variables.keySet()){
            if(name.contains(line) ){
                return variables.get(name).toString();
            }
        }
        return "";
    }
}