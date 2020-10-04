public class Mua {
    public static void main(String[] args) {
        MUAInterpreter interpreter = new MUAInterpreter(Environment.stdin);
        MUAExecutor executor = new MUAExecutor();
        String token;
        Value v;
        while (true) {
            if (interpreter.hasNext()) {
                token = interpreter.nextToken();
                v = executor.execute(token, interpreter);
            } else {
                break;
            }
        }
    }
}