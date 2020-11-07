package math;

import enums.LexemType;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    private List<Token> tokens = new ArrayList<>();
    private final int error = -1;

    int VAR(int i) { return checkTerminal(i, LexemType.VAR); }
    int PREFIX_FUNC(int i) { return checkTerminal(i, LexemType.PREFIX_FUNC); }
    int BINARY_OP(int i) { return checkTerminal(i, LexemType.BINARY_OP); }
    int ASSIGN_OP(int i) { return checkTerminal(i, LexemType.ASSIGN_OP); }
    int DIGIT(int i) { return checkTerminal(i, LexemType.DIGIT); }
    int L_BRACE(int i) { return checkTerminal(i, LexemType.L_BRACE); }
    int R_BRACE(int i) { return checkTerminal(i, LexemType.R_BRACE);}
    int UNARY_OP(int i) { return checkTerminal(i, LexemType.UNARY_OP);}


    public boolean parseTokens(List<Token> tokens) {
        this.tokens = tokens;
        return go() == error;
    }

    private int checkTerminal(int i, LexemType type) {
        Token token = tokens.get(i);
//        System.out.println("i = " + i);
//        System.out.println("now type:" + type);
//        System.out.println("exepted type:" + token.type);
//        System.out.println("exepted value type:" + token.value);
//        System.out.println("__________________________________");
        if (token.type == type) {
            return i + 1;
        } else {
            String message = "Expected " + type + ", got: " + token.type;
            throw new RuntimeException(message);
        }
    }

    private int go() {
        int i = 0;
        while (i < tokens.size()) {
            i = expr(i);
            if (i == error){
                break;
            }
        }
        return i;
    }

    private int expr (int i) {
        while (true) {
            try {
                return assign(i);
            } catch (Exception e1) {
                try {
                    return mathExpr(i);
                } catch (Exception e2) {
                   return error;
                }
            }
        }
    }

    private int assign (int i) {
        i = VAR(i);
        i = ASSIGN_OP(i);
        i = mathExpr(i);
        return i;
    }

    private int mathExpr (int i) {
        i = subExpr(i);
        try {
            while (true) {
                i = BINARY_OP(i);
                i = subExpr(i);
            }
        } catch (Exception e) {
            return i;
        }
}

    private int subExpr (int i) {
        try {
            return value(i);
        } catch (Exception e){
            return braces(i);
        }
    }

    private int value (int i) {
        try {
            return DIGIT(i);
        } catch (Exception e1) {
            try {
                return VAR(i);
            } catch ( Exception e2){
                return func(i);
            }
        }
    }

    private int func (int i) {
        i = PREFIX_FUNC(i);
        i = braces(i);
        return i;
    }

    private int braces(int i) {
        i = L_BRACE(i);
        try {
            i = UNARY_OP(i);
            i = mathExpr(i);
            i = R_BRACE(i);
            return i;
        } catch (Exception e) {
            i = mathExpr(i);
            i = R_BRACE(i);
            return i;
        }
    }
}
