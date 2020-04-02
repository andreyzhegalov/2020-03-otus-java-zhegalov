/*
 * 
 */
package hw01.gradle;

/**
 *
 *  Example usage custom class
 */
public class App {
    public static void main(String... args) {
        if (args.length != 1) {
            System.out.println("Should be one input argument");
            return;
        }

        int value= Integer.valueOf(args[0]);
        HelloOtus helloOtus= new HelloOtus(value);
        System.out.println(String.format("Factorial %d equals %d", value, helloOtus.getFactorial()));
    }
}
