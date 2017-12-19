/**
 * A typical binary min heap supports percolate up/down, extract min.
 * Powered by a Map, thus contains() function runs in O(1) time instead of O(n).
 * @author difan
 *
 */
public class BinaryMinHeap {
	protected GraphNode[] A;
	protected IndexMap hashMap = new IndexMap();
	protected int size;
	
	public BinaryMinHeap() {
		size = 0;
		A = new GraphNode[2];
	}

	// insert g into the queue with its priority
	public void push(GraphNode g) {
		if (size >= A.length - 1) {
			GraphNode[] B = new GraphNode[A.length * 2];
			for (int i = 0; i < A.length; i++) {
				B[i] = A[i];
			}
			A = B;
		}
		A[size] = g;
		hashMap.put(g, size);
		size += 1;
		heapify_up(size - 1);
	}

	// return and remove from the priority queue
	// the GraphNode with the highest priority in the queue
	public GraphNode pop() {
		if (size <= 0) {
			return null;
		}
		GraphNode res = A[0];
		A[0] = A[size - 1];
		hashMap.put(A[0], 0);
		size -= 1;
		if (size <= A.length / 4) {
			//A = Arrays.copyOf(A, Math.max(2, A.length / 2));
			GraphNode[] B = new GraphNode[A.length / 2];
			for (int i = 0; i < B.length; i++) {
				B[i] = A[i];
			}
			A = B;
		}
		heapify_down(0);
		return res;
	}

	public boolean contains(GraphNode node) {
		return hashMap.containsKey(node);
	}

	// this calls the heapify method described below
	public void rebalance() {
		heapify();
	}

	// returns true if the queue is empty
	public boolean isEmpty() {
		return size == 0;
	}

	// heapify the entire array A
	public void heapify() {
		for (int i = size / 2; i >= 0; i--) {
			heapify_down(i);
		}
	}

	// heapify-down the node at index i in A
	public void heapify_down(int i) {
		int l = left(i);
		int r = right(i);
		int smallest = i;
		if (l < size && A[l].priority < A[smallest].priority) {
			smallest = l;
		}
		if (r < size && A[r].priority < A[smallest].priority) {
			smallest = r;
		}
		if (smallest != i) {
			swap(i, smallest);
			heapify_down(smallest);
		}
	}
	
	// heapify-up the node at index i in A
	public void heapify_up(int i) {
		int parent = parent(i);
		while (i > 0 && A[i].priority < A[parent].priority) {
			swap(i, parent);
			i = parent;
			parent = parent(i);
		}
	}
	
	// return the left child of i
	public int left(int i) {
		return 2 * i + 1;
	}
	
	// return the right child of i
	public int right(int i) {
		return 2 * i + 2;
	}
	
	// return the parent of i
	public int parent(int i) {
		return (i - 1) / 2;
	}

	// swap two indices in the array A
	public void swap(int i, int p) {
		//swapValue(i, p);
		GraphNode temp = A[i];
		A[i] = A[p];
		A[p] = temp;
		hashMap.put(A[i], i);
		hashMap.put(A[p], p);
	}

	// return every bucket as a separate line
	// format
	// index: id <priority> [value]
	public String toString() {
		String res = "";
		for (int i = 0; i < size; i++) {
			res += i + ": " + A[i].getId() + " <" + A[i].priority + "> [" + hashMap.get(A[i]) + "]" + "\n";
		}
		return res;
	}
}