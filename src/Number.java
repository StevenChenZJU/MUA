public class Number implements Value {
    private String number;
    public Number(String number) {
        this.number = number;
    }
    public Number(Double number) {
        this.number = String.valueOf(number);
    }
    public static Number newInstance(String content) {
        return new Number(content);
    }
    public static Number newInstance(Double content) {
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
    public Word getWord() {
        return Word.newInstance(number);
    }
    @Override
    public Bool getBool() {
        return null;
    }
    @Override
    public Number getNumber() {
        return Number.newInstance(number);
    }
    @Override
    public List getList() {
        return null;
    }

    @Override
    public String getContent() {
        return String.valueOf(number);
    }

    @Override
    public void setContent(String content) {
        number = String.valueOf(content);
    }
    @Override
    public String toString() {
        return "Number:" + getContent();
    }

    /**
     * Methods Particular to Number
     */
    public Number add(Number n) {
        //TODO
        return null;        
    }
    public Number sub(Number n) {
        //TODO
        return null;        
    }
    public Number mul(Number n) {
        //TODO
        return null;        
    }
    public Number div(Number n) {
        //TODO
        return null;        
    }
    public Number mod(Number n) {
        //TODO
        return null;            
    }
}
