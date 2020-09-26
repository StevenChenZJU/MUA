public class Number extends Value {
    public Number(String content) {
        super(content);
    }
    public static Number newInstance(String content) {
        return new Number(content);
    }
    @Override
    public boolean isWord() {
        return true;
    }
    @Override
    public boolean isBool() {
        return false;
    }
    @Override
    public boolean isNumber() {
        return true;
    }
    @Override
    public boolean isList() {
        return false;
    }
}
