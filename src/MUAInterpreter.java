import java.util.Scanner;
import java.util.regex.Pattern;

public class MUAInterpreter {
    private static Pattern listPattern = Pattern.compile("\\[.*");
    private static Pattern exprPattern = Pattern.compile("\\(.*");

    private Scanner in = new Scanner("");
    public MUAInterpreter (Scanner in) {
        this.in = in;
    }
    public boolean hasNext() {
        return in.hasNext();
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
            if(index >= result.length()) {
                result = String.format("%s %s", result, in.next());
            }
            leftMinusRight += (result.charAt(index) == '(') ? 1 : 0;
            leftMinusRight -= (result.charAt(index) == ')') ? 1 : 0;
            index++;
        }
        return result;
    }


    // Unit Test 
    public static void main (String[] args) {
        String input = "hello 2344 \"sdfe (2 + 3 / thing \"a + 9) [print \"you[sucks aloha ba 134245] [[[you]]]";
        Scanner in = new Scanner(input);
        MUAInterpreter interpreter = new MUAInterpreter(in);
        while (in.hasNext()) {
            System.out.println("Token: " + interpreter.nextToken());
        }
    }

}
