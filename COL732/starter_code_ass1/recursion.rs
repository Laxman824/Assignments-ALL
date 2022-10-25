use std::env::args;

pub struct Recursion {}

impl Recursion {

    pub fn power_set_helper(nums: Vec<i32>, index: usize) -> Vec<Vec<i32>> {
        if index == nums.len() {
            let r: Vec<i32> = Vec::new();
            let mut r2: Vec<Vec<i32>> = Vec::new();
            r2.push(r);
            return r2;
        }
        let a = nums[index];
        let b:Vec<Vec<i32>> = Self::power_set_helper(nums, index+1);
        let mut ret: Vec<Vec<i32>> = Vec::new();
      //  let len  : i32= b.len() as usize;
        for i in &b{
            ret.push(i.to_vec());
        }
        for i in &b {
            let mut n: Vec<i32> = Vec::new();
            n.push(a);
            for j in i {
                n.push(*j);
            }
            ret.push(n);
        }
        return ret;
    }

    pub fn power_set(nums: Vec<i32>) -> Vec<Vec<i32>> {
        return Self::power_set_helper(nums, 0);
    }
}

fn main() {
    let mut args: Vec<String> = args().collect();
    let mut set: Vec<i32> = Vec::new();

    for i in &mut args[1..] {
        set.push(i.parse::<i32>().unwrap())
    }

    println!("{:?}", Recursion::power_set(set));
}