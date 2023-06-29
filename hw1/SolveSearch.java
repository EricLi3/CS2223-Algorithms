package ekli.hw1;

import algs.hw1.fixed.TwoDimensionalStorage;
import algs.hw1.fixed.ps.SearchPermutationArray;
import algs.hw1.fixed.ps.TrialPermutationSquare;

/**
 * COPY this file into your USERID.hw1 package and complete the implementation 
 * of the 'less' and 'contains' methods.
 */
public class SolveSearch implements SearchPermutationArray {

	/**
	 * Given an {@link TwoDimensionalStorage} that contains a Permutation Array, compute the number
	 * of values in each row that are lesser than a specific value.
	 * 
	 * You can assume the TwoDimensionalStorage contains a Permutation Array of size with
	 * R rows and C columns.
	 * 
	 * Note that you can inspect the location of any (row, col) location in the 2-dimensional 
	 * TwoDimensionalStorage by using its 
	 * 
	 * @param array  - TwoDimensionalStorage that contains a Permutation Array
	 * @param target  - value for which you seek total number of smaller values on each row.
	 * @return int[] of integers representing count of values less than target on each row
	 */
	@Override
	public int[] less(TwoDimensionalStorage array, int value) {
		//throw new RuntimeException("Must Complete Implementation of less in SolveSearch");
		
		int[] lessThanTar = new int[array.numRows];
		
		for(int row = 0; row < array.numRows; row++) {
			int low = 0;
			int high = array.numColumns - 1;
			while(low <= high) {
				int mid = (low + high) / 2;
				if(array.getValue(row, mid) < value) {
					low = mid + 1;
				}
				else {
					high = mid - 1;
				}
			}
			lessThanTar[row] = low;
		}
		return lessThanTar;
		
	}

	/**
	 * Returns the row containing value in a TwoDimensionalStorage array that
	 * contains an R x C Permutation Array.
	 * 
	 * If 1 <= value <= R*C then a valid row will be returned, otherwise -1.
	 */
	@Override
	public int contains(TwoDimensionalStorage storage, int value) {
	    if (value < 1 || value > (storage.numRows * storage.numColumns)) {
	        return -1;
	    }

	    for (int row = 0; row < storage.numRows; row++) {
	    	int low = 0;
	    	int high = storage.numColumns - 1;
	    	
	        while(low <= high) {
	        	int mid =  (low + high) / 2;
	        	
	        	if(storage.getValue(row, mid) == value) {
	        		return row;
	        	}
	        	else if(storage.getValue(row, mid) < value) { // if element of array is larger than value then since we have a permutation array, 
	               low = mid + 1;										// the rest of that row can be skipped, because the values go higher and higher.
	            }
	        	else {
	        		high = mid - 1;
	        	}
	        }    
	    }
	    return -1;
	}
	
	/** YOU DO NOT HAVE TO MODIFY THIS MAIN METHOD. RUN AS IS. */
	public static void main(String[] args) {
		// This code helps you evaluate if you have it working for a small example.
		int[][] values = new int[][] { 
			{ 1, 2, 4,  10, 11},
			{ 6, 7, 12, 13, 15},
			{ 3, 5, 8,  9,  14},
		};
		TwoDimensionalStorage sample = new TwoDimensionalStorage(values);
		SolveSearch me = new SolveSearch();
		int result[] = me.less(sample, 5);
		System.out.println("Should be 3 0 1: " + result[0] + " " + result[1] + " " + result[2]);
		int whichRow = me.contains(sample, 5);
		System.out.println("Should be 2: " + whichRow);

		// This code is used to ensure your code is robust enough to handle a small run.

		// compute and validate it works for small run.
		TrialPermutationSquare.runSampleTrial(new SolveSearch());

		// compute and validate for large run. Your algorithm must significantly outperform this result.
		// When I ran my naive solution, the result was 1082400000. You need to do better!
		TrialPermutationSquare.leaderBoard(new SolveSearch());
	}
}
