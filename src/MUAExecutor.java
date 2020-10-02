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
        Operation op = Operation.getOperation(token);
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
        Value[] args = eval(op, in);
        value = apply(op, token, args);
        return value;
    }
    private Value[] 
    eval(Operation op, MUAInterpreter in) {
        int argNum = op.getArgNum();
        Value[] result = new Value[argNum];
        String token;
        Value value;
        for (int i = 0; i < argNum; i++) {
            token = in.nextToken();
            value = execute(token, in);
            result[i] = value;
        }
        return result;
    }
    private Value 
    apply (Operation op, String token, Value[] args) {
        return op.exec(token, args);
    }
}
