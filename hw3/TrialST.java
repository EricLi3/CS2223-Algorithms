package ekli.hw3;

import java.util.Iterator;

import algs.hw3.Collection; // when you copy this class into your USERID.hw3 these warnings go away
import algs.hw3.ID;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.LinearProbingHashST;

// you will eventually use all of these. But until you do, these warnings remain.
import edu.princeton.cs.algs4.AVLTreeST;
import edu.princeton.cs.algs4.BST;
import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.princeton.cs.algs4.SequentialSearchST;

/**
 * Copy this class into your USERID.hw3 package and:
 * 
 * 1. Complete the implementation of countCompares 2. Make the necessary copies
 * to report() using different types as parameters 3. Modify main() to be able
 * to print out FIVE total tables, each one must be labeled properly to match
 * the Table I -- Table V from the homework description
 */
public class TrialST {
	// total number of trials to execute.
	static int TRIAL = 1024;

	// Index positions into the response from report()
	static int BUILD = 0;
	static int HIT = 1;
	static int MISS = 2;

	/**
	 * Returns the TOTAL number of compares in the IDs provided by this Iterable
	 * object.
	 * 
	 * Note that this value must be divided by TWO to properly ensure you don't
	 * double count.
	 */
	static long countCompares(Iterable<ID> iter) {
	    long totalCompares = 0;
	    for (ID id : iter) { // iterate over the collection
	        totalCompares += id.numCompares();
	    }
	    return totalCompares / 2;
	}


	/**
	 * This is a method that reports proper statistics for LinearProbingHashST.
	 * 
	 * It does three things:
	 * 
	 * (a) PUTs all the designated IDs from the collection into the symbol table.
	 * (b) Run a TRIAL of searching for IDs that MUST exist in the symbol table (i.e., HITS)
	 * (c) Run a TRIAL of searching for IDs that DO NOT exist in the symbol table (i.e., MISSES)
	 * 
	 * YOU SHOULD COPY this method and change the TYPE of the 'st' variable 
	 * to a different type and the remaining code will continue to work properly
	 */
	static long[] report(Collection set, LinearProbingHashST<ID, Boolean> st) {
		// DO NOT CHANGE ANYTHING INSIDE THIS METHOD!!
		set.resetNumCompares();
		for (ID id: set.ids()) { st.put(id, true); }
		
		// how many total compares necessary to BUILD
		long buildCount = countCompares(set.ids());  
		set.resetNumCompares();
		
		// run a trial to look for total number of keys THAT MUST EXIST
		Bag<ID> hits = set.hits(TRIAL);
		for (ID id: hits) {  
			if (!st.contains(id)) { System.err.println("Failed for some reason!"); throw new RuntimeException("EXIT!"); }
		}
		long hitCount = countCompares(set.ids());
		set.resetNumCompares();
		
		// run a trial to look for total number of keys THAT MUST NOT EXIST
		for (ID id: set.misses(TRIAL)) {  
			if (st.contains(id)) { System.err.println("Failed for some reason!"); throw new RuntimeException("EXIT!"); }
		}
		long missCount = countCompares(set.ids());
		set.resetNumCompares();
		
		return new long[] { buildCount, hitCount, missCount };
	}
	
	static long[] report(Collection set, AVLTreeST<ID, Boolean> st) {
		// DO NOT CHANGE ANYTHING INSIDE THIS METHOD!!
		set.resetNumCompares();
		for (ID id: set.ids()) { st.put(id, true); }
		
		// how many total compares necessary to BUILD
		long buildCount = countCompares(set.ids());  
		set.resetNumCompares();
		
		// run a trial to look for total number of keys THAT MUST EXIST
		Bag<ID> hits = set.hits(TRIAL);
		for (ID id: hits) {  
			if (!st.contains(id)) { System.err.println("Failed for some reason!"); throw new RuntimeException("EXIT!"); }
		}
		long hitCount = countCompares(set.ids());
		set.resetNumCompares();
		
		// run a trial to look for total number of keys THAT MUST NOT EXIST
		for (ID id: set.misses(TRIAL)) {  
			if (st.contains(id)) { System.err.println("Failed for some reason!"); throw new RuntimeException("EXIT!"); }
		}
		long missCount = countCompares(set.ids());
		set.resetNumCompares();
		
		return new long[] { buildCount, hitCount, missCount };
	}
	static long[] report(Collection set, BST<ID, Boolean> st) {
		// DO NOT CHANGE ANYTHING INSIDE THIS METHOD!!
		set.resetNumCompares();
		for (ID id: set.ids()) { st.put(id, true); }
		
		// how many total compares necessary to BUILD
		long buildCount = countCompares(set.ids());  
		set.resetNumCompares();
		
		// run a trial to look for total number of keys THAT MUST EXIST
		Bag<ID> hits = set.hits(TRIAL);
		for (ID id: hits) {  
			if (!st.contains(id)) { System.err.println("Failed for some reason!"); throw new RuntimeException("EXIT!"); }
		}
		long hitCount = countCompares(set.ids());
		set.resetNumCompares();
		
		// run a trial to look for total number of keys THAT MUST NOT EXIST
		for (ID id: set.misses(TRIAL)) {  
			if (st.contains(id)) { System.err.println("Failed for some reason!"); throw new RuntimeException("EXIT!"); }
		}
		long missCount = countCompares(set.ids());
		set.resetNumCompares();
		
		return new long[] { buildCount, hitCount, missCount };
	}
	static long[] report(Collection set, SeparateChainingHashST<ID, Boolean> st) {
		// DO NOT CHANGE ANYTHING INSIDE THIS METHOD!!
		set.resetNumCompares();
		for (ID id: set.ids()) { st.put(id, true); }
		
		// how many total compares necessary to BUILD
		long buildCount = countCompares(set.ids());  
		set.resetNumCompares();
		
		// run a trial to look for total number of keys THAT MUST EXIST
		Bag<ID> hits = set.hits(TRIAL);
		for (ID id: hits) {  
			if (!st.contains(id)) { System.err.println("Failed for some reason!"); throw new RuntimeException("EXIT!"); }
		}
		long hitCount = countCompares(set.ids());
		set.resetNumCompares();
		
		// run a trial to look for total number of keys THAT MUST NOT EXIST
		for (ID id: set.misses(TRIAL)) {  
			if (st.contains(id)) { System.err.println("Failed for some reason!"); throw new RuntimeException("EXIT!"); }
		}
		long missCount = countCompares(set.ids());
		set.resetNumCompares();
		
		return new long[] { buildCount, hitCount, missCount };
	}
	static long[] report(Collection set, SequentialSearchST<ID, Boolean> st) {
		// DO NOT CHANGE ANYTHING INSIDE THIS METHOD!!
		set.resetNumCompares();
		for (ID id: set.ids()) { st.put(id, true); }
		
		// how many total compares necessary to BUILD
		long buildCount = countCompares(set.ids());  
		set.resetNumCompares();
		
		// run a trial to look for total number of keys THAT MUST EXIST
		Bag<ID> hits = set.hits(TRIAL);
		for (ID id: hits) {  
			if (!st.contains(id)) { System.err.println("Failed for some reason!"); throw new RuntimeException("EXIT!"); }
		}
		long hitCount = countCompares(set.ids());
		set.resetNumCompares();
		
		// run a trial to look for total number of keys THAT MUST NOT EXIST
		for (ID id: set.misses(TRIAL)) {  
			if (st.contains(id)) { System.err.println("Failed for some reason!"); throw new RuntimeException("EXIT!"); }
		}
		long missCount = countCompares(set.ids());
		set.resetNumCompares();
		
		return new long[] { buildCount, hitCount, missCount };
	}
	
	
	public static void main(String[] args) {
		
		System.out.println("Table III: Symbol Table Structure (LinearProbingHashST)");
		System.out.println(String.format("%10s\t%s\t%s\t%s", "N", "BUILD", "HIT", "MISS"));
		for (int N = 64; N <= 65536; N*= 2) {
			
			// make a collection containing N unique IDs, randomly chosen
			Collection set = new Collection(N);
			
			// As I typically do, I am not interested in the values associated with these keys (they are always TRUE).
			// Start by creating a new LinearProbingHashST for these IDs. WE MUST take the time 
			// to reset the number of compares so we are properly accounting for # of compares
			LinearProbingHashST<ID, Boolean> coll = new LinearProbingHashST<>();
			long[] results = report(set, coll);
			
			System.out.println(String.format("%10d\t%4.2f\t%4.2f\t%4.2f", N, 
					1.0*results[BUILD]/TRIAL, 1.0*results[HIT]/TRIAL, 1.0*results[MISS]/TRIAL));
		}
		
		// NOTE: You can copy the above lines and paste multiple times here, always changing the TYPE of the ST
		// that you construct. QUESTIONS? COME TO OFFICE HOURS!
		
		System.out.println("Table II: Symbol Table Structure (SequentialSearchST)");
		System.out.println(String.format("%10s\t%s\t%s\t%s", "N", "BUILD", "HIT", "MISS"));
		for (int N = 64; N <= 65536; N*= 2) {
			
			// make a collection containing N unique IDs, randomly chosen
			Collection set = new Collection(N);
			
			// As I typically do, I am not interested in the values associated with these keys (they are always TRUE).
			// Start by creating a new LinearProbingHashST for these IDs. WE MUST take the time 
			// to reset the number of compares so we are properly accounting for # of compares
			AVLTreeST<ID, Boolean> coll = new AVLTreeST<>();
			long[] results = report(set, coll);
			
			System.out.println(String.format("%10d\t%4.2f\t%4.2f\t%4.2f", N, 
					1.0*results[BUILD]/TRIAL, 1.0*results[HIT]/TRIAL, 1.0*results[MISS]/TRIAL));
		}
		
		System.out.println("Table IV: Symbol Table Structure (SeparateChainingHashST)");
		System.out.println(String.format("%10s\t%s\t%s\t%s", "N", "BUILD", "HIT", "MISS"));
		for (int N = 64; N <= 65536; N*= 2) {
			
			// make a collection containing N unique IDs, randomly chosen
			Collection set = new Collection(N);
			
			// As I typically do, I am not interested in the values associated with these keys (they are always TRUE).
			// Start by creating a new LinearProbingHashST for these IDs. WE MUST take the time 
			// to reset the number of compares so we are properly accounting for # of compares
			BST<ID, Boolean> coll = new BST<>();
			long[] results = report(set, coll);
			
			System.out.println(String.format("%10d\t%4.2f\t%4.2f\t%4.2f", N, 
					1.0*results[BUILD]/TRIAL, 1.0*results[HIT]/TRIAL, 1.0*results[MISS]/TRIAL));
		}
		System.out.println("Table V: Symbol Table Structure (BST)");
		System.out.println(String.format("%10s\t%s\t%s\t%s", "N", "BUILD", "HIT", "MISS"));
		for (int N = 64; N <= 65536; N*= 2) {
			
			// make a collection containing N unique IDs, randomly chosen
			Collection set = new Collection(N);
			
			// As I typically do, I am not interested in the values associated with these keys (they are always TRUE).
			// Start by creating a new LinearProbingHashST for these IDs. WE MUST take the time 
			// to reset the number of compares so we are properly accounting for # of compares
			SeparateChainingHashST<ID, Boolean> coll = new SeparateChainingHashST<>();
			long[] results = report(set, coll);
			
			System.out.println(String.format("%10d\t%4.2f\t%4.2f\t%4.2f", N, 
					1.0*results[BUILD]/TRIAL, 1.0*results[HIT]/TRIAL, 1.0*results[MISS]/TRIAL));
		}
		System.out.println("Table I: Symbol Table Structure (AVLTreeST)");
		System.out.println(String.format("%10s\t%s\t%s\t%s", "N", "BUILD", "HIT", "MISS"));
		for (int N = 64; N <= 65536; N*= 2) {
			
			// make a collection containing N unique IDs, randomly chosen
			Collection set = new Collection(N);
			
			// As I typically do, I am not interested in the values associated with these keys (they are always TRUE).
			// Start by creating a new LinearProbingHashST for these IDs. WE MUST take the time 
			// to reset the number of compares so we are properly accounting for # of compares
			SequentialSearchST<ID, Boolean> coll = new SequentialSearchST<>();
			long[] results = report(set, coll);
			
			System.out.println(String.format("%10d\t%4.2f\t%4.2f\t%4.2f", N, 
					1.0*results[BUILD]/TRIAL, 1.0*results[HIT]/TRIAL, 1.0*results[MISS]/TRIAL));
		}
	}
}
