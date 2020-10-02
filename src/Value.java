import java.util.regex.Pattern;

public class Value {    
    public static Pattern numberPattern = Pattern.compile("-?\\d+\\.?\\d*");
    public static Pattern wordPattern = Pattern.compile("\".+");
    public static Pattern listPattern = Pattern.compile("\\[\\s*.+");
    public static Pattern boolPattern = Pattern.compile("true|false");
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

    public static boolean isValueLiteral (String input) {
        return  listPattern.matcher(input).matches()
            ||  wordPattern.matcher(input).matches()
            ||  numberPattern.matcher(input).matches()
            ||  boolPattern.matcher(input).matches();
    }
}
