----
/*
Node class:
The objects created using this class are used in forming the Hash Table using double hashing process.
This class is generic, finally we use Student objects for this class.
In the array we create for the hashtable using these Node objects.
*/
----

----
/*
Node_ class:
The objects created using this class are used in forming the Hash Table using double hashing process.
It is a generic class.
For the nodes to be comparable we implement Comparable interface. Comparision is done for the fname+lname using toString() method.
The objects of this class have three properties. It has a value, reference to left node and a reference to right node.
These nodes are used in creation the binary search tree sturcture. 
*/
----

----
/*
DH_HashTable class:
Double Hashing Hash Table is a data structure formed using Node objects.
Each element in the array is a node.
// n is the number of elements in the array.
It has following methods:
(i)insert:{
First the index to be placed is calculated using djb2 method.
If an element was already placed ,we again calculate index by adding sdbm to it.
Then the element is placed in that position.If the position is again filled .We repeat the procedure.
Generally, if searched key position has null, the process takes 1 step.It takes some constant number of steps in some other cases. So,order is O(1) generally. 
The worst case occurs when all the positions except one is unoccupied, then all the positions might need to be searched, so total checks in some cases will be a multiple of 'n', the the time 
complexity will be O(n).
 
}
(ii)delete:{
First the index to be deteled is calculated using djb2 method.
If the element at the position dosent match,we again calculate index by adding sdbm to it.
Then the element is checked in that position.If the position is again not matched .We repeat the procedure.
After the position is matched it is deletd.
Generally, if searched key position is deleted ,the process takes 1 step.It takes some constant number of steps in some other cases. So,order is O(1) generally. 
So, the mininum time will be of the order O(1).
The worst case occurs when all the positions are not matched except the final one, then all the positions might need to be searched, so total checks in some cases will be a multiple of 'n', the the time 
complexity will be O(n).
If the element is not found the whole array will be searched, so the order will be of O(n).
}
(iii)update:{
First the index to be placed is calculated using djb2 method.
If the element at the position dosent match,we again calculate index by adding sdbm to it.
Then the element is checked in that position.If the position is again not matched .We repeat the procedure.
After the position is matched it is updated.
Generally, if searched key position is updated ,the process takes 1 step.It takes some constant number of steps in some other cases. So,order is O(1) generally. 
So, the mininum time will be of the order O(1).
The worst case occurs when all the positions are not matched except the final one, then all the positions might need to be searched, so total checks in some cases will be a multiple of 'n', the the time 
complexity will be O(n).
If the element is not found the whole array will be searched, so the order will be of O(n).

}
(iv)contains:{
First the index to be searched is calculated using djb2 method.
If the element at the position dosent match,we again calculate index by adding sdbm to it.
Then the element is checked in that position.If the position is again not matched .We repeat the procedure.
After the position is matched true is returned.
Generally, if searched key position is the key we are seraching for,the process takes 1 step.It takes some constant number of steps in some other cases. So,order is O(1) generally. 
So, the mininum time will be of the order O(1).
The worst case occurs when all the positions are not matched except the final one, then all the positions might need to be searched, so total checks in some cases will be a multiple of 'n', the the time 
complexity will be O(n).
If the element is not found the whole array will be searched, so the order will be of O(n).
Finally,it would return false.

}
(v)get:{
First the index to be placed is calculated using djb2 method.
If the element at the position dosent match,we again calculate index by adding sdbm to it.
Then the element is checked in that position.If the position is again not matched .We repeat the procedure.
After the position is matched it is all details are returned of the node.
So, the mininum time will be of the order O(1).
Generally, if searched key position is required key,the process takes 1 step.It takes some constant number of steps in some other cases. So,order is O(1) generally. 
The worst case occurs when all the positions are not matched except the final one, then all the positions might need to be searched, so total checks in some cases will be a multiple of 'n', the the time 
complexity will be O(n).
If the element is not found the whole array will be searched, so the order will be of O(n).
It return error "E" if there is no such element.

}
(vi)address:{
First the index to be placed is calculated using djb2 method.
If the element at the position dosent match,we again calculate index by adding sdbm to it.
Then the element is checked in that position.If the position is again not matched .We repeat the procedure.
After the position is matched it is the index in the array is returned.
Generally, if searched key position is required key,the process takes 1 step.It takes some constant number of steps in some other cases. So,order is O(1) generally. 
So, the mininum time will be of the order O(1).
The worst case occurs when all the positions are not matched except the final one, then all the positions might need to be searched, so total checks in some cases will be a multiple of 'n', the the time 
complexity will be O(n).
If the element is not found the whole array will be searched, so the order will be of O(n).
It return error "E" if there is no such element.
}
*/
----

----
/*
BST_HashTable class:
This data stucture is an array created using Binary Search trees.
All the elements in the array are binary search trees.
First the index to be placed is calculated using djb2 method.
And the required operation is done in the bst at that index.
*/
----

----
/*
bst class:
BST is a data structure formed using Node_ objects.
A root is defined.
// n is height of the tree.
It has following methods:
(i)insert:{
First the index in which it is to be placed is calculated using djb2.
If the root is null,element is directly inserted.This is the best case.
Some others simple cases will have O(1).
Else, depending on priority , either it is placed after a leaf or after some other node in the tree.
In the worst case the whole tree is traversed, until the least last leaf is found.
Then the insertion is done
So, the worst case will bo of n steps, that is O(n).
}
(ii)delete:{
First the index in which it is to be placed is calculated using djb2.
If the root is element to be deleted,element is directly deleted.This is the best case.
Some others simple cases will have O(1).
Else, depending on priority , position is searched.
Depending on the differeent cases search is done.
In the worst case the whole tree is traversed, until the least last leaf is found.
So, the worst case will bo of n steps, that is O(n).
If the elemnet is not prsent error is returned.

}
(iii)update:{
First the index in which it is to be placed is calculated using djb2.
If the root is element to be updated,element is directly inserted.This is the best case.
Some others simple cases will have O(1).
Else, depending on priority , position is searched.
In the worst case the whole tree is traversed, until the least last leaf is found.
So, the worst case will bo of n steps, that is O(n).
If the elemnet is not prsent error is returned.

}
(iv)contains:{
First the index in which it is to be placed is calculated using djb2.
First root is checked.
Else, depending on priority , position is searched.
In the worst case the whole tree is traversed, until the least last leaf is found.
So, the worst case will bo of n steps, that is O(n).
If contained returns true else false.

}
(v)get:{
First the index in which it is to be placed is calculated using djb2.
If the root is element ,element properyties are directly returned.This is the best case.
Some others simple cases will have O(1).
Else, depending on priority , position is searched.
In the worst case the whole tree is traversed, until the least last leaf is found.
So, the worst case will bo of n steps, that is O(n).
If the elemnet is not prsent error is returned.

}
(vi)address:{
First the index in which it is to be placed is calculated using djb2.
If the root is element,index is returned.This is the best case.
Some others simple cases will have O(1).
Else, depending on priority , position is searched.
Whenever, a left position is searched "L" is added to the string to be,if right "R" is added.
In the worst case the whole tree is traversed, until the least last leaf is found.
So, the worst case will bo of n steps, that is O(n).

}

*/
----

----
/*
Pair class:
Pair is an entity made with two fields.
It is created using generic arguments.
Finally we use two strings in place of the generic types.
toString() method is created to return concatenation of fname and lname between space between them.
*/
----

----
/*
Student class:
This class is used to create student object.
Student has properties fname,lname,hostel,department and cgpa.
Different functions are used to return the required fields.
toString() method is created to return concatenation of fname and lname between space between them.
*/
----
