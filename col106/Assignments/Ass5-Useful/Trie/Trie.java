package Trie;

import java.util.ArrayList;

public class Trie<T> implements TrieInterface<T> {
	
	TrieNode<T> root=new TrieNode<T>();
	int max_input=0;
	public boolean insert(String word, T value) {
		if(word.length()>max_input) {
			max_input=word.length();
		}
		TrieNode<T> node=root;
		boolean in=false;
		for(int i=0;i<word.length();i++) {
			int val=word.charAt(i)-' ';
			if(node.children[val]==null) {
			node.children[val]=new TrieNode<T>();
			node.children[val].node_pos=val;
			node.numOfChildren++;
			node.children[val].prefix=node;
			in=true;
			}
			node=node.children[val];
			
		}
		node.isEndOfWord=true;
		node.value=value;
		return in;
		
	}

	
	public TrieNode<T> search(String word) {

		TrieNode<T> node=root;
		for(int i=0;i<word.length();i++) {
			int val=word.charAt(i)-' ';
			if(node.children[val]==null) {
				return null;
			}
			node=node.children[val];
		}
		if(node.isEndOfWord) {
		return node;
		}
		else {
			return null;
		}
	}

	public TrieNode<T> startsWith(String prefix) {
		TrieNode<T> node=root;
		for(int i=0;i<prefix.length();i++) {
			int val=prefix.charAt(i)-' ';
			if(node.children[val]==null) {
				return null;
			}
			node=node.children[val];
		}
		return node;
	}

	public boolean delete(String word) {
		TrieNode<T> node=root;
		for(int i=0;i<word.length();i++) {
			
			int val=word.charAt(i)-' ';
			
			if(node.children[val]==null) {
			
				return false;
				
			}
			
			node=node.children[val];
			
		}
		
		if(node.isEndOfWord) {
			node.isEndOfWord=false;
			while(node.numOfChildren==0 ) {
			int val=node.node_pos;
			node=node.prefix;
			node.children[val]=null;
			node.numOfChildren--;
			
			if(node==root) {
				break;
			}
			}
			return true;
		}
		else {
			
			return false;
		}
	}
	
	
	public void printTrie(TrieNode trieNode) {
		if(trieNode.isEndOfWord&&trieNode.numOfChildren==0) {
			System.out.println(trieNode.getValue());
		}else if(trieNode.isEndOfWord) {
			System.out.println(trieNode.getValue());
			for(int i=0;i<95;i++) {
				if(trieNode.children[i]!=null) {
					this.printTrie(trieNode.children[i]);
				}
		}
	}
		else {
			for(int i=0;i<95;i++) {
				if(trieNode.children[i]!=null) {
					this.printTrie(trieNode.children[i]);
				}
			}
		}
	}
	
	
	public void print() {
		int i=1;
		System.out.println("-------------");
		System.out.println("Printing Trie");
		
		while(i<=max_input+1) {
			this.printLevel(i);
			i++;
		}
		System.out.println("");
		System.out.println("-------------");
	}
	
	
	public void printLevel(int level) {
		ArrayList<Character> level_list=new ArrayList<Character>();
		this.add_elements(root,level,level_list);
		 for (int j = 1; j <level_list.size(); j++) {  
	            char key = level_list.get(j);  
	            int i = j-1;  
	            while ( (i > -1) && ( level_list.get(i) > key ) ) {  
	            	level_list.set(i+1,level_list.get(i));  
	                i--;  
	            }  
	            level_list.set(i+1,key);  
	        }  
		System.out.print("Level "+level+": ");
		for(int i=0;i<level_list.size();i++) {
			if(level_list.get(i)-' '!=0) {
				if(i==level_list.size()-1) {
					System.out.println(level_list.get(i));
				}else {
					System.out.print(level_list.get(i)+",");
				}
			}
		}
		}
	
	public void add_elements(TrieNode<T> node,int lev_dis,ArrayList<Character> level_list) {
		if(lev_dis==1) {
			for(int i=0;i<95;i++) {
				if(node.children[i]!=null) {
					level_list.add((char)(i+32));
				}
			}
		}
		else {
			for(int i=0;i<95;i++) {
				if(node.children[i]!=null) {
					this.add_elements(node.children[i], lev_dis-1,level_list);
				}
		}
	
	}
	}


	
	
}