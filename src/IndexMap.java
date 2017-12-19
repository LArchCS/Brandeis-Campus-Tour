/**
 * A light weight "hash" map, just works for this assignment, because our verteces have IDs.
 * A more versatile and complete map is "GraphIndexHashMap" with hash function, resizing, and probing.
 * key = GraphNode.id; value = index in BinaryHeap
 * @author difan
 *
 */
public class IndexMap {
	int[] table;

	public IndexMap() {
		table = new int[152];
		for (int i = 0; i < 152; i++) {
			table[i] = -1;
		}
	}

	// check the hash map to see if there is an Entry for the GraphNode ¡°key¡±
	// if there is, change its value to ¡°value¡±,
	// otherwise add it to the int[] table with new value
	public void put(GraphNode key, int value) {
		table[key.id] = value;
	}

	// gets the value for the entry
	// return -1 if the entry does not exist in the hash map
	public int get(GraphNode g) {
		return table[g.id];
	}

	// true if the hash map has that key
	public boolean containsKey(GraphNode g) {
		return table[g.id] != -1;
	}

	// delete by id
	public void delete(int i) {
		if (0 <= i && i < table.length) {
			table[i] = -1;
		}
	}

	// delete by node
	public void delete(GraphNode g) {
		delete(g.id);
	}
}