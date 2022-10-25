package Trie;


import Util.NodeInterface;


public class TrieNode<T> implements NodeInterface<T> {
	TrieNode[] children =new  TrieNode[95];
	T val ;
	boolean IsEndOfWord;
	 TrieNode( T val) {
		 IsEndOfWord=false;
		 for(int i=0; i<95 ; i++) {
			 children[i]=null;		 		 
		 }
		 
	 }
	
	

  
    public T getValue() {
        return this.val;
    }


}