package ekli.hw2;

/**
 * COPY this class into your USERID.hw2
 * 
 * Responsible for allocating memory from within a designated block of chars.
 * 
 * Can reallocate memory (and copy existing chars to smaller or larger
 * destination).
 * 
 * Can defragment available by combining neighboring regions together. ONLY
 * possible if the blocks of allocated memory appear in sorted order within the
 * available list (worth five points on this question).
 * 
 * Can alert user that excess memory remains unfree'd
 * 
 * Address ZERO is always invalid.
 */
public class Memory {

	/** USE THIS StorageNode CLASS AS IS. */
	class StorageNode {
		int addr; // address into storage[] array
		int numChars; // how many chars are allocated

		StorageNode next; // the next StorageNode in linked list.

		/** Allocates a new Storage Node. */
		public StorageNode(int addr, int numChars) {
			this.addr = addr;
			this.numChars = numChars;
			this.next = null;
		}

		/** Allocates a new Storage Node and makes it head of the linked list, next. */
		public StorageNode(int addr, int numChars, StorageNode next) {
			this.addr = addr;
			this.numChars = numChars;
			this.next = next;
		}

		/** Helper toString() method. */
		public String toString() {
			return "[" + addr + " @ " + numChars + " = " + (addr + numChars - 1) + "]";
		}
	}

	/** Storage of char[] that this class manages. */
	final char[] storage;

	// YOU CAN ADD FIELDS HERE
	/** Linked list of allocated storage nodes */
	private StorageNode allocatedNodes;

	/** Linked list of free storage nodes */
	private StorageNode freeNodes;

	/** Total number of characters in memory. */
	// private int totalChars;

	public Memory(int N) {
		// memory address 0 is not valid, so make array N+1 in size and never use
		// address 0.
		storage = new char[N + 1]; // for future reference, the size of the storage array is 1 greater than N

		// DO MORE THINGS HERE
		// allocatedNodes = new StorageNode(null, null, null);
		freeNodes = new StorageNode(1, N, null);

	}

	/**
	 * Make a useful debug() method.
	 * 
	 * You should print information about the AVAILABLE memory chunks and the
	 * ALLOCATED memory chunks.
	 * 
	 * This will prove to be quite useful during debugging.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();

		// Print out available memory chunks
		sb.append("AVAILABLE MEMORY:\n");
		StorageNode currentNode = freeNodes;
		while (currentNode != null) {
			sb.append("Address: " + currentNode.addr + ", Size: " + currentNode.numChars + "\n");
			currentNode = currentNode.next;
		}

		// Print out allocated memory chunks
		sb.append("\nALLOCATED MEMORY:\n");
		currentNode = allocatedNodes;
		while (currentNode != null) {
			sb.append("Address: " + currentNode.addr + ", Size: " + currentNode.numChars + "\n");
			currentNode = currentNode.next;
		}

		return sb.toString();
	}

	/**
	 * Report on # of StorageNode in allocated list (used for testing/debugging)
	 */
	public int blocksAllocated() {
		int count = 0; // counter that we are going to increment
		StorageNode currentNode = allocatedNodes; // head of the list
		while (currentNode != null) { // loops thorough the linked list
			count++; // increment counter
			currentNode = currentNode.next; // advance to next node
		}
		return count;
	}

	/**
	 * Report on # of StorageNode in available / free list (used for
	 * testing/debugging)
	 */
	public int blocksAvailable() {
		int count = 0; // counter that we are going to increment
		StorageNode currentNode = freeNodes; // head of the list
		while (currentNode != null) { // loops thorough the linked list
			count++; // increment counter
			currentNode = currentNode.next; // advance to next node
		}
		return count;
	}

	/**
	 * Report on memory that was allocated but not free'd. Performance must be O(1).
	 */
	public int charsAllocated() {
		return numCharsAllocated; // we have numCharsAllocated as a running counter so O(1).
	}

	/**
	 * Report on available memory remaining to be allocated. Performance must be
	 * O(1).
	 */
	public int charsAvailable() {
		return this.storage.length - 1 - numCharsAllocated; // computes the size of memory and subtracts the number of
															// // to get the charsAvaliable to be allocated.
	} // chars allocated

	/**
	 * Return the char at the given address. Unprotected: can return char for any
	 * address of memory.
	 */
	public char getChar(int addr) {
		// validateAllocated(addr); [SORRY I INCLUDED THIS. YOU DON'T NEED TO DO THIS]
		return storage[addr]; // returns the char in storage at the index addr
	}

	/**
	 * Get char[] at the given address for given number of chars, if valid.
	 * Unprotected: can return char[] for any address of memory. Awkward that you do
	 * not have ability to know IN ADVANCE whether this many characters are stored
	 * there, but a runtime exception will tell you.
	 */
	public char[] getChars(int addr, int numChars) {
		validateAllocated(addr, numChars);
		char[] val = new char[numChars]; // array in which we are going to store the data
		for (int i = 0; i < numChars; i++) { // loops thorough the list we are adding data to,
			val[i] = storage[addr + i]; // data in indexes from val starting at 0, going up to numChars
		} // from storage we are taking address + the i which is incrementing
		return val;
	}

	/**
	 * Get number of characters allocated at the given address, if valid.
	 * Unprotected: can return number of characters for any address of memory.
	 * Awkward that you do not have ability to know IN ADVANCE whether this many
	 * characters are stored there, but a runtime exception will tell you.
	 */
	public int getNumChars(int addr) {
		StorageNode currentNode = allocatedNodes;
		while (currentNode != null) {
			if (currentNode.addr == addr) {
				return currentNode.numChars;
			}
			currentNode = currentNode.next;
		}
		throw new RuntimeException("Invalid address: " + addr);
	}

	/**
	 * Determines if the current address is valid allocation. Throws Runtime
	 * Exception if not. Performance proportional to number of allocated blocks.
	 */
	void validateAllocated(int addr) {
		StorageNode n = allocatedNodes;
		while (n != null) {
			if (n.addr <= addr && addr < n.addr + n.numChars) {
				return;
			}
			n = n.next;
		}
		throw new RuntimeException("Address not found in allocated memory.");
	}

	/**
	 * Determines if the current address is valid allocation for the given number of
	 * characters.
	 */
	void validateAllocated(int addr, int numChar) {
		StorageNode n = allocatedNodes;
		while (n != null) {
			if (n.addr <= addr && addr + numChar <= n.addr + n.numChars) {
				return;
			}
			n = n.next;
		}
		throw new RuntimeException("Address range not found in allocated memory.");
	}

	/** Counter that keeps track of the total number of char allocated. */
	private int numCharsAllocated = 0;

	/**
	 * Internally allocates given memory if possible and return its starting
	 * address.
	 * 
	 * Must ZERO out all memory that is allocated.
	 * 
	 * @param numChars number of consecutive char to be allocated
	 */
	public int alloc(int numChars) throws RuntimeException {
		// Initialize pointers to traverse the linked list of free nodes
		StorageNode previousNode = null;
		StorageNode currentNode = freeNodes;

		// Traverse the linked list until a node with enough consecutive characters is
		// found`
		while (currentNode != null && currentNode.numChars < numChars) {
			previousNode = currentNode; // increments the position in the linked list
			currentNode = currentNode.next;
		}
		// if we get here then no chunk of free memory is big enough
		if (currentNode == null) {
			return -1; // indicate failure
		}
		
		numCharsAllocated += numChars;
		// Allocate the memory
		int address = currentNode.addr;
		currentNode.addr += numChars;
		currentNode.numChars -= numChars;
		if (currentNode.numChars == 0) {
			// The StorageNode has been fully allocated, so remove it from the free list.

			if (previousNode == null) { // checking if currentNode is the head of the linked list
				freeNodes = currentNode.next;
			} else {
				previousNode.next = currentNode.next; // currentNode is not the head of the linked list
			}
		}
		// zero out the allocated memory
		for (int i = address; i < address + numChars; i++) {
			storage[i] = '\0';
		}

		allocatedNodes = new StorageNode(address, numChars, allocatedNodes);
		return address;
	}

	/**
	 * Reallocate to larger space and copy existing chars there, while free'ing the
	 * old memory.
	 */
	public int realloc(int addr, int newSize) {
	    validateAllocated(addr);

	    // Get the old size of the allocation
	    int oldSize = 0;
	    StorageNode n = allocatedNodes;
	    while (n != null) {
	        if (n.addr == addr) {
	            oldSize = n.numChars;
	            break;
	        }
	        n = n.next;
	    }

	    // Determine the number of chars to copy to the new array
	    int numCharsToCopy = Math.min(oldSize, newSize);

	    // Allocate new memory
	    int newAddr = alloc(newSize);

	    // Copy old data into new space
	    for (int i = 0; i < numCharsToCopy; i++) {
	        setChar(newAddr + i, storage[addr + i]);
	    }

	    // Free old memory
	    free(addr);

	    if (newAddr == -1) {
	        // We couldn't allocate the new array,
	        // allocatedNodes has not changed
	        throw new RuntimeException("Could not reallocate memory.");
	    }

	    return newAddr;
	}


	/**
	 * Internally allocates sufficient memory in which to copy the given char[]
	 * array and return the starting address of memory.
	 * 
	 * @param chars - the characters to be copied into the new memory
	 * @return address of memory that was allocated
	 */
	public int copyAlloc(char[] chars) {
		int beginning = alloc(chars.length); // call alloc to allocate memory and return starting index
		
		// fill in the allocated memory
		int start = beginning;
		for (int i = 0; i < chars.length; i++) {
			storage[start++] = chars[i]; // post increment for the storage array
		}
		return beginning;
	}

	/**
	 * Free the memory currently associated with address and add back to available
	 * list.
	 * 
	 * if addr is not within a range of allocated memory, then FALSE is returned.
	 */
	public boolean free(int addr) {
		validateAllocated(addr);
		StorageNode prev = null;
		StorageNode node = allocatedNodes;
		while (node != null) {
			if (node.addr == addr) {
				if (prev == null) {
					allocatedNodes = node.next;
				} else {
					prev.next = node.next;
				}
				
				numCharsAllocated -= node.numChars;
				insertAvailable(node);
				return true;
			}
			prev = node;
			node = node.next;
		}
		return false;
	}

	/**
	 * Inserts a storage node into the available memory list in ascending order by
	 * address.
	 * 
	 * @param node The storage node to be inserted.
	 */
	private void insertAvailable(StorageNode node) {
		if (freeNodes == null) { // empty list
			freeNodes = node;
		} else if (node.addr < freeNodes.addr) { // new value is smaller than head's value
			// Check if nodes are adjacent
			if (node.addr + node.numChars == freeNodes.addr) {
				node.next = freeNodes.next;
				node.numChars += freeNodes.numChars;
				freeNodes = node;
			} else {
				node.next = freeNodes;
				freeNodes = node;
			}
		} else { // find the right place to insert after head
			StorageNode prev = freeNodes;
			StorageNode curr = freeNodes.next;
			while (curr != null) {
				if (node.addr < curr.addr) {
					// Check if nodes are adjacent
					if (node.addr + node.numChars == curr.addr) {
						node.next = curr.next;
						node.numChars += curr.numChars;
						prev.next = node;
					} else {
						prev.next = node;
						node.next = curr;
					}
					return;
				}
				// advance
				prev = curr;
				curr = curr.next;
			}
			// new value is the largest in the linked list
			if (node.addr == prev.addr + prev.numChars) {
				prev.numChars += node.numChars;
			} else {
				prev.next = node;
			}
		}

	}

	/**
	 * Set char, but only if it is properly contained as an allocated segment of
	 * memory. Performance proportional to number of allocated blocks.
	 * 
	 * @exception if the addr is not within address of memory that was formerly
	 *               allocated.
	 */
	public void setChar(int addr, char value) throws RuntimeException {
		validateAllocated(addr);
		StorageNode n = allocatedNodes;
		while (n != null) {
			if (n.addr <= addr && addr < n.addr + n.numChars) {
				storage[addr] = value;
				return;
			}
			n = n.next;
		}
		throw new RuntimeException("Address not found in allocated memory.");
	}

	/**
	 * Set consecutive char values starting with addr to contain the char values
	 * passed in, but only if the full range is properly contained as an allocated
	 * segment of memory. Performance proportional to number of allocated blocks.
	 * 
	 * @exception if the addr is not within address of memory that was formerly
	 *               allocated.
	 */
	public void setChars(int addr, char[] values) throws RuntimeException {
		validateAllocated(addr);
		StorageNode n = allocatedNodes;
		int numChars = values.length;
		while (n != null) {
			if (n.addr <= addr && addr + numChars <= n.addr + n.numChars) {
				for (int i = 0; i < numChars; i++) {
					storage[addr + i] = values[i];
				}
				return;
			}
			n = n.next;
		}
		throw new RuntimeException("Address not found in allocated memory.");
	}

	// ================================================================================================================
	// ======================================== EVERYTHING ELSE BELOW REMAINS AS IS
	// =================================== Cool 👍
	// ================================================================================================================

	/**
	 * Sets int, but only if it is properly contained as an allocated segment of
	 * memory. Performance proportional to number of allocated blocks. USE AS IS.
	 * 
	 * @exception if the addr is not within address of memory that was formerly
	 *               allocated with sufficient space
	 */
	public void setInt(int addr, int value) throws RuntimeException {
		validateAllocated(addr, 4);
		setChar(addr, (char) ((value & 0xff000000) >> 24));
		setChar(addr + 1, (char) ((value & 0xff0000) >> 16));
		setChar(addr + 2, (char) ((value & 0xff00) >> 8));
		setChar(addr + 3, (char) (value & 0xff));
	}

	/**
	 * Return the 4-chars at the given address as an encoded integer. Performance
	 * proportional to number of allocated blocks. USE AS IS.
	 */
	public int getInt(int addr) {
		validateAllocated(addr, 4);
		return (getChar(addr) << 24) + (getChar(addr + 1) << 16) + (getChar(addr + 2) << 8) + getChar(addr + 3);
	}

	/**
	 * Allocate new memory large enough to contain room for an array of numbers and
	 * copy numbers[] into the memory, returning the address of the first char.
	 * 
	 * USE AS IS.
	 * 
	 * Because int values are 32-bits, this means that the total number of char
	 * allocated will be 4*numbers.length
	 * 
	 * @param numbers The int[] values to be copied into the newly allocated
	 *                storage.
	 */
	public int copyAlloc(int[] numbers) {
		int addr = alloc(4 * numbers.length);
		for (int i = 0; i < numbers.length; i++) {
			setInt(addr + 4 * i, numbers[i]);
		}

		return addr;
	}

	/**
	 * Return the string which is constructed from the sequence of char from addr
	 * for len characters. USE AS IS.
	 */
	public String createString(int addr, int len) {
		return String.valueOf(storage, addr, len);
	}

	/**
	 * Return those allocated nodes whose allocated char[] matches the pattern of
	 * char[] passed in. ONLY COMPLETE FOR BONUS
	 * 
	 * @param pattern
	 */
	public java.util.Iterator<StorageNode> match(char[] pattern) {
		throw new RuntimeException("BONUS IMPLEMENTATION");
	}

	/**
	 * This sample program recreates the linked list example from Q2 on the
	 * homework.
	 */
	public static void main(String[] args) {
		Memory mem = new Memory(32);

		mem.alloc(2); // don't use address in this small example...
		int first = mem.alloc(8);
		mem.alloc(3);
		int third = mem.alloc(8);
		mem.alloc(3);
		int second = mem.alloc(8);

		mem.setInt(first, 178); // first node stores 178
		mem.setInt(second, 992); // second node stores 992
		mem.setInt(third, 194); // third node stores 194

		mem.setInt(first + 4, second); // have next pointer for first to point to second
		mem.setInt(second + 4, third); // have next pointer for second to point to third
		mem.setInt(third + 4, 0); // have next pointer for third to be 0 (END OF LIST)

		// How to loop through list?
		System.out.println("Numbers should print in order from 178 -> 992 -> 194");
		int addr = first;
		while (addr != 0) {
			int value = mem.getInt(addr); // get value of node pointed to by addr.
			System.out.println(value);

			addr = mem.getInt(addr + 4); // advance to next one in the list
		}

		System.out.println("Allocated bytes should be 32: " + mem.charsAllocated());
		System.out.println("Available bytes should be 0: " + mem.charsAvailable());
	}
}