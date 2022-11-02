//package COLASSIGN2;

import java.util.Arrays;
interface MyCalculatorinterface{
	int calculate(String expression);
}

public class MyCalculator extends MyStack<String>   {
	public static int calculate(String expression) 
	{
		MyStack<String>   all = new MyStack<String>();	
		char[] array = expression.toCharArray();
		
	//	System.out.println(array.length);
		for(int i =0;i< array.length;i++){
			//System.out.println("kk**************"+i);
			if (array[i]  >= 48 && array[i] <= 57) {
				String buffer = array[i]+"";
				i = i+1;
				while(i < array.length && array[i] >= 48 && array[i] <= 57) {
					buffer= buffer+array[i];
					//System.out.print("kkk");
					i++ ;
					
				}
				i--;
			//	System.out.println("kk"+i);
	
				all.push(buffer.toString());
			}
	
		else if(array[i]=='(')
			all.push(array[i]+"");
		
		else if(array[i]=='+'|| array[i]=='-'|| array[i]=='*')
			all.push(array[i]+""); 
			
		else if(array[i] == ')'){
			MyStack<Integer>   num = new MyStack<Integer>();	
			 
			MyStack<String>  ope =new MyStack<String>();
	
			try {
				while(all.top().equals("(") == false) {
					if(all.top().equals("+")||all.top().equals("-")) {
						ope.push(all.pop());
					}
					else if(all.top().equals("*")) {                         
						all.pop();
						int y = Integer.parseInt(all.pop())*num.pop();//
						num.push(y);
					}
						 
					else {
						num.push(Integer.parseInt(all.pop()));
					}
				}
			} catch (NumberFormatException | EmptyStackException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			while(ope.isEmpty() == false) {
				
				String z="";
				try {
					z = ope.pop();
				} catch (EmptyStackException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				int x=0;
				try {
					x = num.pop();
				} catch (EmptyStackException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				int y=0;
				try {
					y = num.pop();
				} catch (EmptyStackException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(z.equals("+") ) {
					num.push(x+y);
				}
				else {
					num.push(x-y);
				}
				
			}
			//System.out.println("kk"+i);
			try {
				all.pop();
			} catch (EmptyStackException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				all.push(Integer.toString(num.top()));
			} catch (EmptyStackException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		}
		MyStack<Integer>   num = new MyStack<Integer>();	
		 
		 
		MyStack<String>  ope =new MyStack<String>();

		while(all.isEmpty()== false) {
			try {
				if(all.top().equals("+")||all.top().equals("-")) {
					ope.push(all.pop());
				}
				else if(all.top().equals("*")) {
					all.pop();
					int y = Integer.parseInt(all.pop())*num.pop();
					num.push(y);
				}
					 
				else {
					num.push(Integer.parseInt(all.pop()));
				}
			} catch (NumberFormatException | EmptyStackException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		while(ope.isEmpty() == false) {
			String z="";
			try {
				z = ope.pop();
			} catch (EmptyStackException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int x=0;
			try {
				x = num.pop();
			} catch (EmptyStackException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int y=0;
			try {
				y = num.pop();
			} catch (EmptyStackException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(z.equals("+") ) {
				num.push(x+y);
			}
			else {
				num.push(x-y);
			}
			
		}
		System.out.println(all.toString());
		int a=0;
		try {
			a=num.top();
		} catch (EmptyStackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return a;
		
	}



 /* public static void main(String[] args ){ 
	  
  System.out.println(calculate("1 +(1-  6)- 10")); 
  }
  */
  
  
  }
  
