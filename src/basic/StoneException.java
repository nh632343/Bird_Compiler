package basic;
import node.ASTree;

public class StoneException extends RuntimeException {
    public StoneException(String m) { super(m); }
    public StoneException(String m, ASTree t) {
        super(m + " " + t.location());
    }
}
