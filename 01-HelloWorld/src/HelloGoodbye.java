/******************************************************************************
 *  Compilation:  javac HelloGoodbye.java
 *  Execution:    java HelloGoodbye
 *
 *  Takes two names as command-line arguments and prints hello and goodbye
 *  messages.
 *
 *  % java HelloGoodbye Kevin Bob
 *  Hello Kevin and Bob
 *  Goodbye Bob and Kevin
 *
 ******************************************************************************/

public class HelloGoodbye {

    public static void main(String[] args) {
        // Checks there are two arguments at least
        if (args.length < 2)
            return;

        // Prints "Hello, Goodbye" to the terminal window.
        System.out.printf("Hello %s and %s.\n", args[0], args[1]);
        System.out.printf("Goodbye %s and %s.", args[1], args[0]);
    }

}