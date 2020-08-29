package Utils;

public class Utils {
    public static final char DOUBLE_QUOTE = '\"';

    public static boolean isNumeric(final String s){
        try{
            Double.parseDouble(s);
            return true;
        } catch(final NumberFormatException n){
            return false;
        }
    }

    public static int countOccurences(String someString, char searchedChar, int index) {
        if (index >= someString.length()) {
            return 0;
        }
        
        int count = someString.charAt(index) == searchedChar ? 1 : 0;
        return count + countOccurences(
        someString, searchedChar, index + 1);
    }
}