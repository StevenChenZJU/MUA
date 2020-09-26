import java.util.regex.Pattern;

public class Word extends Value {
    private static Pattern numberPattern = Pattern.compile("-?\\d+\\.?\\d*");
    private static Pattern boolPattern = Pattern.compile("true|false");
    public Word(String content) {
        super(content);
    }
    static public Word newInstance(String content) {
        return new Word(content);
    }
    @Override
    public boolean isWord() {
        return true;
    }
    @Override
    public boolean isBool() {
        if (boolPattern.matcher(content).matches()) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean isNumber() {
        if (numberPattern.matcher(content).matches()) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean isList() {
        return false;
    }
}
