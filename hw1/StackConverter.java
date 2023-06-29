package ekli.hw1;

import java.util.Iterator;

import algs.days.day03.FixedCapacityStack;


/**
 * COPY this file into your USERID.hw1 package and complete its implementation.
 * 
 * You do not have to modify the main() method.
 */
public class StackConverter {

	public static int[] toArray(FixedCapacityStack<Integer> stack) {
		//throw new RuntimeException("MUST COMPLETE toArray() method in StackConverter.");
		int c = 0;
		Iterator<Integer> i = stack.iterator();
		while(i.hasNext()) {
			c++; // counts the number of elements in the stack
			i.next();// moves the iterator to the next element in the stack
		}
		int[] stackToArray = new int[c]; // declares an int array of size c which we determined previously 
		int j = 0;						 // to be the number of elements in the stack
		for(Integer item : stack) { // loops thorough the stack
			stackToArray[c - j - 1] = item; // makes sure that the data from the stack gets added from the end of the array to the front
			j++;//increments every time we add an element to the array
		}
		return stackToArray;
	}

	
	public static void main(String[] args) {
		FixedCapacityStack<Integer> stack = new FixedCapacityStack<>(256);
		stack.push(926);
		stack.push(415);
		stack.push(31);
		
		int vals[] = StackConverter.toArray(stack);
		System.out.println("The following output must be [926, 415, 31] :" + java.util.Arrays.toString(vals));

		// note that you can still pop values
		System.out.println("shoud be 31:" + stack.pop());
		System.out.println("shoud be 415:" + stack.pop());
		System.out.println("shoud be 926:" + stack.pop());
	}
}
