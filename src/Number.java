public class Number implements Value {
    private String number;
    public Number(String number) {
        this.number = number;
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
    @Override
    public Value getWord() {
        return Word.newInstance(number);
    }
    @Override
    public Value getBool() {
        return null;
    }
    @Override
    public Value getNumber() {
        return Number.newInstance(number);
    }
    @Override
    public Value getList() {
        return null;
    }

    @Override
    public String getContent() {
        return number;
    }

    @Override
    public void setContent(String content) {
        number = content;
    }
}
