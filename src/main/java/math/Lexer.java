package math;

import enums.LexemType;

import java.util.ArrayList;
import java.util.List;

public class Lexer {

    public List<Token> analyze(String expression) {
        ArrayList<Token> tokenList = new ArrayList<>();

        expression = expression.replaceAll("\\s", "");
        LexemType lastType = null;

        while (true) {
            boolean found = false;
            for (LexemType type : LexemType.values()) {
                String value = type.getMatch(expression);
                if (value != null) {
                    if (value.matches("[+-]") && lastType == LexemType.L_BRACE) {
                        type = LexemType.UNARY_OP;
                    }
                    lastType = type;
                    tokenList.add(new Token(value, type));
                    found = true;
                    expression = expression.substring(value.length());
                    break;
                }
            }
            if (!found) {
                break;
            }
        }
//        tokenList.forEach(t -> System.out.print(t.value +  " : " + t.type + " "));
        return tokenList;
    }
}
