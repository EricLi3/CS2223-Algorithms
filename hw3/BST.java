package ekli.hw3;

import edu.princeton.cs.algs4.Queue;

/**
 * MINIMAL BST that just stores integers as the key and values are eliminated.
 * Note that duplicate values can exist (i.e., this is not a symbol table).
 * 
 * COPY this file into your USERID.hw3 package and complete the final four
 * methods in this class.
 */
public class BST {
	// root of the tree
	Node root;

	// Use Node class as is without any change.
	class Node {
		int key; // SIMPLIFIED to just use int
		Node left, right; // left and right subtrees

		public Node(int key) {
			this.key = key;
		}

		public String toString() {
			return "[" + key + "]";
		}
	}

	/** Check if BST is empty. */
	public boolean isEmpty() {
		return root == null;
	}

	/** Determine if key is contained. */
	public boolean contains(int key) {
		return contains(root, key);
	}

	/** Recursive helper method for contains. */
	boolean contains(Node parent, int key) {
		if (parent == null)
			return false;

		if (key < parent.key) {
			return contains(parent.left, key);
		} else if (key > parent.key) {
			return contains(parent.right, key);
		} else {
			return true; // found it!
		}
	}

	/** Invoke add on parent, should it exist. */
	public void add(int key) {
		root = add(root, key);
	}

	/** Recursive helper method for add. */
	Node add(Node parent, int key) {
		if (parent == null) {
			return new Node(key);
		}

		if (key <= parent.key) {
			parent.left = add(parent.left, key);
		} else if (key > parent.key) {
			parent.right = add(parent.right, key);
		}

		return parent;
	}

	// AFTER THIS POINT YOU CAN ADD CODE....
	// ----------------------------------------------------------------------------------------------------

	/** Return a new BST that is a structural copy of this current BST. */
	public BST copy() {
		BST newBST = new BST(); // create new BST to store copy
		newBST.root = copyNode(root); // populate the new tree via recursive method
		return newBST; // return copy
	}

	private Node copyNode(Node node) { // recursive helper method
		if (node == null) { // base case
			return null;
		}
		Node newNode = new Node(node.key); // new Node for the root
		newNode.left = copyNode(node.left); // recurse on the left and right children.
		newNode.right = copyNode(node.right);	// creating nodes for each
		return newNode;
	}

	/** Return the count of nodes in the BST whose key is even. */
	public int countIfEven() {
		int numberNodesEven = keys(root);
		return numberNodesEven;
	}

	private int keys(Node node) { // recursive helper method
		if (node == null) { // base case
			return 0;
		}
		int count = 0;
		if (node.key % 2 == 0) { // use modulo to check if the key is even or not
			count++;
		}
		count += keys(node.left); // update the value for count by adding the result
		count += keys(node.right); // of calling keys on both the left and right subtrees.
		return count;
	}

	/** Return a Queue<Integer> containing the depths for all nodes in the BST. */
	public Queue<Integer> nodeDepths() {
		Queue<Integer> q = new Queue<>();
		if (root == null) {
			return q;
		}

		depth(root, q); // call the helper method
		return q; // return potential updated queue
	}

	/**
	 * Helper method for determining the depth of a node and adding it to the queue
	 */
	private void depth(Node parent, Queue<Integer> q) {
		// base case
		if (parent == null) {
			return;
		}
		// traverse the tree in order as preference

		// - left
		depth(parent.left, q); // call depth recursively
		// - add self
		q.enqueue(getDepth(parent));
		// - right
		depth(parent.right, q); // call depth recursively
	}

	/**
	 * Helper method for determining the depth of a singular node
	 */
	private int getDepth(Node node) {
		int depth = 0;
		Node curr = root;
		while (curr != null) { // go until we reach a node that is null...
			if (node.key < curr.key) { // if less than root, go left
				curr = curr.left;
			} else if (node.key > curr.key) { // greater than root, go right
				curr = curr.right;
			} else {
				return depth; // else we are already at the node
			}
			depth++; // increment as we move down the tree
		}
		return depth; // return the depth of the given node
	}

	/** Remove all leaf nodes that are odd. */
	public void removeLeafIfOdd() {
		root = removeLeafIfOddHelper(root);	// essentially has updates the tree
	}

	/** Helper method that deletes leaf nodes of a BST if they exist and are odd */
	private Node removeLeafIfOddHelper(Node node) {
		if (node == null) {
			return null;
		}
		// use post order traversal so subtrees leafs are deleted first then we can check if the 
		// node itself is a leaf or 
		node.left = removeLeafIfOddHelper(node.left); // recursive call on left
		node.right = removeLeafIfOddHelper(node.right); // call on right
		if (node.left == null && node.right == null && node.key % 2 != 0) { // check self
			node = null;	// removed the node if a leaf that's odd. 
		}
		return node;
	}
}
