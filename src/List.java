public class List implements Value {
    private String content;
    public List(String content) {
        this.content = content;
    }
    public static List newInstance(String content) {
        int length = content.length();
        if (content.charAt(0) == '[' && content.charAt(length-1) == ']') {
            return new List(content.substring(1, length-1));
        } else {
            return new List(content);
        }
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

    @Override
    public Value getWord() {
        return null;
    }

    @Override
    public Value getBool() {
        return null;
    }

    @Override
    public Value getNumber() {
        return null;
    }

    @Override
    public Value getList() {
        return List.newInstance(content);
    }

    @Override
    public String getContent() {
        return String.valueOf(content);
    }

    @Override
    public void setContent(String content) {
        this.content = String.valueOf(content);
    }

    public String getEmbracedContent() {
        return String.format("[%s]", content);
    }

    @Override
    public String toString() {
        return "List:" + getEmbracedContent();
    }
}
