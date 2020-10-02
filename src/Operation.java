import java.util.Map;
import java.util.regex.Pattern;

public enum Operation {
    UNKNOWN(0){
		Value exec(String token, Value[] args){
			return null;
		}
	}, // ERROR TYPE
    VALUE(0){
		Value exec(String token, Value[] args){
			return null;
		}
	}, // VALUE TYPE -- return value
    NAME(0){
		Value exec(String token, Value[] args){
			return null;
		}
	}, // NAME TYPE -- interpreted as possible name
    MAKE(2){
		Value exec(String token, Value[] args){
			return null;
		}
	},
    THING(1){
		Value exec(String token, Value[] args){
			return null;
		}
	},
    COMMA(0){
		Value exec(String token, Value[] args){
			return null;
		}
	},
    PRINT(1){
		Value exec(String token, Value[] args){
			return null;
		}
	},
    READ(0){
		Value exec(String token, Value[] args){
			return null;
		}
	},
    ADD(2){
		Value exec(String token, Value[] args){
			return null;
		}
	},
    SUB(2){
		Value exec(String token, Value[] args){
			return null;
		}
	},
    MUL(2){
		Value exec(String token, Value[] args){
			return null;
		}
	},
    DIV(2){
		Value exec(String token, Value[] args){
			return null;
		}
	},
    MOD(2){
		Value exec(String token, Value[] args){
			return null;
		}
	};
    
    private final int argNum;

    Operation(int argNum) {
        this.argNum = argNum;
    }
    public int getArgNum() {
        return argNum;
    }
    abstract Value exec(String token, Value[] args);
    /**
     * Static Functions and Variables
     */
    public static Operation getOperation(String token) {
        // 1. if it is inner operation, return type(see Operation class)
        // 2. if it has number/bool/list/word pattern, return Operation.VALUE
        // 3. otherwise, check whether it can be a name, if yes, return Operation.NAME
        Operation op = Operation.UNKNOWN;
        if (Operation.opMap.containsKey(token)) {
            op = opMap.get(token);
        } else if(commaPattern.matcher(token).matches()) {
            op = Operation.COMMA;
        } else if(Value.isValueLiteral(token)) {
            op = Operation.VALUE;
        } else if(namePattern.matcher(token).matches()){
            op = Operation.NAME;
        } // else UNKNOWN
        return op;
    }
    public static Pattern commaPattern = Pattern.compile(":\\.+");
    public static Pattern namePattern = Pattern.compile("[a-zA-Z]\\[a-zA-Z0-9_]*");
    public static Map<String, Operation> opMap
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
}
