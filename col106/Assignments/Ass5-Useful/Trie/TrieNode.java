package Trie;
import Util.NodeInterface;
public class TrieNode<T> implements NodeInterface<T> {
	
	T value;
	TrieNode<T>[] children=new TrieNode[95];
	boolean isEndOfWord;
	int numOfChildren;
	TrieNode<T> prefix;
	int node_pos;
	
	TrieNode() {
		for(int i=0;i<95;i++) {
			children[i]=null;
		}
		isEndOfWord=false;
		numOfChildren=0;
		prefix=null;
		node_pos=-1;
	}
	
	public T getValue() {
        return value;
    }
    

	
}