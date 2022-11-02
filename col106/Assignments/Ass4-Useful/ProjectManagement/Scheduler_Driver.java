package ProjectManagement;
import PriorityQueue.MaxHeap;
import PriorityQueue.PriorityQueueDriverCode;
import RedBlack.RBTree;
import RedBlack.RedBlackNode;
import Trie.Trie;
import Trie.TrieNode;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public class Scheduler_Driver extends Thread implements SchedulerInterface {
	
	Trie<Project> Projects=new Trie<Project>();
	MaxHeap<Job> Jobs=new MaxHeap<Job>();
	ArrayList<Job> jobs_done=new ArrayList<Job>();
	RBTree<String,Job> jobs_incomplete=new RBTree<String, Job>();
	MaxHeap<Job> Left_Jobs=new MaxHeap<Job>();
	ArrayList<String> project_names=new ArrayList<String>();
	Trie<Job> all_jobs=new Trie<Job>();
	Trie<User> Users=new Trie<User>();
	int all_jobs_size=0;
	
	public static void main(String[] args) throws IOException {
        Scheduler_Driver scheduler_driver = new Scheduler_Driver();

        File file;
        if (args.length == 0) {
            URL url = PriorityQueueDriverCode.class.getResource("INP");
            file = new File(url.getPath());
        } else {
            file = new File(args[0]);
        }

        scheduler_driver.execute(file);
    }

    public void execute(File file) throws IOException {

        URL url = Scheduler_Driver.class.getResource("INP");
        file = new File(url.getPath());

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            System.err.println("Input file Not found. "+file.getAbsolutePath());
        }
        String st;
        while ((st = br.readLine()) != null) {
            String[] cmd = st.split(" ");
            if (cmd.length == 0) {
                System.err.println("Error parsing: " + st);
                return;
            }
           
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
                case "":
                    handle_empty_line();
                    break;
                case "ADD":
                    handle_add(cmd);
                    break;
                default:
                    System.err.println("Unknown command: " + cmd[0]);
            }
            
        }
     
       run_to_completion();
       print_stats();
    

    }

    public void run() {
        schedule();
    }



    public void run_to_completion() {
    	
    	while(Jobs.size!=1) {
    			
    			/*Job j=Jobs.extractMax();
    			String project_name=j.project_name;
    			int budget=Projects.search(project_name).getValue().budget;
    			int run_time=j.running_time;
    			if(budget>=run_time) {
    				Projects.search(project_name).getValue().budget-=run_time;
    				j.status="COMPLETED";
    				jobs_done.add(j);
    				System.out.println("Executing: "+j.name+" from: "+j.project_name);
    				System.out.println("Project: "+j.project_name+" budget remaining: "+Projects.search(project_name).getValue().budget);
    				System.out.println("System execution completed");
    				all_jobs_size--;
    			}else {
    				jobs_incomplete.insert(j.project_name,j);
    				System.out.println("Executing: "+j.name+" from: "+j.project_name);
    				System.out.println("Un-sufficient budget.");
    				all_jobs_size--;
    			}*/
    		System.out.println("Running code");
        	System.out.println("Remaining jobs: "+all_jobs_size);
        	while(true) {
        		if(Jobs.size==1) {
        			System.out.println("No jobs are left.");
        			break;
        		}else{
        			Job j=Jobs.extractMax();
        			String project_name=j.project_name;
        			int budget=Projects.search(project_name).getValue().budget;
        			int run_time=j.running_time;
        			if(budget>=run_time) {
        				Projects.search(project_name).getValue().budget-=run_time;
        				j.status="COMPLETED";
        				jobs_done.add(j);
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
  


    public void handle_project(String[] cmd) {
    	System.out.println("Creating project");
    	Project p=new Project(cmd[1],Integer.parseInt(cmd[2]),Integer.parseInt(cmd[3]));
    	Projects.insert(cmd[1],p);
    	project_names.add(cmd[1]);
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
    	
    	Jobs.insert(j);
    	
    	all_jobs.insert(cmd[1],j);
    	all_jobs_size++;
    	}
    	
    /*	if(cmd[1].equals("Kmeans3")) {
    		System.out.println("--------v");
    		for(int i=0;i<Jobs.size;i++) {
    			System.out.println(Jobs.students.get(i));
    		}
    		System.out.println("-------o");
    	}*/
    }


    public void handle_user(String name) {
    	System.out.println("Creating user");
    	User u=new User(name);
    	Users.insert(name,u);
    	
    }


    public void handle_query(String key) {
    	if(key.equals("Kmeans-")){
    		for(int i=0;i<Jobs.size;i++) {
    			System.out.println(i+".)"+Jobs.students.get(i));
    		}
    	}
    	System.out.println("Querying");
    	
    	TrieNode<Job> t=all_jobs.search(key);
    	if(t==null) {
    		System.out.println(key+": "+"NO SUCH JOB");
    	}else {
    		System.out.println(key+": "+t.getValue().status);
    	}
    
    }
    public void handle_empty_line() {
    	System.out.println("Running code");
    	System.out.println("Remaining jobs: "+all_jobs_size);
    	while(true) {
    		if(Jobs.size==1) {
    			System.out.println("No jobs are left.");
    			break;
    		}else{
    			Job j=Jobs.extractMax();
    			String project_name=j.project_name;
    			int budget=Projects.search(project_name).getValue().budget;
    			int run_time=j.running_time;
    			if(budget>=run_time) {
    				Projects.search(project_name).getValue().budget-=run_time;
    				j.status="COMPLETED";
    				jobs_done.add(j);
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
  
    
    public void handle_add(String[] cmd) {
    	System.out.println("ADDING Budget");
    	String project_name=cmd[1];
    	int add_budget=Integer.parseInt(cmd[2]);
    	Projects.search(project_name).getValue().budget+=add_budget;
    	RedBlackNode<String, Job> search = jobs_incomplete.search(project_name);
        while(search.getValues().size()!=0) {
            	all_jobs_size++;
                Jobs.insert(search.list.remove(0));
                
        }
        
    }


    public void print_stats() {
    	int endtime=0;
    	int left_jobs = 0;
    	System.out.println("--------------STATS---------------");
    	System.out.println("Total jobs done: "+jobs_done.size());
    	for(int i=0;i<jobs_done.size();i++) {
    		endtime+=jobs_done.get(i).running_time;
    		System.out.println(jobs_done.get(i).toString(endtime));
    	}
    	System.out.println("------------------------");
    	System.out.println("Unfinished jobs: ");
    	while(true) {
    		Job j=Left_Jobs.extractMax();
    		if(j==null) {
    			break;
    		}else {
    			left_jobs++;
    		System.out.println(j.toString_f());
    		}
    	}
    	System.out.println("Total unfinished jobs: "+left_jobs);
    	System.out.println("--------------STATS DONE---------------");
    }


    public void schedule() {

    }
}
