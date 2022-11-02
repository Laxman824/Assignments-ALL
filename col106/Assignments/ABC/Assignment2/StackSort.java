package COLASSIGN2;


class StackSort extends MyStack{ 
	
	public StackSort() {
		
	}
	public static void sort(int [] nums) throws EmptyStackException {
		MyStack<Integer> s1 = new MyStack<Integer>() ; 
		int rec_p=-2000000000;
		String x="{ ";
		for(int i=0 ;i < nums.length; i++) {
			
			if(s1.isEmpty()) {
				
				s1.push(nums[i]);
				
				x = x+ " 'PUSH' ,";
			} else{
					
					while(nums[i] > s1.top()) {
						int a=s1.pop();
						if(rec_p > a) {
							System.out.println("NOT POSSIBLE");
							return ;
						}else {
							rec_p =a;
						}
						x = x + " 'POP' ,";
						if(s1.isEmpty()) {
							break;
						}
				
					}
					s1.push(nums[i]);
					x=x+" 'PUSH' ,";
				}
			}
		while(s1.isEmpty()== false) {
			

			int a=s1.pop();
			
			if(rec_p > a) {
				
				System.out.println("NOTPOSSIBLE");
				
				return ;
			}else {
				rec_p =a;
			}
			x=x+" 'POP' ";
		}
			System.out.println(x+" }");
		}
		

	
	public static void main(String args[])
	{ 
		int [] nums = {10, 702, 36, 125, 82}; 
			try {
				sort(nums);
			} catch (EmptyStackException e) {
				e.printStackTrace();
			}
	} 
} 
