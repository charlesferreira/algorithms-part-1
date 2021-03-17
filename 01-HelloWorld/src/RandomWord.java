import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

/******************************************************************************
 *  Compilation:  javac RandomWord.java
 *  Execution:    java RandomWord
 *
 *  Reads a sequence of words from standard input and prints one of those
 *  words uniformly at random.
 *
 *  % java RandomWord
 *  %   heads tails
 *  tails
 *
 *  % java RandomWord
 *  %   heads tails
 *  heads
 *
 ******************************************************************************/

public class RandomWord {

    public static void main(String[] args) {
        String word, champion = "";
        for (int i = 1; !StdIn.isEmpty(); i++) {
            word = StdIn.readString();
            if (StdRandom.bernoulli(1.0 / i)) {
                champion = word;
            }

        }

        System.out.println(champion);
    }

}