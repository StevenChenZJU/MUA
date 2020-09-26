import java.util.HashMap;

public class Environment {
    HashMap<Name, Value> env;

    public void binding(Name name, Value value) {
        env.put(name, value);
    }
    public boolean hasName(Name name) {
        return (env.get(name) != null);
    }
    public Value getValue(Name name) {
        return env.get(name);
    }
}
