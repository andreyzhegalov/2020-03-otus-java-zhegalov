package hw05.aop.argparser;

import java.util.List;
import java.util.ArrayList;

public class ArgTypeParser {
    private final List<ArgType> args;

    public ArgTypeParser(final String description) {
        args = parse(description);
    }

    public List<ArgType> getArgs() {
        return args;
    }

    public String getFullDescription() {
        final StringBuilder result = new StringBuilder();
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

    static private List<ArgType> parse(final String description) {
        final var body = getBoby(description);
        final var args = new ArrayList<ArgType>();
        for (int i = 0; i < body.length(); i++) {
            final char curSymbol = body.charAt(i);
            String typeDesc = String.valueOf(curSymbol);
            if (isStartObjectDesc(curSymbol)) {
                final var semicolonInd = getEndIndexOfObjectDesc(i, body);
                typeDesc = body.substring(i, semicolonInd + 1); // include ;
                i = semicolonInd;
            }
            final var argType = SupportedArgTypes.get(typeDesc);
            if (null == argType) {
                throw new IllegalArgumentException("Unsuported arg type");
            }
            args.add(argType);
        }
        return args;
    }

    static private int getEndIndexOfObjectDesc(final int startInd, final String body) {
        final var semicolonInd = body.indexOf(';', startInd);
        if (-1 == semicolonInd) {
            throw new IllegalArgumentException("Wrong Object type description");
        }
        return semicolonInd;
    }

    static private boolean isStartObjectDesc(final char symbol) {
        return 'L' == symbol;
    }

    static private String getBoby(final String description) {
        final var startPos = description.indexOf('(');
        final var endPos = description.indexOf(')');
        if (-1 == startPos || -1 == endPos) {
            throw new IllegalArgumentException("wrong method signature");
        }
        return description.substring(startPos + 1, endPos);
    }
}
