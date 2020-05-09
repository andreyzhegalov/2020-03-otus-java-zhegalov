package hw05.aop.argparser;

import java.util.Vector;

public class ArgTypeParser {
    private final Vector<ArgType> args;

    public ArgTypeParser(String description) {
        args = parse(description);
    }

    public Vector<ArgType> getArgs() {
        return args;
    }

    public String getFullDescription() {
        StringBuilder result = new StringBuilder();
        for (final var arg : args) {
            result.append(arg.getTypeDesc());
        }
        return result.toString();
    }

    public int getFullSlotSize() {
        int result = 0;
        for (final var arg : args) {
            result += arg.getSlotSize();
        }
        return result;
    }

    static private Vector<ArgType> parse(String description) {
        var body = getBoby(description);
        var args = new Vector<ArgType>();
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
        return args;
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

    static private String getBoby(String description) {
        var startPos = description.indexOf('(');
        var endPos = description.indexOf(')');
        if ((-1 == startPos) || (-1 == endPos)) {
            throw new IllegalArgumentException("wrong method signature");
        }
        return description.substring(startPos + 1, endPos);
    }
}
