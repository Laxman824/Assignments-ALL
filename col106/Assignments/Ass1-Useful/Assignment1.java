
import java.io.*;
import java.util.Iterator;
public class Assignment1 {
	static int num_lines;
	static Student stud_list[];
	static LinkedList<Hostel> allHostels=new LinkedList<Hostel>();
	static LinkedList<Course> allCourses=new LinkedList<Course>();
	static LinkedList<Department> allDepartments=new LinkedList<Department>();
	
	private static void getData(String s1,String s2) {
		//To find number of lines.
				num_lines=0;
				BufferedReader br1=null;
				try {
					br1=new BufferedReader(new FileReader(s1));
					
					String contentLine=br1.readLine();
					while(contentLine!=null) {
						num_lines++;
						contentLine=br1.readLine();
					}
				}
					catch (Exception e) {
						System.out.println("Something went wrong.");
					}
				
				//To store Student objects in an array.
				stud_list=new Student[num_lines];
				
				BufferedReader br=null;
				try {
					br=new BufferedReader(new FileReader(s1));
					String contentLine=br.readLine();
					int i=0;
					while(contentLine!=null) {
						String e[]=contentLine.split(" ");
						stud_list[i]=new Student(e[0],e[1],e[2],e[3]);
						i++;
						contentLine=br.readLine();
					}
				}
					catch (Exception e) {
						System.out.println("Something went wrong.");
						
						System.out.println("Hello");
					}
			
				//Adding courses linked list to Student object.
				BufferedReader brc=null;
				try {
					brc=new BufferedReader(new FileReader(s2));
					String contentLine=brc.readLine();
					while(contentLine!=null) {
						String e[]=contentLine.split(" ",4);
						String en=e[0];
						int i=0;
						while (stud_list[i].entryNo().equals(en)!=true) {
							i++;
						}
						CourseGrade g=new CourseGrade(e[1],e[2],e[3]);
						stud_list[i].courses.add(g);
						
						contentLine=brc.readLine();
					}
				}
					catch (Exception e) {
						System.out.println("Something went wrong.");
					}
			
				
				
				//All hostels imlementation.
				
				BufferedReader brho=null;
				
				try {
					brho=new BufferedReader(new FileReader(s1));
					String contentLine=brho.readLine();
					while(contentLine!=null) {
						String e[]=contentLine.split(" ");
						String hos_name=e[3];
						Iterator<Hostel> it=allHostels.positions();
						int num_of_hostels=allHostels.count();
						int i=0;
						if (num_of_hostels==0) {
							Hostel new_hostel=new Hostel(e[3]);
							allHostels.add(new_hostel);
						}
						else {
							while(it.next().name().equals(hos_name)!=true) {
										i++;
										if(i==num_of_hostels) {
											break;
										}
										}
							if (i==num_of_hostels) {
								Hostel new_hostel=new Hostel(e[3]);
								allHostels.add(new_hostel);
								}
						}
						contentLine=brho.readLine();
					}
				}
					catch (Exception e) {
						System.out.println("Something went wrong.");
					}
				
				//All courses implementation
				BufferedReader brco=null;
				
				try {
					brco=new BufferedReader(new FileReader(s2));
					String contentLine=brco.readLine();
					while(contentLine!=null) {
						String e[]=contentLine.split(" ",4);
						String course_name=e[1];
						String course_title=e[3];
						Iterator<Course> it=allCourses.positions();
						int num_of_courses=allCourses.count();
						int i=0;
						if (num_of_courses==0) {
							Course new_course=new Course(e[1],course_title);
							allCourses.add(new_course);
						}
						else {
							while(it.next().name().equals(course_name)!=true) {
										i++;
										if(i==num_of_courses) {
											break;
										}
										}
							if (i==num_of_courses) {
								Course new_course=new Course(e[1],course_title);
								allCourses.add(new_course);
								}
						}
						contentLine=brco.readLine();
					}
				}
					catch (Exception e) {
						System.out.println("Something went wrong.");
					}
				
				
				BufferedReader brde=null;
				
				try {
					brde=new BufferedReader(new FileReader(s1));
					String contentLine=brde.readLine();
					while(contentLine!=null) {
						String e[]=contentLine.split(" ");
						String dept_name=e[2];
						Iterator<Department> it=allDepartments.positions();
						int num_of_depts=allDepartments.count();
						int i=0;
						if (num_of_depts==0) {
							Department new_dept=new Department(e[2]);
							allDepartments.add(new_dept);
							}
						else {
							while(it.next().name().equals(dept_name)!=true) {
										i++;
										if(i==num_of_depts) {
											break;
										}
										}
							if (i==num_of_depts) {
								Department new_dept=new Department(e[2]);
								allDepartments.add(new_dept);
								}
						}
						contentLine=brde.readLine();
					}
				}
					catch (Exception e) {
						System.out.println("Something went wrong.");
					}
				
				
				Iterator<Hostel> ith=allHostels.positions();
				while(ith.hasNext()==true) {
					Hostel host=ith.next();
					String hostel_name=host.name();
					int i=0;
					while(i<num_lines) {
						if (stud_list[i].hostel().equals(hostel_name)){
							host.stud_list.add(stud_list[i]);
							}
						i++;
					}
				}
				
				Iterator<Department> itd=allDepartments.positions();
				while(itd.hasNext()==true) {
					Department dept=itd.next();
					String department_name=dept.name();
					int i=0;
					while(i<num_lines) {
						if (stud_list[i].department().equals(department_name)){
							dept.stud_list.add(stud_list[i]);
							}
						i++;
					}
				}
				
				Iterator<Course> itc=allCourses.positions();
				while(itc.hasNext()==true) {
						Course cour=itc.next();
						String course_name=cour.name();
						int i=0;
						while(i<num_lines) {
							Iterator<CourseGrade_> it=stud_list[i].courses.positions();
							while (it.hasNext()==true) {
								if (it.next().coursenum().equals(course_name)) {
									cour.stud_list.add(stud_list[i]);
									break;
								}
							
							}
							i++;
						}
				}
	}
	private static void answerQueries(String s3) {
		BufferedReader zea=null;
		int num_of_query=0;
		try {
			zea=new BufferedReader(new FileReader(s3));
			String contentLine=zea.readLine();
			
			while(contentLine!=null) {
				num_of_query++;
				contentLine=zea.readLine();
			}
		}catch (Exception e) {
			System.out.println("Something went wrong.");
		}
		
		String learn[]=new String[num_of_query];
		
		BufferedReader pea=null;
		try {
			pea=new BufferedReader(new FileReader(s3));
			String contentLine=pea.readLine();
			String copy=contentLine;
			int ty=0;
			while(contentLine!=null) {
				learn[ty]=copy;
				contentLine=pea.readLine();
				copy=contentLine;
				ty++;
			}
		}catch (Exception e) {
			System.out.println("Something went wrong.");
		}
		
		String[] beats = new String[num_of_query]; 
        int jens = num_of_query; 
        for (int i = 0; i < num_of_query; i++) { 
            beats[jens - 1] = learn[i]; 
            jens = jens - 1; 
        } 
        
        String[] feats=new String[num_of_query+1];
		int eat=0;
		while(eat<num_of_query) {
			feats[eat]=beats[eat];
			eat++;
		}
        
		
		
		
		
		
		
			int pos=0;
			while(feats[pos]!=null) {
				String e[]=feats[pos].split(" ");
				
				if (e[0].equals("INFO")) {
					String entry_num=e[1];
					int i=0;
					while(stud_list[i].entryNo().equals(entry_num)==false) {
						i++;
						if (i==num_lines) {
							break;
						}
					}
					if (i==num_lines) {
						i=0;
						while(stud_list[i].name().equals(entry_num)==false) {
							i++;
						}
					}
					Student s=stud_list[i];
					Iterator<CourseGrade_> t=s.courses.positions();
					int num_of_courses=s.courses.count();
					String course_arr[]=new String[num_of_courses];
					int k=0;
					while (t.hasNext()==true) {
						CourseGrade_ a=t.next();
						course_arr[k]=(a.coursenum()+" "+a.c_grade());
						k++;
					}
					for(int x = 0; x < k-1; ++x) {
			            for (int y = x + 1; y < k; ++y) {
			                if (course_arr[x].compareTo(course_arr[y]) > 0) {
			                    String temp = course_arr[x];
			                    course_arr[x] = course_arr[y];
			                    course_arr[y] = temp;
			                }
			            }
			        }
					System.out.print(s.entryNo()+" "+s.name()+" "+s.department()+" "+s.hostel()+" "+s.cgpa()+" ");
					int z=0;
					while(z<k) {
						System.out.print(course_arr[z]+" ");
						z++;
					}
					System.out.println("");
				}
				
				
				
				
				
				else if (e[0].equals("SHARE")) {
					
					String stud_entryNo=e[1];
					String entity=e[2];
					
					Iterator<Hostel> ah=allHostels.positions();
					while(ah.hasNext()==true) {
						Hostel a=ah.next();
						
						
						
						if (entity.equals(a.name())) {
							Iterator<Student> stud1=a.stud_list.positions();
							String vh[]=new String[a.stud_list.count()];
							int n=0;
							while (stud1.hasNext()==true) {
								Student ax=stud1.next();
								vh[n]=ax.entryNo();
								n++;
							}
							
							for(int i = 0; i < a.stud_list.count()-1; ++i) {
					            for (int j = i + 1; j <a.stud_list.count(); ++j) {
					                if (vh[i].compareTo(vh[j]) > 0) {
					                    // swap words[i] with words[j[
					                    String temp = vh[i];
					                    vh[i] = vh[j];
					                    vh[j] = temp;
					                }
					            }
					        }
							int k=0;
							while (k<a.stud_list.count) {
								if (vh[k].equals(stud_entryNo)==false) {
								System.out.print(vh[k]+" ");}
								k++;
							}
							
							break;
						}
					}
					
					Iterator<Department> ad=allDepartments.positions();
					
					while(ad.hasNext()==true) {
						Department b=ad.next();
						if (entity.equals(b.name())) {
							
							Iterator<Student> stud2=b.stud_list.positions();
							String vd[]=new String[b.stud_list.count()];
							int n=0;
							while (stud2.hasNext()==true) {
								Student ax=stud2.next();
								vd[n]=ax.entryNo();
								n++;
							}
							for(int i = 0; i < b.stud_list.count()-1; ++i) {
					            for (int j = i + 1; j <b.stud_list.count(); ++j) {
					                if (vd[i].compareTo(vd[j]) > 0) {
					             
					                    String temp = vd[i];
					                    vd[i] = vd[j];
					                    vd[j] = temp;
					                }
					            }
					        }
							int k=0;
							while (k<b.stud_list.count) {
								if (vd[k].equals(stud_entryNo)==false) {
								System.out.print(vd[k]+" ");}
								k++;
							}
							break;
						}
						
					}
					
					
					Iterator<Course> ac=allCourses.positions();
					
					while(ac.hasNext()==true) {
						Course c=ac.next();
						if (entity.equals(c.name())) {
							Iterator<Student> stud3=c.stud_list.positions();
							String vc[]=new String[c.stud_list.count()];
							int n=0;
							while (stud3.hasNext()==true) {
								Student cx=stud3.next();
								vc[n]=cx.entryNo();
								n++;
							}
							for(int i = 0; i < c.stud_list.count()-1; ++i) {
					            for (int j = i + 1; j <c.stud_list.count(); ++j) {
					                if (vc[i].compareTo(vc[j]) > 0) {
					             
					                    String temp = vc[i];
					                    vc[i] = vc[j];
					                    vc[j] = temp;
					                }
					            }
					        }
							int k=0;
							while (k<c.stud_list.count) {
								if (vc[k].equals(stud_entryNo)==false) {
								System.out.print(vc[k]+" ");}
								k++;
							}
							
							
							break;
						}
						
					}
					System.out.println("");
				}
				else {
					String course_num=e[1];
					Iterator<Course> it=allCourses.positions();
					while(it.hasNext()==true) {
						Course a=it.next();
						if (a.name().equals(course_num)) {
							System.out.println(a.title());
							break;
						}
						
					}
				}
				
				pos++;
				
				}
			
		
	}

	public static void main(String[] args) {
	getData(args[0],args[1]);
	answerQueries(args[2]);
	}
}
		