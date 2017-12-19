/**
 * Instead of the Array UnionFind normally seen on slides
 * I like to implement UnionFind using a map. It is more versatile and light weight.
 * @author difan
 *
 */
public class UnionFind {
	IndexMap dic;
	Graph g;
	
	public UnionFind(Graph g) {
		dic = new IndexMap();
		this.g = g;
	}
	
	// find with path compression
	public int find(GraphNode x) {
		if (!dic.containsKey(x)) {
			dic.put(x, x.id);
		}
		if (dic.get(x) != x.id) {
			int pathCompression = this.find(g.vertex[dic.get(x)]);
			dic.put(x, pathCompression);
		}
		return dic.get(x);
	}

	// join x to y, order matters
	public void union(GraphNode x, GraphNode y) {
		int rootY = find(y);
		dic.put(g.vertex[find(x)], rootY);
	}
}