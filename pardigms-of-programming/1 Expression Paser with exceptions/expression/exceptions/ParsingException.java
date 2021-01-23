package expression.exceptions;

public class ParsingException extends EvaluatingException {
    public ParsingException(String message) {
        super(message);
    }
    static String getPlace(int ind, int size) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < ind; i++) {
            res.append(' ');
        }
        res.append('^');
        for (int i = 1; i < size; i++) {
            res.append('~');
        }
        return res.toString();
    }
}
