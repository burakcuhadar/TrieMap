public class Node<T> implements Comparable<Node<T>>{
	public static int alphabetSize;
	public T value;
	public Node<T>[] children;
	public String key;
	
	
	public Node(T value) {
		this.value = value;
		children = new Node[Node.alphabetSize];
	}


	@Override
	public int compareTo(Node<T> other) {
		return -((Comparable<T>) this.value).compareTo(other.value);
	}
	
	

}
