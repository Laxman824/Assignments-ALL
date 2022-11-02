public class bst<T> {
	Node_<T> root;
	bst(){
		root=null;
	}
		
	public int insert(Node_<T> new_node) {
		
		if(root==null) {
			root=new_node;
			return 1;
		}
		
		else {
		Node_<T> start=root;
		int i=1;
		
		while(start.isleaf()==false ) {
			int x=new_node.compareTo(start);
			i++;
			
			if(x==0) {
				x=new_node.compareTo2(start);
			}
			
			if(x>0) {
				if(start.r_next()==null) {
					start.set_r_next(new_node);
					new_node.set_prev(start);
					return i;
				}
				start=start.r_next();
			}else if(x<0) {
				if(start.l_next()==null) {
					start.set_l_next(new_node);
					new_node.set_prev(start);
					return i;
				}
				start=start.l_next();
			}
			
		
		}
		
		
		int y=new_node.compareTo(start);
		i++;
		if(y==0) {
			y=new_node.compareTo2(start);
		}
		if(y>0){
			start.set_r_next(new_node);
			new_node.set_prev(start);
		}else {
			start.set_l_next(new_node);
			new_node.set_prev(start);
		}
		return i;
		}
	}
	
	public String update(Node_<T> new_node) {
		Node_<T> start=root;
		int i=1;
		if(start==null) {
			return null;
		}
		String[] val=new_node.value().toString().split(" ");
		String[] sta=start.value().toString().split(" ");
		
		while(!( sta[0].equals(val[0])&&sta[1].equals(val[1]) ) ) {
			int x=new_node.compareTo(start);
			i++;
			if(x==0) {
				x=new_node.compareTo2(start);
			}
			if (x>0) {
				start=start.r_next();
				
				if(start==null) {
					return null;
				}
			}else {
		
				start=start.l_next();
				
				if(start==null) {
					return null;
				}
			}
			
			sta=start.value().toString().split(" ");
		}
		
		start.udate(new_node);
		return i+"";
	}
	
	public String delete(String fname,String lname) {
		Node_<T> start=root;
		int i=1;
		if(start==null) {
			return null;
		}
		
		String[] sta=start.value().toString().split(" ");
		while(!(sta[0].equals(fname) && sta[1].equals(lname))) {
			int x=fname.compareTo(sta[0]);
			i++;
			if(x==0) {
				x=lname.compareTo(sta[1]);
			}
			if (x>0) {
				start=start.r_next();
				if(start==null) {
					return null;
				}
			}else {
				start=start.l_next();
				if(start==null) {
					return null;
				}
			}
			sta=start.value().toString().split(" ");
	
		}
		if (start.num_of_children()==0) {
			if(start.prev()==null) {
				root=null;
				
			}else {
				Node_<T> n=start.prev();
				if(n.l_next()==start) {
					n.set_l_next(null);
				}else {
					n.set_r_next(null);
				}
			}
		}else if(start.num_of_children()==1) {
				
				if(start.l_next()==null) {
					
					if(start.prev()==null) {
						Node_<T> temp=new Node_<T>(start.value());
						temp.set_l_next(start.l_next());
						temp.set_r_next(start.r_next());
					

						start.set_value(temp.r_next().value());
						start.set_l_next(temp.r_next().l_next());
						start.set_r_next(temp.r_next().r_next());
					}
					else {
					
						Node_<T> previous=start.prev();
				
						if(previous.l_next()==start) {
							previous.set_l_next(start.r_next());
							previous.l_next().set_prev(previous);
						}else {
							previous.set_r_next(start.r_next());
							previous.r_next().set_prev(previous);
						}
				}
				
				}else{
					if(start.prev()==null) {
						
						Node_<T> temp=new Node_<T>(start.value());
						temp.set_l_next(start.l_next());
						temp.set_r_next(start.r_next());
					
						start.set_value(temp.l_next().value());
						start.set_l_next(temp.l_next().l_next());
						start.set_r_next(temp.l_next().r_next());
					
					}else {
						Node_<T> previous=start.prev();
						if(previous.l_next()==start) {
							previous.set_l_next(start.l_next());
							previous.l_next().set_prev(previous);
						}else {
							previous.set_r_next(start.l_next());
							previous.r_next().set_prev(previous);
						}
					}
				}
				i++;
		}else{
			
			Node_<T> new_start=start.r_next();
			i++;
			while(!(new_start.isleaf()==true || new_start.l_next()==null)) {
				new_start=new_start.l_next();
				i++;
			}
			if(new_start.isleaf()) {
			start.set_value(new_start.value());
			Node_<T> n=new_start.prev();
			n.set_l_next(null);
			}else {
				Node_<T> n=new_start.prev();
				if(n.l_next()==new_start) {
					n.set_l_next(new_start.r_next());
				}else {
					n.set_r_next(new_start.r_next());
				}
				
				new_start.set_prev(n);
				i++;
			}

			}
		return i+"";
	}
	
	public boolean contains(String fname,String lname) {
		
		Node_<T> start=root;
		if(start==null) {
			return false;
		}
		String[] sta=start.value().toString().split(" ");
		while(!(sta[0].equals(fname) && sta[1].equals(lname))) {
			int x=fname.compareTo(sta[0]);
			if(x==0) {
				x=lname.compareTo(sta[1]);
			}
			if (x>0) {
				start=start.r_next();
				if (start==null) {
					return false;
				}
			}else {
				start=start.l_next();
				if (start==null) {
					return false;
				}
			}
			sta=start.value().toString().split(" ");
			
		}
		
		return true;
		
	}

	public T get(String fname,String lname){
		Node_<T> start=root;
		
		if(start==null) {
			return null;
		}
		String[] sta=start.value().toString().split(" ");
		while(!(sta[0].equals(fname) && sta[1].equals(lname))) {
			int x=fname.compareTo(sta[0]);
			if(x==0) {
				x=lname.compareTo(sta[1]);
			}
			if (x>0) {
				start=start.r_next();
				if (start==null) {
					return null;
				}
			}else {
				start=start.l_next();
				if (start==null) {
					return null;
				}
			}
			sta=start.value().toString().split(" ");
		}
		return start.value();
		
	}
	public String address(String fname,String lname){
		Node_<T> start=root;
		String result="";
		if(start==null) {
			return null;
		}
		String[] sta=start.value().toString().split(" ");
		while(!( sta[0].equals(fname) && sta[1].equals(lname) )) {
			int x=fname.compareTo(sta[0]);
			if(x>0) {
				start=start.r_next();
				result=result+"R";
				if (start==null) {
					return null;
				}
			}else {
				
				start=start.l_next();
				result=result+"L";
				if (start==null) {
					return null;
				}
	
			}
			sta=start.value().toString().split(" ");
		}
		return result;
	}
}

