import java.util.Stack;
import java.util.regex.Pattern;

public class Expression {
    //TODO: support floating point number
    private static Pattern pureExpPattern = Pattern.compile("[()\\+\\-\\*/%\\d]+");
    private String content = String.valueOf("");
    private Expression(String content) {
        this.content = content;
    }
	public static Expression newInstance(String content) {
		return new Expression(content);
	}

	public Value eval() {
        if (pureExpPattern.matcher(content).matches()) {
            // System.out.println("Pure Expression: " + content);
            return PureExpression.eval(content);
        } else {
            // contains postfix expression or mua statements
            String extendedContent = extend(content);
            return Operation.run(extendedContent);
        }
        
    }
    
    private static String extend(String content) {
        // extends all +/-
        for (int i = content.length() - 1; i > 0; i--) {
            // from right to left
            if (content.charAt(i) == '+') {
                // operator +
                String left = getLeftOperand(content, i);
                String right = getRightOperand(content, i);
                String replacement = String.format("add (%s) (%s)", left, right);
                content = content.replace(left + "+" + right, replacement);
                
                i = content.length() - 1;
            }
            else if (isOperatorSub(content, i)) {
                // operator -
                String left = getLeftOperand(content, i);
                String right = getRightOperand(content, i);
                String replacement = String.format("sub (%s) (%s)", left, right);
                content = content.replace(left + "-" + right, replacement);
                
                i = content.length() - 1;
            }
        }

        // extends all * / %
        for (int i = content.length() - 1; i > 0; i--) {
            // from right to left
            if (content.charAt(i) == '*') {
                // operator +
                String left = getLeftOperand(content, i);
                String right = getRightOperand(content, i);
                String replacement = String.format("mul (%s) (%s)", left, right);
                content = content.replace(left + "*" + right, replacement);
                
                i = content.length() - 1;
            }
            else if (content.charAt(i) == '/') {
                // operator +
                String left = getLeftOperand(content, i);
                String right = getRightOperand(content, i);
                String replacement = String.format("div (%s) (%s)", left, right);
                content = content.replace(left + "/" + right, replacement);
                
                i = content.length() - 1;
            } else if (content.charAt(i) == '%') {
                // operator +
                String left = getLeftOperand(content, i);
                String right = getRightOperand(content, i);
                String replacement = String.format("mod (%s) (%s)", left, right);
                content = content.replace(left + "%" + right, replacement);
                
                i = content.length() - 1;
            }
        }
        // remove all ()
        
        return content.replaceAll("\\(|\\)", " ");
    }

    private static boolean isOperatorSub(String content, int index) {
        boolean result = false;
        if (content.charAt(index) == '-'  && 
            (content.charAt(index-1) == ')')) {
            result = true;
        }
        else if (content.charAt(index) == '-'  &&
            PureExpression.isDigit(content.charAt(index-1))) {
            result = true;
        } else if (content.charAt(index) == '-'  &&
            Pattern.matches("[a-zA-z_]", content.substring(index-1, index))) {
            result = true;
        }
        return result;
    }

    private static String getRightOperand(String content, int i) {
        // preserve space 
        int indicator = 0; // the number of ( met minus the number of ) met
        int beginIndex = i + 1;
        int endIndex = i + 1;
        while (indicator >= 0 && endIndex < content.length()) {
            if (content.charAt(endIndex) == '(') {
                indicator++;
            } else if (content.charAt(endIndex) == ')') {
                indicator--;
            }
            endIndex++;
        }
        return content.substring(beginIndex, endIndex);
    }

    private static String getLeftOperand(String content, int i) {
        // preserve space 
        int indicator = 0; // the number of ( met minus the number of ) met
        int beginIndex = i - 1;
        int endIndex = i;
        while (indicator <= 0 && beginIndex >= 0) {
            if (content.charAt(beginIndex) == '(') {
                indicator++;
            } else if (content.charAt(beginIndex) == ')') {
                indicator--;
            }
            beginIndex--;
        }
        // beginIndex will end up with 1 before actual operand
        return content.substring(beginIndex+1, endIndex);
    }

    public static class PureExpression {
        static Stack<Double> operands = new Stack<Double>();
        static Stack<Calculation> operations = new Stack<Calculation>();
        static boolean indicator = false; // indicate we need an operand
        static Pattern op_pattern = Pattern.compile("\\(|\\)|\\+|\\*|/|%|-");
        static Pattern int_pattern = Pattern.compile("-?[0-9]+");
        public static Value eval(String content) {
            String input = "(" + content + ")";
            int index = 0;
            while(index < input.length()) {
                char curChar = input.charAt(index);
                switch(curChar) {
                    case '(': Calculation.LPARA.toStack(); break;
                    case ')': Calculation.RPARA.toStack(); break;
                    case '+': Calculation.PLUS.toStack(); break;
                    case '-': 
                    {
                        if(index != 0 && (input.charAt(index-1) == ')' || isDigit(input.charAt(index-1)))) {
                            Calculation.MINUS.toStack(); 
                        }
                        else {
                            // is negative sign
                            index++;
                            operands.push(-Double.valueOf(input.substring(index, index+1)));
                        }
                        
                        break;
                    }
                    case '*': Calculation.MUL.toStack(); break;
                    case '/': Calculation.DIV.toStack(); break;
                    case '%': Calculation.MOD.toStack(); break;
                    default: // number
                    {
                        if(index != 0 && isDigit(input.charAt(index-1))) {
                            Double prev = operands.pop();
                            prev = prev * 10 + Integer.valueOf(input.substring(index, index+1));
                            
                            operands.push(prev);
                        } else {
                            Double d = Double.valueOf(input.substring(index, index+1));
                            
                            operands.push(d);
                        }
                    }
                }
                index++;
            }
            
            return Value.valueOf(String.valueOf(operands.peek()));
        }
        private enum Calculation {
            PLUS(1, 1) {
                @Override
                void eval() {
                    Double operand1 = operands.pop();
                    Double operand2 = operands.pop();
                    operands.push(operand2 + operand1);
                }
            }, 
            MINUS(1, 1) {
                @Override
                void eval() {
                    Double operand1 = operands.pop();
                    Double operand2 = operands.pop();
                    operands.push(operand2 - operand1);
                }
            }, 
            MUL(2, 2) {
                @Override
                void eval() {
                    Double operand1 = operands.pop();
                    Double operand2 = operands.pop();
                    operands.push(operand2 * operand1);
                }
            }, 
            DIV(2, 2) {
                @Override
                void eval() {
                    Double operand1 = operands.pop();
                    Double operand2 = operands.pop();
                    operands.push(operand2 / operand1);
                }
            },
            MOD(2, 2) {
                @Override
                void eval() {
                    Double operand1 = operands.pop();
                    Double operand2 = operands.pop();
                    operands.push(operand2 % operand1);
                }
            }, 
            LPARA(0, 4) {
                @Override
                void eval() {
                    // do nothing
                }
            }, 
            RPARA(3, 1) {
                @Override
                void eval() {
                    // never triggered
                }
                @Override
                void push() {
                    operations.pop(); // pop out (
                }
            }; // in_prec not important, cuz will never in
            Calculation(int in_prec, int out_prec) {
                this.in_prec = in_prec;
                this.out_prec = out_prec;
            }
            void toStack() { 
                while
                (!operations.isEmpty()) {
                    if(operations.peek().in_prec >= out_prec) {
                        Calculation top = operations.pop();
                        top.eval();
                    } else {
                        break;
                    }
                }
                push(); // right parathesis not going to stack
                        // instead, it pop out the ( here
            }
            void push() { operations.push(this); }
            abstract void eval();
            public int in_prec;
            public int out_prec;
        }
    
        private static boolean isDigit(char input) {
            final Pattern digitPattern = Pattern.compile("[0-9]");
            return digitPattern.matcher(String.valueOf(input)).matches();
        }
    }
    /**
     * Unit Test
     */
    public static void main(String[] args) {
        String[] arr = {
            "(5%3-3*3/(5+4))",
            "(2+3*3/5+4)",
            "(10+-2*3-6)",
            "(add (5%3-3*3/(5+4)) 5)",
            "(2*(-2+6)/4)"
        };
        for (String str : arr) {
            Expression expr = Expression.newInstance(str);
            System.out.println(expr.eval());
        }
    }
}
