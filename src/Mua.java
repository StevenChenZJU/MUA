import java.util.Scanner;

public class Mua {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        MUAInterpreter interpreter = new MUAInterpreter(in);
        MUAExecutor executor = new MUAExecutor();
        while (true) {
            String token = interpreter.nextToken();
            Value v = executor.execute(token, interpreter);
        }
    }
}