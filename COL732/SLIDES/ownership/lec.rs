use std::fmt;

fn main() {
	/* Ownership rules: 
	 * every resource has an owner 
	 * resource always has a single owner
	 * when owner goes out of scope, resource is dropped automatically
	 */
	{
		// #[derive(fmt::Debug)]
		struct DropMe;
		impl Drop for DropMe {
			fn drop(&mut self) {
				println!("Dropping!");
			}
		}
		
		{
			println!("Inner scope begins");
			let x = DropMe;
			println!("Inner scope ends");
		}
		println!("Outer scope ends");
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
		/* Discards x when creating y
		 *  x
		 * ┌───────────┐
		 * │ name      │   ┌──►┌─┬─┐
		 * │           │   │   │0│H│
		 * │ ptr  ─────┼───┼──►│1│I│
		 * │ len       │   │   └─┴─┘
		 * │ capacity  │   │
		 * └───────────┘   │
		 *                 │
		 *  y              │
		 * ┌───────────┐   │
		 * │ name      │   │
		 * │           │   │
		 * │ ptr  ─────┼───┘
		 * │ len       │
		 * │ capacity  │
		 * └───────────┘
		 */
		// Move
		let x = String::from("HI");
		let y = x;
		println!("y: {}", y);
		// println!("x: {}, y: {}", x, y);
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

	{
		// Copy trait
		let x = 5;
		let y = x;
		println!("x: {}, y: {}", x, y);
	}

	{
		// Copy trait across functions
		// let x = 5;
		// let id = |x| x;
		// id(x);
		// println!("{}", x);

		// Closure type was locked in to i32 -> i32
		// let x = String::from("foo");
		// id(x);
	
		// Passing String moves ownership
		// let x = String::from("foo");
		// let id = |x: String| x;
		// id(x);
		// println!("{}", x);
	}

	{
		// Can get ownership back from return value
		let x = String::from("foo");
		let id = |x: String| x;
		let x = id(x);
		println!("{}", x);

		let len = |x: String| (x.len(), x);
		let (l, x) = len(x);
		println!("string {} is of length {}", x, l);
	}

	{
		// Borrow using references
		let x = String::from("foo");
		let len = |x: &String| (x.len());
		let l = len(&x);
		println!("string {} is of length {}", x, l);
	}
	
	{
		// References are immutable
		// let x = String::from("foo");
		// let append = |x: &String| x.push_str(" bar");
		// append(&x);
		// println!("{}", x);
	}

	{
		// References can be mutable
		let mut x = String::from("foo");
		let append = |x: &mut String| x.push_str(" bar");
		append(&mut x);
		println!("{}", x);
	}

	{
		// Cannot have multiple mutable references
		let mut x = String::from("foo");
		let mut r1 = &mut x;
		// let mut r2 = &mut x;
		// println!("{} {}", r1, r2);
		println!("{}", r1);
	}
	
	{
		// Can have multiple immutable references
		let mut x = String::from("foo");
		let r1 = &x;
		let r2 = &x;
		println!("{} {}", r1, r2);

		// Followed by a mutable reference
		let r3 = &mut x;
		println!("{}", r3);
		// Uncommenting this line gives an error
		// println!("{} {}", r2, r3);
	}

	{
		// Dangling pointer
		// let dangle = || -> &String {let s = String::from("HI"); &s};
		// let x = dangle();
		// println!("{}", x);

		let no_dangle = || -> String {let s = String::from("HI"); s};
		let x = no_dangle();
		println!("{}", x);
	}

	/*
	Lifetime: 
	* Every reference has a lifetime
	* lifetime = scope for which that reference is valid 
	* inferred most of the time 
	* requires annotation when compiler can not automatically infer
	*/

	// {					// -
	// 	let r: &i32; 			// |
	// 	{				// |  -
	// 		let x = 5;		// |  b
	// 		r = &x;			// a  |
	// 	}				// |  -
	// 	println!("{}", r);		// |
	// }					// -
	// Errors because a is not contained in b

	
	{					// -
		let x = 5;			// |  
		let r = &x;		// a  -
						// |  b
		println!("{}", r);		// |  _
	}					// -

	{
		let x = String::from("short");
		let y = String::from("longer of the two");

		let longest = || {
			if x.len() > y.len() {
				return x;
			} 
			y
		};

		println!("{}", longest());
	}

	{
		let x = String::from("longer of the two");
		let s;
		{
			let y = String::from("short");
			s = longest(&x, &y);
			println!("longest fn {}", s);
		}

		// Compiler is not that smart to realize that longest 
		// will actually only return &x and never &z.

		// let s2: &str;
		// {
		// 	let z = String::from("s");
		// 	s2 = longest(&x, &z);
		// }
		// println!("longer z {}", s2);

		// let s = longest2(&x, &y);
		// println!("longest2 fn {}", s);

	}
}

fn longest<'a>(x: &'a str, y: &'a str) -> &'a str {
	if x.len() > y.len() {
		return x;
	}
	y
}

// fn longest2<'a>(x: &'a str, y: &str) -> &'a str {
// 	// if x.len() > y.len() {
// 	// 	return x;
// 	// }
// 	x
// }