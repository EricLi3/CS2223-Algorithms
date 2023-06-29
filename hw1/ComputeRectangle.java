package ekli.hw1;

import algs.hw1.fixed.*;
import algs.hw1.fixed.er.Location;
import algs.hw1.fixed.er.SearchForRectangle;
import algs.hw1.fixed.er.TrialEmbeddedRectangle;

/**
 * COPY this class into your USERID.hw1 package and complete the implementation
 * of search.
 * 
 * This method must return a {@link Location} object which records the
 * (startr,startc,r,c) of the rectangle that you found within the
 * EmbeddedRectangle.
 * 
 * For example, given an EmbeddedRectangle with 5 rows and 9 columns might
 * appear as below
 * 
 * 000000000 000111110 000111110 000111110 000111110
 * 
 * Your challenge will be to find the location of randomly generated rectangles
 * within a Storage of suitable dimension. For the above Storage, your program
 * should report (row=1, col=3, numRows=4, numCols=5).
 */
public class ComputeRectangle implements SearchForRectangle {
	
	@Override
	public Location search(TwoDimensionalStorage rect) {
		int numRows = rect.numRows;
		int numCols = rect.numColumns;
		//calculations used later for the starting point of the binary searches
		int midRow = numRows / 2;
		int midCol = numCols / 2;

		// binary search for left boundary
		int leftCol = 0;
		int rightCol = midCol;
		while (leftCol < rightCol) {
			int mid = leftCol + (rightCol - leftCol) / 2;
			if (rect.getValue(midRow, mid) == 1) {
				rightCol = mid;
			} else {
				leftCol = mid + 1;
			}
		}

		// binary search for right boundary
		int rightMostCol = numCols - 1;
		int leftMostCol = midCol;
		while (leftMostCol < rightMostCol) {
			int mid = leftMostCol + (rightMostCol - leftMostCol + 1) / 2;
			if (rect.getValue(midRow, mid) == 1) {
				leftMostCol = mid;
			} else {
				rightMostCol = mid - 1;
			}
		}

		// binary search for top boundary
		int topRow = 0;
		int bottomRow = midRow;
		while (topRow < bottomRow) {
			int mid = topRow + (bottomRow - topRow) / 2;
			if (rect.getValue(mid, leftMostCol) == 1) {
				bottomRow = mid;
			} else {
				topRow = mid + 1;
			}
		}

		// binary search for bottom boundary
		int bottomMostRow = numRows - 1;
		int topMostRow = midRow;
		while (topMostRow < bottomMostRow) {
			int mid = topMostRow + (bottomMostRow - topMostRow + 1) / 2;
			if (rect.getValue(mid, leftMostCol) == 1) {
				topMostRow = mid;
			} else {
				bottomMostRow = mid - 1;
			}
		}

		// calculate height and width of rectangle
		int height = bottomMostRow - topRow + 1;
		int width = rightMostCol - leftCol + 1;

		// check if rectangle meets size requirements
		if (height < numRows / 2 || width < numCols / 2) {
			return null;
		}

		// return location of rectangle
		return new Location(topRow, leftCol, height, width);
	}

	/** YOU DO NOT HAVE TO MODIFY THIS MAIN METHOD. RUN AS IS. */
	public static void main(String[] args) {
		// This code helps you evaluate if you have it working for a small example.
		int[][] values = new int[][] { { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 1, 1, 1, 1, 1 }, { 0, 0, 1, 1, 1, 1, 1 },
				{ 0, 0, 1, 1, 1, 1, 1 }, { 0, 0, 1, 1, 1, 1, 1 }, };
		TwoDimensionalStorage sample = new TwoDimensionalStorage(values);
		SearchForRectangle me = new ComputeRectangle();
		Location loc = me.search(sample);
		System.out.println("Location 1 2 4 5 should be: " + loc);

		// This code is used to ensure your code is robust enough to handle a small run.

		// compute and validate it works for small run.
		TrialEmbeddedRectangle.runSampleTrial(new ComputeRectangle());

		// compute and validate for large run. Your algorithm must significantly
		// outperform this result.
		// When I ran my naive solution, the result was 1082400000. You need to do
		// better!
		TrialEmbeddedRectangle.leaderBoard(new ComputeRectangle());
	}
}
