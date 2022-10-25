use std::env::args;
use std::collections::HashMap;

pub struct Map {}

impl Map {
    pub fn is_isomorphic(s: String, t: String) -> bool {
        
        let sv: Vec<char> = s.chars().collect();
        let tv: Vec<char> = t.chars().collect();
        let mut my_map = HashMap::new();
        let n = s.len();
        if s.len() == t.len() {
            for i in 0..n {
                let c = sv[i];
                if my_map.get(&c) == None {
                    my_map.insert(char::from(c), tv[i]);
                }
                else if my_map.get(&c).unwrap() != &tv[i] {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}

fn main() {
    let s: String = args().nth(1).unwrap();
    let t: String = args().nth(2).unwrap();
    println!("{}", Map::is_isomorphic(s,t));
}