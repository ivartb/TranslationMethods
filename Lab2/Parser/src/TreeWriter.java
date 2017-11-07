import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by -- on 03.10.2017.
 */
public class TreeWriter {
    private FileWriter out;

    void print(Tree t) throws IOException {
        out = new FileWriter("tree.dot");
        out.write("digraph {");
        out.write(System.lineSeparator());
        printNode(t);
        out.write("}");
        out.close();
    }

    private void printNode(Tree t) throws IOException {
        out.write(t.getId() + "[label = \"" + t.getNode() + "\"]");
        out.write(System.lineSeparator());
        if (t.getChildren() == null) {
            return;
        }
        for (Tree child : t.getChildren()) {
            out.write(t.getId() + " -> " + child.getId() + ";");
            out.write(System.lineSeparator());
            printNode(child);
        }
    }
}
