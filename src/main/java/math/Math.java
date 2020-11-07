package math;

import bot.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Math {

    private Lexer lexer;
    private Parser parser;
    private final Map<Long, PolishNotation> polishs = new HashMap<>();

    public Math() {
        this.lexer = new Lexer();
        this.parser = new Parser();
    }

    public String calculate(Context context) {
        PolishNotation polishNotation = getPolish(context.getEvent().getGuild().getIdLong());

        String expression = context.getArgs();
        String answer = "Result: ";
        List<Token> tokenList = lexer.analyze(expression);

        if (tokenList.size() == 0 || parser.parseTokens(tokenList)){
            answer += "Invalid expression!";
        } else {
            answer += polishNotation.compute(tokenList);
        }
        return answer;
    }

    public String clearVars(Context context){
        PolishNotation polishNotation = getPolish(context.getEvent().getGuild().getIdLong());
        return polishNotation.clear();
    }

    public String printVars(Context context) {
        PolishNotation polishNotation = getPolish(context.getEvent().getGuild().getIdLong());
        return polishNotation.getVars();
    }

    private synchronized PolishNotation getPolish(long id) {
        PolishNotation polishNotation = polishs.get(id);
        if (polishNotation == null) {
            polishNotation = new PolishNotation();
            polishs.put(id, polishNotation);
        }
        return polishNotation;
    }
}
