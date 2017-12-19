/**
 * Improves on minimum spanning tree for Campus tour
 * @author difan
 *
 */
public class FindCampusTour_Prim_Dijkstra {
	Graph g;
	GraphNode home;
	boolean skateboard;
	boolean minTime;
	FindCampusTour_Prim prim;
	FindMinPath_Dijkstra dijkstra;
	SinglyLinkedList<GraphEdge> tour;

	public FindCampusTour_Prim_Dijkstra(Graph g, GraphNode home, boolean skateboard, boolean minTime) {
		this.g = g;
		this.home = home;
		this.skateboard = skateboard;
		this.minTime = minTime;
		prim = new FindCampusTour_Prim(g, home, skateboard, minTime);
	}

	public SinglyLinkedList<GraphEdge> getCampusTour() {
		prim.prim();
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
					// a break point in DFS found -
					// instead of tracking back, using dijkstra to find shortest path between current location to ancestor
					if (curtStart.id != lastDest.id && lastDest.activeEdge != null) {
						GraphNode curtEnd = e.end;
						Graph newG = cloneGraph(lastDest, curtEnd);
						//Graph newG = cloneGraph(lastDest, curtStart);
						FindMinPath_Dijkstra dijkstra = new FindMinPath_Dijkstra(newG, newG.home, newG.goal, skateboard, minTime);
						SinglyLinkedList<GraphEdge> shortestPath = dijkstra.getMinPath();
						SinglyLinkedListNode<GraphEdge> shortCurt = shortestPath.head;
						while (shortCurt != null) {
							tour.insert(shortCurt.getData());
							shortCurt = shortCurt.next;
						}
						node = g.vertex[newG.goal.id];
					}
				}
				if (tour.size == 0 || tour.tail.getData().end.id != e.end.id) {
					tour.insert(e);
				}
				//tour.insert(e);
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
	
	public Graph cloneGraph(GraphNode lastDest, GraphNode curtEnd) {
		Graph newG = new Graph();
		GraphNode newLastDest = newG.vertex[lastDest.id];
		GraphNode newCurtEnd = newG.vertex[curtEnd.id];
		newLastDest.isHome = true;
		newCurtEnd.isGoal = true;
		newG.home = newLastDest;
		newG.goal = newCurtEnd;
		return newG;
	}
}