import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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
    private Scanner in = new Scanner("");
    public MUAInterpreter (Scanner in) {
        this.in = in;
    }
    public Operation nextOperation() {
        String token = in.next();
        if (OpMap.get(token) != null) {
            return OpMap.get(token);
        } else {
            return Operation.SELF;
        }
    }   
}
