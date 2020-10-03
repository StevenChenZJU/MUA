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
        return getContent();
    }

    /**
     * Methods Particular to Number
     */
    public Number add(Number n) {
        Double left = Double.parseDouble(number);
        Double right = Double.parseDouble(n.getContent());
        Double result = left + right;
        return Number.newInstance(result);        
    }
    public Number sub(Number n) {
        Double left = Double.parseDouble(number);
        Double right = Double.parseDouble(n.getContent());
        Double result = left - right;
        return Number.newInstance(result);        
    }
    public Number mul(Number n) {
        Double left = Double.parseDouble(number);
        Double right = Double.parseDouble(n.getContent());
        Double result = left * right;
        return Number.newInstance(result);
    }
    public Number div(Number n) {
        Double left = Double.parseDouble(number);
        Double right = Double.parseDouble(n.getContent());
        Double result = left / right;
        return Number.newInstance(result);
    }
    public Number mod(Number n) {
        // round to Integer before moding
        Double left = Double.parseDouble(number);
        Double right = Double.parseDouble(n.getContent());
        long longLeft = Math.round(left);
        long longRight = Math.round(right);
        Double result = (double) (longLeft % longRight);
        return Number.newInstance(result);            
    }

    /**
     * Unit Test
     */
    public static void main(String[] args) {
        Number a = Number.newInstance("26.");
        Number b = Number.newInstance("5");
        Number c = Number.newInstance(34.);
        System.out.println("Add:"+a.add(b));
        System.out.println("Sub:"+a.sub(b));
        System.out.println("Mul:"+a.mul(b));
        System.out.println("Div:"+c.div(b));
        System.out.println("Mod:"+a.mod(b));
    }
}
