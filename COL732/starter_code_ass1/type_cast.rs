use std::env::args;
pub struct Typecast {}

impl Typecast {
    pub fn product(num1: String, num2: String) -> String {
        let m1 = num1.parse::<i32>().unwrap();
        let m2 = num2.parse::<i32>().unwrap();
        let m3 = m1*m2;
        return m3.to_string();
    }
}

fn main() {
    let s: String = args().nth(1).unwrap();
    let t: String = args().nth(2).unwrap();
    println!("{}", Typecast::product(s,t));
}