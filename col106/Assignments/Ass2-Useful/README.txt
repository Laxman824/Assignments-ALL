/*
Assigment2Driver Class:
In this class inventory is crreated by reading input from text files.
Seeller and Buyer threads are initialized and made to start.
*/

/*
Buyer Class:
buy():
lock is used in this method, so only one thread can get it at a time.
Condition full and empty are used so that when the catalog is empty a buyer cannot buy and when one purchase is made while the catalog is full, the full condition lets seller threds work.

*/

/*
PriorityQueue Class:This method is used to put elements into the queue. Implementation uses a circular array, two parameters are used for indicating the some specific positions:
(i)front:represents first element in the queue.
(ii)rear:represents position where new element is to be added.
Also sorting is done such that elements with higher priority shift towards the front,so when dequeue is used element with higher priority comes out first.

*/


/*
Queue Class:
enqueue():This method is used to put elements into the queue. Implementation uses a circular array, two parameters are used for indicating the some specific positions:
(i)front:represents first element in the queue.
(ii)rear:represents position where new element is to be added.

*/

/*
Seller Class:
sell():
lock is used in this method, so only one thread can get it at a time.
Here ,I used the condition if (inventory.size>0) because sometimes all the threds enter in the run() in SellerBase and some extra threds will run.To avoid this this has been used.
Condition full and empty are used so that when the catalog is full a seller cannot sell and when one item is sold while the catalog is empty, the empty condition lets buyer threds work.

*/

