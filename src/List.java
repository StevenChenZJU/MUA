import java.util.ArrayList;
import java.util.Scanner;

public class List implements Value {
    private String content;
    private ArrayList<String> tokens = null;
    public List(String content) {
        this.content = content;
    }
    public static List newInstance(String content) {
        int length = content.length();
        if (!content.isEmpty() && content.charAt(0) == '[' && content.charAt(length-1) == ']') {
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
    public Word getWord() {
        return null;
    }

    @Override
    public Bool getBool() {
        return null;
    }

    @Override
    public Number getNumber() {
        return null;
    }

    @Override
    public List getList() {
        return List.newInstance("[ " + content + " ]");
    }

    @Override
    public String getContent() {
        return String.valueOf(content);
    }

    @Override
    public void setContent(String content) {
        this.content = String.valueOf(content);
    }

    @Override
    public String toString() {
        return getEmbracedContent();
    }

    /**
     * Methods Particular to List
     */
    public String getEmbracedContent() {
        return String.format("[%s]", content);
    }
    public boolean isEmpty() {
        return content.trim().isEmpty();
    }
    public ArrayList<String> getTokens() {
        MUAInterpreter interpreter = new MUAInterpreter(new Scanner(content));
        ArrayList<String> list = new ArrayList<String>();
        while (interpreter.hasNext()) {
            list.add(interpreter.nextToken());
        }
        return list;
    }
    private static List tokensToList(String[] arr, int first, int last) {
        // from arr[first] to arr[last](included)
        if (first < 0 || last >= arr.length || first > last) {
            //TODO exception
            return null;
        }
        StringBuffer buff = new StringBuffer("[ ");
        for (int i = first; i <= last; i++) {
            String str = arr[i];
            buff.append(str);
        }
        buff.append(" ]");
        return List.newInstance(buff.toString());
    }
    public Value first() {
        if (tokens == null) {
            tokens = getTokens();
        } 
        if (tokens.size() != 0)
            return Value.valueOf(tokens.get(0));
        else {
            // TODO: exception
            return null;
        }  
    }
    public Value last() {
        if (tokens == null) {
            tokens = getTokens();
        } 
        if (tokens.size() != 0)
            return Value.valueOf(tokens.get(tokens.size()-1));
        else {
            // TODO: exception
            return null;
        }  
    }
    public List butFirst() {
        if (tokens == null) {
            tokens = getTokens();
        } 
        if (tokens.size() == 0) {
            //TODO: exception
            return null;
        } else if (tokens.size() == 1) {
            return List.newInstance("[]");
        } else {
            return tokensToList(tokens.toArray(new String[0]), 1, tokens.size()-1);
        }
    }
    public List butLast() {
        if (tokens == null) {
            tokens = getTokens();
        } 
        if (tokens.size() == 0) {
            //TODO: exception
            return null;
        } else if (tokens.size() == 1) {
            return List.newInstance("[]");
        } else {
            return tokensToList(tokens.toArray(new String[0]), 0, tokens.size()-2);
        }
    }
    // 
    public List join(String s) {
        if (tokens == null) {
            tokens = getTokens();
        } 
        return List.newInstance(String.format("[ %s %s ]", content, s));
    }
}
