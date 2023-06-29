package ekli.hw1;

import java.util.Arrays;

/**
 * COPY THIS CLASS into your USERID.hw1 and modify it by completing the
 * implementation of the methods that currently throw RuntimeExceptions.
 * 
 * A Staque is a unique structure that uses a single char array to provide both
 * Stack and Queue behaviors.
 * 
 * You will need to add class attributes and some initialization code in the
 * constructor.
 */
public class Staque {

	/** This contains the storage you will manage. */
	char storage[];
	final int separator;

	// YOU MAY ADD MORE CLASS ATTRIBUTES HERE
	int stackN; // how many elements are in the stack
	int top; // INDEX position of the 'length char' of topmost element of stack.
				// NOTE : when top == separator, STACK must be EMPTY

	int queueN; // how many elements are in the stack
	int first; // INDEX position of the 'length char'
	int last; // end of queue, where we will add chars

	/**
	 * Create the internal storage array to contain 2*size + 1 bytes.
	 * 
	 * @param size The number of bytes to use for the stack (or the queue).
	 */
	public Staque(int size) {
		if (size < 5 || size > 65536) {
			throw new RuntimeException("Invalid Size: " + size);
		}

		storage = new char[2 * size + 1];
		separator = size; // this is the midpoint.

		// YOU MAY ADD MORE HERE
		top = separator; // stack is empty
		first = separator; // stack is empty
		last = separator;
	}

	/** Returns whether the Staque has at least one element in its stack. */
	public boolean canPop() {
		return (stackN >= 1);
	}

	/** Returns whether the Staque has at least one element in its queue. */
	public boolean canDequeue() {
		return (queueN >= 1);
	}

	/**
	 * Returns whether there is enough memory to push the char[] array to its stack.
	 */
	public boolean canPush(char[] bytes) {
		int size = bytes.length + 1; // include an extra character for number of chars pushed
		// int stackSize = (((storage.length)/2)-1);
		return (0 <= top - size);
	}

	/**
	 * Returns whether there is enough memory to enqueue the char[] array to its
	 * queue.
	 */
	public boolean canEnqueue(char[] bytes) {
		int size = bytes.length + 1; // include an extra character that is the number of char pushed
		int queueSize = (((storage.length) / 2) - 1);
		return (queueSize <= first + size); // checking if after adding bytes if the number of elements is less than or
											// equal to the
											// size allocated for queue.
	}

	/**
	 * Push char[] array onto the stack.
	 * 
	 * @param bytes -- an array of bytes at least 1 in length but no more than 255.
	 * @exception RuntimeException if not enough room to store.
	 */
	void push(char bytes[]) {
		// Check if we can push by invoking canPush()
		if (canPush(bytes) == false) {
			throw new RuntimeException("There is not enough room in the stack.");
		}
		// copy all char from bytes[] into storage[]
		//
		// \2 A B X
		// st top sep push (new char[]{ 'C'});
		//
		// \1 C \2 A B X
		// top

		// copy all char from bytes[] into storage
		int start = top - 1; // TOP - 1 is the next char index into which to write RIGHT-most char
		for (int i = bytes.length - 1; i >= 0; i--) { // up to down or right to left
			storage[start--] = bytes[i]; // start-- is one after/before the size value element
		}

		storage[start] = (char) bytes.length; // unprintable character (length of the element. )

		// adjust top since it has to change the reflect reality
		top = top - 1 - bytes.length;

		// insert length char at right location

		stackN++;// stackN has to change
	}

	/** Helper method to push a single character. LEAVE AS IS. */
	void push(char ch) {
		push(new char[] { ch });
	}

	/**
	 * Pop the char[] array that is at the top of the stack and return it.
	 * 
	 * @return -- an array of bytes at least 1 in length but no more than 255.
	 * @exception RuntimeException if stack was empty.
	 */
	char[] pop() {
		char[] bytes;
		// check that you can pop
		if (canPop() == false) {
			throw new RuntimeException("Stack was empty");
		}

		// copy all char from bytes[] into storage[]
		//
		// \2 A B X
		// st top sep push (new char[]{ 'C'});
		//
		// \1 C \2 A B X
		// top

		// find how long the topmost element is
		int length = storage[top];
		storage[top] = 0; // clear up mess
		bytes = new char[length];

		// copy bytes from storage into bytes
		int start = top + 1;
		for (int i = 0; i < length; i++) {
			bytes[i] = storage[start];
			storage[start++] = 0; // clear up mess and the post increment
		}

		// update top increment to higher
		top = top + 1 + length;

		// decrement stackN
		stackN--;

		return bytes;
	}

	/**
	 * Enqueue char[] array to tail of the queue.
	 * 
	 * @param bytes -- an array of bytes at least 1 in length but no more than 255.
	 * @exception RuntimeException if not enough room to store.
	 */
	void enqueue(char bytes[]) {
		// check if we can enqueue using canEnqueue()
		if (canEnqueue(bytes) == false) {
			throw new RuntimeException("There is not enough room in the queue.");
		}
		// copy all char from bytes into storage
		int start = last + 1; // last + 1 is the next char index to which to write the char
		storage[start] = (char) bytes.length; // insert length char at right location

		for (int i = 0; i < bytes.length; i++) {
			storage[++start] = bytes[i];
		}

		// adjust last since it has to change due to the number of chars added
		last = last + bytes.length + 1;

		// change queueN
		queueN++;
	}

	/** Helper method to enqueue a single character. LEAVE AS IS. */
	void enqueue(char ch) {
		enqueue(new char[] { ch });
	}

	/**
	 * Dequeue char[] array from the head of the queue.
	 * 
	 * @return -- array of bytes at least 1 in length but no more than 255.
	 * @exception RuntimeException if queue is empty.
	 */
	char[] dequeue() {
		char[] bytes;

		// check that we can dequeue if not throw exception
		if (canDequeue() == false) {
			throw new RuntimeException("Queue was empty");
		}

		// find how long the topmost element is
		int length = storage[first + 1];
		// storage[first] = 0;
		bytes = new char[length];

		// copy all chars from storage into bytes
		int start = first + 1;
		for (int i = 0; i < length; i++) {
			bytes[i] = storage[start + 1];
			storage[start + 1] = 0;		// set the old data to zero.
			start++;
		}
		// update first
		first = first + length + 1;
		// decrement queueN
		queueN--;

		return bytes;

	}

	// NOTE: WHEN I ORIGINALLY RELEASED THE HOMEWORK, THE FOLLOWING THREE METHODS
	// WERE MISSING.
	// SO COPY output(), same(), and main() INTO YOUR Staque.java FILE (TOWARDS THE
	// END) AND THEN
	// YOU WILL HAVE THESE METHODS TO USE AS WELL.

	/** Helper method to output storage. USE AS IS. */
	static void output(int frame, Staque stq, String note) {
		System.out.println(String.format("%5d:%s\t<%s>", frame, Arrays.toString(stq.storage), note));
	}

	/** Check if byte arrays are identical. USE AS IS. */
	static boolean same(char[] one, char[] two) {
		if (one.length != two.length) {
			return false;
		}
		for (int i = 0; i < one.length; i++) {
			if (one[i] != two[i]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Use as is to validate your implementation matches the output from the
	 * homework spec.
	 */
	public static void main(String[] args) {
		System.out.println("Note that in the output, the encoded lengths may appear as a strange");
		System.out.println("character (like " + ((char) 7) + ") because of how characters appear.");
		System.out.println();

		Staque stq = new Staque(7);
		output(0, stq, "empty");

		char[] c1 = new char[] { 'M' }; // these are char bytes[] to push/enqueue
		char[] c2 = new char[] { 'x', 'y' };
		char[] c3 = new char[] { 'a', 'b', 'c' };
		char[] c4 = new char[] { 'w', 'x', 'y', 'z' };

		stq.push(c3);
		output(1, stq, "pushed 3");

		stq.enqueue(c4);
		output(2, stq, "enqueued 4");

		stq.enqueue(c1);
		output(3, stq, "enqueued 1");

		stq.push(c2);
		output(4, stq, "pushed 2");

		char[] p1 = stq.pop();
		if (!same(p1, c2)) {
			throw new RuntimeException("Bytes pop'd didn't match!");
		}

		char[] d1 = stq.dequeue();
		if (!same(d1, c4)) {
			throw new RuntimeException("Bytes pop'd didn't match!");
		}

		output(5, stq, "pop'd and dequed");
	}
}