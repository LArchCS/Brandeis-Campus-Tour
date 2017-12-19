/**
 * A Singly Linked List.
 * @author difan
 *
 */
public class SinglyLinkedList<T> {
	SinglyLinkedListNode<T> head;
	SinglyLinkedListNode<T> tail;
	int size;
	
	public SinglyLinkedList() {
		head = null;
		tail = null;
		size = 0;
	}
	
	public T get(int index) {
		if (0 > index || index >= size) {
			return null;
		}
		int i = 0;
		SinglyLinkedListNode<T> e = head;
		while (i < index) {
			e = e.next;
		}
		return (e == null) ? null: e.getData();
	}
	
	public void add(T e) {
		SinglyLinkedListNode<T> node = new SinglyLinkedListNode<T>(e);
		// edge case: empty list
		if (size == 0) {
			head = node;
			tail = node;
			size += 1;
			return;
		}
		node.next = head;
		head = node;
		size += 1;
	}
	
	public void insert(T e) {
		SinglyLinkedListNode<T> node = new SinglyLinkedListNode<T>(e);
		// edge case: empty list
		if (size == 0) {
			head = node;
			tail = node;
			size += 1;
			return;
		}
		tail.setNext(node);
		tail = node;
		size += 1;
	}

	public T pop(int index) {
		if (size > 0) {
			return removeIndex(index);
		}
		return null;
	}

	public T removeIndex(int index) {
		// edge case: index out of range
		if (index > size - 1) {
			return null;
		}
		SinglyLinkedListNode<T> dummy = new SinglyLinkedListNode<T>(null);
		SinglyLinkedListNode<T> prev = dummy;
		SinglyLinkedListNode<T> curt = head;
		prev.setNext(curt);
		// find the position and remove
		for (int i = 0; i < index; i++) {
			prev = prev.getNext();
			curt = curt.getNext();
		}
		// found and remove
		remove(prev, curt);
		return curt.getData();
	}
	
	// remove a given node from the list
	// to be used by two other remove methods, keep the code DRY
	// running time: O(1)
	private void remove(SinglyLinkedListNode<T> prev, SinglyLinkedListNode<T> curt) {
		// found and remove
		prev.setNext(curt.getNext());
		// edge case: head
		if (head == curt) {
			head = curt.getNext();
		}
		// edge case: tail
		if (tail == curt) {
			tail = prev;
		}
		size -= 1;
	}
}