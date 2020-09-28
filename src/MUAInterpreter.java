import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MUAInterpreter {
    private static Map<String, Operation> opMap
    = Map.of(
    "make",     Operation.MAKE,
    "thing",    Operation.THING,
    "print",    Operation.PRINT,
    "read",     Operation.READ,
    "add",      Operation.ADD,
    "sub",      Operation.SUB,
    "mul",      Operation.MUL,
    "div",      Operation.DIV,
    "mod",      Operation.MOD
    );
    private static Pattern numberPattern = Pattern.compile("-?\\d+\\.?\\d*");
    private static Pattern listPattern = Pattern.compile("\\[\\s*\\w+");
    private static Pattern wordPattern = Pattern.compile("\"\\w+");
    private static Pattern commaPattern = Pattern.compile(":\\w+");
    private static Pattern namePattern = Pattern.compile("[a-zA-Z]\\w*");
    private static Pattern boolPattern = Pattern.compile("true|false");
    private Scanner in = new Scanner("");
    public MUAInterpreter (Scanner in) {
        this.in = in;
    }
    public String nextToken () {
        return in.next();
    }
    public Operation interpretOperation (String token) {
        // 1. if it is inner operation, return type(see Operation class)
        // 2. if it has number/bool/list/word pattern, return Operation.VALUE
        // 3. otherwise, check whether it can be a name, if yes, return Operation.NAME
        Operation op = Operation.UNKNOWN;
        if (opMap.containsKey(token)) {
            op = opMap.get(token);
        } else if(isValueLiteral(token)) {
            op = Operation.VALUE;
        } else if(namePattern.matcher(token).matches()){
            op = Operation.NAME;
        } // else UNKNOWN
        return op;
    }   
    public Value nextValue() {
        Value result = null;
        if (in.hasNext(numberPattern)) {
            result = nextNumber();
        } else if (in.hasNext(listPattern)) {
            result = nextList();
        } else if (in.hasNext(wordPattern)) {
            result = nextWord();
        } else if(in.hasNext(boolPattern)) {
            result = nextBool();
        } else {
            // reading an operation
            
        }
        return result;
    }
    public Name nextName() {
        Name result = null;
        String token = in.next();
        // BUG: only check whether the name is in namespace
        // let executor do the checking
        if (namePattern.matcher(token).matches()) {
            result = Name.newInstance(token);
        } else {
            // throw exception for no such name
        }
        return result;
    }
    public Word nextWord() {
        Word result = null;
        String token = in.next();
        if (wordPattern.matcher(token).matches()) {
            // remove the first \"
            result = Word.newInstance(token.substring(1));
        } else {
            

        }
        return result;
    }
    public List nextList() {
        List result = null;
        String content = String.valueOf("");
        String token = in.next();
        if (token.startsWith("[")) {
            int stack = 1;
            while (stack != 0) {
                content = content.concat(token + " ");
                token = in.next();
                if (token.endsWith("]")) {
                    stack --;
                }
                if (token.startsWith("[")) {
                    stack ++;
                }
            }
            result = List.newInstance
                    (content.substring(1, content.length()-1));
        } else {
            
        }
        return result;
    }
    public Number nextNumber() {
        Number result = null;
        String token = in.next();
        if (numberPattern.matcher(token).matches()) {
            result = Number.newInstance(token);
        } else {
            
        }
        return result;
    }
    public Bool nextBool() {
        Bool result = null;
        String token = in.next();
        if (boolPattern.matcher(token).matches()) {
            result = Bool.newInstance(token);
        } else {
            
        }
        return result;
    }
    private boolean isValueLiteral (String input) {
        return  listPattern.matcher(input).matches()
            ||  wordPattern.matcher(input).matches()
            ||  numberPattern.matcher(input).matches()
            ||  boolPattern.matcher(input).matches();
    }
}
