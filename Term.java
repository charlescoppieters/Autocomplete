/* *****************************************************************************
 *  Name:    Ethan Abraham
 *  NetID:   ea10
 *  Precept: P02
 *
 *  Partner Name:    Charles Coppieters 'T Wallant
 *  Partner NetID: cwallant
 *  Partner Precept: P06
 *
 *  Description:  Represents an autoccomplete term, which has an associated
 * string and a weight as a long
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class Term implements Comparable<Term> {
    private final String query; // the term our user might want
    private final long weight; // the likelihood our user wants this term

    // Initializes a term with the given query string and weight.
    public Term(String query, long weight) {
        if (query == null)
            throw new IllegalArgumentException("Query can't be null!");
        if (weight < 0)
            throw new IllegalArgumentException("Weight can't be negative!");
        this.query = query;
        this.weight = weight;
    }

    // Compares the two terms in descending order by weight.
    public static Comparator<Term> byReverseWeightOrder() {
        return new ReverseWeightOrder();
    }

    // overwriting compare method
    private static class ReverseWeightOrder implements Comparator<Term> {
        public int compare(Term test1, Term test2) {
            if (test1.weight < test2.weight)
                return 1;
            else if (test1.weight > test2.weight)
                return -1;
            return 0;
        }
    }

    // Compares the two terms in lexicographic order,
    // but using only the first r characters of each query.
    public static Comparator<Term> byPrefixOrder(int r) {
        return new PrefixOrder(r);
    }

    private static class PrefixOrder implements Comparator<Term> {
        private final int r; // length of prefix

        // initializes PrefixOrder object and our "r, prefix length"
        public PrefixOrder(int r) {
            if (r < 0)
                throw new IllegalArgumentException("r must be greater than 0");
            this.r = r;
        }

        // compares two terms based on their prefixes
        public int compare(Term test1, Term test2) {
            int max;
            // if prefix is longer than either query we only go to query length
            if (Math.min(test2.query.length(), test1.query.length()) < r)
                max = Math.min(test2.query.length(), test1.query.length());
            else
                max = r;
            // must be identical throughout prefix length
            for (int i = 0; i < max; i++) {
                if (test1.query.charAt(i) > test2.query.charAt(i))
                    return 1;
                else if (test1.query.charAt(i) < test2.query.charAt(i))
                    return -1;
            }
            // if prefix is identical but one query is longer
            if (r > max)
                return test1.query.compareTo(test2.query);
            return 0;
        }
    }

    // Compares the two terms in lexicographic order by query.
    public int compareTo(Term that) {
        return query.compareTo(that.query); // can just use Std String lib
    }

    // Returns a string representation of this term in the following format:
    // the weight, followed by a tab, followed by the query.
    public String toString() {
        return Long.toString(weight) + "\t" + query;
    }

    // unit testing (required)
    public static void main(String[] args) {
        Term testTerm1 = new Term("Charles", 100000);
        Term testTerm2 = new Term("Ethan", 99999);
        Term testTerm3 = new Term("The Sedge", 1000000000);
        StdOut.println("testTerm1 is lexico-equal to testTerm2? (0 for yes)" +
                               (testTerm1.compareTo(testTerm2)));
        Term[] termers = { testTerm1, testTerm2, testTerm3 };
        for (int i = 0; i < termers.length; i++)
            StdOut.println(termers[i].toString());
        Arrays.sort(termers, byReverseWeightOrder());
        for (int i = 0; i < termers.length; i++)
            StdOut.println(termers[i].toString());
        Term preTerm1 = new Term("apples", 1000);
        Term preTerm2 = new Term("application", 100);
        Term preTerm3 = new Term("applesandbannanas", 1100);
        Term preTerm4 = new Term("ape", 10000);
        Term[] pretermers = { preTerm1, preTerm2, preTerm3, preTerm4 };
        for (int i = 0; i < pretermers.length; i++)
            StdOut.println(pretermers[i].toString());
        Arrays.sort(termers, byPrefixOrder(3));
        for (int i = 0; i < pretermers.length; i++)
            StdOut.println(pretermers[i].toString());
    }

}
