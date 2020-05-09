package hw05.aop.argparser;

import java.util.Vector;

public class ArgTypeParser {
    private final Vector<ArgType> args;
    private final String description;


    public ArgTypeParser(String description) {
        this.description = description;
        args = new Vector<>();
        parse();
    }

    private void parse() {
        var body = getBoby(description);

        var argSet = new SupportedArgTypes();
        for (int i = 0; i < body.length(); i++) {
            final char curSymbol = body.charAt(i);
            String typeDesc = String.valueOf(curSymbol);
            if (isStartObjectDesc(curSymbol)) {
                var semicolonInd = getEndIndexOfObjectDesc(i, body);
                typeDesc = body.substring(i, semicolonInd + 1); // include ;
                i = semicolonInd;
            }
            var argType = argSet.get(typeDesc);
            if (null == argType) {
                throw new IllegalArgumentException("Unsuported arg type");
            }
            args.add(argType);
        }
    }

    static private int getEndIndexOfObjectDesc(int startInd, String body) {
        var semicolonInd = body.indexOf(';', startInd);
        if (-1 == semicolonInd) {
            throw new IllegalArgumentException("Wrong Object type description");
        }
        return semicolonInd;
    }

    static private boolean isStartObjectDesc(char symbol) {
        return 'L' == symbol;
    }

    Vector<ArgType> getArgs() {
        return args;
    }

    static private String getBoby(String description) {
        var startPos = description.indexOf('(');
        var endPos = description.indexOf(')');
        if ((-1 == startPos) || (-1 == endPos)) {
            throw new IllegalArgumentException("wrong method signature");
        }
        return description.substring(startPos + 1, endPos);
    }
}

