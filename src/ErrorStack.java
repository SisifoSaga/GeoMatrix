import java.util.ArrayList;
import java.util.List;

public class ErrorStack {
    private final List<String> errors;

    public ErrorStack() {
        this.errors = new ArrayList<>();
    }

    public void addError(String error) {
        errors.add(error);
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public List<String> getErrors() {
        return errors;
    }

    public void printErrors() {
        for (String error : errors) {
            System.err.println(error);
        }
    }
}
