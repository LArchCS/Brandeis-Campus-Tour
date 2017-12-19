/**
 * A graph node.
 * The graph is represented using adjacency list.
 * @author difan
 *
 */
public class GraphNode {
	Graph g;
	// Vertex format is: # label x y name
	int id;
	String label;
	int x;
	int y;
	String name;
	// store info for priority queue: home, goal, priority
	double priority = Integer.MAX_VALUE;
	boolean isHome = false;
	boolean isGoal = false;
	// adjacency list representation of graph - vertex: edges
	SinglyLinkedList<GraphEdge> children = new SinglyLinkedList<GraphEdge>();
	// these fields are for DFS
	GraphEdge activeEdge;
	SinglyLinkedList<GraphEdge> treeEdge = new SinglyLinkedList<GraphEdge>();;

	public GraphNode(int id, String label, int x, int y, String name) {
		this.id = id;
		this.label = label;
		this.x = x;
		this.y = y;
		this.name = name;
	}
		
	public int getId() {
		return this.id;
	}
	
	public void addEdge(GraphEdge edge) {
		children.insert(edge);
	}
	
	public String toString() {
		return String.format("%d %s %d %d %s", id, label, x, y, name);
	}
	
	public String printNode() {
		//return String.format("%d %s %d %d %s", id, label, x, y, name);
		return String.format("(%s) %s", label, name);
	}
}