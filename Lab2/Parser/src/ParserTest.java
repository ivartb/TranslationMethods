import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.Random;

/**
 * Created by -- on 04.10.2017.
 */
public class ParserTest {
    private Random random = new Random(239);

    @Test
    public void test01_oneVar() throws ParseException {
        System.out.print("oneVar tests: ");
        baseTest(1, 1, false);
        System.out.println("OK");
    }

    @Test
    public void test02_manyVars() throws ParseException {
        System.out.print("manyVars tests: ");
        baseTest(100, 1, false);
        System.out.println("OK");
    }

    @Test
    public void test03_pointers() throws ParseException {
        System.out.print("pointers tests: ");
        baseTest(1, 1, true);
        System.out.println("OK");
    }

    @Test
    public void test04_manyTypes() throws ParseException {
        System.out.print("manyTypes tests: ");
        baseTest(1, 100, false);
        System.out.println("OK");
    }

    @Test
    public void test05_random() throws ParseException {
        System.out.print("random tests: ");
        baseTest(1000, 1000, true);
        System.out.println("OK");
    }

    private void baseTest(int vars, int types, boolean pointers) throws ParseException {
        for (int i = 0; i < 100; i++) {
            StringBuilder test = new StringBuilder();
            for (int j = 0; j < random.nextInt(types) + 1; j++) {
                test.append(definition(vars, pointers));
            }
            new Parser().parse(new ByteArrayInputStream(test.toString().getBytes()));
        }
    }

    private static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String lower = upper.toLowerCase();
    private static final String digits = "0123456789";
    private final char[] alphabet = (upper + lower + digits).toCharArray();

    private String definition(int vars, boolean pointers) {
        StringBuilder def = new StringBuilder();
        def.append(id()).append(" ");
        for (int i = 0; i < vars; i++) {
            def.append(var(pointers)).append(", ");
        }
        def.delete(def.length() - 2, def.length());
        def.append(";\n");
        return def.toString();
    }

    private String var(boolean pointers) {
        StringBuilder var = new StringBuilder();
        if (pointers) {
            for (int i = 0; i < random.nextInt(5); i++) {
                var.append("*");
            }
        }
        var.append(id());
        return var.toString();
    }

    private String id() {
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            id.append(alphabet[random.nextInt(alphabet.length)]);
        }
        return id.toString();
    }
}