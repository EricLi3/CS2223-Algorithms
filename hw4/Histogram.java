package ekli.hw4;

import java.util.Random;

import edu.princeton.cs.algs4.BinarySearchST;
//import edu.princeton.cs.algs4.*;

/**
 * COPY this class into your USERID.hw4 package and complete this class.
 */
public class Histogram {

	// You will need some symbol table that you can use to store the keys
	// in such a way that you can retrieve them in order.

	private BinarySearchST<Integer, Integer> st;
	private int size;

	public Histogram() {
		st = new BinarySearchST<>();
		size = 0;	// size of the histogram / ST
	}

	/**
	 * Increase the count for the number of times 'key' exists in the Histogram.
	 * 
	 * @param n
	 */
	public void record(int key) {
		if (st.contains(key)) {
			// Key is already in the symbol table, increment its count
			st.put(key, st.get(key) + 1);
		} else {
			// Key is not in the symbol table, add it with the count 1
			st.put(key, 1);	// put adds a key values pair into the symbol table
			size++;
		}
	}

	/** Return whether histogram is empty. */
	public boolean isEmpty() {
		return size == 0;
	}

	/** Return the lowest integer key in the histogram. */
	public int minimum() {
		return st.min();
	}

	/** Return the largest integer key in the histogram. */
	public int maximum() {
		return st.max();
	}

	/** Return sum of counts for keys from lo (inclusive) to high (inclusive). */
	public int total(int lo, int hi) {
		if (lo > hi) {
			throw new IllegalArgumentException("lo must be less than or equal to hi");
		}
		int total = 0;
		for (int key : st.keys(lo, hi)) {
			total += st.get(key);
		}
		return total;
	}

	/**
	 * Produce a report for all keys (and their counts) in ascending order of keys.
	 */
	public void report() {
		System.out.println("Raw Histogram");
		for (int key : st.keys()) {
			System.out.println(key + ": " + st.get(key));
		}
	}

	/**
	 * Produce a report for all bins (with aggregate counts) in ascending order by
	 * range.
	 * 
	 * The first range label that is output should be "0 - (binSize-1)" since the
	 * report always starts from 0.
	 * 
	 * It is acceptable if the final range label includes values that exceed
	 * maximum().
	 */
	public void report(int binSize) {
		System.out.println("Histogram (binSize=" + binSize + ")");
		for (int i = 0; i <= maximum(); i += binSize) {
			System.out.println(String.format("%d-%d\t%d", i, i + binSize - 1, total(i, i + binSize - 1)));
		}
	}

	/**
	 * Kick things off with random integers, but using a fixed generated sequence so
	 * you can reproduce.
	 * 
	 * USE THIS METHOD AS IS WITHOUT ANY CHANGES.
	 */
	public static void main(String[] args) {
		Histogram sample = new Histogram();

		Random rnd = new Random(0);
		for (int i = 0; i < 20; i++) {
			int v = rnd.nextInt(20);
			sample.record(v);
		}

		sample.report();
		sample.report(1);
		sample.report(5);
	}
}
