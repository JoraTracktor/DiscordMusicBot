package math;

import enums.LexemType;

public class Token {
    public String value;
    public LexemType type;

    public Token(String value, LexemType type) {
        this.value = value;
        this.type = type;
    }
}
