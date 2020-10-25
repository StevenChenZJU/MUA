import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public enum Operation {
    // ERROR TYPE
    UNKNOWN(0){
		Value exec(String operator, Value[] args){
			return null;
		}
    }, 
    // VALUE -- return value
    VALUE(0){
		Value exec(String operator, Value[] args){
			return Value.valueOf(operator);
		}
    }, 
    // NAME -- interpreted as possible name
    FUNCTION(0){
		Value exec(String operator, Value[] args){
            Value value = Environment.getValue(operator);
			if (value != null && value.isList()) {
                // Execute the Function binded with the name
                Scanner scanner = new Scanner(value.getContent());
                MUAInterpreter in = new MUAInterpreter(scanner);
                //TODO: function call
                return null;
            } else {
                return null;
            }
		}
    }, 
    // MAKE -- 
    MAKE(2){
		Value exec(String operator, Value[] args){
            String name = args[0].getContent();
            Value value = args[1];
            Environment.binding(name, value);
            return value;
		}
	},
    THING(1){
		Value exec(String operator, Value[] args){
            String name = args[0].getContent();
            return Environment.getValue(name);
		}
	},
    COMMA(0){
		Value exec(String operator, Value[] args){
            String name = operator.substring(1);
            return Environment.getValue(name);
		}
	},
    PRINT(1){
		Value exec(String operator, Value[] args){
            Value value = args[0];
            System.out.println(value);
            return value;
		}
	},
    READ(0){
		Value exec(String operator, Value[] args){
            String token = Environment.stdin.next();
            return Value.valueOf(token);
		}
	},
    ADD(2){
		Value exec(String operator, Value[] args){
            Number left = args[0].getNumber();
            Number right = args[1].getNumber();
            if (left != null && right != null) {
                return left.add(right);
            } else {
                return null;
            }
		}
	},
    SUB(2){
		Value exec(String operator, Value[] args){
            Number left = args[0].getNumber();
            Number right = args[1].getNumber();
            if (left != null && right != null) {
                return left.sub(right);
            } else {
                return null;
            }
		}
	},
    MUL(2){
		Value exec(String operator, Value[] args){
            Number left = args[0].getNumber();
            Number right = args[1].getNumber();
            if (left != null && right != null) {
                return left.mul(right);
            } else {
                return null;
            }
		}
	},
    DIV(2){
		Value exec(String operator, Value[] args){
            Number left = args[0].getNumber();
            Number right = args[1].getNumber();
            if (left != null && right != null) {
                return left.div(right);
            } else {
                return null;
            }
		}
	},
    MOD(2){
		Value exec(String operator, Value[] args){
            Number left = args[0].getNumber();
            Number right = args[1].getNumber();
            if (left != null && right != null) {
                return left.mod(right);
            } else {
                return null;
            }
		}
    },
    ERASE(1){
        Value exec(String operator, Value[] args){
            Word name = args[0].getWord();
            return Environment.erase(name.getContent());
        }
    },
    ISNAME(1){
        Value exec(String operator, Value[] args){
            Word possibleName = args[0].getWord();
            boolean result = namePattern.matcher(
                possibleName.getContent()
                ).matches();
            Value value = Bool.newInstance(result);
            return value;
        }
    },
    RUN(1){
        Value exec(String operator, Value[] args){
            List list = args[0].getList();
            String content = list.getContent();
            MUAInterpreter interpreter = new MUAInterpreter(new Scanner(content));
            MUAExecutor executor = new MUAExecutor();
            String token = null;
            Value result = null;
            while (true) {
                if (interpreter.hasNext()) {
                    token = interpreter.nextToken();
                    result = executor.execute(token, interpreter);
                } else {
                    break;
                }
            }
            return result;
        }
    },
    OR(2) {
        @Override
        Value exec(String operator, Value[] args) {
            Bool left = args[0].getBool();
            Bool right = args[1].getBool();
            return left.and(right);
        }
    },
    AND(2) {
        @Override
        Value exec(String operator, Value[] args) {
            Bool left = args[0].getBool();
            Bool right = args[1].getBool();
            return left.or(right);
        }
    },
    NOT(1) {
        @Override
        Value exec(String operator, Value[] args) {
            Bool bool = args[0].getBool();
            return bool.not();
        }
    },
    LT(2) {
        @Override
        Value exec(String operator, Value[] args) {
            Value left = args[0];
            Value right = args[1];
            boolean result = false;
            if (left.isNumber() && right.isNumber()) {
                result = left.getNumber().lessThan(right.getNumber());
            } else {
                result = left.getWord().lessThan(right.getWord());
            }
            return Bool.newInstance(result);
        }
    },
    GT(2) {
        @Override
        Value exec(String operator, Value[] args) {
            Value left = args[0];
            Value right = args[1];
            boolean result = false;
            if (left.isNumber() && right.isNumber()) {
                result = left.getNumber().greaterThan(right.getNumber());
            } else {
                result = left.getWord().greaterThan(right.getWord());
            }
            return Bool.newInstance(result);
        }
    },
    EQ(2) {
        @Override
        Value exec(String operator, Value[] args) {
            Value left = args[0];
            Value right = args[1];
            boolean result = false;
            if (left.isNumber() && right.isNumber()) {
                result = left.getNumber().equalWith(right.getNumber());
            } else {
                result = left.getWord().equalWith(right.getWord());
            }
            return Bool.newInstance(result);
        }
    };
    
    private final int argNum;

    Operation(int argNum) {
        this.argNum = argNum;
    }
    public int getArgNum() {
        return argNum;
    }
    abstract Value exec(String operator, Value[] args);
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
            //TODO: get the argument in the first sublist
            op = Operation.FUNCTION;
        } // else UNKNOWN
        return op;
    }
    public static Pattern commaPattern = Pattern.compile(":.+");
    public static Pattern namePattern = Pattern.compile("[a-zA-Z][a-zA-Z0-9_]*");
    public static Map<String, Operation> opMap = new HashMap<String, Operation>();
    static {
        opMap.put("make", Operation.MAKE);
        opMap.put("thing", Operation.THING);
        opMap.put("print", Operation.PRINT);
        opMap.put("read", Operation.READ);
        opMap.put("add", Operation.ADD);
        opMap.put("sub", Operation.SUB);
        opMap.put("mul", Operation.MUL);
        opMap.put("div", Operation.DIV);
        opMap.put("mod", Operation.MOD);
        opMap.put("erase", Operation.ERASE);
        opMap.put("isname", Operation.ISNAME);
        opMap.put("run",Operation.RUN);
        opMap.put("or",Operation.OR);
        opMap.put("and",Operation.AND);
        opMap.put("not",Operation.NOT);
        opMap.put("lt",Operation.LT);
        opMap.put("gt",Operation.GT);
        opMap.put("eq",Operation.EQ);
    }
}
