import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    private final Map<String, String> symbols = new HashMap<>();

    public void addSymbol(String name, String type) {
        symbols.put(name, type);
    }

    public String getType(String name) {
        return symbols.get(name);
    }

    public boolean contains(String name) {
        return symbols.containsKey(name);
    }
}
