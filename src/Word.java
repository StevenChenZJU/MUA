public class Word implements Value {
    private String word; // not containing "
    public Word(String word) {
        this.word = word;
    }
    static public Word newInstance(String content) {
        if(!content.isEmpty() && content.charAt(0) != '\"')
            return new Word(content);
        else 
            // remove the \"
            return new Word(content.substring(1));
    }
    @Override
    public boolean isWord() {
        return true;
    }
    @Override
    public boolean isBool() {
        if (boolPattern.matcher(word).matches()) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean isNumber() {
        if (numberPattern.matcher(word).matches()) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean isList() {
        // if (listPattern.matcher(word).matches()) {
        //     return true;
        // } else {
        //     return false;
        // }
        return false;
    }
    @Override
    public Word getWord() {
        return Word.newInstance(word);
    }
    @Override
    public Bool getBool() {
        if (isBool()) {
            return Bool.newInstance(word);
        } else {
            return null;
        }
    }
    @Override
    public Number getNumber() {
        if (isNumber()) {
            return Number.newInstance(word);
        } else {
            return null;
        }
    }
    @Override
    public List getList() {
        // if (isList()) {
        //     return List.newInstance(word);
        // } else {
        //     return null;
        // }
        return null;
    }

    @Override
    public String getContent() {
        return String.valueOf(word);
    }

    @Override
    public void setContent(String content) {
        word = String.valueOf(content);
    }
    @Override
    public String toString() {
        return getContent();
    }
    /**
     * Method specific to Word
     */
    public boolean greaterThan(Word n) {
        String left = word;
        String right = n.getContent();
        return left.compareTo(right) > 0;
    }
    public boolean lessThan(Word n) {
        String left = word;
        String right = n.getContent();
        return left.compareTo(right) < 0;
    }
    public boolean equalWith(Word n) {
        String left = word;
        String right = n.getContent();
        return left.equals(right);
    }
}
