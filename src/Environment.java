import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class Environment {
    private static HashMap<String, Value> global
     = new HashMap<String, Value>();
    private static Stack<HashMap<String, Value>> localStack 
     = new Stack<HashMap<String, Value>>();
    public static Scanner stdin = new Scanner(System.in);
    
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
}
