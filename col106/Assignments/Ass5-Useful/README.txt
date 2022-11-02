******
-----------------------
Different data structures created for this assignment:
Trie<Project> Projects:
Trie to store all projects.
MaxHeap<Job> Jobs:
Max Heap of job object from which executions are done.
ArrayList<Job> jobs_done:
Array List storing completed jobs.
RBTree<String,Job> jobs_incomplete:
Red Black Tree into which unexecuted jobs are thrown into.
RBTree<String,Job> all_jobs_tree_project:
Red black tree of all the jobs ordered by project name.
RBTree<String,Job> all_jobs_tree_user:
Red black tree of all the jobs ordered by user name.
RBTree<String,Job> all_jobs_tree_new:
Red black tree of all the jobs ordered by a combination of project name and user name.
MaxHeap<Job> Left_Jobs:
Max Heap which contains finally unfinished jobs.
ArrayList<String> project_names:
ArrayList for storing project names.
ArrayList<String> user_names:
ArrayList for storing user names.
Trie<Job> all_jobs:
Trie containing all jobs.
Trie<User> Users:
Trie containing all users.
ArrayList<Job> jobs_unfinished:
ArrayList containg all jobs which are not completed.
-----------------------
{PriorityQueue}

Student:
This class contains a static variable, the variable is used store age of each node, this helps in giving priority when they have same initial
priorities.

Max_Heap:
This class contains an arraylist which can be visualized as a binary tree.
When insertion happens, heapify operation is used so as to restore the structure of the max_heap.
This operation takes O(log n) which is of the order of height of the max_heap.
When extract max happens, top element from the max heap is removed. Then that position is restored by last added eleement.Now, again heapify is
done on the heap to restore properties of the max heap.
This operation similar to insertion takes O(log n) time.

How heapify works:
It adds an element into the required slot and compares with parent.
Depending on the value, swap is done. This continues so that the structure of max heap is maintained.
-----------------------
{ProjectManagement}

This uses all the data structures created to do the works.
All the user created are stored in a trie named Users.

User:User object just has a String name.

It is used to check if such a user exits corresponding to  job.
All the projects created are stored in a Trie called projets.

The project object has properties budget and priority.
Present requested jobs will be placed in a Max Heap.

Job:Job object has a static int which is used to set priority when the basic priorities are equal.

run_to_completion(): handle_endoflines are executed until the priority queue becomes empty.
Also all the jobs in the red black tree are placed into max_heap named left jobs.
Finally in the end all the left jobs can be printed in required order.

handle_project():A project object is created and all the projects are placed the projects.

handle_job():Job objects are created and placed in some required data structures.

handle_user():User objects are created and added into Users.

handle_query():Takes name of the job and returns the status of the job.

handle_empty_line():This is used to execute the jobs. First the top element from jobs is taken out and the corresponding project is found
and ,if the budget is sufficient the running time is deducted else next elemnet from the heap is taken out and the process continues.
Unexecuted jobs are placed into red black tree.

handle_add():When budget is added, project corresponding jobs are placed back into the max heap.

print_stats():All stats of the jobs are given. For storing required things we use different data structres.

handle_new_priority(): 
Return the list of waiting Jobs with a priority higher than or equal to given parameter.
Jobs are stored in a data structure.
Priority of the jobs are equal to priority of projects.
From the trie for projects, we find the job array and return it in the required order.

handle_new_user():
Return list of all Jobs of user depending upon given time intervals.
Time intervals can be calculated from arraval_time and completion_time of Job objects.
Priority of the jobs are equal to priority of projects.

handle_new_project():
Return list of all Jobs for project depending upon given time intervals.
Data structure all_jobs_tree_project is created for this.
Depending on the project respective jobs are stored in arraylist.
ArrayList is searched and priorities are compared, and finally are returned in the specified order.

handle_new_projectUser():
Return list of all Jobs of user for project depending upon given time intervals.
Comparisions for the job are done in both all_jobs_tree_project and all_jobs_tree_user.
Depending on the project respective jobs are stored in arraylist.
ArrayList is searched and priorities are compared, and finally are returned in the specified order.

timed_top_consumer():
Return the list of top budget consuming users ,sorting is done by consumption first,and then by the user's latest job's completion time.
All users are stroed in an arrayList.
Depending in increment of budget consumed, the users are sorted.
Finally the number of required are returned from the arraylist.

flush():
Improve priority of long waiting jobs, execute all jobs waiting for given parameter or longer,if there is sufficient budget,in the order of their relative priority.
Jobs with long waiting time are identified.
Priorities are increased and the max heap containg them is sorted.
All the increased are executed.

timed_handle_job():Same function as handle_job without print statements.

timed_run_to_completion():Same function as handle_run_to_completion without print statements.

timed_handle_project():Same function as handle_project without print statements.

timed_handle_user():Same function as handle_user without print statements.
-----------------------
{RedBlack}

Red Black Node:
Contains properties like colour, reference to right,left and previous nodes.
Contains a list of nodes with same key.

insert():
General Binary search inserted using properties from from previous assignment.
Time complexity is O(log n) where 'n' is number of elements already inserted so far. 

arrange():
Used to retain the red black tree properties after insertion.
This is done using recolouring and rotation.
Depending on the colour of uncle to the position where the `problem occured first.
If the uncle colour is black or it is null.
There will be four types of rotations depending on the the orientation of the nodes.
If the uncle is red just recoluring is done and the process repeats once again.

search():
Used to search through the Red Black Tree.
Normal serach of the binary serach tree.
Time complexity is O(log n).
-----------------------
{Trie}
TrieNode<T> root :Root of the trie.

insert():
Created a pos node of type TrieNode, used for traversing the the trie.
Then loop over the key to add elemets to the trie. Store value in the last position.

delete():
Traverse from bottom of the word delete until there is no effect on other elements of the trie.
Time complexity is O(n) where 'n' is length of the word.

search():
Similar procedure of insert.
Time complexity is O(n) where 'n' is length of the word.

printTrie():
Used to print connecting strings to the node.
We use dfs to do this.

printLevel():
We find level using loops and the print all the elements in that level.		

print():
This function just uses printLevel() to print every level. Numbers of levels is initially calculated.

startsWith():
Takes a prefix, returns the node of last character of prefix. Similar to search(). But instead of entire word, this method takes prefix.
		
-----------------------
{Student}

 Overide the toString() function to get the desired output.
-----------------------
******

