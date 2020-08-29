import java.util.ArrayList;
import java.util.HashMap;

import Errors.VariableNameError;
import Utils.*;

public class Lexer {
    public ArrayList<Token> understand(String line, HashMap<String, Object> variables){
        String[] tokens = line.split(" ");
        ArrayList<Token> tokenList = new ArrayList<Token>();
        for(String tokenStr : tokens){
            switch(tokenStr){
                case "+":
                case "-":
                case "/":
                case "*":{
                    tokenList.add(new Token(Token.TokenType.operator, tokenStr));
                    break;
                }
                default:{
                    if(tokenStr.replace("\"", "").matches("\\d+")){
                        tokenList.add(new Token(Token.TokenType.number, Double.parseDouble(tokenStr)));
                    }else if((tokenStr.contains("true") && tokenStr.length() == 4) || (tokenStr.contains("false") && tokenStr.length() == 5)){
                        tokenList.add(new Token(Token.TokenType.bool, Boolean.parseBoolean(tokenStr)));
                    }else if(tokenStr.charAt(0) == Utils.DOUBLE_QUOTE && tokenStr.charAt(tokenStr.length()-1) == Utils.DOUBLE_QUOTE){
                        tokenList.add(new Token(Token.TokenType.string, tokenStr));
                    }else{
                        boolean isVar = false;
                        for(String varString : variables.keySet()){
                            if(tokenStr.strip().contains(varString.strip()) && tokenStr.length() == varString.length()){
                                String value = variables.get(tokenStr).toString();
                                if(Utils.isNumeric(value)){
                                    tokenList.add(new Token(Token.TokenType.number, Double.parseDouble(value)));
                                }else if((value.contains("true") && value.length() == 4) || (value.contains("false") && value.length() == 5)){
                                    tokenList.add(new Token(Token.TokenType.bool, Boolean.parseBoolean(value)));
                                }else{
                                    if(value.charAt(0) != Utils.DOUBLE_QUOTE){
                                        value = Utils.DOUBLE_QUOTE + value;
                                    }
                                    if(value.charAt(tokenStr.length()-1) != Utils.DOUBLE_QUOTE){
                                        value += Utils.DOUBLE_QUOTE;
                                    }
                                    tokenList.add(new Token(Token.TokenType.string, value));
                                }
                                isVar = true;
                            }
                        }

                        if(!isVar){
                            new VariableNameError(tokenStr.strip());
                            return null;
                        }
                    }
                }
            }
        }
        return tokenList;
    }
}