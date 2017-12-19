/**
 * Node for linked list.
 * @author difan
 *
 */
public class SinglyLinkedListNode<T> {
	T edge;
	SinglyLinkedListNode<T> next;
	
	public SinglyLinkedListNode(T e) {
		edge = e;
		next = null;
	}
	public void setNext(SinglyLinkedListNode<T> n) {
		next = n;
	}
	
	public SinglyLinkedListNode<T> getNext() {
		return next;
	}

	public T getData() {
		return edge;
	}
}