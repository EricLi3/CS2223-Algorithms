package ekli.hw1;

/**
 * COPY THIS FILE into your USERID.hw1 and instrument it accordingly to keep track of the 
 * Number of multiplications and additions/subtractions.
 * 
 * Strassen in 1969 showed an algorithm that outperforms naive matrix multiplication. The 
 * following table shows the number of multiplications with Naive Matrix multiplication as 
 * compared with the number of multiplications used by Strassen.
 *
 * This sample implementation comes from the following github link:
 * 
 * https://gist.github.com/sid24rane/bdd8f579979fd79f98d7bb481ce79892
 */
public class Strassen {
	
	public static long numMultiplications;
	public static long numAdditions;
	
	public static int[][] multiply(int[][] A, int[][] B) {
		int n = A.length;

		int[][] res = new int[n][n];

		// if the input matrix is 1x1
		if (n == 1) {
			res[0][0] = A[0][0] * B[0][0];
			numMultiplications++;
		} else {
			
			// first matrix
			int[][] a = new int[n / 2][n / 2];
			int[][] b = new int[n / 2][n / 2];
			int[][] c = new int[n / 2][n / 2];
			int[][] d = new int[n / 2][n / 2];

			// second matrix
			int[][] e = new int[n / 2][n / 2];
			int[][] f = new int[n / 2][n / 2];
			int[][] g = new int[n / 2][n / 2];
			int[][] h = new int[n / 2][n / 2];

			// dividing matrix A into 4 parts
			divideArray(A, a, 0, 0);
			divideArray(A, b, 0, n / 2);
			divideArray(A, c, n / 2, 0);
			divideArray(A, d, n / 2, n / 2);

			// dividing matrix B into 4 parts
			divideArray(B, e, 0, 0);
			divideArray(B, f, 0, n / 2);
			divideArray(B, g, n / 2, 0);
			divideArray(B, h, n / 2, n / 2);

			/** 
	              p1 = (a + d)(e + h)
	              p2 = (c + d)e
	              p3 = a(f - h)
	              p4 = d(g - e)
	              p5 = (a + b)h
	              p6 = (c - a) (e + f)
	              p7 = (b - d) (g + h)
			 **/

			int[][] p1 = multiply(addMatrices(a, d), addMatrices(e, h));
			int[][] p2 = multiply(addMatrices(c,d),e);
			int[][] p3 = multiply(a, subMatrices(f, h));           
			int[][] p4 = multiply(d, subMatrices(g, e));
			int[][] p5 = multiply(addMatrices(a,b), h);
			int[][] p6 = multiply(subMatrices(c, a), addMatrices(e, f));
			int[][] p7 = multiply(subMatrices(b, d), addMatrices(g, h));


			/**
	              C11 = p1 + p4 - p5 + p7
	              C12 = p3 + p5
	              C21 = p2 + p4
	              C22 = p1 - p2 + p3 + p6
			 **/

			int[][] C11 = addMatrices(subMatrices(addMatrices(p1, p4), p5), p7);
			int[][] C12 = addMatrices(p3, p5);
			int[][] C21 = addMatrices(p2, p4);
			int[][] C22 = addMatrices(subMatrices(addMatrices(p1, p3), p2), p6);

			// adding all subarray back into one
			copySubArray(C11, res, 0, 0);
			copySubArray(C12, res, 0, n / 2);
			copySubArray(C21, res, n / 2, 0);
			copySubArray(C22, res, n / 2, n / 2);
		}
		return res;
	}



	// Adding 2 matrices
	public static int[][] addMatrices(int[][] a, int[][] b) {
		int n = a.length;
		int[][] res = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				res[i][j] = a[i][j] + b[i][j];
				numAdditions++;
			}
		}
		return res;
	}

	// Subtracting 2 matrices
	public static int[][] subMatrices(int[][] a, int[][] b) {
		int n = a.length;
		int[][] res = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				res[i][j] = a[i][j] - b[i][j];
				numAdditions++;
			}
		}
		return res;
	}

	// print matrix
	public static void printMatrix(int[][] a) {
		int n = a.length;
		System.out.println("Resultant Matrix ");
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(a[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println();
	}

	// divides array
	public static void divideArray(int[][] P, int[][] C, int iB, int jB) 
	{
		for(int i1 = 0, i2 = iB; i1 < C.length; i1++, i2++) {
			for(int j1 = 0, j2 = jB; j1 < C.length; j1++, j2++) {
				C[i1][j1] = P[i2][j2];
			}
		}
	}

	// copies
	public static void copySubArray(int[][] C, int[][] P, int iB, int jB) 
	{
		for(int i1 = 0, i2 = iB; i1 < C.length; i1++, i2++) {
			for(int j1 = 0, j2 = jB; j1 < C.length; j1++, j2++) {
				P[i2][j2] = C[i1][j1];
			}
		}
	}  
}
