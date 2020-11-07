package enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LexemType {

    //FUNC_NAME ("^[A-Z][a-z]*"),
    PREFIX_FUNC ("^sin|cos|tan|abs|exp|log|log10|round|sqrt|cbrt"),
    VAR ("^[a-z][a-zA-Z0-9]*"),
    BINARY_OP ("^[-*/^+!%]"),
    ASSIGN_OP ("^="),
    DIGIT ("^\\d+"),
    L_BRACE ("^\\("),
    R_BRACE ("^\\)"),
    UNARY_OP("^u+|^u-");

    Pattern pattern;

    private LexemType(String regex) {
        pattern = Pattern.compile(regex);
    }

    public String getMatch(String line) {
        Matcher matcher  = pattern.matcher(line);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return null;
    }
}
