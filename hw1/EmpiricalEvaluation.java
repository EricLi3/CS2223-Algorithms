package ekli.hw1;
       
import edu.princeton.cs.algs4.StopwatchCPU;

// Make sure that you do not import Strassen from the main 

/**
 * Copy this class file into your USERID.hw1 and modify it (and instrument other classes) so you
 * can produce a table that counts the number of multiplications/divisions and additions/subtractions
 * within:
 * 
 * (A) MatrixMultiply      [I have done this for you already]
 * (B) Strassen            [You need to COPY this file into your USERID.hw1 and instrument the code]
 * (C) DeterminantMatrix   [I have done this for you already]
 * 
 * NOTE: THE OUTPUT TABLE IS UNUSAL because the rows are all separated by 256 instead of the regular DOUBLING
 *       that you will see in class. I have done this for a reason, however, and you should find out why
 *       when you read the instructions.
 */
public class EmpiricalEvaluation {
	public static void main(String[] args) {
		System.out.println("BE PATIENT! This might take a few minutes to run...");
		System.out.println(String.format("%12S %16S %16S %16S %16S %10S %10SS", "N", 
				"#MM Mul", "#MM Add/Sub", "#SM Mul", "#SM Add/Sub", 
				"Time MM", "Time SM"));
		for (int N : new int[] { 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048}) {
			
			// Create Matrix of all 1s
			int[][] A = new int[N][N];
			for (int r = 0; r < A.length; r++) { 
				for (int c = 0; c < A[0].length; c++) {
					A[r][c] = 1;
				}
			}
			
			// I have started you off by showing how I've instrumented MatrixMultiply and timed its execution.
			algs.hw1.fixed.MatrixMultiply.numMultiplications = 0;
			algs.hw1.fixed.MatrixMultiply.numAdditions = 0;
			StopwatchCPU watch = new StopwatchCPU();
			algs.hw1.fixed.MatrixMultiply.multiply(A, A);
			double timeMM = watch.elapsedTime();
			
			// NOW you do the same below for Strassen Matrix Multiplication. NOTE that the computation
			// will prove to be excessive for large N. Keep in my logic that avoids calling Strassen for 
			// matrices that are 1,024 and higher.
			// MODIFY BELOW....
			ekli.hw1.Strassen.numMultiplications = 0;
			ekli.hw1.Strassen.numAdditions = 0;
			StopwatchCPU watch1 = new StopwatchCPU();
			double timeSM = watch1.elapsedTime();  // replace with an actual result
			if (N < 1024) {
				// here is where you should invoke 
				Strassen.multiply(A, A);
			}

			// You will modify the following to add additional columns
			System.out.println(String.format("%12d %16d %16d %16d %16d %10f %10f", N, 
					algs.hw1.fixed.MatrixMultiply.numMultiplications,     /** I've done this one. */
					algs.hw1.fixed.MatrixMultiply.numAdditions,           /** I've done this one. */
					ekli.hw1.Strassen.numMultiplications,                 /** Replace with count of Mul/Div for Strassen. */
					ekli.hw1.Strassen.numAdditions,                       /** Replace with count of Add/Sub for Strassen. */  
					timeMM, timeSM));
		}
	}
}
