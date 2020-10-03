import java.util.regex.Pattern;

public interface Value {    
    public static Pattern numberPattern = Pattern.compile("-?\\d+\\.?\\d*");
    public static Pattern wordPattern = Pattern.compile("\".+");
    public static Pattern listPattern = Pattern.compile("\\[\\s*.+");
    public static Pattern boolPattern = Pattern.compile("true|false");
    boolean isWord();
    boolean isBool();
    boolean isNumber();
    boolean isList();
    Word    getWord();
    Bool    getBool();
    Number  getNumber();
    List    getList();
    String  getContent();
    void    setContent(String content);
    String  toString();

    public static boolean isValueLiteral (String input) {
        return  listPattern.matcher(input).matches()
            ||  wordPattern.matcher(input).matches()
            ||  numberPattern.matcher(input).matches()
            ||  boolPattern.matcher(input).matches();
    }
    public static Value valueOf (Value v) {
        if (v.isList()) {
            return v.getList();
        } else if(v.isBool()) {
            return v.getBool();
        } else if(v.isNumber()) {
            return v.getNumber();
        } else {
            return v.getWord();
        }
    }
    public static Value valueOf (String token) {
        if (listPattern.matcher(token).matches()) {
            return List.newInstance(token);
        } else if(boolPattern.matcher(token).matches()) {
            return Bool.newInstance(token);
        } else if(numberPattern.matcher(token).matches()) {
            return Number.newInstance(token);
        } else {
            return Word.newInstance(token);
        }
    }
}
