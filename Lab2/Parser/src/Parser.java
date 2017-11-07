import java.io.InputStream;

/**
 * Created by -- on 03.10.2017.
 */
public class Parser {
    private LexicalAnalyzer lex;

    Tree parse(InputStream is) throws ParseException {
        lex = new LexicalAnalyzer(is);
        lex.nextToken();
        return S();
    }

    private Tree next(Token t) throws ParseException {
        lex.nextToken();
        return new Tree(t.toString());
    }

    private Tree S() throws ParseException {
        if (lex.curToken() == Token.ID) {
            return new Tree("S", D(), Ss());
        } else {
            throw new ParseException("Expected ID at position ", lex.curPos());
        }
    }

    private Tree Ss() throws ParseException {
        if (lex.curToken() == Token.ID) {
            return new Tree("Ss", D(), Ss());
        } else if (lex.curToken() == Token.END) {
            return new Tree("Ss");
        } else {
            throw new ParseException("Expected ID or END at position ", lex.curPos());
        }
    }

    private Tree D() throws ParseException {
        if (lex.curToken() == Token.ID) {
            return new Tree("D", T(), L(), next(Token.SEMICOLON));
        } else {
            throw new ParseException("Expected ID at position ", lex.curPos());
        }
    }

    private Tree T() throws ParseException {
        if (lex.curToken() == Token.ID) {
            return new Tree("T", next(lex.curToken()));
        } else {
            throw new ParseException("Expected ID at position ", lex.curPos());
        }
    }

    private Tree L() throws ParseException {
        if (lex.curToken() == Token.ID || lex.curToken() == Token.ASTERISK) {
            return new Tree("L", V(), Ls());
        } else {
            throw new ParseException("Expected ID or * at position ", lex.curPos());
        }
    }

    private Tree Ls() throws ParseException {
        if (lex.curToken() == Token.COMMA) {
            return new Tree("Ls", next(Token.COMMA), V(), Ls());
        } else if (lex.curToken() == Token.SEMICOLON) {
            return new Tree("Ls");
        } else {
            throw new ParseException("Expected , or ; at position ", lex.curPos());
        }
    }

    private Tree V() throws ParseException {
        if (lex.curToken() == Token.ASTERISK) {
            return new Tree("V", next(Token.ASTERISK), V());
        } else if (lex.curToken() == Token.ID) {
            return new Tree("V", next(lex.curToken()));
        } else {
            throw new ParseException("Expected ID or * at position ", lex.curPos());
        }
    }
}
