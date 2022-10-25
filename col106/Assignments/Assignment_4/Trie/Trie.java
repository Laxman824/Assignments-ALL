package Trie;

public class Trie<T> implements TrieInterface {
	TrieNode<T> root ;
	Trie(){
		root = null; 
	}
	public boolean delete(String word) {
		 int level ;
		 int length =word.length();
		 int index;
		 TrieNode nextnode =root;
		 for(level =0;level<length;level++) {
			 index = word.charAt(level)-32;
			 
			 if (nextnode.children[index]==null) {
				 return false;
			 }
				 nextnode=nextnode.children[index];
			 
    }
		 if(nextnode == null) {
			 return false;
		 }
		 else {
			 nextnode.val = null;
			 nextnode.IsEndOfWord = false;
			 return true ;
		 }
    }

    @Override
    public TrieNode search(String word) {
    	 int level ;
		 int length =word.length();
		 int index;
		 TrieNode nextnode =root;
		 for(level =0;level<length;level++) {
			 index = word.charAt(level)-32;
			 
			 if (nextnode.children[index]==null) {
				 return null ;
			 }
				 nextnode=nextnode.children[index];
			 
    }
		 if(nextnode.IsEndOfWord == true)
			 return nextnode;
		 else
			 return null;
    }

    @Override
    public TrieNode startsWith(String prefix) {
    	 TrieNode Trienode = search(prefix);
         return Trienode ;
        	
        
    }

    @Override
    public void printTrie(TrieNode trieNode) {

    }

    @Override
    public boolean insert(String word, Object value) {
    	int level ;
		int length =word.length();
		int index;
		TrieNode nextnode=root;
		for(level =0;level < length;level++)
		{
			index =word.charAt(level)-32;
			if (nextnode.children[index]==null)
				nextnode.children[index]=new TrieNode(value);
			nextnode =nextnode.children[index];
		
			
		}
		if(nextnode.IsEndOfWord == false) {
			nextnode.IsEndOfWord=true;
			nextnode.val = (T) value;
			return true;
		}
		else
			return false;
    }

    @Override
    public void printLevel(int level) {
    	
    	
    }

    @Override
    public void print() {

    }
}