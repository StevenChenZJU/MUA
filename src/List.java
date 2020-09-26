public class List extends Value {
    public List(String content) {
        super(content);
    }
    public static List newInstance(String content) {
        return new List(content);
    }
    @Override
    public boolean isWord() {
        return false;
    }
    @Override
    public boolean isBool() {
        return false;
    }
    @Override
    public boolean isNumber() {
        return false;
    }
    @Override
    public boolean isList() {
        return true;
    }
}
