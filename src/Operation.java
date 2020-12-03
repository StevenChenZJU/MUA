import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public enum Operation {
    // ERROR TYPE
    UNKNOWN(0){
		public Value exec(String operator, Value[] args){
            System.out.println("EXEC UNKNOWN!");
			return null;
		}
    }, 
    // VALUE -- return value
    VALUE(0){
		public Value exec(String operator, Value[] args){
			return Value.valueOf(operator);
		}
    }, 
    // NAME -- interpreted as possible name
    FUNCTION(0){
		public Value exec(String operator, Value[] args){
            Value function = Environment.getValue(operator);
			if (function != null && function.isList()) {
                // 0. get the first list and second list, seperate the first list
                List functionDefinition = function.getList();
                List params = functionDefinition.first().getList();
                List functionBody = functionDefinition.last().getList();
                String[] tokens = params.getTokens(); // with the length with args
                // 1. create local frame, bind the local variable
                HashMap<String, Value> frame = new HashMap<String, Value>();
                for (int i = 0; i < args.length; i++) {
                    String name = tokens[i];
                    Value value = args[i];
                    frame.put(name, value);
                }
                Environment.pushLocalFrame(frame);
                // 2. execute the list
                Value returnValue = run(functionBody.getContent());
                // 3. pop out the frame 
                Environment.popLocalFrame();
                // 4. return value
                return returnValue;
            } else {
                // Exception
                return null;
            }
		}
    }, 
    // EXPRESSION ()
    EXPRESSION(0) {
		@Override
		public Value exec(String operator, Value[] args) {
            // the whole expression will be in operator
            String content = operator.substring(1, operator.length()-1).trim();
            Expression expression = Expression.newInstance(content);
            Value value = expression.eval();
		    return value;
		}
    },    
    // MAKE -- 
    MAKE(2){
		public Value exec(String operator, Value[] args){
            String name = args[0].getContent();
            Value value = args[1];
            Environment.binding(name, value);
            return value;
		}
	},
    THING(1){
		public Value exec(String operator, Value[] args){
            String name = args[0].getContent();
            return Environment.getValue(name);
		}
	},
    COMMA(0){
		public Value exec(String operator, Value[] args){
            String name = operator.substring(1);
            return Environment.getValue(name);
		}
	},
    PRINT(1){
		public Value exec(String operator, Value[] args){
            Value value = args[0];
            System.out.println(value);
            return value;
		}
	},
    READ(0){
		public Value exec(String operator, Value[] args){
            String token = Environment.stdin.next();
            return Value.valueOf(token);
		}
	},
    ADD(2){
		public Value exec(String operator, Value[] args){
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
		public Value exec(String operator, Value[] args){
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
		public Value exec(String operator, Value[] args){
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
		public Value exec(String operator, Value[] args){
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
		public Value exec(String operator, Value[] args){
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
        public Value exec(String operator, Value[] args){
            Word name = args[0].getWord();
            return Environment.erase(name.getContent());
        }
    },
    ISNAME(1){
        public Value exec(String operator, Value[] args){
            Word possibleName = args[0].getWord();
            boolean result = Environment.hasLocal(possibleName.getContent()) 
                            | Environment.hasGlobal(possibleName.getContent());
            Value value = Bool.newInstance(result);
            return value;
        }
    },
    RUN(1){
        public Value exec(String operator, Value[] args){
            List list = args[0].getList();
            String content = list.getContent();
            return run(content);
        }
    },
    OR(2) {
        @Override
        public Value exec(String operator, Value[] args) {
            Bool left = args[0].getBool();
            Bool right = args[1].getBool();
            return left.or(right);
        }
    },
    AND(2) {
        @Override
        public Value exec(String operator, Value[] args) {
            Bool left = args[0].getBool();
            Bool right = args[1].getBool();
            return left.or(right);
        }
    },
    NOT(1) {
        @Override
        public Value exec(String operator, Value[] args) {
            Bool bool = args[0].getBool();
            return bool.not();
        }
    },
    LT(2) {
        @Override
        public Value exec(String operator, Value[] args) {
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
        public Value exec(String operator, Value[] args) {
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
        public Value exec(String operator, Value[] args) {
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
    },
    ISNUMBER(1) {
        @Override
        public Value exec(String operator, Value[] args) {
            Value value = args[0];
            return Bool.newInstance(value.isNumber());
        }
    },
    ISWORD(1) {
        @Override
        public Value exec(String operator, Value[] args) {
            Value value = args[0];
            return Bool.newInstance(value.isWord());
        }
    },
    ISBOOL(1) {
        @Override
        public Value exec(String operator, Value[] args) {
            Value value = args[0];
            return Bool.newInstance(value.isBool());
        }
    },
    ISLIST(1) {
        @Override
        public Value exec(String operator, Value[] args) {
            Value value = args[0];
            return Bool.newInstance(value.isList());
        }
    },
    ISEMPTY(1) {
        @Override
        public Value exec(String operator, Value[] args) {
            Value value = args[0];
            boolean result = false;
            if (value.isList()) {
                result = value.getList().isEmpty();
            } else if (value.isWord()) {
                result = value.getWord().isEmpty();
            }
            return Bool.newInstance(result);
        }
    },
    IF(3) {
        @Override
        public Value exec(String operator, Value[] args) {
            Bool condition = args[0].getBool();
            List trueStatement = args[1].getList();
            List falseStatement = args[2].getList();
            if (condition.isTrue()) {
                return run(trueStatement.getContent());
            } else {
                return run(falseStatement.getContent());
            }
        }
    },
    RETURN(1) {
        @Override
        public Value exec(String operator, Value[] args) {
            Value returnValue = args[0];
            if (returnValue == null) {
                //TODO: exception
                return null;
            } else {
                // return the value
                // and this statement is the last statement
                // so the value will be returned as the return value of functions
                return returnValue;
            }
        }
    },
    EXPORT(1) {

        @Override
        public Value exec(String operator, Value[] args) {
            Value name = args[0];
            if (Environment.hasLocal(name.getContent())) {
                Environment.export(name.getContent());
                return Environment.getValue(name.getContent());
            } else {
                //TODO: exception
                return null;   
            }
        }

    };
    
    private final int argNum;

    Operation(int argNum) {
        this.argNum = argNum;
    }
    public int getArgNum() {
        return argNum;
    }
    abstract public Value exec(String operator, Value[] args);
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
            op = Operation.FUNCTION;
        } else if (!token.isEmpty() && token.charAt(0) == '(') {
            op = Operation.EXPRESSION;
        } else {
            System.out.println("UNKNOWN OP OCCUR! WHICH IS: " + token);
        }
        return op;
    }
    public static Value run(String statements) {
        MUAInterpreter interpreter = new MUAInterpreter(new Scanner(statements));
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
        opMap.put("isnumber",Operation.ISNUMBER);
        opMap.put("isword",Operation.ISWORD);
        opMap.put("isbool",Operation.ISBOOL);
        opMap.put("islist",Operation.ISLIST);
        opMap.put("isempty",Operation.ISEMPTY);
        opMap.put("if", Operation.IF);
        opMap.put("return", Operation.RETURN);
        opMap.put("export", Operation.EXPORT);
    }

}
