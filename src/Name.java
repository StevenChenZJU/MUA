public class Name {
    private Word name;
    public Name(Word name) {
        this.name = name;
    }
    public static Name newInstance(String name) {
        return new Name(Word.newInstance(name));
    }

    public Word getWord() {
        return Word.newInstance(name.getContent());
    }
}
