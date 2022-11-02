public class Node<V> extends NodeBase<V> {
	
	public Node(int priority, V value) {
		this.priority = priority;
		this.value = value;
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return priority;
	}

	@Override
	public V getValue() {
		// TODO Auto-generated method stub
		return value;
	}

	

}
