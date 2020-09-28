public class Value {
    protected String content;
    protected Value(String content) {
        this.content = content;
    }
    public boolean isWord() {
        return false;
    }
    public boolean isBool() {
        return false;
    }
    public boolean isNumber() {
        return false;
    }
    public boolean isList() {
        return false;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
