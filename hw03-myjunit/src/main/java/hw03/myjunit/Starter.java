package hw03.myjunit;

public class Starter {
    public static void main(final String[] args) throws ClassNotFoundException {
        final var clazz = Class.forName(args[0]);
        final var myjunit = new MyJunit(clazz);
        myjunit.run();
        System.out.println(myjunit.getReport());
    }
}
