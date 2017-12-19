/**
 * Using Prim's Algorithm to generate minimum spanning tree.
 * Also constructs back tracing paths to make it a campus tour.
 * @author difan
 *
 */
public class FindCampusTour_Prim {
	Graph g;
	GraphNode home;
	boolean skateboard;
	boolean minTime;
	MinPriorityQueue Q = new MinPriorityQueue();
	
	public FindCampusTour_Prim(Graph g, GraphNode home, boolean skateboard, boolean minTime) {
		this.g = g;
		this.home = home;
		this.skateboard = skateboard;
		this.minTime = minTime;
	}

	public SinglyLinkedList<GraphEdge> getCampusTour() {
		prim();
		// Construct the minimum spanning tree in DFS pre-order traversal order
		// Also construct back edges tracing back to ancestors
		SinglyLinkedList<GraphEdge> tour = new SinglyLinkedList<GraphEdge>();
		SinglyLinkedList<GraphNode> stack = new SinglyLinkedList<GraphNode>();
		stack.insert(home);
		int[] seen = new int[g.vertex.length];
		while (stack.size > 0) {
			GraphNode node = stack.pop(stack.size - 1);
			seen[node.id] = 1;
			if (node.activeEdge != null) {
				GraphEdge e = node.activeEdge;
				if (tour.size > 0) {
					GraphNode lastDest = tour.tail.getData().end;
					GraphNode curtStart = e.start;
					// a break point in DFS found, trace back until merge to route
					while (curtStart != lastDest && lastDest.activeEdge != null) {
						GraphEdge rEdge = lastDest.activeEdge.getReverse();
						tour.insert(rEdge);
						lastDest = tour.tail.getData().end;
					}
				}
				tour.insert(e);
			}
			while (node.treeEdge.size > 0) {
				GraphEdge e = node.treeEdge.pop(0);
				if (seen[e.end.id] == 0) {
					stack.insert(e.end);
					e.end.activeEdge = e;
				}
			}
		}
		return tour;
	}

	public SinglyLinkedList<GraphEdge> prim() {
		home.priority = 0;
		for (int i = 5; i < g.vertex.length; i++) {
			Q.offer(g.vertex[i]);
		}
		SinglyLinkedList<GraphEdge> treePaths = new SinglyLinkedList<GraphEdge>();
		while (!Q.isEmpty() && Q.A[0].priority < Integer.MAX_VALUE) {
			GraphNode u = Q.poll();
			SinglyLinkedListNode<GraphEdge> node = u.children.head;
			while (node != null) {
				GraphEdge e = node.getData();
				GraphNode v = e.end;
				double w = minTime ? e.getTime(skateboard) : e.length;
				if (Q.contains(v) && v.priority > w) {
					Q.setPriority(v, w);
					v.activeEdge = e;
				}
				node = node.next;
			}

			if (u.activeEdge != null) {
				treePaths.insert(u.activeEdge);
				u.activeEdge = null;
			}
		}
		SinglyLinkedListNode<GraphEdge> curt = treePaths.head;
		while (curt != null) {
			GraphEdge e = curt.getData();
			e.start.treeEdge.insert(e);
			curt = curt.next;
		}
		
		return treePaths;
	}
}