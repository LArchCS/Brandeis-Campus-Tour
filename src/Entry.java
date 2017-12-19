/**
 * Entry for hash map.
 * Binds Key and Value.
 * @author difan
 *
 */
public class Entry {
	protected GraphNode key;
	protected int value;
	
	
	public Entry(GraphNode key, int value) {
		setKey(key);
		setValue(value);
	}
	
	public void setKey(GraphNode key) {
		this.key = key;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public GraphNode getKey() {
		return this.key;
	}
	
	public int getValue() {
		return this.value;
	}
	
	// id <priority> [value]
	public String toString() {
		return key.getId() + ":<" + key.priority + "><" + value + ">";
	}
}