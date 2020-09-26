import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MUAInterpreter {
    private static Map<String, Operation> OpMap
    = Map.of(
    "make", Operation.MAKE,
    "thing", Operation.THING,
    "print", Operation.PRINT,
    "read", Operation.READ,
    "add", Operation.ADD,
    "sub", Operation.SUB,
    "mul", Operation.MUL,
    "div", Operation.DIV,
    "mod", Operation.MOD
    );
    private static Pattern numberPattern = Pattern.compile("-?\\d+\\.?\\d*");
    private static Pattern listPattern = Pattern.compile("\\[\\s*\\w+");
    private static Pattern wordPattern = Pattern.compile("\"\\w+");
    private static Pattern thingPattern = Pattern.compile(":\\w+");
    private static Pattern namePattern = Pattern.compile("[a-zA-Z]\\w*");
    private static Pattern boolPattern = Pattern.compile("true|false");
    private Scanner in = new Scanner("");
    public MUAInterpreter (Scanner in) {
        this.in = in;
    }
    public Operation nextOperation() {
        if (in.hasNext(thingPattern)) {
            while(String.valueOf(in.nextByte()).isEmpty())
                continue; // read space till reading the ":"
            return Operation.THING;
        } else {
            String token = in.next();
            if (OpMap.get(token) != null) {
                return OpMap.get(token);
            } else {
                return Operation.SELF;
            }
        }
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
            result = MUAExecutor.execute(this);
        }
        return result;
    }
    public Word nextWord() {
        Word result = null;
        String token = in.next();
        if (wordPattern.matcher(token).matches()) {
            result = Word.newInstance(token);
        } else {
            Value temp = MUAExecutor.execute(this);
            if (temp.isWord()) {
                result = (Word) temp;
            }
            else {

            }

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
            Value temp = MUAExecutor.execute(this);
            if (temp.isList()) {
                result = (List) temp;
            }
            else {

            }
        }
        return result;
    }
    public Number nextNumber() {
        Number result = null;
        String token = in.next();
        if (numberPattern.matcher(token).matches()) {
            result = Number.newInstance(token);
        } else {
            Value temp = MUAExecutor.execute(this);
            if (temp.isNumber()) {
                result = (Number) temp;
            }
            else {

            }
        }
        return result;
    }
    public Bool nextBool() {
        Bool result = null;
        String token = in.next();
        if (boolPattern.matcher(token).matches()) {
            result = Bool.newInstance(token);
        } else {
            Value temp = MUAExecutor.execute(this);
            if (temp.isBool()) {
                result = (Bool) temp;
            }
            else {

            }
        }
        return result;
    }
}
