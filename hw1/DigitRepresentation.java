package ekli.hw1;

import algs.days.day03.FixedCapacityStack;
import edu.princeton.cs.algs4.StdOut;

/**
 * COPY this class into your USERID.hw1 package and complete the implementation of reverseRepresentation.
 */
public class DigitRepresentation {

	static FixedCapacityStack<Integer> reverseRepresentation(int n, int b) {
		//throw new RuntimeException ("Complete this implementation");
		
		FixedCapacityStack<Integer> stack;
		stack = new FixedCapacityStack<Integer>(n);
		
		int base = b;
		while(n > 0) {
			int digit = n % b;// use the modulo to get the digit which is from 0 to 9 
			stack.push(digit);//push the digit to the stack
			n = n / b;
			base = base * b;
		}
		return stack;
	}

	public static void main(String args[]) {
		System.out.println("b       21 in base b");
		System.out.println("--------------------");
		int N = 21;
		FixedCapacityStack<Integer> a = new FixedCapacityStack<Integer>(N);
		
		for (int b = 2; b <= 10; b++) {
			// FINISH THIS IMPLEMENTATION
			a = reverseRepresentation(N, b);
		
			StdOut.printf("%-2d      ", b); // formats the base so the width of the field should be 2 characters
			for(int digit : a) { // loop thorough the stack and print out each digit 
			StdOut.print(digit);
			 
			}
			System.out.println(); // adds a new line character for the next set of base and digits
		}
	}
}
