// main
fn main() {
    let mut ins = Instant::now();
    let file = File::options().append(true).open("test.txt").unwrap();
    let time = ins.elapsed();

    //write time to file
    let mut file = BufWriter::new(file);
    file.write_all(format!("{:?}", time).as_bytes()).unwrap();
    
}