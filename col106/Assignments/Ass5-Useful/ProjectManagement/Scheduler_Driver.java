package ProjectManagement;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import PriorityQueue.MaxHeap;
import RedBlack.RBTree;
import RedBlack.RedBlackNode;
import Trie.Trie;
import Trie.TrieNode;

public class Scheduler_Driver extends Thread implements SchedulerInterface {
	Trie<Project> Projects=new Trie<Project>();
	MaxHeap<Job> Jobs=new MaxHeap<Job>();
	ArrayList<Job> jobs_done=new ArrayList<Job>();
	RBTree<String,Job> jobs_incomplete=new RBTree<String, Job>();
	RBTree<String,Job> all_jobs_tree_project=new RBTree<String, Job>();
	RBTree<String,Job> all_jobs_tree_user=new RBTree<String, Job>();
	RBTree<String,Job> all_jobs_tree_new=new RBTree<String, Job>();
	MaxHeap<Job> Left_Jobs=new MaxHeap<Job>();
	ArrayList<String> project_names=new ArrayList<String>();
	ArrayList<String> user_names=new ArrayList<String>();
	Trie<Job> all_jobs=new Trie<Job>();
	Trie<User> Users=new Trie<User>();
	ArrayList<Job> jobs_unfinished=new ArrayList<Job>();
	ArrayList<User> user_report=new ArrayList<User>();
	int all_jobs_size=0;
	public int global_time=0;

    public static void main(String[] args) throws IOException {

        Scheduler_Driver scheduler_driver = new Scheduler_Driver();
        File file;
        if (args.length == 0) {
            URL url = Scheduler_Driver.class.getResource("INP");
            file = new File(url.getPath());
        } else {
            file = new File(args[0]);
        }

        scheduler_driver.execute(file);
    }

    public void execute(File commandFile) throws IOException {


        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(commandFile));

            String st;
            while ((st = br.readLine()) != null) {
                String[] cmd = st.split(" ");
                if (cmd.length == 0) {
                    System.err.println("Error parsing: " + st);
                    return;
                }
                String project_name, user_name;
                Integer start_time, end_time;

                long qstart_time, qend_time;

                switch (cmd[0]) {
                    case "PROJECT":
                        handle_project(cmd);
                        break;
                    case "JOB":
                        handle_job(cmd);
                        break;
                    case "USER":
                        handle_user(cmd[1]);
                        break;
                    case "QUERY":
                        handle_query(cmd[1]);
                        break;
                    case "": // HANDLE EMPTY LINE
                        handle_empty_line();
                        break;
                    case "ADD":
                        handle_add(cmd);
                        break;
                    //--------- New Queries
                    case "NEW_PROJECT":
                    case "NEW_USER":
                    case "NEW_PROJECTUSER":
                    case "NEW_PRIORITY":
                        timed_report(cmd);
                        break;
                    case "NEW_TOP":
                        qstart_time = System.nanoTime();
                        timed_top_consumer(Integer.parseInt(cmd[1]));
                        qend_time = System.nanoTime();
            //            System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                        break;
                    case "NEW_FLUSH":
                        qstart_time = System.nanoTime();
                        timed_flush( Integer.parseInt(cmd[1]));
                        qend_time = System.nanoTime();
                        
     //          System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                        break;
                    default:
                        System.err.println("Unknown command: " + cmd[0]);
                }

            }


   run_to_completion();
   print_stats();

        } catch (FileNotFoundException e) {
            System.err.println("Input file Not found. " + commandFile.getAbsolutePath());
        } catch (NullPointerException ne) {
            ne.printStackTrace();

        }
    }

 
    
    public ArrayList<JobReport_> timed_report(String[] cmd) {
        long qstart_time, qend_time;
        ArrayList<JobReport_> res = null;
        switch (cmd[0]) {
            case "NEW_PROJECT":
                qstart_time = System.nanoTime();
                res = handle_new_project(cmd);
                qend_time = System.nanoTime();
       //        System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                break;
            case "NEW_USER":
                qstart_time = System.nanoTime();
                res = handle_new_user(cmd);
                qend_time = System.nanoTime();
       //        System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));

                break;
            case "NEW_PROJECTUSER":
            	
                qstart_time = System.nanoTime();
                res = handle_new_projectuser(cmd);
                qend_time = System.nanoTime();
       //        System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                break;
            case "NEW_PRIORITY":
         
                qstart_time = System.nanoTime();
                res = handle_new_priority(cmd[1]);
                qend_time = System.nanoTime();
     //          System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                break;
        }

        return res;
    }
    
    ArrayList<UserReport_> to_return_user_report=new ArrayList<UserReport_>();
    public ArrayList<UserReport_> timed_top_consumer(int top) {
    	int i=0;
    	while(top!=0) {
    		to_return_user_report.add(user_report.get(i));
    		i++;
    		top--;
    		if(i>=user_report.size()) {
    			break;
    		}
    	}
    	return to_return_user_report;
    		
    }
    
    ArrayList<JobReport_> to_return_new_priority=new ArrayList<JobReport_>();
    private ArrayList<JobReport_> handle_new_priority(String s) {
    	int prior=Integer.parseInt(s);
    	for(int i=0;i<jobs_unfinished.size();i++) {
    		if(jobs_unfinished.get(i).priority>=prior) {
    			to_return_new_priority.add(jobs_unfinished.get(i));
    		}
    	}
        return to_return_new_priority;
    }
    
    
    ArrayList<Job> to_return_pu =new ArrayList<Job>();
    ArrayList<JobReport_> to_return2_pu =new ArrayList<JobReport_>();
    private ArrayList<JobReport_> handle_new_projectuser(String[] cmd) {
    	
    	int pro_jobs_size=all_jobs_tree_new.search(cmd[1]+"--"+cmd[2]).getValues().size();
        
        for(int i=0;i<pro_jobs_size;i++) {
        	Job j=all_jobs_tree_new.search(cmd[1]+"--"+cmd[2]).getValues().get(i);
         	int arr=j.arrival_time();
        	if(arr>=Integer.parseInt(cmd[3]) && arr<=Integer.parseInt(cmd[4])) {
        		
        		if(to_return_pu.size()==0) {
        			to_return_pu.add(j);
        		}else {
    			for(int m=0;m<to_return_pu.size();m++) {
    				int comp=j.compareTo2(to_return_pu.get(m));
    				if(comp>0) {
    					to_return_pu.add(m,j);
    					break;
    				}else if(m==to_return_pu.size()-1) {
    					to_return_pu.add(j);
    					break;
    				}
    			}
    		}
        }
       }
  
        for(int i=0;i<to_return_pu.size();i++) {
        	to_return2_pu.add(to_return_pu.get(i));
        }
        return to_return2_pu;
    }
    
    ArrayList<JobReport_> to_return_new_user =new ArrayList<JobReport_>();
    private ArrayList<JobReport_> handle_new_user(String[] cmd) {
    	if(all_jobs_tree_user.search(cmd[1])!=null) {
         int user_jobs_size=all_jobs_tree_user.search(cmd[1]).getValues().size();
         for(int i=0;i<user_jobs_size;i++) {
         	Job j=all_jobs_tree_user.search(cmd[1]).getValues().get(i);
         	int arr=j.arrival_time();
         	if(arr>=Integer.parseInt(cmd[2]) && arr<=Integer.parseInt(cmd[3])) {
     			to_return_new_user.add(j);
     		}
         }
    	}
         return to_return_new_user;
         
    }
    
    ArrayList<JobReport_> to_return_new_project =new ArrayList<JobReport_>();
    
    private ArrayList<JobReport_> handle_new_project(String[] cmd) {
        for(int i=0;i<all_jobs_tree_project.search(cmd[1]).getValues().size();i++) {
        	Job j=all_jobs_tree_project.search(cmd[1]).getValues().get(i);
         	int arr=j.arrival_time();
        	if(arr>=Integer.parseInt(cmd[2]) && arr<=Integer.parseInt(cmd[3])) {
    			to_return_new_project.add(j);
    		}
        }
        return to_return_new_project;
    }

    public void run_to_completion() {
    	
    	while(Jobs.size!=1) {
    			
    		System.out.println("Running code");
        	System.out.println("Remaining jobs: "+all_jobs_size);
        	while(true) {
        		if(Jobs.size==1) {
        			System.out.println("No jobs are left.");
        			break;
        		}else{
        			Job j=Jobs.extractMax();
        			String project_name=j.project_name;
        			String user_name=j.user_name;
        			int budget=Projects.search(project_name).getValue().budget;
        			int run_time=j.running_time;
        			if(budget>=run_time) {
        				Projects.search(project_name).getValue().budget-=run_time;
        				global_time=global_time+run_time;
        				j.completion_time=global_time;
        				j.status="COMPLETED";
        				User u=Users.search(user_name).getValue();
        				u.budget_consumed+=run_time;
        				u.latest_job_completion=j.completion_time;
        				int u_index=user_report.indexOf(u);
        				while(u_index!=0) {
        					int comp=u.compareTo(user_report.get(u_index-1));
        					if(comp>0) {
        						user_report.remove(u);
        						user_report.add(u_index-1,u);
        						u_index--;
        					}else {
        						break;
        					}
        				}
        				jobs_done.add(j);
        				jobs_unfinished.remove(j);
        				all_jobs_size--;
        				System.out.println("Executing: "+j.name+" from: "+j.project_name);
        				System.out.println("Project: "+j.project_name+" budget remaining: "+Projects.search(project_name).getValue().budget);
        				System.out.println("System execution completed");
        				break;
        			}else {
        				jobs_incomplete.insert(j.project_name,j);
        				System.out.println("Executing: "+j.name+" from: "+j.project_name);
        				System.out.println("Un-sufficient budget.");
        				all_jobs_size--;
        				
        			}
        		}
        		
        	}
    		
    		}
    	
    	
    	
    	for(int i=0;i<project_names.size();i++) {
     		RedBlackNode<String, Job> search = jobs_incomplete.search(project_names.get(i));
     		if(search!=null) {
            if (search.getValues() != null) {
                for (Job person1 : search.getValues()) {
                    Left_Jobs.insert(person1);
                }
            }
     		}
    	}
  }
    public void handle_add(String[] cmd) {
    	System.out.println("ADDING Budget");
    	String project_name=cmd[1];
    	int add_budget=Integer.parseInt(cmd[2]);
    	Projects.search(project_name).getValue().budget+=add_budget;
    	RedBlackNode<String, Job> search = jobs_incomplete.search(project_name);
    	if(search!=null) {
    		while(search.getValues().size()!=0) {
            		all_jobs_size++;
            		Jobs.insert(search.list.remove(0));
                
    		}
    	}
        
    }
    public void print_stats() {
    	int left_jobs = 0;
    	System.out.println("--------------STATS---------------");
    	System.out.println("Total jobs done: "+jobs_done.size());
    	for(int i=0;i<jobs_done.size();i++) {
    		System.out.println(jobs_done.get(i).toString());
    	}
    	System.out.println("------------------------");
    	System.out.println("Unfinished jobs: ");
    	while(true) {
    		Job j=Left_Jobs.extractMax();
    		if(j==null) {
    			break;
    		}else {
    			left_jobs++;
    		System.out.println(j.toString());
    		}
    	}
    	System.out.println("Total unfinished jobs: "+left_jobs);
    	System.out.println("--------------STATS DONE---------------");
    }
    public void handle_empty_line() {
       schedule();
    }
    public void schedule() {
        execute_a_job();
    }
    public void handle_query(String key) {
    	System.out.println("Querying");
    	TrieNode<Job> t=all_jobs.search(key);
    	if(t==null) {
    		System.out.println(key+": "+"NO SUCH JOB");
    	}else {
    		String sta=t.getValue().status;
    		if(sta.equals("REQUESTED")){
    		System.out.println(key+": "+"NOT FINISHED");
    	}else {
    		System.out.println(key+": "+t.getValue().status);
    	}
    	}
    
    }
    public void handle_user(String name) {
    	System.out.println("Creating user");
    	User u=new User(name);
    	Users.insert(name,u);
    	user_report.add(u);
    }
    public void handle_job(String[] cmd) {
    	
    	
    	System.out.println("Creating job");
    	TrieNode<Project> search_project=Projects.search(cmd[2]);
    	TrieNode<User> serach_user=Users.search(cmd[3]);
    	if(search_project==null) {
    		System.out.println("No such project exists. "+cmd[2]);
    	}else if(serach_user==null) {
    		System.out.println("No such user exists: "+cmd[3]);
    	}else {
    	String project_name=cmd[2];
    	int prior=Projects.search(project_name).getValue().priority;
    	Job j=new Job(cmd[1],cmd[2],cmd[3],Integer.parseInt(cmd[4]),prior);
    	j.arrival_time=global_time;
    	Jobs.insert(j);
    	jobs_unfinished.add(j);
    	all_jobs_tree_project.insert(j.project_name,j);
    	all_jobs_tree_new.insert(j.project_name+"--"+j.user_name, j);
    	all_jobs_tree_user.insert(j.user_name,j);
    	all_jobs.insert(cmd[1],j);
    	all_jobs_size++;
    	}
    }
    public void handle_project(String[] cmd) {
    	
    	System.out.println("Creating project");
    	Project p=new Project(cmd[1],Integer.parseInt(cmd[2]),Integer.parseInt(cmd[3]));
    	Projects.insert(cmd[1],p);
    	project_names.add(cmd[1]);
    }
    public void execute_a_job() {
    	System.out.println("Running code");
    	System.out.println("Remaining jobs: "+all_jobs_size);
    	while(true) {
    		if(Jobs.size==1) {
    			System.out.println("No jobs are left.");
    			break;
    		}else{
    			Job j=Jobs.extractMax();
    			j.new_priority=j.priority;
    			String project_name=j.project_name;
    			String user_name=j.user_name;
    			int budget=Projects.search(project_name).getValue().budget;
    			int run_time=j.running_time;
    			if(budget>=run_time) {
    				Projects.search(project_name).getValue().budget-=run_time;
    				global_time=global_time+run_time;
    				j.completion_time=global_time;
    				j.status="COMPLETED";
    				User u=Users.search(user_name).getValue();
    				u.budget_consumed+=run_time;
    				u.latest_job_completion=j.completion_time;
    				int u_index=user_report.indexOf(u);
    				
    				while(u_index!=0) {
    					int comp=u.compareTo(user_report.get(u_index-1));
    					if(comp>0) {
    						user_report.remove(u);
    						user_report.add(u_index-1,u);
    						u_index--;
    					}else {
    						break;
    					}
    				}
    				jobs_done.add(j);
    				jobs_unfinished.remove(j);
    				all_jobs_size--;
    				System.out.println("Executing: "+j.name+" from: "+j.project_name);
    				System.out.println("Project: "+j.project_name+" budget remaining: "+Projects.search(project_name).getValue().budget);
    				System.out.println("Execution cycle completed");
    				break;
    			}else {
    				jobs_incomplete.insert(j.project_name,j);
    				System.out.println("Executing: "+j.name+" from: "+j.project_name);
    				System.out.println("Un-sufficient budget.");
    				all_jobs_size--;
    				
    				}
    			}
    		
    		}
    	}	
    public void execute_a_job2() {
    	
    		if(Jobs.size==1) {
    			System.out.println("No jobs are left.");
    			
    		}else{
    			
    			Job j=Jobs.extractMax();
    			j.new_priority=j.priority;
    			String project_name=j.project_name;
    			String user_name=j.user_name;
    			int budget=Projects.search(project_name).getValue().budget;
    			int run_time=j.running_time;
    			if(budget>=run_time) {
    				Projects.search(project_name).getValue().budget-=run_time;
    				global_time=global_time+run_time;
    				j.completion_time=global_time;
    				j.status="COMPLETED";
    				User u=Users.search(user_name).getValue();
    				u.budget_consumed+=run_time;
    				u.latest_job_completion=j.completion_time;
    				int u_index=user_report.indexOf(u);
    				
    				while(u_index!=0) {
    					int comp=u.compareTo(user_report.get(u_index-1));
    					if(comp>0) {
    						user_report.remove(u);
    						user_report.add(u_index-1,u);
    						u_index--;
    					}else {
    						break;
    					}
    				}
    				jobs_done.add(j);
    				jobs_unfinished.remove(j);
    				all_jobs_size--;
    				System.out.println("Flushed: "+j.toString());
    			
    			}else {
    				jobs_incomplete.insert(j.project_name,j);
    				System.out.println("Executing: "+j.name+" from: "+j.project_name);
    				System.out.println("Un-sufficient budget.");
    				all_jobs_size--;
    				
    				}
    			}
    		
    	}	
    	
    public void timed_flush(int waittime) {
    	int prior_inc = 0;
    	for(int i=1;i<Jobs.size;i++) {
    	
    		if(Jobs.students.get(i).get_waiting_time(global_time)>=waittime) {
    			prior_inc++;
    			Job j=Jobs.students.get(i);
    			j.new_priority=9999;
    			Jobs.arrange(i);
    		}
    	}
    	for(int i=0;i<prior_inc;i++) {
    		execute_a_job2();
    	}
    	
    	
    	}
    /*
    
    
    
    
    
    
    
    
    
    
    Following methods have no prints
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    */
    public void timed_handle_user(String name){
    	User u=new User(name);
    	Users.insert(name,u);
    	user_report.add(u);
    }
    public void timed_handle_job(String[] cmd){
    	TrieNode<Project> search_project=Projects.search(cmd[2]);
    	TrieNode<User> serach_user=Users.search(cmd[3]);
    	if(search_project==null) {
 
    	}else if(serach_user==null) {
    		
    	}else {
    	String project_name=cmd[2];
    	int prior=Projects.search(project_name).getValue().priority;
    	Job j=new Job(cmd[1],cmd[2],cmd[3],Integer.parseInt(cmd[4]),prior);
    	j.arrival_time=global_time;
    	Jobs.insert(j);
    	jobs_unfinished.add(j);
    	all_jobs_tree_project.insert(j.project_name,j);
    	all_jobs_tree_new.insert(j.project_name+"--"+j.user_name, j);
    	all_jobs_tree_user.insert(j.user_name,j);
    	all_jobs.insert(cmd[1],j);
    	all_jobs_size++;
    	}
    }
    public void timed_handle_project(String[] cmd){
    	Project p=new Project(cmd[1],Integer.parseInt(cmd[2]),Integer.parseInt(cmd[3]));
    	Projects.insert(cmd[1],p);
    	project_names.add(cmd[1]);
    }
    public void timed_run_to_completion(){
    	while(Jobs.size!=1) {
    	
        	while(true) {
        		
        		if(Jobs.size==1) {
        			break;
        		}else{
        			
        			Job j=Jobs.extractMax();
        			
        			String project_name=j.project_name;
        			String user_name=j.user_name;
        			int budget=Projects.search(project_name).getValue().budget;
        			int run_time=j.running_time;
        			
        			if(budget>=run_time) {
        				Projects.search(project_name).getValue().budget-=run_time;
        				global_time=global_time+run_time;
        				j.completion_time=global_time;
        				j.status="COMPLETED";
        				User u=Users.search(user_name).getValue();
        				u.budget_consumed+=run_time;
        				u.latest_job_completion=j.completion_time;
        				int u_index=user_report.indexOf(u);
        				while(u_index!=0) {
        					int comp=u.compareTo(user_report.get(u_index-1));
        					if(comp>0) {
        						user_report.remove(u);
        						user_report.add(u_index-1,u);
        						u_index--;
        					}else {
        						break;
        					}
        				}
        				jobs_done.add(j);
        				jobs_unfinished.remove(j);
        				all_jobs_size--;
        				break;
        			}else {
        				jobs_incomplete.insert(j.project_name,j);
        				all_jobs_size--;
        				
        			}
        		}
        		
        	}
    		
    		}
    	for(int i=0;i<project_names.size();i++) {
     		RedBlackNode<String, Job> search = jobs_incomplete.search(project_names.get(i));
     		if(search!=null) {
            if (search.getValues() != null) {
                for (Job person1 : search.getValues()) {
                    Left_Jobs.insert(person1);
                }
            }
     		}
    	}
    }
    
}
