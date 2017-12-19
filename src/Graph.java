import java.io.File; // for file
import java.io.FileNotFoundException; // for file
import java.util.Scanner; // to read file

/**
 * This is the graph to hold vertices and edges.
 * Although it is called a graph, it functions more like a graph builder.
 * @author difan
 *
 */
public class Graph {
	String vertexFile;
	String edgeFile;
	GraphNode[] vertex = new GraphNode[152];
	GraphEdge[] edges = new GraphEdge[596];
	GraphNode home = null;
	GraphNode goal = null;

	public Graph() {
		this(null, null);
	}

	public Graph(String s1, String s2) {
		vertexFile = s1 != null ? s1 : "MapDataVertices.txt";
		edgeFile = s2 != null ? s2 : "MapDataEdges.txt";
		constructVertex();
		constructEdges();
	}

	public void constructVertex() {
		File f = new File(vertexFile);
		try {
			Scanner input = new Scanner(f);
			while (input.hasNextLine()) {
				String line = input.nextLine();
				Scanner token = new Scanner(line);
				if (!token.hasNextInt()) {
					continue;
				}
				int id = token.nextInt();
				String label = token.next();
				int x = token.nextInt();
				int y = token.nextInt();
				String name = token.nextLine().trim();
				GraphNode node = new GraphNode(id, label, x, y, name.substring(1, name.length() - 1));
				node.g = this;
				vertex[id] = node;
				token.close();
			}
			input.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void constructEdges() {
		File f = new File(edgeFile);
		try {
			Scanner input = new Scanner(f);
			while (input.hasNextLine()) {
				String line = input.nextLine();
				Scanner token = new Scanner(line);
				if (!token.hasNextInt()) {
					continue;
				}
				// #, label1 label2 v1 v2 length, angle, direction (C) name
				int id = token.nextInt();
				token.next();  // label1
				token.next();  // label2
				int v1 = token.nextInt();
				int v2 = token.nextInt();
				int length = token.nextInt();
				int angel = token.nextInt();
				String direction = token.next();
				String condition = token.next();
				String name = token.nextLine().trim();
				GraphEdge edge = new GraphEdge(id, vertex[v1], vertex[v2], length, angel, direction,
						condition.substring(1, 2), name.substring(1, name.length() - 1));
				edges[id] = edge;
				vertex[v1].addEdge(edge);
				token.close();
			}
			input.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}