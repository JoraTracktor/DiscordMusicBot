package math;

import java.util.List;

public class Math {

    private Lexer lexer;
    private Parser parser;
    private PolishNotation polishNotation;

    public Math() {
        this.lexer = new Lexer();
        this.parser = new Parser();
        this.polishNotation = new PolishNotation();
    }

    public String calculate(String expression){
        String answer = "Result: ";
        List<Token> tokenList = lexer.analyze(expression);

        if (tokenList.size() == 0 || parser.parseTokens(tokenList)){
            answer += "Invalid expression!";
        } else {
            answer += polishNotation.compute(tokenList);
        }
        return answer;
    }

    public String clearVars(){
        return polishNotation.clear();
    }

    public String printVars() {
        return polishNotation.getVars();
    }
}
