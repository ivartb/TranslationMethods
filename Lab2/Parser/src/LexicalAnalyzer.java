import java.io.IOException;
import java.io.InputStream;

/**
 * Created by -- on 03.10.2017.
 */
public class LexicalAnalyzer {
    private InputStream is;
    private int curChar;
    private int curPos;
    private Token curToken;

    LexicalAnalyzer(InputStream is) throws ParseException {
        this.is = is;
        curPos = 0;
        nextChar();
    }

    private boolean isBlank(int c) {
        return Character.isWhitespace(c);
    }

    private void nextChar() throws ParseException {
        curPos++;
        try {
            curChar = is.read();
        } catch (IOException e) {
            throw new ParseException(e.getMessage(), curPos);
        }
    }

    private void skipBlank() throws ParseException {
        while (isBlank(curChar)) {
            nextChar();
        }
    }

    public void nextToken() throws ParseException {
        skipBlank();
        if (curChar == ',') {
            nextChar();
            curToken = Token.COMMA;
        } else if (curChar == ';') {
            nextChar();
            curToken = Token.SEMICOLON;
        } else if (curChar == '*') {
            nextChar();
            curToken = Token.ASTERISK;
        } else if (Character.isLetterOrDigit(curChar)) {
            while (Character.isLetterOrDigit(curChar)) {
                nextChar();
            }
            curToken = Token.ID;
        } else if (curChar == -1) {
            curToken = Token.END;
        } else {
            throw new ParseException("Illegal character found: " + (char) curChar, curPos);
        }
    }

    public Token curToken() {
        return curToken;
    }

    public int curPos() {
        return curPos;
    }
}
