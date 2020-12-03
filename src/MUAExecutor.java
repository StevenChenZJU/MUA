public class MUAExecutor {
    public Value 
    execute (String token, MUAInterpreter in) {
        Value value = null;
        // interpreter determine what to do with the token
        // 1. if it is inner operation, return type(see Operation class)
        // 2. if it has number/bool/list/word pattern, return Operation.VALUE
        // 3. otherwise, check whether it can be a name, if yes, return Operation.NAME
        Operation op = Operation.getOperation(token);
        // then the Executor evaluate the operation to get arguments
        // get totally <argNum> values using Executor.execute
        // each time read in a **token** and execute it
        Value[] args = null; 
        // then Executor apply the operation:
        // TODO: rewrite the comment
        if (op == Operation.FUNCTION) {
            // get function arguments
            args = eval(op, token, in);
        } else {
            args = eval(op, in);
        }
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
    private Value[]
    eval(Operation op, String token, MUAInterpreter in) {
        // for function
        if (op != Operation.FUNCTION) {
            // TODO: Exception
            return null;
        } else {
            Value function = Environment.getValue(token);
            if (!function.isList()) {
                // TODO: exception
                return null;
            }
            List functionDefinition = function.getList();
            List params = functionDefinition.first().getList();
            // List functionBody = functionDefinition.last().getList();
            String[] tokens = params.getTokens();
            int argNum = tokens.length;
            Value[] result = new Value[argNum];
            String arg;
            Value value;
            for (int i = 0; i < argNum; i++) {
                arg = in.nextToken();
                value = execute(arg, in);
                result[i] = value;
            }
            return result;
        }
        
    }
    private Value 
    apply (Operation op, String token, Value[] args) {
        return op.exec(token, args);
    }
}
