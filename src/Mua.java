public class Mua {
    public static void main(String[] args) {
        MUAInterpreter interpreter = new MUAInterpreter(Environment.stdin);
        MUAExecutor executor = new MUAExecutor();
        while (true) {
            String token = interpreter.nextToken();
            Value v = executor.execute(token, interpreter);
        }
    }
}