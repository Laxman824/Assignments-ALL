use std::env;
use std::fmt;
//use std::fs;
//use std::io::stdin;
use std::fs::File;
//use std::io::prelude::*;
use std::io::{self, BufRead};
use std::path::Path;

// #[derive(Debug)]
struct State {
	pc: usize,
	accum: usize,
	mbox: [usize; 100],
	neg_flag: bool,
	reg: [usize; 6],
}

fn read_lines<P>(filename: P) -> io::Result<io::Lines<io::BufReader<File>>>
where P: AsRef<Path>, {
    let file = File::open(filename)?;
    Ok(io::BufReader::new(file).lines())
}

impl fmt::Display for State {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        writeln!(f, "PC: {}, accum: {}, neg_flag: {}, reg: {:?}, mbox: ", self.pc, self.accum, self.neg_flag, self.reg)?;
	for i in 0..10 {
		let l = i*10;
		for j in l..l+10 {
			write!(f, "{}:{}\t", j, self.mbox[j]);
		}
		writeln!(f, "");
	}
	Ok(())
    }
}

fn load(file_path: &String) -> State {

	let mut s = State {
		pc: 0,
		accum: 0,
		mbox: [0; 100],
		neg_flag: false,
		reg: [0; 6],
	};
	let mut count = 0;
	// file ni line by line read  chesinavani instruncitons ni mbox lo store
	
	if let Ok(lines) = read_lines(file_path) {
		for line in lines {
			if let Ok(ip) = line {
				s.mbox[count] = ip.parse().unwrap();
				count+=1;
				
			}
		}
	}



	return s;
	
}

/* Returns if the program has finished */
fn run(state: &mut State) -> bool {

	while state.pc < 100 {
//	write the of conditions here 
	println!("PC is {}", state.pc);
	println!("pc is {}", state.mbox[state.pc]);
	println!("{}", state);
	if state.mbox[state.pc] >= 1900 && state.mbox[state.pc] <= 1999 {
		// println!("Hello");
		let xx = state.mbox[state.pc] - 1900;
		state.accum = state.accum + state.mbox[xx];
		state.neg_flag = false;
		state.pc += 1;
	}
	else if state.mbox[state.pc] >= 1000 && state.mbox[state.pc] <= 1500 {

		let mut n = state.mbox[state.pc] - 1000;
		n = n/100;
		for i in 0..n {
			state.accum = state.accum + state.reg[i];
		}
		state.neg_flag = false;
		state.pc += 1;
	} else if state.mbox[state.pc] >= 2900 && state.mbox[state.pc] <= 2999 {
		let xx = state.mbox[state.pc] - 2900;
		let  b = state.mbox[xx] as i32 -state.accum as i32;
		
		if b < 0 {
			state.accum = 0;
			state.neg_flag =true;
		}
		else {
			state.accum = b as usize;
		}
		state.pc += 1;
	}
	else if state.mbox[state.pc] >= 2000 && state.mbox[state.pc] <= 2500{
		let mut n = state.mbox[state.pc] - 1000;
		n = n/100;
		for i in 0..n {
			state.accum = state.accum - state.reg[i];
		}

		if state.accum < 0{
			state.accum = 0;
			state.neg_flag = true;
		}
		state.pc += 1;
	}
	else if state.mbox[state.pc] >= 3000 && state.mbox[state.pc] <= 3099 {
		let xx = state.mbox[state.pc] - 3000;
		state.mbox[xx] = state.accum;
		state.pc += 1;
	}
	else if state.mbox[state.pc] >= 5900 && state.mbox[state.pc] <= 5999 {
		let xx = state.mbox[state.pc] - 5900;
		state.accum = state.mbox[xx];
		state.pc += 1;
	}
	else if state.mbox[state.pc] >= 5000 && state.mbox[state.pc] <= 5599 {
		let nxx:i32 = state.mbox[state.pc] as i32 - 5000;
		let n:i32 = nxx/100;
		let xx = nxx - n*100;
		for i in 0..n {
			state.reg[i as usize] = state.mbox[(xx+i) as usize];
		}
		state.pc += 1;
	}
	
	else if state.mbox[state.pc] >= 6000 && state.mbox[state.pc] <= 6099 	{
		let xx = state.mbox[state.pc] - 6000;
		state.pc = state.mbox[xx];
	}
	else if  state.mbox[state.pc] >= 7000 && state.mbox[state.pc] <= 7099 {
		if state.accum == 0 {
			let xx = state.mbox[state.pc] - 7000;
			state.pc = xx;
		}
		else {
			state.pc += 1;
		}
	}
	else if state.mbox[state.pc] >=7100 &&  state.mbox[state.pc] <= 7199 {
		if state.neg_flag == false {
			let xx = state.mbox[state.pc] - 7100;
			state.pc = xx;
		}
		else {
			state.pc += 1;
		}
	}
	else if  state.mbox[state.pc] == 9001 {
		let mut line = String::new();
		let b1 = std::io::stdin().read_line(&mut line).unwrap();
		state.accum = b1;
		state.pc+= 1;
	}
	else if  state.mbox[state.pc] == 9002 {
		// println!("{}", state.accum);
		state.pc += 1;
	}
	else if state.mbox[state.pc]== 9003 {
		println!("{}", state);
		state.pc += 1;
	}
	else if	state.mbox[state.pc] == 0 {
		return true;
	}	
	}
	//if else *****************************
	// conditions 
	// other case pc should not increment like 60x00,70xx,71xx
	return false;
}
 
fn main() -> Result<(), String> {
	let args: Vec<String> = env::args().collect();
	let file_path = args.get(1).ok_or("Required file path")?;

	// Load the program
	let mut state = load(file_path);
	// println!("{}", state.mbox.len());
	

		
	// Run the program
	run(&mut state);
	// println!("{}", state);
	Ok(())
}