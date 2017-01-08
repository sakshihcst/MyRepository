package DataStructures.LinkedListImpl;

interface LinkedListInterface<T> {
	
}

/*https://code.snipcademy.com/tutorials/data-structures/linked-lists/singly-linked-list
	http://www.java2novice.com/data-structures-in-java/linked-list/singly-linked-list/
		http://www.sanfoundry.com/java-program-implement-singly-linked-list/
*/
class Node<T> {
	
	private T value;
	private Node<T> next;
	
	public Node(T value, Node<T> next) {
		this.value = value;
		this.setNext(next);
	}

	public Node<T> getNext() {
		return next;
	}

	public void setNext(Node<T> next) {
		this.next = next;
	}
}

public class LinkedList<T> implements LinkedListInterface<T>{
	
	int size = 0;
	private Node<T> head;
    private Node<T> tail;
    
    public LinkedList() {
    	head = null;
    	tail = null;
    	size = 0;
    }
    
    public int getSize(){
    	return size;
    }
    
    public void add(T element){
    	
    }
    
    public void add(int index, T element){
    	if(index<=0){
    		addFirst(element);
    	}else if(index>=size) {
    		addLast(element);
    	}else {
    		Node<T> node = head;
    		for(int i=1;i<index;i++){
    			node = node.getNext();
    		}
    		Node<T> newNode = new Node<T>(element, null);
    		newNode.setNext(node.getNext());
    		node.setNext(newNode);
    		size++;
    	}

    }
    
    public void addFirst(T element){
    	Node<T> node = new Node<T>(element, null);
    	size++;
    	if(head == null){
    		head = node;
    		tail = node;
    	}else {
    		node.setNext(head);
    		head = node;
    	}
    }
    
    public void addLast(T element){
    	Node<T> node = new Node<T>(element, null);
    	size++;
    	if(head == null){
    		head = node;
    		tail = node;
    	}else {
    		tail.setNext(node);
    		tail = node;
    	}
    	
    }
    
    public void remove(T element){
    	
    }
    
    public void remove(int index, T element){
    	
    }
    
    public void removeFirst(T element){
    	if(head!=null){
    		Node<T> node = head;
    		head = head.getNext();
    		node = null;
    		size--;
    	}
    	if(head==null){
    		tail=null;
    	}
    }
    
    public void removeLast(T element){
    	if(tail!=null){
    		
    	}
    }
    
    public void contains(T element){
    	
    }
    
    public void traverse(){
    	
    }

}



