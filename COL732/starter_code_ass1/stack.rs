use std::env::args;
pub struct Stack {}

impl Stack {
    pub fn calculate(s: String) -> i32 {
        let mut operands_stack = vec![];
        let (mut val ,mut res, mut sign)= (0,0,1);
        operands_stack.push(1);
        for ch in s.chars() {
            match ch {
                '(' => {
                    operands_stack.push(sign);
                },
                ')' => {
                    operands_stack.pop();
                },
                '0'..='9'=> {
                    val = 10 * val + (ch as u8 - '0' as u8) as i32;
                },
                '+' => {
                    res += sign * val;
                    val = 0;
                    sign = *operands_stack.last().unwrap() ;
                },
                '-' => {
                    res += sign * val;
                    val = 0;
                    sign = -1*operands_stack.last().unwrap() ;
                },
                _ => {},
            }
        }
        res + sign * val
    }
}

fn main() {
    let args: Vec<String> = args().collect();
    let expr: String = (&args[1..]).join(" ");

    println!("{:?}", Stack::calculate(expr));
}