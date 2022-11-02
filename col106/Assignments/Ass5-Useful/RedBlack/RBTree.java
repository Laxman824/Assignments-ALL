package RedBlack;
public class RBTree<T extends Comparable<T>, E> implements RBTreeInterface<T, E>  {
	
	public RedBlackNode<T,E> root;
	public RBTree(){
		root=null;
		
	}
    public void insert(T key, E value) {
    	
    	RedBlackNode<T,E> node=new RedBlackNode<T,E>("Red",null,null,null,key,value);
    	
    	if(root==null) {
    		
    		root=node;
    		root.colour="Black";
    		root.list.add(value);
    	
    		return;
    		
    	}
    	
    	else {
    		RedBlackNode<T,E> start=root;
    		while(true) {
    			int compare=start.key.compareTo(key);
    			
    			if(compare>0) {
    				
    				if(start.left==null) {
    					start.left=node;
    					node.prev=start;
    					node.add(value);
    					
    					this.arrange(node);
    					break;
    				}
    				start=start.left;
    			}
    			else if(compare<0) {
    				
    				if(start.right==null) {
    					start.right=node;
    					node.prev=start;
    					node.add(value);
    					this.arrange(node);
    					
    					break;
    					
    				}
    				start=start.right;
    				
    			}
    			else {
    				
    				start.add(value);
    				break;
    			}
    			}
    		}
    	
    	//Arrangement if any problems;
    	
   }
    
    public void arrange(RedBlackNode<T,E> no) {
    	RedBlackNode<T,E> node=no;
    	
    	if(node.prev.colour.equals("Red")) {
    		
    		String order="";
    		String sequence="";
    		RedBlackNode<T,E> uncle;
    		RedBlackNode<T,E> parent=node.prev;
    		if(parent.right==node) {
    			order=order+"R";
    		}else {
    			order=order+"L";
    		}
    		
    		
    		RedBlackNode<T,E> grandparent=parent.prev;
    		if(grandparent.right==parent) {
    			order=order+"R";
    			uncle=grandparent.left;
    		}else {
    			order=order+"L";
    			uncle=grandparent.right;
    		}
    		String grand_pos;
    		
    		if(grandparent==root) {
    			grand_pos="root";
    		}else if(grandparent==grandparent.prev.right) {
    			grand_pos="R";
    		}else {
    			grand_pos="L";
    		}
    		
    		
    		for(int i=1;i>=0;i--) {
    			sequence=sequence+order.charAt(i);
    		}
    		
    		if(uncle==null || uncle.colour.equals("Black") ) {
    			if(sequence.equals("LL")) {
    				parent.colour="Black";
    				grandparent.colour="Red";
    				if(grand_pos.equals("R")) {
    					grandparent.prev.right=parent;
    					parent.prev=grandparent.prev;
    					grandparent.left=parent.right;
    					if(parent.right!=null) {
    					parent.right.prev=grandparent;
    					}
    					parent.right=grandparent;
    					grandparent.prev=parent;
    				}else if(grand_pos.equals("L")){
    					grandparent.prev.left=parent;
    					parent.prev=grandparent.prev;
    					grandparent.left=parent.right;
    					if(parent.right!=null) {
        					parent.right.prev=grandparent;
        					}
    					parent.right=grandparent;
    					grandparent.prev=parent;
    				}else {
    					grandparent.left=parent.right;
    					if(parent.right!=null) {
        					parent.right.prev=grandparent;
        					}
    					parent.right=grandparent;
    					grandparent.prev=parent;
    					root=parent;
    					root.prev=null;
    				}
    				
    			}else if(sequence.equals("LR")){
    				
    				grandparent.left=node;
    				node.prev=grandparent;
    				parent.right=node.left;
    				if(node.left!=null) {
    					node.left.prev=parent;
    				}
    				node.left=parent;
    				parent.prev=node;
    				
    				node.colour="Black";
    				grandparent.colour="Red";
    				if(grand_pos.equals("R")) {
    					grandparent.prev.right=node;
    					node.prev=grandparent.prev;
    					grandparent.left=node.right;
    					if(node.right!=null) {
    						node.right.prev=grandparent;
    					}
    					node.right=grandparent;
    					grandparent.prev=node;
    				}else if(grand_pos.equals("L")){
    					grandparent.prev.left=node;
    					node.prev=grandparent.prev;
    					grandparent.left=node.right;
    					if(node.right!=null) {
    						node.right.prev=grandparent;
    					}
    					node.right=grandparent;
    					grandparent.prev=node;
    				}else {
    					grandparent.left=node.right;
    					if(node.right!=null) {
    						node.right.prev=grandparent;
    					}
    					node.right=grandparent;
    					grandparent.prev=node;
    					root=node;
    					root.prev=null;
    				}
    			}else if(sequence.equals("RR")){
    				parent.colour="Black";
    				grandparent.colour="Red";
    				if(grand_pos.equals("R")) {
    					grandparent.prev.right=parent;
    					parent.prev=grandparent.prev;
    					grandparent.right=parent.left;
    					if(parent.left!=null) {
    						parent.left.prev=grandparent;
    					}
    					parent.left=grandparent;
    					grandparent.prev=parent;
    				}else if(grand_pos.equals("L")){
    					grandparent.prev.left=parent;
    					parent.prev=grandparent.prev;
    					grandparent.right=parent.left;
    					if(parent.left!=null) {
    						parent.left.prev=grandparent;
    					}
    					parent.left=grandparent;
    					grandparent.prev=parent;
    				}else {
    					
    					grandparent.right=parent.left;
    					if(parent.left!=null) {
    						parent.left.prev=grandparent;
    					}
    					parent.left=grandparent;
    					grandparent.prev=parent;
    					root=parent;
    					root.prev=null;
    				}
    			}else {
    				grandparent.right=node;
    				node.prev=grandparent;
    				parent.left=node.right;
    				if(node.right!=null) {
    					node.right.prev=parent;
    				}
    				node.right=parent;
    				parent.prev=node;
    				
    				node.colour="Black";
    				grandparent.colour="Red";
    				if(grand_pos.equals("R")) {
    					grandparent.prev.right=node;
    					node.prev=grandparent.prev;
    					grandparent.right=node.left;
    					if(node.left!=null) {
    						node.left.prev=grandparent;
    					}
    					node.left=grandparent;
    					grandparent.prev=node;
    				}else if(grand_pos.equals("L")) {
    					grandparent.prev.left=node;
    					node.prev=grandparent.prev;
    					grandparent.right=node.left;
    					if(node.left!=null) {
    						node.left.prev=grandparent;
    					}
    					node.left=grandparent;	
    					grandparent.prev=node;
    				}else {
    					grandparent.right=node.left;
    					if(node.left!=null) {
    						node.left.prev=grandparent;
    					}
    					node.left=grandparent;
    					grandparent.prev=node;
    					root=node;
    					root.prev=null;
    				}
    				
    			}
    		}else {
    			while(parent.colour.equals("Red")) {
    			parent.colour="Black";
    			uncle.colour="Black";
    			grandparent.colour="Red";
    			if(grandparent==root) {
    				grandparent.colour="Black";
    				break;
    			}
    			if(grandparent.prev.colour.equals("Black")) {
    				break;
    			}else {
    				node=grandparent;
    				parent=grandparent.prev;
    				grandparent=parent.prev;
    				arrange(node);
    			}
    			
    		}
    	}
    		
    		
    }
    }

    public RedBlackNode<T, E> search(T key) {
    	RedBlackNode<T, E> start=root;
    	if(start==null) {
    		return null;
    	}
    	boolean ret=false;
    	while(ret==false) {
    	if(start.key.compareTo(key)==0) {
    		ret=true;
    		return start; 		
    	}else if(start.key.compareTo(key)>0) {
    		if(start.left==null) {
    			ret=true;
    			return null;
    		}
    		start=start.left;
    	}else {
    		if(start.right==null) {
    			ret=true;
    			return null;
    		}
    		start=start.right;
    		}
    	}
		return start;
    }
}