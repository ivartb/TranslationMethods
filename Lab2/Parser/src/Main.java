import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by -- on 03.10.2017.
 */
public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("No input string found");
            return;
        }
        try (InputStream is = new ByteArrayInputStream(args[0].getBytes())) {
            Tree tree = new Parser().parse(is);
            new TreeWriter().print(tree);
            System.out.println("OK");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            System.err.println(e.getMessage());
        }
    }
}
