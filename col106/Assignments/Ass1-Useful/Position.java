public class Position<T> implements Position_<T> {
	T data;
	Position<T> next_node;
	public Position(T e) {
		data=e;
		next_node=null;
	}
	public T value() {
		return data;
	}

	public Position<T> after() {
		return next_node;
	
	}
	

}

