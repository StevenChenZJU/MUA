import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MUAInterpreter {
    static Map<String, Operation> OpMap
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
    static Pattern number_pattern = Pattern.compile("-?\\d+\\.?\\d*");
    static Pattern list_pattern = Pattern.compile("\\[\\s*\\w+");
    static Pattern word_pattern = Pattern.compile("\"\\w+");
    static Pattern thing_pattern = Pattern.compile(":\\w+");
    static Pattern name_pattern = Pattern.compile("[a-zA-Z]\\w*");
    static Pattern bool_pattern = Pattern.compile("true|false");
    private Scanner in = new Scanner("");
    public MUAInterpreter (Scanner in) {
        this.in = in;
    }
    public Operation nextOperation() {
        if (in.hasNext(thing_pattern)) {
            in.nextByte(); // read the ":"
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
        if (in.hasNext(number_pattern)) {
            result = nextNumber();
        } else if (in.hasNext(list_pattern)) {
            result = nextList();
        } else if (in.hasNext(word_pattern)) {
            result = nextWord();
        } else if(in.hasNext(bool_pattern)) {
            result = nextBool();
        } else {
            // reading an operation
            result = MUAExecutor.execute(this);
        }
        return result;
    }
    public Word nextWord() {
        Word result = null;
        return result;
    }
    public List nextList() {
        List result = null;
        return result;
    }
    public Number nextNumber() {
        Number result = null;
        return result;
    }
    public Bool nextBool() {
        Bool result = null;
        return result;
    }
}
