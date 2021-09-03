/* *****************************************************************************
 *  Name:    Ethan Abraham
 *  NetID:   ea10
 *  Precept: P02
 *
 *  Partner Name:    Charles Coppieters 'T Wallant
 *  Partner NetID: cwallant
 *  Partner Precept: P06
 *
 *  Description:  Conducts a binary search to find the index of the first or
 * last search key which can be conducted upon an array.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class BinarySearchDeluxe {

    // Returns the index of the first key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int firstIndexOf(Key[] a,
                                         Key key, Comparator<Key> comparator) {
        // throws illegal argument exception for illegal arguments
        if (a == null || key == null || comparator == null)
            throw new IllegalArgumentException("Arguments must not be null");
        int lo = 0; // represents the lowest value in the sub array being
        // binary searched
        int hi = a.length - 1; // represents the highest value in the sub array being
        // binary searched
        int result = -1;
        // while loop that makes the array smaller and smaller until low <= hi
        // at which we either know the key is not in the array or we have
        // found the first index of the key
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            // make a temporary int that represents the comparison between
            // a[mid] and key to save space so we don't have to compare
            // so many times
            int tempComp = comparator.compare(a[mid], key);
            // if the key is equal to a[mid], we set result to mid and set hi
            // to be mid - 1 which will happen again and again as the while
            // loop repeats so that we can find the first index of the key
            // in case there are more than one of the key in the array
            if (tempComp == 0) {
                result = mid;
                hi = mid - 1;
            }
            // if key is not equal to a[mid] we increment lo and hi respectively
            // to move towards finding the key next time through the loop
            else if (tempComp < 0)
                lo = mid + 1;
            else
                hi = mid - 1;
        }
        return result;
    }

    // Returns the index of the last key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int lastIndexOf(Key[] a,
                                        Key key, Comparator<Key> comparator) {
        // throws illegal argument exception for illegal arguments
        if (a == null || key == null || comparator == null)
            throw new IllegalArgumentException("Arguments must not be null");
        int lo = 0; // represents the lowest value in the sub array being
        // binary searched
        int hi = a.length - 1; // represents the highest value in the sub array
        // being binary searched
        int result = -1;
        // while loop that makes the array smaller and smaller until low <= hi
        // at which we either know the key is not in the array or we have
        // found the last index of the key
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            // make a temporary int that represents the comparison between
            // a[mid] and key to save space so we don't have to compare
            // so many times
            int tempComp = comparator.compare(a[mid], key);
            // if the key is equal to a[mid], we set result to mid and set hi
            // to be mid - 1 which will happen again and again as the while
            // loop repeats so that we can find the last index of the key
            // in case there are more than one of the key in the array
            if (tempComp == 0) {
                result = mid;
                lo = mid + 1;
            }
            // if key is not equal to a[mid] we increment lo and hi respectively
            // to move towards finding the key next time through the loop
            else if (tempComp < 0)
                lo = mid + 1;
            else
                hi = mid - 1;
        }
        return result;
    }

    // unit testing (required)
    public static void main(String[] args) {
        Term[] example = new Term[5];
        Term term0 = new Term("a", 1);
        example[0] = term0;
        Term term1 = new Term("b", 1);
        example[1] = term1;
        Term term2 = new Term("b", 1);
        example[2] = term2;
        Term term3 = new Term("c", 1);
        example[3] = term3;
        Term term4 = new Term("d", 1);
        example[4] = term4;
        String keyString = "b";
        Term keyTerm = new Term(keyString, 1);
        int firstKey = firstIndexOf(example, keyTerm,
                                    Term.byPrefixOrder(keyString.length()));
        int lastKey = lastIndexOf(example, keyTerm,
                                  Term.byPrefixOrder(keyString.length()));
        StdOut.println("The first index of the key is: " + firstKey);
        StdOut.println("The last index of the key is: " + lastKey);

    }
}
