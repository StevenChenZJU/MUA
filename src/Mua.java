import java.util.Scanner;

public class Mua {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        MUAInterpreter interpreter = new MUAInterpreter(in);
        while (true) {
            // execute one operation each time
            // wrap eval() and apply() in execute()
            MUAExecutor.execute(interpreter);
        }
    }
}