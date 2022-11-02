//package COLASSIGN2;

class StackSort extends MyStack<Integer>{ 
	
	public StackSort() {
		
	}
	public static String[] sort(int [] nums)  {
		MyStack<Integer> s1 = new MyStack<Integer>() ;
		int s=0;
		String[] abc=new String[2*nums.length];
		String[] abc1=new String[]{"NOTPOSSIBLE"};
		int rec_p=-2000000000;
	//	String x="{ ";
		for(int i=0 ;i < nums.length; i++) {
			
			if(s1.isEmpty()) {
				
				s1.push(nums[i]);
				
			//	x = x+ " PUSH ";
				abc[s]="PUSH";
				s++;
			} else{
					
					try {
						while(nums[i] > s1.top()) {
							int a=s1.pop();
							if(rec_p > a) {
							//	System.out.println("NOT POSSIBLE");
								return abc1;
							}else {
								rec_p =a;
							}
						//	x = x + " POP ";
							abc[s]="POP";
							s++;

							if(s1.isEmpty()) {
								break;
							}

						}
					} catch (EmptyStackException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					s1.push(nums[i]);
				//	x=x+" PUSH ";
					abc[s]="PUSH";
					s++;

				}
			}
		while(s1.isEmpty()== false) {
			

			int a=0;
			try {
				a = s1.pop();
			} catch (EmptyStackException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(rec_p > a) {
				
			//	System.out.println("NOTPOSSIBLE");
				
				return abc1;
			}else {
				rec_p =a;
			}
//			x=x+" POP ";
			abc[s]="POP";
			s++;

		}
		//	System.out.println(x+" }");
		return abc;
		}
	public static String[][] kSort(int [] nums)  {
	
		
		return null;
	}
}

	

		

	
/*	public static void main(String args[])
	{ 
		int [] nums = {10, 702, 36, 125, 82}; 
			
			
		String[] a=sort(nums);
		
		for(int i=0;i<a.length;i++) {
				
				System.out.println(a[i]);
			}
		
	} 
} 
*/
