import java.util.regex.Pattern;

interface Value {    
    public static Pattern numberPattern = Pattern.compile("-?\\d+\\.?\\d*");
    public static Pattern wordPattern = Pattern.compile("\".+");
    public static Pattern listPattern = Pattern.compile("\\[\\s*.+");
    public static Pattern boolPattern = Pattern.compile("true|false");
    boolean isWord();
    boolean isBool();
    boolean isNumber();
    boolean isList();
    Value getWord();
    Value getBool();
    Value getNumber();
    Value getList();
    String getContent();
    void setContent(String content);

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
}
