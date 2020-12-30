import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

public class Environment {
    
    private static HashMap<String, Value> global
     = new HashMap<String, Value>();
    private static Stack<HashMap<String, Value>> localStack 
     = new Stack<HashMap<String, Value>>();
    public static Scanner stdin = new Scanner(System.in);
    
    static {
        global.put("pi", Value.valueOf("3.14159"));
    }

    public static void pushLocalFrame(HashMap<String, Value> localFrame) {
        localStack.push(localFrame);
    }
    public static HashMap<String, Value> popLocalFrame() {
        return localStack.pop();
    }
    private static HashMap<String, Value> getLocalEnv() {
        if (localStack.isEmpty()) {
            return null;
        } else {
            return localStack.peek();
        }
    }
    public static void binding(String name, Value value) {
        if (localStack.isEmpty()) {
            global.put(name, value);
        } else {
            getLocalEnv().put(name, value);
        }
    }
    public static void export(String name) {
        HashMap<String, Value> local = getLocalEnv();
        if (local != null && local.containsKey(name)) {
            global.put(name, local.get(name));
        } 
    }
    public static boolean hasLocal(String name) {
        HashMap<String, Value> local = getLocalEnv();
        if (local != null && local.containsKey(name)) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean hasGlobal(String name) {
        return global.containsKey(name);
    }
    public static Value getLocalValue(String name) {
        HashMap<String, Value> local = getLocalEnv();
        if (local != null && local.containsKey(name)) {
            return local.get(name);
        } else {
            return null;
        }
    }
    public static Value getGlobalValue(String name) {
        if (hasGlobal(name)) {
            return global.get(name);
        } else {
            return null;
        }
    }
    public static Value getValue(String name) {
        if (hasLocal(name)) {
            return getLocalValue(name);
        } else if (hasGlobal(name)) {
            return getGlobalValue(name);
        } else {
            System.out.println("CANNOT FIND: " + name);
            return null;
        }
    }
    public static Value eraseLocal(String name) {
        HashMap<String, Value> local = getLocalEnv();
        return local.remove(name);
    }
    public static Value eraseGlobal(String name) {
        return global.remove(name);
    }
    public static Value erase(String name) {
        if (hasLocal(name)) {
            return eraseLocal(name);
        } else {
            return eraseGlobal(name);
        }
    }
    public static void save(String fname) {
        HashMap<String, Value> namespace = null;
        if (localStack.isEmpty()) {
            namespace = global;
        } else {
            namespace = localStack.peek();
        }
        Set<String> keySet = namespace.keySet();
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fname)));
            for (String key : keySet) {
                String str = namespace.get(key).toString();
                if (namespace.get(key).isBool() || namespace.get(key).isWord())
                    str = "\"" + str;
                writer.write(String.format("make \"%s %s\n", key, str));
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Cannot find file: " + fname);
            e.printStackTrace();
        }
    }
    public static void eraseAll() {
        if (!localStack.isEmpty()) {
            localStack.peek().clear();
        } else {
            global.clear();
        }
    }
    public static void postAll() {
        HashMap<String, Value> namespace = null;
        if (localStack.isEmpty()) {
            namespace = global;
        } else {
            namespace = localStack.peek();
        }
        Set<String> keySet = namespace.keySet();
        for (String key : keySet) {
            System.out.println(String.format("%s = %s", key, namespace.get(key).toString()));
        }
    }
}
