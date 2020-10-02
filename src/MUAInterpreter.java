import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MUAInterpreter {
    private static Pattern numberPattern = Pattern.compile("-?\\d+\\.?\\d*");
    private static Pattern listPattern = Pattern.compile("\\[\\s*.+");
    private static Pattern exprPattern = Pattern.compile("\\(\\s*.+");
    private static Pattern wordPattern = Pattern.compile("\".+");
    private static Pattern commaPattern = Pattern.compile(":\\.+");
    private static Pattern namePattern = Pattern.compile("[a-zA-Z]\\[a-zA-Z0-9_]*");
    private static Pattern boolPattern = Pattern.compile("true|false");
    private Scanner in = new Scanner("");
    public MUAInterpreter (Scanner in) {
        this.in = in;
    }
    public String nextToken () {
        // get the next complete token
        // especially for token begins with [ and ( 
        String input = in.next();
        if (listPattern.matcher(input).matches()) {
            return nextListToken(input);
        } else if (exprPattern.matcher(input).matches()) {
            return nextExprToken(input);
        } else {
            return input;
        }
    }
    public String nextListToken(String input) {
        String result = input;
        String token;
        int index = 1;
        int leftMinusRight = 1; // the number of [ minus the number of ]
        // if a token begins with ", we need to skip it
        while (leftMinusRight > 0) {
            // if off the result's length, read more
            if (index >= result.length()) {
                while ((token = in.next()).charAt(0) == '\"') {
                    // concatenate
                    result = String.format("%s %s", result, token);
                    index = result.length();
                }
                result = String.format("%s %s", result, token);
            }
            leftMinusRight += (result.charAt(index) == '[') ? 1 : 0;
            leftMinusRight -= (result.charAt(index) == ']') ? 1 : 0;
            index++;
        }
        return result;
    }
    public String nextExprToken(String input) {
        String result = input;
        int index = 1;
        int leftMinusRight = 1; // the number of [ minus the number of ]
        while (leftMinusRight > 0) {
            // if off the result's length, read more
            if(index >= result.length())
                result = String.format("%s %s", result, in.next());
            leftMinusRight += (result.charAt(index) == '(') ? 1 : 0;
            leftMinusRight -= (result.charAt(index) == ')') ? 1 : 0;
            index++;
        }
        return result;
    }
    public Operation interpretOperation (String token) {
        // 1. if it is inner operation, return type(see Operation class)
        // 2. if it has number/bool/list/word pattern, return Operation.VALUE
        // 3. otherwise, check whether it can be a name, if yes, return Operation.NAME
        Operation op = Operation.UNKNOWN;
        if (Operation.opMap.containsKey(token)) {
            op = Operation.opMap.get(token);
        } else if(commaPattern.matcher(token).matches()) {

        } else if(isValueLiteral(token)) {
            op = Operation.VALUE;
        } else if(namePattern.matcher(token).matches()){
            op = Operation.NAME;
        } // else UNKNOWN
        return op;
    }   
    private boolean isValueLiteral (String input) {
        return  listPattern.matcher(input).matches()
            ||  wordPattern.matcher(input).matches()
            ||  numberPattern.matcher(input).matches()
            ||  boolPattern.matcher(input).matches();
    }

    // Unit Test 
    public static void main (String[] args) {
        String input = "hello 2344 \"sdfe (2+3 / thing \"a + 9) [print \"you[sucks aloha ba 134245] [[[you]]]";
        Scanner in = new Scanner(input);
        MUAInterpreter interpreter = new MUAInterpreter(in);
        while (in.hasNext()) {
            System.out.println("Token: " + interpreter.nextToken());
        }
    }

}
