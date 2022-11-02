package RedBlack;

public class Test {
	public static void main(String[] args) {
		RBTree<Integer,Integer> test=new RBTree<Integer,Integer>();
		test.insert(0,0);
		test.insert(1,1);
		
		test.insert(2,2);
		test.insert(3,3);
		test.insert(4, 4);
		test.insert(5, 5);
		test.insert(6, 6);
		test.insert(7, 7);
		test.insert(8, 8);
		test.insert(9, 9);
		test.insert(10, 10);
		test.insert(11,11);
		test.insert(12, 12);
		test.insert(13, 13);
		test.insert(14, 14);
		test.insert(15,15);
		test.insert(16, 16);
		System.out.println(test.root);
		System.out.println(test.root.left);
		System.out.println(test.root.right);
		System.out.println(test.root.left.left);
		System.out.println(test.root.left.right);
		System.out.println(test.root.right.left);
		System.out.println(test.root.right.right);
		System.out.println(test.root.right.left.left);
		System.out.println(test.root.right.left.right);
		
		
	}
}
