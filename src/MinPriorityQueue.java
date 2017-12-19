/**
 * A min priority queue extends a binray min heap.
 * Powered by a Map, thus contains() function runs in O(1) time instead of O(n).
 * Supports decrease / increase priority in place.
 * @author difan
 *
 */
public class MinPriorityQueue extends BinaryMinHeap {
	public MinPriorityQueue() {
		super();
	}

	// return and remove from the priority queue
	// the GraphNode with the highest priority in the queue
	public GraphNode poll() {
		return super.pop();
	}
	
	public void offer(GraphNode g) {
		super.push(g);
	}

	// call heap's increase/decrease key
	public void setPriority(GraphNode g, double newPriority) {
		g.priority = newPriority;
		heapify();
		/*int index = hashMap.get(g);
		GraphNode key = A[index] == null ? g : A[index];
		if (newPriority > key.priority) {
			increaseKey(key, newPriority);
		} else if (newPriority < key.priority) {
			decreaseKey(key, newPriority);
		}*/
	}

	public boolean contains(GraphNode g) {
		return hashMap.containsKey(g);
	}

	// decrease a node's priority, and heapify-up
	public void decreaseKey(GraphNode g, double newPriority) {
		int index = hashMap.get(g);
		if (newPriority >= g.priority || index == -1) {
			return;
		}
		A[index].priority = newPriority;
		heapify_up(index);
	}

	// increase a node's priority, and heapify-down
	public void increaseKey(GraphNode g, double newPriority) {
		int index = hashMap.get(g);
		if (newPriority <= g.priority || index == -1) {
			return;
		}
		A[index].priority = newPriority;
		heapify_down(index);
	}
}