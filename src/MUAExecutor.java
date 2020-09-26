import java.util.LinkedList;

public class MUAExecutor {
    public static Environment env = new Environment();
    public static Value 
    execute (MUAInterpreter in) {
        Operation op = in.nextOperation();
        Value[] args = eval(op, in);
        return apply(op, args);
    }

    private static Value[] eval(Operation op, MUAInterpreter in) {
        final ArgType[] argTypes = op.getArgsType();
        LinkedList<Value> list = new LinkedList<Value>(); 
        for (ArgType type: argTypes) {
            Value next = null;
            switch(type) {
                case WORD: next = in.nextWord(); break;
                case LIST: next = in.nextList(); break;
                case NUMBER: next = in.nextNumber(); break;
                case BOOL: next = in.nextBool(); break;
                case VALUE: next = in.nextValue(); break;
            }
            list.add(next);
        }
        return list.toArray(new Value[0]);
    }

    private static Value apply(Operation op, Value[] args) {
        return null;
    }
}
