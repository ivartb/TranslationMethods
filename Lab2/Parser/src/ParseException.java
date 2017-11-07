/**
 * Created by -- on 03.10.2017.
 */
public class ParseException extends java.text.ParseException {
    /**
     * Constructs a ParseException with the specified detail message and
     * offset.
     * A detail message is a String that describes this particular exception.
     *
     * @param s           the detail message
     * @param errorOffset the position where the error is found while parsing.
     */
    public ParseException(String s, int errorOffset) {
        super(s, errorOffset);
    }

    public String getMessage() {
        return super.getMessage() + getErrorOffset();
    }
}
