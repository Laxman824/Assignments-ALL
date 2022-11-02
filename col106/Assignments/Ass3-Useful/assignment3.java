import java.io.BufferedReader;
import java.io.FileReader;

public class assignment3 {
	public static void main(String[] args) {
		String type=args[1];
		String sze=args[0];
		String inputfile=args[2];
		int given_size=Integer.parseInt(sze);
		if(type.equals("SCBST")) {
			BST_HashTable<Pair<String,String>,Student> hash=new BST_HashTable<Pair<String,String>,Student>(given_size);
			BufferedReader br=null;
			try {
				br=new BufferedReader(new FileReader(inputfile));
				String contentLine=br.readLine();
				while(contentLine!=null) {
					String[] read=contentLine.split(" ");
					if(read[0].equals("insert")) {
						Student new_stud=new Student(read[1],read[2],read[3],read[4],read[5]);
						Pair<String,String> key=new Pair<String,String>(read[1],read[2]);
						System.out.println(hash.insert(key,new_stud));
					}
					else if(read[0].equals("update")) {
						
						Student new_stud=new Student(read[1],read[2],read[3],read[4],read[5]);
						Pair<String,String> key=new Pair<String,String>(read[1],read[2]);

						int k=hash.update(key,new_stud);
						if(k==-1000) {
							System.out.println("E");
						}else {
							System.out.println(k);
						}
					}
					else if(read[0].equals("delete")) {
						Pair<String,String> key=new Pair<String,String>(read[1],read[2]);
						int k=hash.delete(key);
						if(k==-1000) {
							System.out.println("E");
						}else {
							System.out.println(k);
						}
						
					}
					else if(read[0].equals("contains")) {
				
						Pair<String,String> key=new Pair<String,String>(read[1],read[2]);
						 boolean b=hash.contains(key);
						 if (b) {
							 System.out.println("T");
						 }else {
							 System.out.println("F");
						 }
					}
					else if(read[0].equals("get")) {
						Pair<String,String> key=new Pair<String,String>(read[1],read[2]);
						Student boy=hash.get(key);
						if(boy!=null) {
						System.out.println(boy.fname()+" "+boy.lname()+" "+boy.hostel()+" "+boy.department()+" "+boy.cgpa());
						}
					}
	//address
					else {
						Pair<String,String> key=new Pair<String,String>(read[1],read[2]);
						String t=hash.address(key);
						if(t!=null) {
							System.out.println(t);
						}
					}
					
					contentLine=br.readLine();
				}
			}catch(Exception e){
				System.out.println("Something is wrong");
			}

		}
		else {
			DH_HashTable<Pair<String,String>,Student> hash=new DH_HashTable<Pair<String, String>, Student>(given_size);
			BufferedReader br1=null;
			try {
				br1=new BufferedReader(new FileReader(inputfile));
				String contentLine=br1.readLine();
				while(contentLine!=null) {
					 String[] arr=contentLine.split(" ");
					 if(arr[0].equals("insert")) {
						 Student new_entry=new Student(arr[1],arr[2],arr[3],arr[4],arr[5]);
						 Pair<String,String> new_pair=new Pair<String, String>(arr[1],arr[2]);
						 System.out.println(hash.insert(new_pair, new_entry));
						
					 }
					 
					 else if(arr[0].equals("update")) {
						
						 Student new_entry=new Student(arr[1],arr[2],arr[3],arr[4],arr[5]);
						 Pair<String,String> new_pair=new Pair<String, String>(arr[1],arr[2]);
						 int k=hash.update(new_pair,new_entry);
						 if(k==-1000) {
							 System.out.println("E");
						 }else {
							 System.out.println(k);
						 }

					 }
					 else if(arr[0].equals("delete")) {
						 
						 Pair<String,String> new_pair=new Pair<String, String>(arr[1],arr[2]);
						 int k=hash.delete(new_pair);
						 if(k==-1000) {
							 System.out.println("E");
						 }else {
							 System.out.println(k);
						 }
						 
					 }
					 else if(arr[0].equals("contains")) {
						 Pair<String,String> new_pair=new Pair<String, String>(arr[1],arr[2]);
						 boolean b=hash.contains(new_pair);
						 if (b) {
							 System.out.println("T");
						 }else {
							 System.out.println("F");
						 }

					 }
					 else if(arr[0].equals("get")) {
						 Pair<String,String> new_pair=new Pair<String, String>(arr[1],arr[2]);
						 Student boy=hash.get(new_pair);

						 if(boy!=null) {
						 System.out.println(boy.fname()+" "+boy.lname()+" "+boy.hostel()+" "+boy.department()+" "+boy.cgpa());
						 }
					 }
		
					 else {
						 Pair<String,String> new_pair=new Pair<String, String>(arr[1],arr[2]);
						 String t=hash.address(new_pair);
						 if(t!=null) {
							 System.out.println(t);
						 }

					 }
				 contentLine=br1.readLine();
				}
			} catch (Exception e) {
				System.out.println("File not found");
			}

		}
	}
}
