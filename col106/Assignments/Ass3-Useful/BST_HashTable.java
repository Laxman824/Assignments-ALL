public class BST_HashTable<K,T> implements MyHashTable_<K,T> {
	
	bst<T>[] bst_arr;
	int arr_size;
	
	BST_HashTable(int size){
		bst_arr=new bst[size];
		arr_size=size;
		for(int i=0;i<arr_size;i++) {
			bst_arr[i]=new bst<T>();
		}
	}
	
	public static long djb2(String str, int hashtableSize) { 
	    long hash = 5381; 
	    for (int i = 0; i < str.length(); i++) { 
	        hash = ((hash << 5) + hash) + str.charAt(i); 
	    } 
	    return Math.abs(hash) % hashtableSize; 
	}
	
	public static long sdbm(String str, int hashtableSize) { 
	    long hash = 0; 
	    for (int i = 0; i < str.length(); i++) { 
	        hash = str.charAt(i) + (hash << 6) + (hash << 16) - hash; 
	    } 
	    return Math.abs(hash) % (hashtableSize - 1) + 1; 
	}
	public int hashfunc(String k,int i) {
		int l=(int) (djb2(k,arr_size)+i*sdbm(k,arr_size));
		return l;
	}

	public int insert(K key, T obj) {
		String x=key.toString();
		String[] y=x.split(" ");
		String fname=y[0];
		String lname=y[1];
		int in_key=hashfunc(fname+lname,0);
		Node_<T> stud=new Node_<T>(obj);
		int k=bst_arr[in_key].insert(stud);
		
		return k;
	}

	public int update(K key, T obj)  {
	
		String x=key.toString();
		String[] y=x.split(" ");
		String fname=y[0];
		String lname=y[1];
		int in_key=hashfunc(fname+lname,0);
		Node_<T> stud=new Node_<T>(obj);
	
		String k=bst_arr[in_key].update(stud);
		
		if(k==null) {
			return -1000;
		}else {
		return Integer.parseInt(k);
		}
	}

	@Override
	public int delete(K key) {
	
		String x=key.toString();
		String[] y=x.split(" ");
		String fname=y[0];
		String lname=y[1];
		
		int in_key=hashfunc(fname+lname,0);
		String k=bst_arr[in_key].delete(fname,lname);
		if(k!=null) {
		return Integer.parseInt(k);
		}else {
		return -1000;	
		}
	}

	@Override
	public boolean contains(K key) {
		
		String x=key.toString();
		String[] y=x.split(" ");
		String fname=y[0];
		String lname=y[1];
		int in_key=hashfunc(fname+lname,0);
		
		boolean t=bst_arr[in_key].contains(fname,lname);
		
		return t;
	}

	@Override
	public T get(K key) throws NotFoundException {
		
		try {
			
			String x=key.toString();
			String[] y=x.split(" ");
			String fname=y[0];
			String lname=y[1];
			int in_key=hashfunc(fname+lname,0);
			
		
		T stud=bst_arr[in_key].get(fname,lname);
		if(stud==null) {
			throw new NotFoundException();
		}
		return stud;
		}
		catch(NotFoundException e) {
			System.out.println("E");
			return null;
		}
	}

	@Override
	public String address(K key) throws NotFoundException {
		try {
			String x=key.toString();
			String[] y=x.split(" ");
			String fname=y[0];
			String lname=y[1];
			int in_key=hashfunc(fname+lname,0);
	
		String output=in_key+"-";
		String search=bst_arr[in_key].address(fname,lname);
		if (search==null) {
			throw new NotFoundException();
		}
		else {
			return output+search;
		}
		}catch(NotFoundException e) {
			System.out.println("E");
			return null;
		}
	}

}
