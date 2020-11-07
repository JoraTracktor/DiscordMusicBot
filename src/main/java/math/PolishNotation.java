package math;

import enums.LexemType;

import java.util.*;

public class PolishNotation {

    private HashMap<String, String> variables  = new HashMap<>();
    private Deque<Token> polish = new ArrayDeque<>();
    private Deque<Token> polishStack = new ArrayDeque<>();
    private Deque<Double> resultStack = new ArrayDeque<>();

    public String compute(List<Token> tokens) {
        polish.clear();
        polishStack.clear();
        resultStack.clear();
        String answer = "";
        if (tokens.get(1).type == LexemType.ASSIGN_OP) {
            String varName = tokens.get(0).value;
            String varValue;
            tokens.remove(0);
            tokens.remove(0);

            toPolishNotation(tokens);
            varValue = calcExpr();
            if (varValue.matches("\\d+")){
                variables.put(varName, varValue);
                answer = String.format("Var %s = %s was added!", varName, varValue);
            } else {
                answer = varValue;
            }
        } else {
            toPolishNotation(tokens);
            answer = calcExpr();
        }
       //tokens.forEach( e -> System.out.println(e.value + " " + e.type));
       return answer;
    }

    public String clear() {
        variables.clear();
        return "Variables was deleted!";
    }

    public String getVars() {
        if (variables.size() == 0) {
            return "No variables!";
        } else {
            StringBuilder vars = new StringBuilder();
            for(Map.Entry<String, String> entry : variables.entrySet()) {
                vars.append(String.format("%s = %s\n", entry.getKey(), entry.getValue()));
            }
            return vars.toString();
        }
    }

    private void toPolishNotation(List<Token> tokens) {
        for (Token token : tokens) {
            if (token.type == LexemType.VAR || token.type ==LexemType.DIGIT) {
                polish.addLast(token);
            } else if (token.type == LexemType.PREFIX_FUNC || token.type == LexemType.L_BRACE) {
                polishStack.push(token);
            } else if (token.type == LexemType.R_BRACE) {
                while (true) {
                    Token mathSymbol = polishStack.pop();
                    if (mathSymbol.type == LexemType.L_BRACE) {
                        break;
                    }
                    polish.addLast(mathSymbol);
                }
            } else if (token.type == LexemType.BINARY_OP || token.type == LexemType.UNARY_OP) {
                while (!polishStack.isEmpty()) {
                    Token mathSymbol = polishStack.getFirst();
                    if (!(token.type == LexemType.UNARY_OP) && getPriority(mathSymbol) >= getPriority(token)) {
                        polish.addLast(polishStack.pop());
                    } else {
                        break;
                    }
                }
                polishStack.push(token);
            }
        }
        while (!polishStack.isEmpty()){
            polish.addLast(polishStack.pop());
        }
//        polish.forEach(e -> System.out.println(e.value));
    }

    private int getPriority(Token operation){
        int priority;
        switch (operation.type){
            case PREFIX_FUNC: priority = 5; break;
            case UNARY_OP: priority = 4; break;
            case BINARY_OP: priority = getBinaryPriority(operation.value); break;
            case L_BRACE:
            case R_BRACE: priority = 0; break;
            default:
                throw new IllegalStateException("Unexpected operation: " + operation);
        }
        return priority;
    }

    private int getBinaryPriority(String value) {
        if (value.matches("[+-]")) {
            return 1;
        } else if (value.matches("[*/]")) {
            return 2;
        } else {
            return 3;
        }
    }

    private String calcExpr(){
        String result;
        String error = "Invalid expression!";

        for (Token token: polish){
            if (token.type == LexemType.VAR){
                if (variables.containsKey(token.value)) {
                    resultStack.push(Double.parseDouble(variables.get(token.value)));
                } else {
                    result = "Var no exist!";
                    return result;
                }
            } else if (token.type == LexemType.DIGIT) {
                resultStack.push(Double.parseDouble(token.value));
            } else if (token.type == LexemType.UNARY_OP) {
                if (resultStack.size() == 0) {
                    result = error;
                    return result;
                }
                double op = resultStack.pop();
                switch (token.value) {
                    case "+": resultStack.push(op); break;
                    case "-": resultStack.push(0 - op); break;
                }

            } else if (token.type == LexemType.PREFIX_FUNC) {
//                if (resultStack.size() == 0) {
//                    result = error;
//                    return result;
//                }
                double op = resultStack.pop();

                switch (token.value){
                    case "sin": resultStack.push(java.lang.Math.sin(op)); break;
                    case "cos": resultStack.push(java.lang.Math.cos(op)); break;
                    case "tan": resultStack.push(java.lang.Math.tan(op)); break;
                    case "abs": resultStack.push(java.lang.Math.abs(op)); break;
                    case "exp": resultStack.push(java.lang.Math.exp(op)); break;
                    case "log": resultStack.push(java.lang.Math.log(op)); break;
                    case "log10": resultStack.push(java.lang.Math.log10(op)); break;
                    case "round": resultStack.push((double) java.lang.Math.round(op)); break;
                    case "sqrt": resultStack.push(java.lang.Math.sqrt(op)); break;
                    case "cbrt": resultStack.push(java.lang.Math.cbrt(op)); break;
                }
            } else if (token.type == LexemType.BINARY_OP){

                if (resultStack.size() < 2){
                    result = error;
                    return result;
                }

                double op1 = resultStack.pop();
                double op2 = resultStack.pop();

                switch (token.value) {
                    case "+": resultStack.push(op2 + op1); break;
                    case "-": resultStack.push(op2 - op1); break;
                    case "*": resultStack.push(op2 * op1); break;
                    case "/": resultStack.push(op2 / op1); break;
                    case "^": resultStack.push(java.lang.Math.pow(op2, op1)); break;
                    case "%": resultStack.push(op2 % op1); break;
                }
            } else {
                result = error + " " + token;
                return result;
            }
        }
        result = resultStack.size() == 0 ? error :  String.valueOf((int) java.lang.Math.round(resultStack.pop()));
        return result;
    }
}
