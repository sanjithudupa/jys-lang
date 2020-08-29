public class Token {
    public static enum TokenType{
        operator,
        number,
        bool,
        string;

        public String type(){
            if(this == operator){
                return "operator";
            }

            if(this == number){
                return "number";
            }

            if(this == bool){
                return "bool";
            }

            if(this == string){
                return "string";
            }

            return "unknown";
        }
    }

    public TokenType type;
    private Object value;

    public Token(TokenType type, Object value){
        this.type = type;
        this.value = value;
    }

    public void print(){
        if(type != null && value != null){
            System.out.println(type.type() + " of value " + getValue());
        }
    }

    public Object getValue(){
        return value;
    }
}