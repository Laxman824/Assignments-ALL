// rustc lec3.rs && ./lec3
use std::thread;
use std::time::Duration;

static SZ: u32 = 1_000_000;

fn main() {
    // {
    //     // Process ends when main thread ends.
    //     let handle = thread::spawn(|| {
    //         for i in 1..10 {
    //             println!("hi number {} from the spawned thread!", i);
    //             thread::sleep(Duration::from_millis(1));
    //         }
    //     });

    //     for i in 1..5 {
    //         println!("hi number {} from the main thread!", i);
    //         thread::sleep(Duration::from_millis(1));
    //     }

    //     handle.join().unwrap();
    // }

    // {
    //     let c = 0;

    //     let handle = thread::spawn(|| {
    //         // c += 1;
    //         println!("{}", &c);
    //     });

    //     handle.join().unwrap();
    // }
    
    // {
    //     let mut c = 0;

    //     let handle = thread::spawn(move || {
    //         c += 1;
    //         println!("{}", &c);
    //     });

    //     handle.join().unwrap();
    // }

    // {
    //     let mut c = 0;

    //     let handle = thread::spawn(move || {
    //         c += 1;
    //         println!("{}", c);
    //         // println!("{:p}", &c);
    //     });

    //     handle.join().unwrap();
    //     println!("{}", c);
    //     // println!("{:p}", &c);
    // }

    // {
    //     let mut s = String::from("Hello ");

    //     let handle = thread::spawn(move || {
    //         println!("{}", s);
    //     });

    //     handle.join().unwrap();
    //     println!("{}", s);
    // }

    // {
    //     use std::rc::Rc;
    //     let mut rc = Rc::new(String::from("Hello "));

    //     let mut rc2 = Rc::clone(&rc);
    //     let handle = thread::spawn(move || {
    //         let s = rc2.as_ref();
    //         let rc3 = Rc::clone(&rc2);
    //         println!("{}", s);
    //     });

    //     handle.join().unwrap();
    //     let s = rc.as_ref();
    //     println!("{}", s);
    //     let rc4 = Rc::clone(&rc);
    // }

    // {
    //     use std::sync::Arc;
    //     let rc = Arc::new(String::from("Hello "));

    //     let rc2 = Arc::clone(&rc);
    //     let handle = thread::spawn(move || {
    //         let s = rc2.as_ref();
    //         // let rc3 = Arc::clone(&rc2);
    //         println!("{}", s);
    //     });

    //     handle.join().unwrap();
    //     let s = rc.as_ref();
    //     println!("{}", s);
    //     // let rc4 = Arc::clone(&rc);
    // }
    
    // {
    //     use std::sync::Arc;
    //     use std::cell::RefCell;
    //     let mut rc = Arc::new(RefCell::new(String::from("Hello ")));

    //     let mut rc2 = Arc::clone(&rc);
    //     let handle = thread::spawn(move || {
    //         let s = rc2.borrow_mut();
    //         s.push_str("world");
    //         println!("{}", s);
    //     });

    //     // let s = rc.borrow();
    //     // println!("{}", s);

    //     handle.join().unwrap();
    // }

    // {
    //     use std::sync::Arc;
    //     use std::sync::Mutex;
    //     let rc = Arc::new(Mutex::new(String::from("Hello ")));

    //     let rc2 = Arc::clone(&rc);
    //     let handle = thread::spawn(move || {
    //         let s = &mut *(rc2.lock().unwrap());
    //         s.push_str("world");
    //         println!("{}", s);
    //     });

    //     {
    //         let s = &*(rc.lock().unwrap());
    //         println!("{}", s);
    //     }
    //     handle.join().unwrap();

    //     // let s = &*(rc.lock().unwrap());
    //     // println!("{}", s);
    // }

    // {
    //     use std::sync::mpsc;

    //     let (tx, rx) = mpsc::channel();

    //     thread::spawn(move || {
    //         let val = String::from("hi");
    //         tx.send(val).unwrap();
    //         // println!("val is {}", val);
    //     });

    //     let received = rx.recv().unwrap();
    //     println!("Got: {}", received);
    // }


    // {
    //     use std::sync::mpsc;
    //     let (tx, rx) = mpsc::channel();
    //     thread::spawn(move || {
    //         let vals = vec![
    //             String::from("hi"),
    //             String::from("from"),
    //             String::from("the"),
    //             String::from("thread"),
    //         ];

    //         for val in vals {
    //             tx.send(val).unwrap();
    //             thread::sleep(Duration::from_secs(1));
    //         }
    //     });

    //     for received in rx {
    //         println!("Got: {}", received);
    //     }
    // }

    // {
    //     use std::sync::mpsc;
    //     let (tx, rx) = mpsc::channel();

    //     let tx2 = tx.clone();
    //     thread::spawn(move || {
    //         let vals = vec![
    //             String::from("hi"),
    //             String::from("from"),
    //             String::from("the"),
    //             String::from("thread"),
    //         ];

    //         for val in vals {
    //             tx.send(val).unwrap();
    //             thread::sleep(Duration::from_secs(1));
    //         }
    //     });
        
    //     thread::spawn(move || {
    //         let vals = vec![
    //             String::from("more"),
    //             String::from("messages"),
    //             String::from("for"),
    //             String::from("you"),
    //         ];

    //         for val in vals {
    //             tx2.send(val).unwrap();
    //             thread::sleep(Duration::from_secs(1));
    //         }
    //     });

    //     for received in rx {
    //         println!("Got: {}", received);
    //     }
    // }

}
