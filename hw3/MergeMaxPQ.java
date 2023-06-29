package ekli.hw3;

import java.util.Arrays;
import java.util.Random;

/**
 * COPY THIS CLASS INTO YOUR USERID.hw3
 */
public class MergeMaxPQ {

	// Maintain count of number of times a value from one MaxPQ is compared to
	// another value in another MaxPQ
	static int numCompareDirectly;

	/**
	 * Retrieve all values from one and two (leaving them both empty) and return a
	 * single int[] array that contains the combined values in sorted order.
	 * 
	 * You will also need to keep track of the number of times that this method
	 * directly compares a value from 'one' and another value from 'two'. Keep this
	 * count in 'numCompareDirectly' and refer to this within your runTrial()
	 * method.
	 */
	public static int[] merge(MaxPQ one, MaxPQ two) {
		int[] result = new int[one.size() + two.size()];
		numCompareDirectly = 0;

		int i = result.length - 1;
		while (!one.isEmpty() && !two.isEmpty()) {
			int first = one.peekMax();
			int second = two.peekMax();
			if (first > second) {
				result[i--] = one.delMax();
			} else {
				result[i--] = two.delMax();
			}
			numCompareDirectly++;
		}

		while (!one.isEmpty()) {
			result[i--] = one.delMax();
		}
		while (!two.isEmpty()) {
			result[i--] = two.delMax();
		}

		return result;
	}

	/** Create a heap of N random integers from 1 to 1048576. USE AS IS. */
	static MaxPQ randomHeap(int N) {
		Random rnd = new Random(0); // ensure same each time.....
		MaxPQ pq = new MaxPQ(N);
		for (int i = 0; i < N; i++) {
			pq.insert(rnd.nextInt(1048576));
		}
		pq.resetKeyOperationsCount();
		return pq;
	}

	/** Return TRUE if in sorted order. Call this to make sure your merge works. */
	public static boolean isSorted(int[] array) {
		for (int i = 0; i < array.length - 1; i++) {
			if (array[i] > array[i + 1]) {
				return false;
			}
		}
		return true;
	}

	public static void runTrial() {
		System.out.println("\t1024\t2048\t4096\t8192\t16384\t32768\t65536");
		System.out.println("     +---------------------------------------------------------");

		for (int n1 = 1024; n1 <= 65536; n1 *= 2) {
			System.out.print(String.format("%5d", n1) + "|\t");
			for (int n2 = 1024; n2 <= 65536; n2 *= 2) {
				MaxPQ h1 = randomHeap(n1);
				MaxPQ h2 = randomHeap(n2);

				// now validate that merge into a single ascending array worked.
				int[] combined = merge(h1, h2);
				if (!isSorted(combined)) {
					System.out.println("BAD");
					return;
				}

				// compute total number of key operations that were performed
				int totalOps = numCompareDirectly + h1.keyOperations() + h2.keyOperations();

				// instead of printing 0, find out the total number of operations for both h1
				// and h2
				System.out.print(totalOps + "\t");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		MaxPQ one = new MaxPQ(11);
		MaxPQ two = new MaxPQ(11);

		one.insert(13);
		one.insert(77);
		one.insert(50);

		two.insert(31);
		two.insert(41);
		two.insert(59);

		int[] combined = merge(one, two);

		// should produce
		System.out.println("[13, 31, 41, 50, 59, 77] should be output");
		System.out.println(Arrays.toString(combined));
		System.out.println();

		runTrial();
	}
}
