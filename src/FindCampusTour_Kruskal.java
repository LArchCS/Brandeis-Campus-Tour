/**
 * Using Kruskal's Algorithm and UnionFind to generate minimum spanning tree.
 * Also constructs back tracing paths to make it a campus tour.
 * @author difan
 *
 */
public class FindCampusTour_Kruskal {
	Graph g = null;
	GraphNode home = null;
	boolean skateboard = false;
	boolean minTime = false;
	UnionFind uf = null;
	
	public FindCampusTour_Kruskal(Graph g, GraphNode home, boolean skateboard, boolean minTime) {
		this.g = g;
		this.home = home;
		this.skateboard = skateboard;
		this.minTime = minTime;
		this.uf = new UnionFind(g);
	}

	public SinglyLinkedList<GraphEdge> getCampusTour() {
		kruskal();
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

	public SinglyLinkedList<GraphEdge> kruskal() {
		// add vertex into union find structure
		for (int i = 5; i < g.vertex.length; i++) {
			uf.find(g.vertex[i]);
		}
		
		// sort edges either based on length or time
		for (int i = 20; i < g.edges.length; i++) {
			g.edges[i].getTime(skateboard);
		}
		new QuickSort(g.edges, minTime);
		
		// union find and return active path
		SinglyLinkedList<GraphEdge> treePaths = new SinglyLinkedList<GraphEdge>();
		for (int i = 0; i < g.edges.length; i++) {
			GraphEdge e = g.edges[i];
			GraphNode x = e.start;
			GraphNode y = e.end;
			// ignore sample vertex
			if (x.id < 5 || y.id < 5) {
				continue;
			}
			int xFind = uf.find(x);
			int yFind = uf.find(y);
			//GraphNode xFind = uf.find(x);
			//GraphNode yFind = uf.find(y);
			if (xFind != yFind && yFind != uf.find(home)) {
				uf.union(y, x);
				treePaths.insert(e);
				GraphEdge revE = e.getReverse();
				treePaths.insert(revE);
			}
		}
		SinglyLinkedListNode<GraphEdge> curt = treePaths.head;
		while (curt != null) {
			GraphEdge e = curt.getData();
			e.start.treeEdge.insert(e);
			curt = curt.next;
		}
		for (int i = 0; i < g.vertex.length; i++) {
			g.vertex[i].activeEdge = null;
		}
		
		return treePaths;
	}
}