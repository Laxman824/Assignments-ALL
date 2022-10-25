use std::env;

pub struct Vector {}

#[derive(Debug, PartialEq, Eq)]
pub struct Interval {
    pub start: i32,
    pub end: i32,
}

impl Interval {
    #[inline]
    pub fn new(start: i32, end: i32) -> Self {
        Interval { start, end }
    }
}

impl Vector {
    pub fn overlap(intervals: Vec<Interval>) -> Vec<Interval> {
        return Vec::new();
    }
}

fn main() {
    let args: Vec<String> = env::args().collect();
    let intervals = &args[1..];

    let mut input: Vec<Interval> = Vec::new();
    let mut temp: i32 = 0;

    for (pos, e) in intervals.iter().enumerate() {
        if let 1=pos%2{
            input.push(Interval::new(temp, e.parse::<i32>().unwrap()))
        }
        else {
            temp = e.parse::<i32>().unwrap();
        }
    }

    println!("{:?}", Vector::overlap(input));
}