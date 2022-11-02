package col106.assignment3.BST;

import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;
 class BSTNode<T extends Comparable,E extends Comparable> {
	 
			
			T key;
			E value;
			BSTNode left;
			BSTNode right;
			
			public BSTNode(T key, E value ) {
				this.key=key;
				this.value=value;
				this.left=null;
				this.right=null;
			}
			
			public E getvalue() {
				
			return value;
			}
			
			
			
			public  T getkey() {
				
				 return key;
			}
			public BSTNode<T,E> getleft(){
				return left;
			}
			public BSTNode<T,E> getright(){
				return right;
			}
			public void setvalue(E newvalue)
			{
				value=newvalue;
			}
			public void setKey(T newkey) {
				key=newkey;
			}
			public void setleft(BSTNode<T,E> newleft) {
				left=newleft;
			}
			public void setright(BSTNode<T,E> newright) {
				right=newright;
			}
			
			
		}


  

@SuppressWarnings("rawtypes")
public  class BST<T extends Comparable, E extends Comparable> implements BSTInterface<T, E>  {
	
	public static void main() {
		
		BSTDriverCode BDC = new BSTDriverCode();
		System.setOut(BDC.fileout());
	}
	//Start  from here.............
	 
	public BSTNode<T,E> root;
	
	public BST()
	{
		
		root = null;
	}
	
	
		public boolean searchfor(E value) {
			
			return searchfor(root,value);
		}
	  
		private   boolean  searchfor(BSTNode<T,E> p,E value) {

			if(p == null || p.value==value) {

				return false;
			}
			 if(p.getvalue().equals(value)) {
				
				return true;
				
			}
			if(value.compareTo(p.getvalue())< 0){	
													//as p is root node ,it should be > 0 
				return searchfor(p.getleft(),value);
			}
			else {    
								//key > node's key then search in right side 
				return searchfor(p.getright(),value);
			}
				
		 			
	}
	
	
	

    @SuppressWarnings("unchecked")
	public void insert(T key, E value) {
    	
			this.root = insert(this.root,key,value);
		
    }
    	@SuppressWarnings({ "unchecked", "rawtypes" })
		private BSTNode insert(BSTNode root,T key,E value) {
    		
    		if(root == null) {
    			
    			BSTNode a = new BSTNode(key,value);
    				return a;
    				
    		}
    		else if(value.compareTo(root.getvalue()) < 0) {   ///

    			root.setleft(insert(root.getleft(),key,value) );

    			
    		
    		}else {
    			
    			root.setright(insert(root.getright(),key,value));
    			
    		}
    	return root;		
    	
    	}
  
    	
    	
   private  BSTNode min(BSTNode<T,E> q) {     //finding the leftest node in right side of root
   			
  	    if(q.getleft() == null) {				
  				
  	    	return q	;								// To find the min, go left as far as possible
  				
  				}else {

  					return min(q.getleft());
  				}
  				
  		}
   private BSTNode min2(BSTNode r)
   
   {
	   BSTNode q = r;
	   
	   if(q.right.left== null) {      
		 
		   return q ;
	   }
	   else {
		   
		   q  = q.right;
		   
		   while(q.left.left != null) {
			   
			   q = q.left;
		 
		   }
		     
		   return q;
		   
	   }
	   
	   
   }
   
    	

   public void delete(T key) {
   	
   	BSTNode p = this.root;
   	
	ArrayList<BSTNode > bst_arraylist = new ArrayList<BSTNode >();
		
   	Queue <BSTNode> queue = new LinkedList<BSTNode>();   //creating empty node 
   	
   	queue.add(p);
   	
   while(!queue.isEmpty()) { //if queue is notempty pop a node and print 
   		
   	BSTNode<T,E> q = queue.poll();//poll used to check head and also remove it
		
   	bst_arraylist.add(q);
		
		if(q!=null) {    //as leftnode  is notnull we will add it to queue 
		
		queue.add(q.getleft());
		
		queue.add(q.getright());
   }
   }
   
   BSTNode dele_root = null;   ///show changed to dele_root
   
   BSTNode parent = null;
   
   for(int k= 0 ; k < bst_arraylist.size();k++) {
   	
   	if(bst_arraylist.get(k) != null && bst_arraylist.get(k).key.compareTo(key) == 0) {
   		
   		dele_root = bst_arraylist.get(k);
   		
   		break;
   	}
   }
   deleteNode(this.root,(E) dele_root.value);
   }
   
	public BSTNode deleteNode(BSTNode root, E key)
	{
		
		BSTNode parent = null;

		
		BSTNode curr = root;

		
		while (curr != null && curr.value != key)
		{
			
			parent = curr;

			
			if (key.compareTo(curr.value) < 0) {
				curr = curr.left;
			}
			else {
				curr = curr.right;
			}
		}

		
		if (curr == null) {
			return root;
		}

		// Case 1:it is a leaf node
		if (curr.left == null && curr.right == null)
		{
			
			if (curr != root) {
				if (parent.left == curr) {
					parent.left = null;
				} else {
					parent.right = null;
				}
			}
			
			else {
				root = null;
			}
		}

		// Case 2: two children
		else if (curr.left != null && curr.right != null)
		{
			BSTNode successor  = min(curr.right);

			E val = (E) successor.value;
			T tkey = (T) successor.key;
			
			deleteNode(root, (E) successor.value);

			
			curr.value = val;
			curr.key = tkey;
		}

		// Case 3:  only one child
		else
		{
			
			BSTNode child = (curr.left != null)? curr.left: curr.right;

			if (curr != root)
			{
				if (curr == parent.left) {
					parent.left = child;
				} else {
					parent.right = child;
				}
			}

			
			else {
				root = child;
			}
		}

		return root;
	}
   

   
 
    
   
	public void update(T key, E value) {
    	
    	
    	delete(this.root.key);
    	//delete((T) search(this.root,key).value);
    	
    	insert(key,value);
    	
    	
    	


	}																		
    private BSTNode search(BSTNode   w , T key){     //find Node with given key and return it's value
    	
    	BSTNode p = this.root;
       	
    	ArrayList<BSTNode > bst_arraylist = new ArrayList<BSTNode >();
    		
       	Queue <BSTNode> queue = new LinkedList<BSTNode>();   //creating empty node 
       	
       	queue.add(p);
       	
       while(!queue.isEmpty()) { //if queue is notempty pop a node and print 
       		
       	BSTNode<T,E> q = queue.poll();//poll used to check head and also remove it
    		
       	bst_arraylist.add(q);
    		
    		if(q!=null) {    //as leftnode  is notnull we will add it to queue 
    		
    		queue.add(q.getleft());
    		
    		queue.add(q.getright());
       }
       }
       
       BSTNode dele_root = null;   ///show changed to dele_root
       
      
       
       for(int k= 0 ; k < bst_arraylist.size();k++) {
       	
       	if(bst_arraylist.get(k) != null && bst_arraylist.get(k).key.compareTo(key) == 0) {
       		
       		dele_root = bst_arraylist.get(k);
       		
       		break;
       	}
       }
	return dele_root;
    }

    public  void  printBST () {
    	
    	BSTNode<T,E> p = this.root;
    	
		ArrayList<BSTNode <T,E>> bst = new ArrayList<BSTNode <T,E>>();
		
    	Queue <BSTNode<T,E>> queue = new LinkedList<BSTNode<T,E> >();   //creating empty node 
    	
    	queue.add(p);
    	
    while(!queue.isEmpty()) { //if queue is notempty pop a node and print 
    		
    		BSTNode<T,E> q = queue.poll();//poll used to check head and also remove it
    		if(q == null) {
    			
    			//System.out.print("  "+q+ "  ");
    		}
    		
    		else {
    			
    		System.out.println(q.value+", "+q.key);
    		}
    		
    		
    		bst.add(q);
    		
    		if(q!=null) {    //as leftnode  is notnull we will add it to queue 
    		
    		queue.add(q.getleft());
    		
    		queue.add(q.getright());
    }


    }
 //   System.out.println();
}
}



	