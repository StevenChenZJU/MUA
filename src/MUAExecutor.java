import java.util.Stack;

public class MUAExecutor {
    public Environment global = new Environment();
    public Stack<Environment> local_stack = new Stack<Environment>();
    public Value 
    execute (String token, MUAInterpreter in) {
        Value value = null;
        // interpreter determine what to do with the token
        // 1. if it is inner operation, return type(see Operation class)
        // 2. if it has number/bool/list/word pattern, return Operation.VALUE
        // 3. otherwise, check whether it can be a name, if yes, return Operation.NAME
        Operation op = in.interpretOperation(token);
        // then Executor apply the operation:
        // (1) if an inner operation, first get parameter by:
        //      1. read token, execute it and get returned value
        //      2. check whether the returned value type is correct
        //    then **execute the operation** using lambda expression predefined
        // (2) if an Operation.VALUE type, ask the interpreter 
        //      to get the complete value by using in.nextValue()
        //      and return the Value directly
        // (3) if an **possible** name, check whether in namespace
        //      1. if in namespace and it binds a **list**,
        //          which means it **should be** a function call(but not definitely)
        //          then do what (1) do
        //      2. if in namespace and it binds a value other than list
        //          **or not in the namespace at all**
        //          then ** return it as a word**
        value = apply(op, token);
        return value;
    }

    private Value apply (Operation op, String token) {
        Value value = null;
        switch (op) {
            case UNKNOWN: break; // ERROR TYPE
            case VALUE: break; // VALUE TYPE -- return value
            case NAME: break; // NAME TYPE -- interpreted as possible name
            case MAKE: break;
            case THING: break;
            case COMMA: break;
            case PRINT: break;
            case READ: break;
            case ADD: break;
            case SUB: break;
            case MUL: break;
            case DIV: break;
            case MOD: break;
        }
        return value;
    }
}
