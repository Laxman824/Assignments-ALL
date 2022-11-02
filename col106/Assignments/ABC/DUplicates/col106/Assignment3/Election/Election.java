package col106.assignment3.Election;
import col106.assignment3.Heap.*;
import col106.assignment3.BST.*;

public class Election implements ElectionInterface {
	/* 
	 * Do not touch the code inside the upcoming block 
	 * If anything tempered your marks will be directly cut to zero
	*/
	public static void main() {
		ElectionDriverCode EDC = new ElectionDriverCode();
		System.setOut(EDC.fileout());
	}
	/*
	 * end code
	 */
	
	//write your code here 
    public void insert(String name, String candID, String state, String district, String constituency, String party, String votes){
		//write your code here 
	}
	public void updateVote(String name, String candID, String votes){
		//write your code here
	}
	public void topkInConstituency(String constituency, String k){
		//write your code here
	}
	public void leadingPartyInState(String state){
		//write your code here
	}
	public void cancelVoteConstituency(String constituency){
		//write your code here
	}
	public void leadingPartyOverall(){
		//write your code here
	}
	public void voteShareInState(String party,String state){
		//write your code here
	}
	
	public void printElectionLevelOrder() {
		//write your code here
		
	}
}











