public class Bool implements Value {
    private String bool;
    public Bool(String bool) {
        this.bool = bool;
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

    @Override
    public Word getWord() {
        return Word.newInstance(bool);
    }

    @Override
    public Bool getBool() {
        return Bool.newInstance(bool);
    }

    @Override
    public Number getNumber() {
        return null;
    }

    @Override
    public List getList() {
        return null;
    }

    @Override
    public String getContent() {
        return String.valueOf(bool);
    }

    @Override
    public void setContent(String content) {
        bool = String.valueOf(content);
    }
    @Override
    public String toString() {
        return getContent();
    }
}
