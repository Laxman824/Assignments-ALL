package COLASSIGN2;

import java.util.Arrays;

public class MyCalculator extends MyStack{
	public static int calculate(String expression) throws EmptyStackException
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
													//if ")" symbol occurs solve the before expression and push into stack
	
		
		else if(array[i]=='+'|| array[i]=='-'|| array[i]=='*')
			all.push(array[i]+""); 
			
		else if(array[i] == ')'){
			MyStack<Integer>   num = new MyStack<Integer>();	
			 
			MyStack<String>  ope =new MyStack<String>();
	
			while(all.top().equals("(") == false) {
				if(all.top().equals("+")||all.top().equals("-")) {
					ope.push(all.pop());
				}
				else if(all.top().equals("*")) {                         //////////////////////
					all.pop();
					int y = Integer.parseInt(all.pop())*num.pop();
					num.push(y);
				}
					 
				else {
					num.push(Integer.parseInt(all.pop()));
				}
			}
			while(ope.isEmpty() == false) {
				
				String z = ope.pop();
				int x = num.pop();
				int y = num.pop();
				if(z.equals("+") ) {
					num.push(x+y);
				}
				else {
					num.push(x-y);
				}
				
			}
			//System.out.println("kk"+i);
			all.pop();
			all.push(Integer.toString(num.top()));
		}
			
		}
		MyStack<Integer>   num = new MyStack<Integer>();	
		 
		 
		MyStack<String>  ope =new MyStack<String>();

		while(all.isEmpty()== false) {
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
		}
		while(ope.isEmpty() == false) {
			String z = ope.pop();
			int x = num.pop();
			int y = num.pop();
			if(z.equals("+") ) {
				num.push(x+y);
			}
			else {
				num.push(x-y);
			}
			
		}
		System.out.println(all.toString());
		return num.top();
		
		
	}
			public static void main(String[] args ){
				try {
					System.out.println(calculate("1 + 10"));
				} 
				catch (EmptyStackException e) {
					
					e.printStackTrace();
				}
			} 



		} 
