/* *****************************************************************************
 *  Name:    Ethan Abraham
 *  NetID:   ea10
 *  Precept: P02
 *
 *  Partner Name:    Charles Coppieters 'T Wallant
 *  Partner NetID: cwallant
 *  Partner Precept: P06
 *
 *  Description:  Provides autocomplete functionality for a given set of Strings
 * and weights using Term and Binary Seach Deluxe
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Autocomplete {
    private final Term[] terms; // the data structure containing all out terms

    // Initializes the data structure from the given array of terms.
    public Autocomplete(Term[] terms) {
        if (terms == null)
            throw new IllegalArgumentException("Terms array can't be null!");
        Term[] temp = new Term[terms.length]; // presorted temporary array
        for (int i = 0; i < terms.length; i++) {
            if (terms[i] == null)
                throw new IllegalArgumentException("Term cannot be null");
            temp[i] = terms[i];
        }
        this.terms = mergesort(temp);
    }

    // merges two arrays of Term objects essential to our mergesort implementation
    private Term[] merge(Term[] a, Term[] b) {
        Term[] c = new Term[a.length + b.length];
        int i = 0;
        int j = 0;
        for (int k = 0; k < c.length; k++) {
            if (i >= a.length)
                c[k] = b[j++];
            else if (j >= b.length)
                c[k] = a[i++];
            else if (a[i].compareTo(b[j]) <= 0)
                c[k] = a[i++];
            else
                c[k] = b[j++];
        }
        return c;
    }

    // sorts a Term array using mergesort
    private Term[] mergesort(Term[] presort) {
        int n = presort.length;
        if (n <= 1) // single val, already sorted
            return presort;
        Term[] a = new Term[n / 2]; // partition a
        Term[] b = new Term[n - n / 2]; // partition b
        for (int i = 0; i < a.length; i++)
            a[i] = presort[i];
        for (int i = 0; i < b.length; i++)
            b[i] = presort[i + n / 2];
        return merge(mergesort(a), mergesort(b));
    }


    // Returns all terms that start with given prefix, in descending order of weight.
    public Term[] allMatches(String prefix) {
        if (prefix == null)
            throw new IllegalArgumentException("Prefix cannot be null!");
        Term[] matched;
        if (prefix.length() == 0) { // if prefix length is 0, all terms match
            matched = new Term[terms.length];
            for (int i = 0; i < terms.length; i++)
                matched[i] = terms[i];
            Arrays.sort(matched, Term.byReverseWeightOrder());
            return matched;
        }
        Term pre = new Term(prefix, 0);
        int first = BinarySearchDeluxe.firstIndexOf(
                terms, pre, Term.byPrefixOrder(prefix.length()));
        if (first < 0) // term does not appear, return empty array
            return new Term[] { };
        int last = BinarySearchDeluxe.lastIndexOf(
                terms, pre, Term.byPrefixOrder(prefix.length()));
        // number of terms is dist between last and first
        matched = new Term[last - first + 1];
        for (int i = first; i <= last; i++) // filling array
            matched[i - first] = terms[i];
        Arrays.sort(matched, Term.byReverseWeightOrder());
        return matched;
    }

    // Returns the number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {
        if (prefix == null)
            throw new IllegalArgumentException("Prefix cannot be null!");
        if (prefix.length() == 0) // if prefix is 0, return all terms count
            return terms.length;
        Term pre = new Term(prefix, 0);
        int first = BinarySearchDeluxe.firstIndexOf(
                terms, pre, Term.byPrefixOrder(prefix.length()));
        if (first < 0) // if first does not appear, no terms match
            return 0;
        int last = BinarySearchDeluxe.lastIndexOf(
                terms, pre, Term.byPrefixOrder(prefix.length()));
        return last - first + 1;
    }

    // unit testing (required)
    public static void main(String[] args) {

        // read in the terms from a file
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        Term[] terms = new Term[n];
        for (int i = 0; i < n; i++) {
            long weight = in.readLong();           // read the next weight
            in.readChar();                         // scan past the tab
            String query = in.readLine();          // read the next query
            terms[i] = new Term(query, weight);    // construct the term
        }

        // read in queries from standard input and print the top k matching terms
        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(terms);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            StdOut.printf("%d matches\n", autocomplete.numberOfMatches(prefix));
            for (int i = 0; i < Math.min(k, results.length); i++)
                StdOut.println(results[i]);
        }
    }

}
