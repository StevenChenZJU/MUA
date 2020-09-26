public class Bool extends Value {
    public Bool(String content) {
        super(content);
    }
    public static Bool newInstance(String content) {
        return new Bool(content);
    }
    @Override
    public boolean isWord() {
        return true;
    }
    @Override
    public boolean isBool() {
        return true;
    }
    @Override
    public boolean isNumber() {
        return false;
    }
    @Override
    public boolean isList() {
        return false;
    }
}
