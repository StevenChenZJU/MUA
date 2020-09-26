
public class MUAExecutor {
    public static Environment env = new Environment();
    public static Value 
    execute (MUAInterpreter in) {
        Operation op = in.nextOperation();
        Value[] args = eval(op, in);
        return apply(op, args);
    }

    private static Value[] eval(Operation op, MUAInterpreter in) {
        return null;
    }

    private static Value apply(Operation op, Value[] args) {
        return null;
    }
}
