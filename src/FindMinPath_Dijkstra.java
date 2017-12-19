/**
 * The legendary Dijkstra's Algorithm to find the shortest path from start to target.
 * @author difan
 *
 */
public class FindMinPath_Dijkstra {
	Graph g;
	GraphNode home;
	GraphNode goal;
	boolean skateboard;
	boolean minTime;
	boolean part1;
	MinPriorityQueue Q = new MinPriorityQueue();

	public FindMinPath_Dijkstra (Graph g, GraphNode home, GraphNode goal, boolean skateboard, boolean minTime) {
		this.g = g;
		this.home = home;
		this.goal = goal;
		this.skateboard = skateboard;
		this.minTime = minTime;
	}

	public SinglyLinkedList<GraphEdge> getMinPath() {
		home.priority = 0;
		Q.offer(home);
		dijkstra();
		SinglyLinkedList<GraphEdge> path = new SinglyLinkedList<GraphEdge>();
		while (goal != null && goal.activeEdge != null) {
			path.add(goal.activeEdge);
			goal = goal.activeEdge.start;
		}
		return path;
	}

	public void dijkstra() {
		while (!Q.isEmpty()) {
			GraphNode u = Q.poll();
			if (u.isGoal) {
				break;
			}
			SinglyLinkedListNode<GraphEdge> node = u.children.head;
			while (node != null) {
				relax(u, node.edge);
				node = node.next;
			}
		}
	}

	public void relax(GraphNode u, GraphEdge e) {
		GraphNode v = e.end;
		double w = minTime ? e.getTime(skateboard) : e.length;
		if (v.priority > u.priority + w) {
			if (Q.contains(v) == true) {
				Q.setPriority(v, u.priority + w);
			} else {
				v.priority = u.priority + w;
				Q.offer(v);
			}
			v.activeEdge = e;
		}
	}
}