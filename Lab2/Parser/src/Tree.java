import java.util.Arrays;
import java.util.List;

/**
 * Created by -- on 03.10.2017.
 */
public class Tree {
    private String node;
    private int id;
    private List<Tree> children;
    private static int count = 0;

    public Tree(String node, Tree... children) {
        this.node = node;
        id = ++count;
        this.children = Arrays.asList(children);
    }

    public Tree(String node) {
        this.node = node;
        id = ++count;
    }

    public List<Tree> getChildren() {
        return children;
    }

    public String getNode() {
        return node;
    }

    public int getId() {
        return id;
    }
}
