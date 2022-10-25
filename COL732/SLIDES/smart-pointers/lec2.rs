use std::{fmt, ops::Deref};

fn main() {
    /* Closures */ 
	{
		// Closures can use the environment. That can also move ownership.
		let x = String::from("foo");
		let id = || x;
		id();
		// println!("{}", x);

		// Compiler can choose to let closure only borrow the value.
		let x = String::from("foo");
		let id = || println!("{}", x);
		id();
		println!("{}", x);
	}

	{
		// Can also mutably borrow the value
		let mut x = String::from("hello");
		// append has to implement FnMut
		let mut append = || x.push_str(" world");
		append();
		println!("{}", x);
	}
     
    {
		// Stack : known size
		let s = "Hello World!";
		println!("{}", s);

		// Heap : unknown size
		let mut s1 = String::from("Hello");
		s1.push_str(" world!");
		println!("{}", s1);
	}

    {
		// Deep copy
		/*
		 x
		┌───────────┐
		│ name      │
		│           │       ┌─┬─┐
		│ ptr  ─────┼──────►│0│H│
		│ len       │       │1│I│
		│ capacity  │       └─┴─┘
		└───────────┘
			
		 y
		┌───────────┐
		│ name      │
		│           │       ┌─┬─┐
		│ ptr  ─────┼──────►│0│H│
		│ len       │       │1│I│
		│ capacity  │       └─┴─┘
		└───────────┘
		 */
		let x = String::from("HI");
		let y = x.clone();
		println!("x: {}, y: {}", x, y);
	}

	

	/*
	┌────────────────────────────────────┐
	│ Cons                               │
	│      ┌───────┬─────────────────────┤
	│      │ Cons  ├─────┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼│
	│  i32 │       │Cons ├────────────┼┼┼│
	│      │  i32  │     │ Cons       │┼┼│
	│      │       │ i32 │            │┼┼│
	│      │       │     │  i32       │┼┼│
	│      │       │     ├────────────┼┼┼│
	│      │       ├─────┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼│
	└──────┴───────┴─────────────────────┘
    */
	// enum List {
	// 	Nil,
	// 	Cons(i32, List),
	// }


    /* 
	┌──────┬───────┐
	│ Cons │       │
	│      │ Box   │
	│      │       │
	│  i32 │ usize │
	└──────┴───────┘
	*/

	#[derive(Debug)]
	enum List {
		Nil,
		Cons(i32, Box<List>),
	}

	impl List {
		fn new(vals: &[i32]) -> List {
			let mut l = List::Nil;
			for v in vals.iter().rev() {
				l = List::Cons(*v, Box::new(l));
			}
			l
		}
	}

	impl fmt::Display for List {
		fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
			write!(f, "<")?;
			let mut l: &List;
			match self {
				List::Nil => {
					write!(f, ">")?;
					return Ok(());
				}
				List::Cons(v, l0) => {
					write!(f, "{}", v)?;
					l = l0;
				}
			}
			loop {
				match l {
					List::Nil => {
						write!(f, ">")?;
						return Ok(());
					}
					List::Cons(v, l0) => {
						write!(f, ", {}", v)?;
						l = l0;
					}
				}
			}
			// Use `self.number` to refer to each positional data point.
			// write!(f, "({}, {})", self.0, self.1)
		}

	}

	{
        println!("--- List with Box ---");
        use List::{Cons, Nil};

		let x = Cons(2, Box::new(Cons(3, Box::new(Nil))));
		// println!("{:?}", x);
		println!("{}", x);

		let x = List::new(&[1, 2, 3, 4]);
		println!("{}", x);
        println!("----List with Box ends----");
    }

    {
        use List::{Cons, Nil};

        // Two lists are unable to point to the same list because Box owns `a`
        // let a = Cons(5, Box::new(Cons(10, Box::new(Nil))));
        // let b = Cons(3, Box::new(a));
        // let c = Cons(4, Box::new(a));
	}
    
    {
        use std::rc::Rc;
        enum List2 {
            Cons(i32, Rc<List2>),
            Nil,
        }
        use List2::{Cons, Nil};
        let a = Rc::new(Cons(5, Rc::new(Cons(10, Rc::new(Nil)))));
        println!("count after creating a = {}", Rc::strong_count(&a));
        let b = Cons(3, Rc::clone(&a));
        println!("count after creating b = {}", Rc::strong_count(&a));
        {
            let c = Cons(4, Rc::clone(&a));
            println!("count after creating c = {}", Rc::strong_count(&a));
        }
        println!("count after c dies = {}", Rc::strong_count(&a));
        // clone is very light: only increments the reference counter. 
        // Does not do deep-copy of the data (unlike String).
    }

    {
        // Interior mutability
        use std::cell::RefCell;
        let x = RefCell::new(0);
        let r1 = x.borrow();
        let r2 = x.borrow();
        // Notice that this is also runtime error whereas with & and &mut, 
        // compiler eagerly dropped the immutable references to allow the
        // mutable reference.
        println!("{} {}", r1, r2);

        // let r3 = x.borrow_mut();
        // println!("{}", r3);
        // Runtime error
        // println!("{} {} {}", r1, r2, r3);
    }

    {
        use std::rc::Rc;
        use std::cell::RefCell;
        type Link<T> = Rc<RefCell<T>>;
        enum List2 {
            Cons(i32, Link<List2>),
            Nil,
        }
        
        

        use List2::{Cons, Nil};
        let a = Rc::new(RefCell::new(Nil));
        let b = Cons(3, Rc::clone(&a));
        let c = Cons(4, Rc::clone(&a));
        {
            *(a.borrow_mut()) = Cons(5, Rc::new(RefCell::new(Nil)));
        }
        // println!("Interior mutability {}", b);
    }

	#[derive(Debug)]
    struct DropMe(i32);
    impl Drop for DropMe {
        fn drop(&mut self) {
            println!("Dropping {}", self.0);
        }
    }
    
    {
        use std::cell::RefCell;
        use std::rc::Rc;

        enum List4 {
            Cons(DropMe, Rc<RefCell<List4>>),
            Nil,
        }
        use List4::{Cons, Nil};

        {
            println!("Circular ref");
            let v1 = DropMe(1);
            let v2 = DropMe(2);
            let v3 = DropMe(3);

            let l1 = Rc::new(RefCell::new(Cons(v1, Rc::new(RefCell::new(Nil)))));
            let l2 = Rc::new(RefCell::new(Cons(v2, Rc::clone(&l1))));
            *l1.as_ref().borrow_mut() = Cons(v3, Rc::clone(&l2));

            println!("l1 count: {} l2 count: {}", Rc::strong_count(&l1), Rc::strong_count(&l2));
            // Reference cycles can leak memory! Both counters are at 2. Dropping 2 and 3 are not printed.
        }
        println!("Circular ref ends");
    }

    {
        // A more real example of reference cycles: doubly linked list.
        use std::cell::RefCell;
        use std::cell::RefMut;
        use std::rc::Rc;

        type Link<T> = Option<Rc<RefCell<Node<T>>>>;

        struct Node<T> {
            val: T,
            fwd: Link<T>,
            bwd: Link<T>
        }

        struct DList<T> {
            head: Link<T>,
            tail: Link<T>
        }

        impl<T> Node<T> {
            fn new(v: T) -> Self {
                Node {
                    val: v,
                    fwd: None,
                    bwd: None
                }
            }
        }

        impl<T> DList<T> {
            fn new() -> Self {
                DList { head: None, tail: None }
            }

            fn push_front(&mut self, val: T) {
                let new_head = Rc::new(RefCell::new(Node::new(val)));
                match self.head.take() {
                    None => {
                        self.tail = Some(Rc::clone(&new_head));
                        self.head = Some(new_head);
                    },
                    Some(old_head) => {
                        {
                            let mut n: RefMut<Node<T>> = new_head.borrow_mut();
                            (*n).fwd = Some(Rc::clone(&old_head));
                            // We have to let n get dropped otherwise Rust doesn't let someone else own new_head in 
                            // self.head = Some(new_head);
                        }
                        let mut o: RefMut<Node<T>> = old_head.borrow_mut();
                        (*o).bwd = Some(Rc::clone(&new_head));
                        self.head = Some(new_head);
                    }
                }
            }

            fn update_head(&mut self, new_val: T) {
                match self.head.take() {
                    None => { 
                        panic!("can not update head of an empty list"); 
                    }
                    Some(old_head) => {
                        {
                            let mut n = old_head.borrow_mut();
                            (*n).val = new_val;
                        }
                        self.head = Some(old_head);
                    }
                }
            }
        }

        {
            println!("Doubly linked list starts");
            let mut l = DList::new();
            for i in 1..100 {
                l.push_front(DropMe(i));
                // let _x = DropMe(i);
            }
            // Memory leaked 100 structs due to circular references!
        }
        println!("Doubly linked list ends");
    }

    {
        use std::cell::RefCell;
        use std::rc::{Rc, Weak};

        enum List4 {
            Cons(DropMe, Weak<RefCell<List4>>),
            Nil,
        }
        use List4::{Cons, Nil};

        {
            println!("Weak references");
            let v1 = DropMe(1);
            let v2 = DropMe(2);
            let v3 = DropMe(3);

            let nil = Rc::new(RefCell::new(Nil));

            let l1 = Rc::new(RefCell::new(Cons(v1, Rc::downgrade(&nil))));
            let l2 = Rc::new(RefCell::new(Cons(v2, Rc::downgrade(&l1))));
            *l1.as_ref().borrow_mut() = Cons(v3, Rc::downgrade(&l2));

            println!("l1 count: {} l2 count: {}", Rc::strong_count(&l1), Rc::strong_count(&l2));
            // Weak references are not counted in strong counts. Now, there is no memory leak.
        }
    }
    
    {
        println!("Weak references in doubly linked-lists");
        // Doubly linked list but with weak back pointers disables cycles.
        use std::cell::RefCell;
        use std::cell::RefMut;
        use std::rc::{Rc, Weak};

        type Link<T> = Option<Rc<RefCell<Node<T>>>>;

        struct Node<T> {
            val: T,
            fwd: Link<T>,
            bwd: Option<Weak<RefCell<Node<T>>>>
        }

        struct DList<T> {
            head: Link<T>,
            tail: Link<T>
        }

        impl<T> Node<T> {
            fn new(v: T) -> Self {
                Node {
                    val: v,
                    fwd: None,
                    bwd: None
                }
            }
        }

        impl<T> DList<T> {
            fn new() -> Self {
                DList { head: None, tail: None }
            }

            fn push_front(&mut self, val: T) {
                let new_head = Rc::new(RefCell::new(Node::new(val)));
                match self.head.take() {
                    None => {
                        self.tail = Some(Rc::clone(&new_head));
                        self.head = Some(new_head);
                    },
                    Some(old_head) => {
                        {
                            let mut n: RefMut<Node<T>> = new_head.borrow_mut();
                            (*n).fwd = Some(Rc::clone(&old_head));
                            // We have to let n get dropped otherwise Rust doesn't let someone else own new_head in 
                            // self.head = Some(new_head);
                        }
                        let mut o: RefMut<Node<T>> = old_head.borrow_mut();
                        (*o).bwd = Some(Rc::downgrade(&new_head));
                        self.head = Some(new_head);
                    }
                }
            }

            fn update_head(&mut self, new_val: T) {
                match self.head.take() {
                    None => { 
                        panic!("can not update head of an empty list"); 
                    }
                    Some(old_head) => {
                        {
                            let mut n = old_head.borrow_mut();
                            (*n).val = new_val;
                        }
                        self.head = Some(old_head);
                    }
                }
            }
        }

        {
            println!("Doubly linked list weak");
            let mut l = DList::new();
            for i in 1..10 {
                l.push_front(DropMe(i));
            }
            // Memory not leaked this time!
        }
        println!("Doubly linked list weak ends");
    }

    {
        use std::rc::{Rc, Weak};

        println!("Null weak references");
        let r1;
        {
            let r = Rc::new(DropMe(1));
            r1 = Rc::clone(&r);
        }
        // r1 is guaranteed to be non-null
        println!("r1 points to {:?}", r1);


        // Weak references may be dropped
        let w;
        {
            let r = Rc::new(DropMe(1));
            w = Rc::downgrade(&r);
        }
        // Uncommenting this line causes a crash
        // println!("w points to {:?}", w.upgrade().unwrap());
    }
}
